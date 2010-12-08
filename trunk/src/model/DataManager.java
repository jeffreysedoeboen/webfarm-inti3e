package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.InputStream;

import database.DoorDao;
import database.HumidityDao;
import database.LightSensorDao;
import database.LightSwitchDao;
import database.MovementDao;
import database.TempDao;

public class DataManager {
	private ServerSocket welcomeSocket = null;
	private ObjectInputStream inputstream = null;
	private Socket socket;
	
	public DataManager(){
		
		try {
			welcomeSocket = new ServerSocket();
			socket = welcomeSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void read(){
		try {
			inputstream = new ObjectInputStream(socket.getInputStream());
		
		while(true){
			String inputData = (String) inputstream.readObject();
			String[] splittedData = inputData.split(":");
			for (String s : splittedData){
				char indentifier = s.charAt(0);
				String value = s.substring(1);
				switch (indentifier){
				case ('d'): 
							boolean door =Boolean.parseBoolean(value);
							DoorDao dd = new DoorDao();
							dd.addNewDoor(door);
							break;
				case ('b'): 
							boolean movement = Boolean.parseBoolean(value);
							MovementDao md = new MovementDao();
							md.addNewMovement(movement);
							break;
				case ('s'):
							boolean lightswitch = Boolean.parseBoolean(value);
							LightSwitchDao lsd = new LightSwitchDao();
							lsd.addNewLightSwitch(lightswitch);
							break;
				case ('l'):
							boolean lightsensor = Boolean.parseBoolean(value);
							LightSensorDao ld = new LightSensorDao();
							ld.addNewLight( lightsensor);
							break;
				case ('t'):
							String temperature = value;
							TempDao td = new TempDao();
							td.addNewTemp(temperature);
							break;
				case ('v'):
							int humidity = Integer.parseInt(value);
							HumidityDao hd = new HumidityDao();
							hd.addNewHumidity(humidity);
							break;
				}	
			}
			
		}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
	}

}
