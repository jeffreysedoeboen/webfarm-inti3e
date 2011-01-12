/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:19:39
 */
package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.inti3e.database.DBmanager;
import com.inti3e.model.LightSensor;

/**
 * The Class LightSensorDao.
 */
public class LightSensorDao {


	/** The sql get all lights. */
	private String sqlGetAllLights		= "SELECT date, time, light FROM APP.LIGHTSENSOR ORDER BY date,time ASC";
	
	/** The sql new light. */
	private String sqlNewLight 			= "INSERT INTO APP.LIGHTSENSOR (\"DATE\", \"TIME\", \"LIGHT\" ) VALUES (?,?,?)";
	
	/** The sql get light between. */
	private String sqlGetLightBetween	= "SELECT date,time,light FROM APP.LIGHTSENSOR WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";
	
	/** The con. */
	private Connection        con      = null ;
	
	/** The ps get all lights. */
	private PreparedStatement psGetAllLights = null ;
	
	/** The ps new light. */
	private PreparedStatement psNewLight = null;
	
	/** The ps get light between. */
	private PreparedStatement psGetLightBetween = null;


	/**
	 * Instantiates a new light sensor dao.
	 */
	public LightSensorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllLights   	= con.prepareStatement(sqlGetAllLights);
			this.psNewLight		  	= con.prepareStatement(sqlNewLight);
			this.psGetLightBetween 	= con.prepareStatement(sqlGetLightBetween);	

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	/**
	 * Gets the light on.
	 *
	 * @return the light on
	 */
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

	/**
	 * Gets the all lights.
	 *
	 * @return the all lights
	 */
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

	/**
	 * Adds the new light.
	 *
	 * @param light the light
	 */
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

	/**
	 * Prints the sql exception.
	 *
	 * @param se the se
	 */
	private void printSQLException(SQLException se) {
		while(se != null) {

			System.out.print("SQLException: State:   " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());			

			se = se.getNextException();
		}
	}
	
	/**
	 * Gets the lights between dates.
	 *
	 * @param dateFormat1 the date format1
	 * @param time1 the time1
	 * @param dateFormat2 the date format2
	 * @param time2 the time2
	 * @return the lights between dates
	 */
	public ArrayList<LightSensor> getLightsBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		
		//asserts
		assert(dateFormat1 != null);
		assert(time1 != null);
		assert(dateFormat2 != null);
		assert(time2 != null);
		
		ArrayList<LightSensor> lights = new ArrayList<LightSensor>();
		ArrayList<LightSensor> lightArray = new ArrayList<LightSensor>();
		
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

	/**
	 * Gets the light between hours.
	 *
	 * @param time1 the time1
	 * @param time2 the time2
	 * @param date1 the date1
	 * @param date2 the date2
	 * @param lightArray the light array
	 * @return the light between hours
	 */
	@SuppressWarnings("deprecation")
	private ArrayList<LightSensor> getLightBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<LightSensor> lightArray) {
		
		//asserts
		assert(time1 != null);
		assert(time2 != null);
		assert(lightArray != null);
		
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
		return filterLightList(lightArray);
	}
	
	/**
	 * Filter light list.
	 *
	 * @param lights the lights
	 * @return the array list
	 */
	private ArrayList<LightSensor> filterLightList(ArrayList<LightSensor> lights) {
		ArrayList<LightSensor> removeLights = new ArrayList<LightSensor>();
		if (lights.size() > 0) {
			String open = "";
			for (LightSensor l : lights) {
				if (open.equals(l.getLight())) {
					removeLights.add(l);
				}
				open = l.getLight();
			}
			lights.removeAll(removeLights);
			
			if (lights.size() > 25) {
				ArrayList<LightSensor> filteredLights = new ArrayList<LightSensor>();
				
				LightSensor firstValue = lights.get(0);
				LightSensor lastValue = lights.get(lights.size()-6);
				long amountOfSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),
															lastValue.getDate(),lastValue.getTime());
				long avgSeconds = amountOfSeconds/20;
				long currentSeconds = avgSeconds;
				
				LightSensor firstBlockLight = lights.get(0), betweenBlockLight = null, lastBlockLight = null;
				for (LightSensor l : lights.subList(0, lights.size()-5)) {
					long diffSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),l.getDate(),l.getTime());
					if (diffSeconds > currentSeconds || l == lights.get(lights.size()-6)) {
						if (filteredLights.size() == 0 || firstBlockLight != null && 
								!firstBlockLight.getLight().equals(filteredLights.get(filteredLights.size()-1).getLight())) {
							filteredLights.add(firstBlockLight);
						}
						if (betweenBlockLight != null && 
								!betweenBlockLight.getLight().equals(filteredLights.get(filteredLights.size()-1).getLight())) {
							filteredLights.add(betweenBlockLight);
						}
						if (lastBlockLight != null && 
								!lastBlockLight.getLight().equals(filteredLights.get(filteredLights.size()-1).getLight())) {
							filteredLights.add(lastBlockLight);
						}
						
						firstBlockLight = l;
						betweenBlockLight = null;
						lastBlockLight = null;
						currentSeconds += avgSeconds;
					} else {
						if (betweenBlockLight == null && !firstBlockLight.getLight().equals(l.getLight())) {
							betweenBlockLight = l;
						}
						lastBlockLight = l;
					}
				}
				
				if (!filteredLights.get(filteredLights.size()-1).getLight().equals(lights.get(lights.size()-6).getLight())) {
					filteredLights.add(lights.get(lights.size()-6));
				}
				filteredLights.addAll(lights.subList(lights.size()-5, lights.size()));
				lights = filteredLights;
			}
		}
		return lights;
	}
	
	/**
	 * Gets the amount of seconds.
	 *
	 * @param date1 the date1
	 * @param time1 the time1
	 * @param date2 the date2
	 * @param time2 the time2
	 * @return the amount of seconds
	 */
	@SuppressWarnings("deprecation")
	private long getAmountOfSeconds(java.util.Date date1, String time1, java.util.Date date2, String time2) {
		String[] splittedTime1 = time1.split(":");
		int hour1 	= Integer.parseInt(splittedTime1[0]);
		int minute1 = Integer.parseInt(splittedTime1[1]);
		int second1 = Integer.parseInt(splittedTime1[2]);
		String[] splittedTime2 = time2.split(":");
		int hour2 	= Integer.parseInt(splittedTime2[0]);
		int minute2 = Integer.parseInt(splittedTime2[1]);
		int second2 = Integer.parseInt(splittedTime2[2]);
		
		int time1Seconds = hour1*3600+minute1*60+second1;
		int time2Seconds = hour2*3600+minute2*60+second2;
		
		long amountOfSeconds = time2Seconds-time1Seconds;
		
		int date1Days = date1.getDate()+date1.getMonth()*30+date1.getYear()*365;
		int date2Days = date2.getDate()+date2.getMonth()*30+date2.getYear()*365;
		
		int amountOfDays = date2Days-date1Days;
		
		amountOfSeconds += amountOfDays*86400;
		
		return amountOfSeconds;
	}
}

