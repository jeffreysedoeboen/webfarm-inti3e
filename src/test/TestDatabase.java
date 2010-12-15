package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;

import com.inti3e.database.dao.DoorDao;
import com.inti3e.database.dao.HumidityDao;
import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.database.dao.LightSwitchDao;
import com.inti3e.database.dao.MovementDao;
import com.inti3e.database.dao.TempDao;
import com.inti3e.model.Door;
import com.inti3e.model.Humidity;
import com.inti3e.model.LightSensor;
import com.inti3e.model.LightSwitch;
import com.inti3e.model.Movement;
import com.inti3e.model.Temperature;

public class TestDatabase {
	
	@Test
	public void testTempDao() {
		TempDao td = new TempDao();
		td.addNewTemp("80");
		ArrayList<Temperature> temps = td.getAllTemps();
		for(Temperature temp: temps) {
			System.out.println(temp.getTemp());
		}
		assertEquals("80",temps.get(temps.size() -1).getTemp());
	}
	
	@Test
	public void testHumidityDao() {
		HumidityDao td = new HumidityDao();
		td.addNewHumidity(12);
		ArrayList<Humidity> humidyArray = td.getAllHumids();
		for(Humidity humidity: humidyArray) {
			System.out.println(humidity.getHumidity());
		}
		assertEquals(12,humidyArray.get(humidyArray.size() -1).getHumidity());
	}
	
	@Test
	public void testMovementDao() {
		MovementDao td = new MovementDao();
		td.addNewMovement(true);
		ArrayList<Movement> movements = td.getAllMovements();
		for(Movement movement: movements) {
			System.out.println(movement.getMove());
		}
		assertEquals("1",movements.get(movements.size() -1).getMove());
	}
	
	@Test
	public void testLightSwitchDao() {
		LightSwitchDao td = new LightSwitchDao();
		td.addNewLightSwitch(false);
		ArrayList<LightSwitch> lightSwitches = td.getAllLightSwitches();
		for(LightSwitch lightSwitch: lightSwitches) {
			System.out.println(lightSwitch.getLight());
		}
		assertEquals("0",lightSwitches.get(lightSwitches.size() -1).getLight());
	}
	
	@Test
	public void testLightSensor() {
		LightSensorDao td = new LightSensorDao();
		td.addNewLight(true);
		ArrayList<LightSensor> lightSensors = td.getAllLights();
		for(LightSensor lightSensor: lightSensors) {
			System.out.println(lightSensor.getLight());
		}
		assertEquals("1",lightSensors.get(lightSensors.size() -1).getLight());
	}
	
	@Test
	public void testDoorDao() {
		DoorDao td = new DoorDao();
		td.addNewDoor(true);
		ArrayList<Door> doors = td.getAllDoors();
		for(Door door: doors) {
			System.out.println(door.getDoor());
		}
		assertEquals("1",doors.get(doors.size() -1).getDoor());
	}

}
