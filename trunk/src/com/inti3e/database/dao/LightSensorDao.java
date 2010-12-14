package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.inti3e.model.LightSensor;

import com.inti3e.database.DBmanager;


public class LightSensorDao {


	private String sqlGetAllLights		= "SELECT date, time, light FROM APP.LIGHTSENSOR";
	private String sqlNewLight 			= "INSERT INTO APP.LIGHTSENSOR (\"DATE\", \"TIME\", \"LIGHT\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllLights = null ;
	private PreparedStatement psNewLight = null;


	public LightSensorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllLights   = con.prepareStatement(sqlGetAllLights);
			this.psNewLight		 = con.prepareStatement(sqlNewLight);

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}

	public void createTable(){

		try {
			//get the table listing
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, null,  new String[] {"TABLE"});
			ArrayList<String> tableList = new ArrayList<String>();
			while(rs.next()){
				tableList.add(rs.getString("TABLE_NAME"));
			}
			//check if the table does not already exists and then create them if needed
			if(!tableList.contains("LIGHTSENSOR")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.LIGHTSENSOR (" +
						"date DATE," +
						"time VARCHAR(50)," +
						"light VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getLightOn() {
		ArrayList<LightSensor> lightMessures = getAllLights();
		LightSensor s = lightMessures.get(lightMessures.size()-1);
		if(s.getLight().equals("true")) {
			return true;
		} else {
			return false;
		}
		
	}

	public ArrayList<LightSensor> getAllLights(){
		ArrayList<LightSensor> lightSensors = new ArrayList<LightSensor>();
		try {
			ResultSet rs = psGetAllLights.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				String light = rs.getString(3);
				LightSensor lightSensor = new LightSensor(date, time, light);
				lightSensors.add(lightSensor);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return lightSensors;
	}

	public void addNewLight(boolean light) {
		try {
			Calendar calendar = Calendar.getInstance();
			psNewLight.clearParameters();
			psNewLight.setDate(1, new java.sql.Date(new java.util.Date().getTime()));			
			psNewLight.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
			psNewLight.setInt(3, light ? 1:0);
			psNewLight.executeUpdate();
		} catch (SQLException se) {
			printSQLException(se) ;
		}
	}

	private void printSQLException(SQLException se) {
		while(se != null) {

			System.out.print("SQLException: State:   " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());			

			se = se.getNextException();
		}
	}

	
	

}

