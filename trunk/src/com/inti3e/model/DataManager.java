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

public class DataManager extends Thread {
	private static DataManager uniqueInstance = null;
	
	private ServerSocket welcomeSocket = null;
	private Socket socket;
	private Boolean switchLight;
	private int outPut = 0;

	private DataManager() {
		socket = null;
		switchLight = false;
	}
	
	public static synchronized DataManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DataManager();
		}
		return uniqueInstance;
	}
	
	public void run() {
		try {
			welcomeSocket = new ServerSocket(4000);
			socket = welcomeSocket.accept();
			System.out.println("Socket created");
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void turnLight(boolean light) {
		assert(socket != null);
		try {
			OutputStream out = socket.getOutputStream();
			if(light) {
				outPut = 1;
			} else {
				outPut = 0;
			}
			System.out.println(outPut);
			out.write('L');
			out.write(outPut + 48); //ASCI
			out.write(':');
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read() {
		System.out.println("start");
		char in = 'p';
		try {
			while ((in = (char) socket.getInputStream().read()) != '!') {
				System.out.println(in);
				char input = 'k';
				String value = "";
				while ((input = (char) socket.getInputStream().read()) != ':') {
					value += String.valueOf(input);
				}
				System.out.println(value);
				switch (in) {
					case ('D'):
						System.out.println("Deur");
					boolean door;
					if(value.equals("1")) {
						door = true;
					} else {
						door = false;
					}
					DoorDao dd = new DoorDao();
					dd.addNewDoor(door);

					break;
					case ('B'):
						System.out.println("Movement");
					boolean movement;
					if(value.equals("1")) {
						movement = true;
					} else {
						movement = false;
					}
					System.out.println(movement);
					MovementDao md = new MovementDao();
					md.addNewMovement(movement);
					break;
					case ('S'):
						System.out.println("Switch");
						boolean manualSwitch;
						if(value.equals("1")) {
							manualSwitch = true;
						} else {
							manualSwitch = false;
						}
						SwitchDao lsd = new SwitchDao();
						lsd.addNewLightSwitch(manualSwitch);
					break;
					case ('L'):
						System.out.println("Lightsensor");
						boolean lightSensor;
						if(value.equals("1")) {
							lightSensor = true;
						} else {
							lightSensor = false;
						}
						LightSensorDao ld = new LightSensorDao();
						ld.addNewLight(lightSensor);
					break;
					case ('T'):
						TempDao td = new TempDao();
						td.addNewTemp(value);
					break;
					case ('H'):
						HumidityDao hd = new HumidityDao();
						hd.addNewHumidity(Integer.parseInt(value));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
