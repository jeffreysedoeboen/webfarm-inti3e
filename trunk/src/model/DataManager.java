package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private boolean connOpen;

	public DataManager() {

		try {
			welcomeSocket = new ServerSocket(4000);
			socket = welcomeSocket.accept();
			connOpen = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

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
					switch (in) {
					case ('d'):
						boolean door = Boolean.parseBoolean(value);
						DoorDao dd = new DoorDao();
						dd.addNewDoor(door);

						break;
					case ('b'):
						boolean movement = Boolean.parseBoolean(value);
						MovementDao md = new MovementDao();
						md.addNewMovement(!movement); // sensor geeft 1 als er geen actie is, 0 als er wel actie is.
						break;
					case ('s'):
						boolean lightswitch = Boolean.parseBoolean(value);
						LightSwitchDao lsd = new LightSwitchDao();
						lsd.addNewLightSwitch(lightswitch);
						break;
					case ('l'):
						boolean lightsensor = Boolean.parseBoolean(value);
						LightSensorDao ld = new LightSensorDao();
						ld.addNewLight(lightsensor);
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
		}

	}

}
