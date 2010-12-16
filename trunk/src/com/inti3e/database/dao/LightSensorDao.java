package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.inti3e.database.DBmanager;
import com.inti3e.model.LightSensor;


public class LightSensorDao {


	private String sqlGetAllLights		= "SELECT date, time, light FROM APP.LIGHTSENSOR";
	private String sqlNewLight 			= "INSERT INTO APP.LIGHTSENSOR (\"DATE\", \"TIME\", \"LIGHT\" ) VALUES (?,?,?)";
	private String sqlGetLightOfDate	= "SELECT time, light FROM APP.LIGHTSENSOR WHERE date=?";

	private Connection        con      = null ;
	private PreparedStatement psGetAllLights = null ;
	private PreparedStatement psNewLight = null;
	private PreparedStatement psGetLightOfDate = null;


	public LightSensorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllLights   = con.prepareStatement(sqlGetAllLights);
			this.psNewLight		  = con.prepareStatement(sqlNewLight);
			this.psGetLightOfDate = con.prepareStatement(sqlGetLightOfDate);

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
		if (lightMessures.size() >= 1) {
			LightSensor s = lightMessures.get(lightMessures.size()-1);
			if(s.getLight().equals("true")) {
				return true;
			}
		}
		return false;
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
		String lightMin = "";
		try {
			Calendar calendar = Calendar.getInstance();
			psNewLight.clearParameters();
			psNewLight.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			if(calendar.get(Calendar.MINUTE) < 10) {
				lightMin = "0" + calendar.get(Calendar.MINUTE);
			} else {
				lightMin = "" + calendar.get(Calendar.MINUTE);
			}
			psNewLight.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + lightMin + ":" + calendar.get(Calendar.SECOND));
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

	public ArrayList<LightSensor> getLightsOfDate(String dateFormat){
		ArrayList<LightSensor> lights = new ArrayList<LightSensor>();
		String[] splittedDateFormat = dateFormat.split("-");
		int year 	= Integer.parseInt(splittedDateFormat[2]);
		int month 	= Integer.parseInt(splittedDateFormat[1]);
		int day 	= Integer.parseInt(splittedDateFormat[0]);
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(year, month - 1, day);
		Date date = new Date(gc.getTimeInMillis());
		try {
			psGetLightOfDate.setDate(1, date);
			
			ResultSet rs = psGetLightOfDate.executeQuery();
			while (rs.next()){
				String time = rs.getString(1);
				String lightState = rs.getString(2);
				LightSensor light = new LightSensor(date, time, lightState);
				lights.add(light);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return lights;
	}
}

