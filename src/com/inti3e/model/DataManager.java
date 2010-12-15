package com.inti3e.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.inti3e.database.dao.DoorDao;
import com.inti3e.database.dao.HumidityDao;
import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.database.dao.LightSwitchDao;
import com.inti3e.database.dao.MovementDao;
import com.inti3e.database.dao.TempDao;


public class DataManager {
	private ServerSocket welcomeSocket = null;
	private Socket socket;
	private boolean switchLight;
	private int outPut = 0;

	public DataManager() {

		try {
			welcomeSocket = new ServerSocket(4000);
			socket = welcomeSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setSwitchLight(boolean light) {
		switchLight = light;
	}

	public void read() {
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
					LightSwitchDao lsd = new LightSwitchDao();
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
					System.out.println("Temperature");
					String temperature = value;
					TempDao td = new TempDao();
					td.addNewTemp(temperature);
					break;
				case ('H'):
					System.out.println("Luchtvochtigheid");
					int humidity = Integer.parseInt(value);
					HumidityDao hd = new HumidityDao();
					hd.addNewHumidity(humidity);
					break;
				}
				if(switchLight) {
					OutputStream out = socket.getOutputStream();
					System.out.println("SwitchLight = " + switchLight);
					if(outPut == 1) {
						outPut = 0;
					} else {
						outPut = 1;
					}
					System.out.println(outPut);
					out.write('L');
					out.write(outPut + 48);//ASCI
					out.write(':');
					switchLight = false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
