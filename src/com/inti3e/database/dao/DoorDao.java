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

import com.inti3e.model.Door;
import com.inti3e.model.Humidity;
import com.inti3e.model.Temperature;
import com.inti3e.model.User;

import com.inti3e.database.DBmanager;


public class DoorDao {


	private String sqlGetAllDoor		= "SELECT date, time, door FROM APP.DOOR";
	private String sqlNewDoor			= "INSERT INTO APP.DOOR (\"DATE\", \"TIME\", \"DOOR\" ) VALUES (?,?,?)";
	private String sqlGetDoorOfDate		= "SELECT time, door FROM APP.DOOR WHERE date=?";

	private Connection        con      = null ;
	private PreparedStatement psGetAllDoor = null ;
	private PreparedStatement psNewDoor = null;
	private PreparedStatement psGetDoorOfDate = null;


	public DoorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllDoor   = con.prepareStatement(sqlGetAllDoor);
			this.psNewDoor 		= con.prepareStatement(sqlNewDoor);
			this.psGetDoorOfDate= con.prepareStatement(sqlGetDoorOfDate);

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
			if(!tableList.contains("DOOR")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.DOOR (" +
						"DATE DATE," +
						"TIME VARCHAR(50)," +
						"DOOR SMALLINT" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		String doorMin = "";
		try {
			Calendar calendar = Calendar.getInstance();
			psNewDoor.clearParameters();
			psNewDoor.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			if(calendar.get(Calendar.MINUTE) < 10) {
				doorMin = "0" + calendar.get(Calendar.MINUTE);
			} else {
				doorMin = "" + calendar.get(Calendar.MINUTE);
			}
			psNewDoor.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + doorMin + ":" + calendar.get(Calendar.SECOND));
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
	
	public ArrayList<Door> getDoorsOfDate(String dateFormat){
		ArrayList<Door> doors = new ArrayList<Door>();
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
		return doors;
	}

}
