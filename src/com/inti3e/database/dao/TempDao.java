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


	private String sqlGetAllTemps		= "SELECT date, time, temp FROM APP.Temp ORDER BY date,time ASC";
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
	
	public String getCurrentTemp() {
		Temperature t = null;
		ArrayList<Temperature> tempMeasures = getAllTemps();
		if (tempMeasures.size() >= 1) {
			t = tempMeasures.get(tempMeasures.size()-1);
		}
		return t.getTemp();
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
			printSQLException(se);
		} 
		return filterTempList(temps);
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
	
	private ArrayList<Temperature> filterTempList(ArrayList<Temperature> temps) {
		ArrayList<Temperature> temperatures = null;
		if (temps.size() > 25) {
			temperatures = new ArrayList<Temperature>();
			Temperature firstValue = temps.get(0);
			Temperature lastValue = temps.get(temps.size()-6);
			long amountOfSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),
														lastValue.getDate(),lastValue.getTime());
			long avgSeconds = amountOfSeconds/20;
			long currentSeconds = avgSeconds;
			ArrayList<Temperature> tempTemps = new ArrayList<Temperature>();
			for (Temperature t : temps) {
				long diffSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),t.getDate(),t.getTime());
				if (diffSeconds > currentSeconds || t == temps.get(temps.size()-1)) {
					Temperature firstTempValue = tempTemps.get(0);
					double avgValue = 0;
					for (Temperature tempT : tempTemps) {
						avgValue += Integer.parseInt(tempT.getTemp());
					}
					avgValue /= tempTemps.size();
					if (tempTemps.size() > 0) {
						temperatures.add(new Temperature(firstTempValue.getDate(), 
								firstTempValue.getTime(), ""+Math.round(avgValue)));
					}
					tempTemps.clear();
					currentSeconds += avgSeconds;
				}
				tempTemps.add(t);
			}
			
			temperatures.addAll(temps.subList(temps.size()-6, temps.size()));
		} else {
			temperatures = temps;
		}
		return temperatures;
	}
	
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
