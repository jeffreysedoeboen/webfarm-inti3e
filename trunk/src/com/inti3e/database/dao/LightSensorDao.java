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


	private String sqlGetAllLights		= "SELECT date, time, light FROM APP.LIGHTSENSOR ORDER BY date,time ASC";
	private String sqlNewLight 			= "INSERT INTO APP.LIGHTSENSOR (\"DATE\", \"TIME\", \"LIGHT\" ) VALUES (?,?,?)";
	private String sqlGetLightOfDate	= "SELECT time, light FROM APP.LIGHTSENSOR WHERE date=?";
	private String sqlGetLightBetween	= "SELECT date,time,light FROM APP.LIGHTSENSOR WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";
	
	private Connection        con      = null ;
	private PreparedStatement psGetAllLights = null ;
	private PreparedStatement psNewLight = null;
	private PreparedStatement psGetLightOfDate = null;
	private PreparedStatement psGetLightBetween = null;


	public LightSensorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllLights   	= con.prepareStatement(sqlGetAllLights);
			this.psNewLight		  	= con.prepareStatement(sqlNewLight);
			this.psGetLightOfDate 	= con.prepareStatement(sqlGetLightOfDate);
			this.psGetLightBetween 	= con.prepareStatement(sqlGetLightBetween);	

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	public boolean getLightOn() {
		ArrayList<LightSensor> lightMessures = getAllLights();
		if (lightMessures.size() >= 1) {
			LightSensor s = lightMessures.get(lightMessures.size()-1);
			if(s.getLight().equals("1")) {
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
		String lightHour = "";
		String lightMin = "";
		String lightSec = "";
		try {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) < 10) { lightHour = "0" + calendar.get(Calendar.HOUR_OF_DAY); }
			else { lightHour = "" + calendar.get(Calendar.HOUR_OF_DAY); }
			if(calendar.get(Calendar.MINUTE) < 10) { lightMin = "0" + calendar.get(Calendar.MINUTE); }
			else { lightMin = "" + calendar.get(Calendar.MINUTE); }
			if(calendar.get(Calendar.SECOND) < 10) { lightSec = "0" + calendar.get(Calendar.SECOND); }
			else { lightSec = "" + calendar.get(Calendar.SECOND); }
			
			psNewLight.clearParameters();
			psNewLight.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewLight.setString(2, "" + lightHour + ":" + lightMin + ":" + lightSec);
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

	//TODO NOT USED
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
	
	public ArrayList<LightSensor> getLightsBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		ArrayList<LightSensor> lights = new ArrayList<LightSensor>();
		ArrayList<LightSensor> lightArray = new ArrayList<LightSensor>();
		System.out.println(dateFormat1+" / "+dateFormat2);
		String[] splittedDate1 = dateFormat1.split("-");
		int day1 	= Integer.parseInt(splittedDate1[0]);
		int month1 	= Integer.parseInt(splittedDate1[1]);
		int year1 	= Integer.parseInt(splittedDate1[2]);

		String[] splittedDate2 = dateFormat2.split("-");
		int day2 	= Integer.parseInt(splittedDate2[0]);
		int month2 	= Integer.parseInt(splittedDate2[1]);
		int year2 	= Integer.parseInt(splittedDate2[2]);

		GregorianCalendar gc = new GregorianCalendar();

		gc.set(year1, month1 - 1, day1);
		Date date1 = new java.sql.Date(gc.getTimeInMillis());
		gc.set(year2, month2 - 1, day2);
		Date date2 = new java.sql.Date(gc.getTimeInMillis());

		try {
			psGetLightBetween.setDate(1, date1);
			psGetLightBetween.setDate(2, date2);
			System.out.println(date1.toGMTString() + " / " + date2.toGMTString() + " <> " + time1 + " / " + time2);
			ResultSet rs = psGetLightBetween.executeQuery();
			while (rs.next()){
				Date date 	= rs.getDate(1);
				String time = rs.getString(2);
				String light = rs.getString(3);
				LightSensor addlight = new LightSensor(date, time, light);
				lightArray.add(addlight);
			}

			lights = getLightBetweenHours(time1, time2, date1, date2, lightArray);
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return lights;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<LightSensor> getLightBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<LightSensor> lightArray) {
		String[] splittedTime1 = time1.split(":");
		String[] splittedTime2 = time2.split(":");
		int hour1 = Integer.parseInt(splittedTime1[0]);
		int hour2 = Integer.parseInt(splittedTime2[0]);
		int minute1 = Integer.parseInt(splittedTime1[1]);
		int minute2 = Integer.parseInt(splittedTime2[1]);
		
		ArrayList<LightSensor> removeLights = new ArrayList<LightSensor>();
		for (LightSensor d : lightArray) {
			java.util.Date lightDate = d.getDate();
			String[] splittedTime = d.getTime().split(":");
			int hour = Integer.parseInt(splittedTime[0]);
			int minute = Integer.parseInt(splittedTime[1]);
			int second = Integer.parseInt(splittedTime[2]);
			
			if (lightDate.getDay() == date1.getDay() &&
					lightDate.getMonth() == date1.getMonth() &&
					lightDate.getYear() == date1.getYear()) {
				if(hour < hour1) {
					removeLights.add(d);
				} else if (hour == hour1) {
					if (minute <= minute1) {
						removeLights.add(d);
					} else if (minute == minute1) {
						if (second <= 0) {
							removeLights.add(d);
						}
					}
				}
			}
			if (lightDate.getDay() == date2.getDay() &&
					lightDate.getMonth() == date2.getMonth() &&
					lightDate.getYear() == date2.getYear()) {
				if(hour > hour2) {
					removeLights.add(d);
				} else if (hour == hour2) {
					if (minute > minute2) {
						removeLights.add(d);
					} else if (minute == minute2) { 
						if (second > 0) {
							removeLights.add(d);
						}
					}
				}
			}
		}
		lightArray.removeAll(removeLights);
		return lightArray;
	}
}

