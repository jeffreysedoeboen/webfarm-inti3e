/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:17:04
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
import com.inti3e.model.Door;

/**
 * The Class DoorDao.
 */
public class DoorDao {

	/** The sql get all doors. */
	private String sqlGetAllDoor		= "SELECT date, time, door FROM APP.DOOR ORDER BY date,time ASC";
	
	/** The sql new door. */
	private String sqlNewDoor			= "INSERT INTO APP.DOOR (\"DATE\", \"TIME\", \"DOOR\" ) VALUES (?,?,?)";
	
	/** The sql get door between dates. */
	private String sqlGetDoorBetween	= "SELECT date,time,door FROM APP.DOOR WHERE date BETWEEN ? AND ? ORDER BY date,time ASC";
	
	/** The con. */
	private Connection        con      = null ;
	
	/** The ps get all doors. */
	private PreparedStatement psGetAllDoor = null ;
	
	/** The ps new door. */
	private PreparedStatement psNewDoor = null;
	
	/** The ps get door between dates. */
	private PreparedStatement psGetDoorBetween = null;


	/**
	 * Instantiates a new door dao.
	 */
	public DoorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try {
			this.psGetAllDoor   	= con.prepareStatement(sqlGetAllDoor);
			this.psNewDoor 			= con.prepareStatement(sqlNewDoor);
			this.psGetDoorBetween 	= con.prepareStatement(sqlGetDoorBetween);	
		}
		catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	/**
	 * Gets the current state of door.
	 *
	 * @return the current state of door
	 */
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

	/**
	 * Get all door.
	 *
	 * @return all door
	 */
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

	/**
	 * Adds the new door.
	 *
	 * @param open the state of the door
	 */
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

	/**
	 * Prints the sql exception.
	 *
	 * @param se the sqlexception
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
	 * Get the doors between dates.
	 *
	 * @param dateFormat1 start date
	 * @param time1 start time
	 * @param dateFormat2 end date
	 * @param time2 end time
	 * @return the doors between dates
	 */
	public ArrayList<Door> getDoorsBetweenDates(String dateFormat1, String time1, String dateFormat2, String time2){
		//asserts
		assert(dateFormat1 != null);
		assert(time1 != null);
		assert(dateFormat2 != null);
		assert(time2 != null);
		
		ArrayList<Door> doors = new ArrayList<Door>();
		ArrayList<Door> doorArray = new ArrayList<Door>();
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
			ResultSet rs = psGetDoorBetween.executeQuery();
			while (rs.next()){
				Date date 	= rs.getDate(1);
				String time = rs.getString(2);
				int door = rs.getInt(3);
				Door adddoor = new Door(date, time, door);
				doorArray.add(adddoor);
			}

			doors = getDoorsBetweenHours(time1, time2, date1, date2, doorArray);
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return filterDoorList(doors);
	}

	/**
	 * Gets the date between hours.
	 *
	 * @param time1 the start time
	 * @param time2 the end time
	 * @param date1 the start date
	 * @param date2 the end date
	 * @param doorArray the door array
	 * @return the date between hours
	 */
	@SuppressWarnings({ "deprecation" })
	private ArrayList<Door> getDoorsBetweenHours(String time1, String time2, java.util.Date date1, java.util.Date date2, ArrayList<Door> doorArray) {
		//asserts
		assert(time1 != null);
		assert(time2 != null);
		assert(doorArray != null);
		
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
	
	/**
	 * Filter door list. List that contains fewer entries to make the graphs more clear.
	 *
	 * @param doors all doors that have to be filtered
	 * @return the filtered array list
	 */
	private ArrayList<Door> filterDoorList(ArrayList<Door> doors) {
		ArrayList<Door> removeDoors = new ArrayList<Door>();
		if (doors.size() > 0) {
			int open = -1;
			for (Door d : doors) {
				if (open == d.getDoor()) {
					removeDoors.add(d);
				}
				open = d.getDoor();
			}
			doors.removeAll(removeDoors);
			
			if (doors.size() > 25) {
				ArrayList<Door> filteredDoors = new ArrayList<Door>();
				
				Door firstValue = doors.get(0);
				Door lastValue = doors.get(doors.size()-6);
				long amountOfSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),
															lastValue.getDate(),lastValue.getTime());
				long avgSeconds = amountOfSeconds/20;
				long currentSeconds = avgSeconds;
				
				Door firstBlockDoor = doors.get(0), betweenBlockDoor = null, lastBlockDoor = null;
				for (Door d : doors.subList(0, doors.size()-5)) {
					long diffSeconds = getAmountOfSeconds(firstValue.getDate(),firstValue.getTime(),d.getDate(),d.getTime());
					if (diffSeconds > currentSeconds || d == doors.get(doors.size()-6)) {
						if (filteredDoors.size() == 0 || firstBlockDoor != null && 
								firstBlockDoor.getDoor() != filteredDoors.get(filteredDoors.size()-1).getDoor()) {
							filteredDoors.add(firstBlockDoor);
						}
						if (betweenBlockDoor != null && 
								betweenBlockDoor.getDoor() != filteredDoors.get(filteredDoors.size()-1).getDoor()) {
							filteredDoors.add(betweenBlockDoor);
						}
						if (lastBlockDoor != null && 
								lastBlockDoor.getDoor() != filteredDoors.get(filteredDoors.size()-1).getDoor()) {
							filteredDoors.add(lastBlockDoor);
						}
						
						firstBlockDoor = d;
						betweenBlockDoor = null;
						lastBlockDoor = null;
						currentSeconds += avgSeconds;
					} else {
						if (betweenBlockDoor == null && firstBlockDoor.getDoor() != d.getDoor()) {
							betweenBlockDoor = d;
						}
						lastBlockDoor = d;
					}
				}
				
				if (filteredDoors.get(filteredDoors.size()-1).getDoor() != doors.get(doors.size()-6).getDoor()) {
					filteredDoors.add(doors.get(doors.size()-6));
				}
				filteredDoors.addAll(doors.subList(doors.size()-5, doors.size()));
				doors = filteredDoors;
			}
		}
		return doors;
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
