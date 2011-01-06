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
import com.inti3e.model.Temperature;

public class TempDao {


	private String sqlGetAllTemps		= "SELECT date, time, temp FROM APP.Temp ORDER BY date ASC";
	private String sqlNewTemp 			= "INSERT INTO APP.Temp (\"DATE\", \"TIME\", \"TEMP\" ) VALUES (?,?,?)";
	private String sqlGetTempOfDate		= "SELECT time, temp FROM APP.Temp WHERE date=?";
	private String sqlGetTempBetween	= "SELECT date,time, temp FROM APP.Temp WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";

	private Connection        con      = null ;
	private PreparedStatement psGetAllTemps = null ;
	private PreparedStatement psNewTemp = null;
	private PreparedStatement psGetTempOfDate = null;
	private PreparedStatement psGetTempBetween = null;


	public TempDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllTemps   = con.prepareStatement(sqlGetAllTemps);
			this.psNewTemp 		 = con.prepareStatement(sqlNewTemp);
			this.psGetTempOfDate = con.prepareStatement(sqlGetTempOfDate);
			this.psGetTempBetween = con.prepareStatement(sqlGetTempBetween);

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}

	public ArrayList<Temperature> getAllTemps(){
		ArrayList<Temperature> temps = new ArrayList<Temperature>();
		try {
			ResultSet rs = psGetAllTemps.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				String temp = rs.getString(3);
				Temperature temperature = new Temperature(date, time, temp);
				temps.add(temperature);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return temps;
	}

	public void addNewTemp(String temperature){
		String tempHour = "";
		String tempMin = "";
		String tempSec = "";
		try {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) < 10) { tempHour = "0" + calendar.get(Calendar.HOUR_OF_DAY); }
			else { tempHour = "" + calendar.get(Calendar.HOUR_OF_DAY); }
			if(calendar.get(Calendar.MINUTE) < 10) { tempMin = "0" + calendar.get(Calendar.MINUTE); }
			else { tempMin = "" + calendar.get(Calendar.MINUTE); }
			if(calendar.get(Calendar.SECOND) < 10) { tempSec = "0" + calendar.get(Calendar.SECOND); }
			else { tempSec = "" + calendar.get(Calendar.SECOND); }
			
			psNewTemp.clearParameters();
			psNewTemp.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewTemp.setString(2, "" + tempHour + ":" + tempMin + ":" + tempSec);
			psNewTemp.setString(3, temperature);
			psNewTemp.executeUpdate();
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

	public ArrayList<Temperature> getTempsOfDate(String dateFormat){
		ArrayList<Temperature> temps = new ArrayList<Temperature>();
		String[] splittedDateFormat = dateFormat.split("-");
		int year 	= Integer.parseInt(splittedDateFormat[2]);
		int month 	= Integer.parseInt(splittedDateFormat[1]);
		int day 	= Integer.parseInt(splittedDateFormat[0]);
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(year, month - 1, day);
		Date date = new Date(gc.getTimeInMillis());
		try {
			psGetTempOfDate.setDate(1, date);

			ResultSet rs = psGetTempOfDate.executeQuery();
			while (rs.next()){
				String time = rs.getString(1);
				String temp = rs.getString(2);
				Temperature temperature = new Temperature(date, time, temp);
				temps.add(temperature);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return temps;
	}

	public ArrayList<Temperature> getTempsBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		ArrayList<Temperature> temps = new ArrayList<Temperature>();
		ArrayList<Temperature> tempArray = new ArrayList<Temperature>();
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
			psGetTempBetween.setDate(1, date1);
			psGetTempBetween.setDate(2, date2);

			ResultSet rs = psGetTempBetween.executeQuery();
			while (rs.next()){
				Date date 	= rs.getDate(1);
				String time = rs.getString(2);
				String temp = rs.getString(3);
				Temperature temperature = new Temperature(date, time, temp);
				tempArray.add(temperature);
			}

			temps = getDateBetweenHours(time1, time2, date1, date2, tempArray);
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return temps;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Temperature> getDateBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<Temperature> temperatureArray) {
		String[] splittedTime1 = time1.split(":");
		String[] splittedTime2 = time2.split(":");
		int hour1 = Integer.parseInt(splittedTime1[0]);
		int hour2 = Integer.parseInt(splittedTime2[0]);
		int minute1 = Integer.parseInt(splittedTime1[1]);
		int minute2 = Integer.parseInt(splittedTime2[1]);
		
		ArrayList<Temperature> removeTemperatures = new ArrayList<Temperature>();
		for (Temperature d : temperatureArray) {
			java.util.Date temperatureDate = d.getDate();
			String[] splittedTime = d.getTime().split(":");
			int hour = Integer.parseInt(splittedTime[0]);
			int minute = Integer.parseInt(splittedTime[1]);
			int second = Integer.parseInt(splittedTime[2]);
			
			if (temperatureDate.getDay() == date1.getDay() &&
					temperatureDate.getMonth() == date1.getMonth() &&
					temperatureDate.getYear() == date1.getYear()) {
				if(hour < hour1) {
					removeTemperatures.add(d);
				} else if (hour == hour1) {
					if (minute <= minute1) {
						removeTemperatures.add(d);
					} else if (minute == minute1) {
						if (second <= 0) {
							removeTemperatures.add(d);
						}
					}
				}
			}
			if (temperatureDate.getDay() == date2.getDay() &&
					temperatureDate.getMonth() == date2.getMonth() &&
					temperatureDate.getYear() == date2.getYear()) {
				if(hour > hour2) {
					removeTemperatures.add(d);
				} else if (hour == hour2) {
					if (minute > minute2) {
						removeTemperatures.add(d);
					} else if (minute == minute2) { 
						if (second > 0) {
							removeTemperatures.add(d);
						}
					}
				}
			}
		}
		temperatureArray.removeAll(removeTemperatures);
		return temperatureArray;
	}
}
