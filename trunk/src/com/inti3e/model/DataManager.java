/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:38:37
 */
package com.inti3e.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.inti3e.database.dao.DoorDao;
import com.inti3e.database.dao.HumidityDao;
import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.database.dao.SwitchDao;
import com.inti3e.database.dao.MovementDao;
import com.inti3e.database.dao.TempDao;
import com.inti3e.model.webcam.RecordManager;

/**
 * The Class DataManager.
 */
public class DataManager extends Thread {
	
	/** The unique instance. */
	private static DataManager uniqueInstance = null;
	
	/** The welcome socket. */
	private ServerSocket welcomeSocket = null;
	
	/** The socket. */
	private Socket socket;
	
	/** The out put. */
	private int outPut = 0;
	
	/** The Record manager. */
	private RecordManager movementManager;
	
	/** The dd. */
	private DoorDao dd;
	
	/** The lsd. */
	private SwitchDao lsd;
	
	/** The md. */
	private MovementDao md;
	
	/** The ld. */
	private LightSensorDao ld;
	
	/** The hd. */
	private HumidityDao hd;
	
	/** The td. */
	private TempDao td;

	/**
	 * Instantiates a new data manager.
	 */
	private DataManager() {
		dd = new DoorDao();
		lsd = new SwitchDao();
		md = new MovementDao();
		ld = new LightSensorDao();
		hd = new HumidityDao();
		td = new TempDao();
		
		
		movementManager = RecordManager.getInstance();
		socket = null;
	}
	
	/**
	 * Gets the single instance of DataManager.
	 *
	 * @return single instance of DataManager
	 */
	public static synchronized DataManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DataManager();
		}
		return uniqueInstance;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			welcomeSocket = new ServerSocket(4000);
			while (!welcomeSocket.isClosed()) {
				socket = welcomeSocket.accept();
				System.out.println("Socket created");
				read();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shutdown.
	 */
	public void shutdown() {
		try {
			welcomeSocket.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Turn light.
	 *
	 * @param light the light
	 */
	public synchronized void turnLight(boolean light) {
		assert(socket != null);
		try {
			OutputStream out = socket.getOutputStream();
			if(light) {
				outPut = 1;
			} else {
				outPut = 0;
			}
			System.out.println("Switching light to: "+light);
			out.write('L');
			out.write(outPut + 48); //ASCI
			out.write(':');
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read.
	 */
	private void read() {
		char in = 'p';
		try {
			while ((in = (char) socket.getInputStream().read()) != '!') {
				char input = 'k';
				String value = "";
				while ((input = (char) socket.getInputStream().read()) != ':') {
					value += String.valueOf(input);
				}
				System.out.println(in+" : "+value);
				switch (in) {
					case ('D'):
						boolean door;
						if(value.equals("1")) {
							door = true;
						} else {
							door = false;
						}
						dd.addNewDoor(door);
						break;
					case ('B'):

						boolean movement;
						if(value.equals("1")) {
							movement = true;
							movementManager.startRecording();
						} else {
							movement = false;
							movementManager.stopRecording();
						}
						md.addNewMovement(movement);
						break;

					case ('S'):
						boolean manualSwitch;
						if(value.equals("1")) {
							manualSwitch = true;
						} else {
							manualSwitch = false;
						}
						lsd.addNewLightSwitch(manualSwitch);
						break;
					case ('L'):
						boolean lightSensor;
						if(value.equals("1")) {
							lightSensor = true;
						} else {
							lightSensor = false;
						}
						ld.addNewLight(lightSensor);
						break;
					case ('T'):
						td.addNewTemp(value);
						break;
					case ('H'):
						hd.addNewHumidity(Integer.parseInt(value));
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
