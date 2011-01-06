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
import com.inti3e.model.Humidity;


public class HumidityDao {


	private String sqlGetAllTemps		= "SELECT date, time, humidity FROM APP.Humidity ORDER BY date ASC";
	private String sqlNewTemp 			= "INSERT INTO APP.Humidity (\"DATE\", \"TIME\", \"HUMIDITY\" ) VALUES (?,?,?)";
	private String sqlGetHumidOfDate	= "SELECT time, humidity FROM APP.Humidity WHERE date=?";
	private String sqlGetHumidityBetween= "SELECT date,time, humidity FROM APP.Humidity WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";
	
	private Connection        con      = null ;
	private PreparedStatement psGetAllHumids = null ;
	private PreparedStatement psNewHumid = null;
	private PreparedStatement psGetHumidOfDate = null;
	private PreparedStatement psGetHumidityBetween = null;


	public HumidityDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{
			this.psGetAllHumids   = con.prepareStatement(sqlGetAllTemps);
			this.psNewHumid 	  = con.prepareStatement(sqlNewTemp);
			this.psGetHumidOfDate = con.prepareStatement(sqlGetHumidOfDate);
			this.psGetHumidityBetween = con.prepareStatement(sqlGetHumidityBetween);
		} 
		catch(SQLException se) {
			printSQLException(se) ;
		}
	}

	public ArrayList<Humidity> getAllHumids(){
		ArrayList<Humidity> humiditys = new ArrayList<Humidity>();
		try {
			ResultSet rs = psGetAllHumids.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				int humidity = rs.getInt(3);
				Humidity humid = new Humidity(date, time, humidity);
				humiditys.add(humid);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return humiditys;
	}

	public void addNewHumidity(int humidity){
		String humidHour = "";
		String humidMin = "";
		String humidSec = "";
		try {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) < 10) { humidHour = "0" + calendar.get(Calendar.HOUR_OF_DAY); }
			else { humidHour = "" + calendar.get(Calendar.HOUR_OF_DAY); }
			if(calendar.get(Calendar.MINUTE) < 10) { humidMin = "0" + calendar.get(Calendar.MINUTE); }
			else { humidMin = "" + calendar.get(Calendar.MINUTE); }
			if(calendar.get(Calendar.SECOND) < 10) { humidSec = "0" + calendar.get(Calendar.SECOND); }
			else { humidSec = "" + calendar.get(Calendar.SECOND); }
			
			psNewHumid.clearParameters();
			psNewHumid.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewHumid.setString(2, "" + humidHour + ":" + humidMin + ":" + humidSec);
			psNewHumid.setInt(3, humidity);
			psNewHumid.executeUpdate();
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

	public ArrayList<Humidity> getHumidsOfDate(String dateFormat){
		ArrayList<Humidity> humids = new ArrayList<Humidity>();
		String[] splittedDateFormat = dateFormat.split("-");
		int year 	= Integer.parseInt(splittedDateFormat[0]);
		int month 	= Integer.parseInt(splittedDateFormat[1]);
		int day 	= Integer.parseInt(splittedDateFormat[2]);
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(year, month - 1, day);
		Date date = new Date(gc.getTimeInMillis());
		try {
			psGetHumidOfDate.setDate(1, date);

			ResultSet rs = psGetHumidOfDate.executeQuery();
			while (rs.next()){
				String time = rs.getString(1);
				int humidInt = rs.getInt(2);
				Humidity humid = new Humidity(date, time, humidInt);
				humids.add(humid);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return humids;
	}
	public ArrayList<Humidity> getHumiditysBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		ArrayList<Humidity> temps = new ArrayList<Humidity>();
		ArrayList<Humidity> tempArray = new ArrayList<Humidity>();
		System.out.println("From: " + dateFormat1);
		System.out.println("To: " + dateFormat2);
		String[] splittedDate1 = dateFormat1.split("-");
		int year1 	= Integer.parseInt(splittedDate1[2]);
		int month1 	= Integer.parseInt(splittedDate1[1]);
		int day1 	= Integer.parseInt(splittedDate1[0]);

		String[] splittedDate2 = dateFormat2.split("-");
		int year2 	= Integer.parseInt(splittedDate2[2]);
		int month2 	= Integer.parseInt(splittedDate2[1]);
		int day2 	= Integer.parseInt(splittedDate2[0]);

		GregorianCalendar gc = new GregorianCalendar();

		gc.set(year1, month1 - 1, day1);
		Date date1 = new Date(gc.getTimeInMillis());
		gc.set(year2, month2 - 1, day2);
		Date date2 = new Date(gc.getTimeInMillis());

		try {
			psGetHumidityBetween.setDate(1, date1);
			psGetHumidityBetween.setDate(2, date2);

			ResultSet rs = psGetHumidityBetween.executeQuery();
			while (rs.next()){
				Date date 	= rs.getDate(1);
				String time = rs.getString(2);
				int hum = Integer.parseInt(rs.getString(3));
				Humidity humidity = new Humidity(date, time, hum);
				tempArray.add(humidity);
			}

			temps = getDateBetweenHours(time1, time2, date1, date2, tempArray);
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return temps;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Humidity> getDateBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<Humidity> humidityArray) {
		String[] splittedTime1 = time1.split(":");
		String[] splittedTime2 = time2.split(":");
		int hour1 = Integer.parseInt(splittedTime1[0]);
		int hour2 = Integer.parseInt(splittedTime2[0]);
		int minute1 = Integer.parseInt(splittedTime1[1]);
		int minute2 = Integer.parseInt(splittedTime2[1]);
		
		ArrayList<Humidity> removeHumiditys = new ArrayList<Humidity>();
		for (Humidity d : humidityArray) {
			java.util.Date humidityDate = d.getDate();
			String[] splittedTime = d.getTime().split(":");
			int hour = Integer.parseInt(splittedTime[0]);
			int minute = Integer.parseInt(splittedTime[1]);
			int second = Integer.parseInt(splittedTime[2]);
			
			if (humidityDate.getDay() == date1.getDay() &&
					humidityDate.getMonth() == date1.getMonth() &&
					humidityDate.getYear() == date1.getYear()) {
				if(hour < hour1) {
					removeHumiditys.add(d);
				} else if (hour == hour1) {
					if (minute <= minute1) {
						removeHumiditys.add(d);
					} else if (minute == minute1) {
						if (second <= 0) {
							removeHumiditys.add(d);
						}
					}
				}
			}
			if (humidityDate.getDay() == date2.getDay() &&
					humidityDate.getMonth() == date2.getMonth() &&
					humidityDate.getYear() == date2.getYear()) {
				if(hour > hour2) {
					removeHumiditys.add(d);
				} else if (hour == hour2) {
					if (minute > minute2) {
						removeHumiditys.add(d);
					} else if (minute == minute2) { 
						if (second > 0) {
							removeHumiditys.add(d);
						}
					}
				}
			}
		}
		humidityArray.removeAll(removeHumiditys);
		return humidityArray;
	}
}