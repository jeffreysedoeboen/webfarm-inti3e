/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:43:43
 */
package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.inti3e.database.CreateDBTables;
import com.inti3e.database.dao.DoorDao;
import com.inti3e.database.dao.HumidityDao;
import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.database.dao.SwitchDao;
import com.inti3e.database.dao.MovementDao;
import com.inti3e.database.dao.TempDao;
import com.inti3e.model.Door;
import com.inti3e.model.Humidity;
import com.inti3e.model.LightSensor;
import com.inti3e.model.LightSwitch;
import com.inti3e.model.Movement;
import com.inti3e.model.Temperature;

/**
 * The Class TestDatabase.
 */
public class TestDatabase {
	
	/** The create tables. */
	CreateDBTables createTables = new CreateDBTables();
	
	/**
	 * Test temp dao.
	 */
	@Test
	public void testTempDao() {
		TempDao td = new TempDao();
		td.addNewTemp("80");
		ArrayList<Temperature> temps = td.getTempsBetweenDates("01-01-01", "12:00:00", "30-12-99", "12:00:00");
		for(Temperature temp: temps) {
			System.out.println(temp.getTemp());
		}
		assertEquals("80",temps.get(temps.size() -1).getTemp());
	}
	
	/**
	 * Test humidity dao.
	 */
	@Test
	public void testHumidityDao() {
		HumidityDao td = new HumidityDao();
		td.addNewHumidity(12);
		ArrayList<Humidity> humidyArray = td.getHumiditiesBetweenDates("01-01-01", "12:00:00", "30-12-99", "12:00:00");
		for(Humidity humidity: humidyArray) {
			System.out.println(humidity.getHumidity());
		}
		assertEquals(12,humidyArray.get(humidyArray.size() -1).getHumidity());
	}
	
	/**
	 * Test movement dao.
	 */
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
	
	/**
	 * Test light switch dao.
	 */
	@Test
	public void testLightSwitchDao() {
		SwitchDao td = new SwitchDao();
		td.addNewLightSwitch(false);
		ArrayList<LightSwitch> lightSwitches = td.getAllLightSwitches();
		for(LightSwitch lightSwitch: lightSwitches) {
			System.out.println(lightSwitch.getLight());
		}
		assertEquals("0",lightSwitches.get(lightSwitches.size() -1).getLight());
	}
	
	/**
	 * Test light sensor.
	 */
	@Test
	public void testLightSensor() {
		LightSensorDao td = new LightSensorDao();
		td.addNewLight(true);
		ArrayList<LightSensor> lightSensors = td.getLightsBetweenDates("01-01-01", "12:00:00", "30-12-99", "12:00:00");
		for(LightSensor lightSensor: lightSensors) {
			System.out.println(lightSensor.getLight());
		}
		assertEquals("1",lightSensors.get(lightSensors.size() -1).getLight());
	}
	
	/**
	 * Test door dao.
	 */
	@Test
	public void testDoorDao() {
		DoorDao td = new DoorDao();
		td.addNewDoor(true);
		ArrayList<Door> doors = td.getDoorsBetweenDates("01-01-01", "12:00:00", "30-12-99", "12:00:00");
		for(Door door: doors) {
			System.out.println(door.getDoor());
		}
		assertEquals(1,doors.get(doors.size() -1).getDoor());
	}

}
