/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:21:55
 */
package com.inti3e.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class CreateDBTables.
 */
public class CreateDBTables {
	//Attributen
	/** The connection. */
	private Connection conn;
	
	//Constructor
	/**
	 * Instantiates a new creates the db tables.
	 */
	public CreateDBTables(){
		DBmanager dbm = DBmanager.getInstance();
		conn = dbm.getConnection();
		try {
			createTablesIfNotExists();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Methoden
	/**
	 * Creates the tables if they do not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createTablesIfNotExists() throws SQLException {
		createDoorTableIfNotExists();
		createHumidityTableIfNotExists();
		createLightsensorTableIfNotExists();
		createMovementTableIfNotExists();
		createLightswitchTableIfNotExists();
		createTemperatureTableIfNotExists();
		createUsersTableIfNotExists();
	}
	
	/**
	 * Creates the door table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createDoorTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("DOOR")) {
			System.err.println("Table APP.DOOR not yet created, it will be created.");
			
			//Create table APP.DOOR
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.DOOR (" +
					"DATE DATE," +
					"TIME VARCHAR(50)," +
					"DOOR SMALLINT" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the humidity table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createHumidityTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("HUMIDITY")) {
			System.err.println("Table APP.HUMIDITY not yet created, it will be created.");
			
			//Create table APP.HUMIDITY
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.HUMIDITY (" +
					"DATE DATE," +
					"TIME VARCHAR(50)," +
					"HUMIDITY INTEGER" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the users table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createLightsensorTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("LIGHTSENSOR")) {
			System.err.println("Table APP.LIGHTSENSOR not yet created, it will be created.");
			
			//Create table APP.LIGHTSENSOR
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.LIGHTSENSOR (" +
					"date DATE," +
					"time VARCHAR(50)," +
					"light VARCHAR(25)" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the movement table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createMovementTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("MOVEMENT")) {
			System.err.println("Table APP.MOVEMENT not yet created, it will be created.");
			
			//Create table APP.MOVEMENT
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.MOVEMENT (" +
					"DATE DATE," +
					"TIME VARCHAR(50)," +
					"MOVEMENT VARCHAR(25)" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the light table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createLightswitchTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("LIGHTSWITCHES")) {
			System.err.println("Table APP.LIGHTSWITCHES not yet created, it will be created.");
			
			//Create table APP.LIGHTSWITCHES
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.LIGHTSWITCHES (" +
					"date DATE," +
					"time VARCHAR(50)," +
					"LightSwitch VARCHAR(25)" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the temperature table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createTemperatureTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("TEMP")) {
			System.err.println("Table APP.TEMP not yet created, it will be created.");
			
			//Create table APP.TEMP
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.Temp (" +
					"DATE DATE," +
					"TIME VARCHAR(50)," +
					"TEMP VARCHAR(25)" +
			")");
			stat.close();
		}
	}
	
	/**
	 * Creates the users table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createUsersTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("USERS")) {
			System.err.println("Table APP.USERS not yet created, it will be created.");
			
			//Create table APP.USERS
			Statement stat = conn.createStatement();
			stat.execute("CREATE TABLE APP.USERS (" +
					"ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
					"NAME VARCHAR(255) UNIQUE NOT NULL," +
					"PASSWORD VARCHAR(50) NOT NULL" +
					")");
			stat.close();
		}
	}
	
	/**
	 * Check if table exists.
	 *
	 * @param name the name
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 */
	private boolean checkIfTableExists(String name) throws SQLException {
		ResultSet tableNames = conn.getMetaData().getTables( null, null, name, new String[] {"TABLE"});
		boolean containsTable = false;
		while(tableNames.next()) {
			String tableName = tableNames.getString("TABLE_NAME");
			if (tableName.equals(name)) {
				containsTable = true;
			}
		}
		return containsTable;
	}
}