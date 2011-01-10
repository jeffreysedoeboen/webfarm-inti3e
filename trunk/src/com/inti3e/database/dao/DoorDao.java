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
import com.inti3e.model.Door;
import com.inti3e.model.LightSensor;


public class DoorDao {

	private String sqlGetAllDoor		= "SELECT date, time, door FROM APP.DOOR ORDER BY date,time ASC";
	private String sqlNewDoor			= "INSERT INTO APP.DOOR (\"DATE\", \"TIME\", \"DOOR\" ) VALUES (?,?,?)";
	private String sqlGetDoorOfDate		= "SELECT time, door FROM APP.DOOR WHERE date=?";
	private String sqlGetDoorBetween	= "SELECT date,time,door FROM APP.DOOR WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";
	
	private Connection        con      = null ;
	private PreparedStatement psGetAllDoor = null ;
	private PreparedStatement psNewDoor = null;
	private PreparedStatement psGetDoorOfDate = null;
	private PreparedStatement psGetDoorBetween = null;


	public DoorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try {
			this.psGetAllDoor   	= con.prepareStatement(sqlGetAllDoor);
			this.psNewDoor 			= con.prepareStatement(sqlNewDoor);
			this.psGetDoorOfDate	= con.prepareStatement(sqlGetDoorOfDate);
			this.psGetDoorBetween 	= con.prepareStatement(sqlGetDoorBetween);	
		}
		catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	public boolean getCurrentDoor() {
		ArrayList<Door> doorMessures = getAllDoors();
		if (doorMessures.size() >= 1) {
			Door d = doorMessures.get(doorMessures.size()-1);
			if(d.getDoor() == 1) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Door> getAllDoors(){
		ArrayList<Door> doors = new ArrayList<Door>();
		try {
			ResultSet rs = psGetAllDoor.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				int door = rs.getInt(3);
				Door d = new Door(date, time, door);
				doors.add(d);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return doors;
	}

	public void addNewDoor( boolean open ){
		String doorHour = "";
		String doorMin = "";
		String doorSec = "";
		try {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) < 10) { doorHour = "0" + calendar.get(Calendar.HOUR_OF_DAY); }
			else { doorHour = "" + calendar.get(Calendar.HOUR_OF_DAY); }
			if(calendar.get(Calendar.MINUTE) < 10) { doorMin = "0" + calendar.get(Calendar.MINUTE); }
			else { doorMin = "" + calendar.get(Calendar.MINUTE); }
			if(calendar.get(Calendar.SECOND) < 10) { doorSec = "0" + calendar.get(Calendar.SECOND); }
			else { doorSec = "" + calendar.get(Calendar.SECOND); }
			
			psNewDoor.clearParameters();
			psNewDoor.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewDoor.setString(2, "" + doorHour + ":" + doorMin + ":" + doorSec);
			psNewDoor.setInt(3, open ? 1:0);
			psNewDoor.executeUpdate();
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
	public ArrayList<Door> getDoorsOfDate(String dateFormat){
		ArrayList<Door> doors = new ArrayList<Door>();
		if(dateFormat != null) {
			System.out.println(dateFormat);
			String[] splittedDateFormat = dateFormat.split("-");
			int year 	= Integer.parseInt(splittedDateFormat[2]);
			int month 	= Integer.parseInt(splittedDateFormat[1]);
			int day 	= Integer.parseInt(splittedDateFormat[0]);
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(year, month - 1, day);
			Date date = new Date(gc.getTimeInMillis());
			try {
				psGetDoorOfDate.setDate(1, date);

				ResultSet rs = psGetDoorOfDate.executeQuery();
				while (rs.next()){
					String time = rs.getString(1);
					int doorInt = rs.getInt(2);
					Door door = new Door(date, time, doorInt);
					doors.add(door);
				}
			} catch (SQLException se) {
				printSQLException(se) ;		
			} 
		}
		return doors;
	}
	
	
	public ArrayList<Door> getDoorsBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		ArrayList<Door> doors = new ArrayList<Door>();
		ArrayList<Door> doorArray = new ArrayList<Door>();
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
			psGetDoorBetween.setDate(1, date1);
			psGetDoorBetween.setDate(2, date2);
			System.out.println(date1.toGMTString() + " / " + date2.toGMTString() + " <> " + time1 + " / " + time2);
			ResultSet rs = psGetDoorBetween.executeQuery();
			while (rs.next()){
				Date date 	= rs.getDate(1);
				String time = rs.getString(2);
				int door = rs.getInt(3);
				Door adddoor = new Door(date, time, door);
				doorArray.add(adddoor);
			}

			doors = getDateBetweenHours(time1, time2, date1, date2, doorArray);
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return doors;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Door> getDateBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<Door> doorArray) {
		String[] splittedTime1 = time1.split(":");
		String[] splittedTime2 = time2.split(":");
		int hour1 = Integer.parseInt(splittedTime1[0]);
		int hour2 = Integer.parseInt(splittedTime2[0]);
		int minute1 = Integer.parseInt(splittedTime1[1]);
		int minute2 = Integer.parseInt(splittedTime2[1]);
		
		ArrayList<Door> removeDoors = new ArrayList<Door>();
		for (Door d : doorArray) {
			java.util.Date doorDate = d.getDate();
			String[] splittedTime = d.getTime().split(":");
			int hour = Integer.parseInt(splittedTime[0]);
			int minute = Integer.parseInt(splittedTime[1]);
			int second = Integer.parseInt(splittedTime[2]);
			
			if (doorDate.getDay() == date1.getDay() &&
					doorDate.getMonth() == date1.getMonth() &&
					doorDate.getYear() == date1.getYear()) {
				if(hour < hour1) {
					removeDoors.add(d);
				} else if (hour == hour1) {
					if (minute <= minute1) {
						removeDoors.add(d);
					} else if (minute == minute1) {
						if (second <= 0) {
							removeDoors.add(d);
						}
					}
				}
			}
			if (doorDate.getDay() == date2.getDay() &&
					doorDate.getMonth() == date2.getMonth() &&
					doorDate.getYear() == date2.getYear()) {
				if(hour > hour2) {
					removeDoors.add(d);
				} else if (hour == hour2) {
					if (minute > minute2) {
						removeDoors.add(d);
					} else if (minute == minute2) { 
						if (second > 0) {
							removeDoors.add(d);
						}
					}
				}
			}
		}
		doorArray.removeAll(removeDoors);
		return doorArray;
	}
}
