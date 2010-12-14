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

import com.inti3e.model.Temperature;

import com.inti3e.database.DBmanager;

public class TempDao {


	private String sqlGetAllTemps		= "SELECT date, time, temp FROM APP.Temp";
	private String sqlNewTemp 			= "INSERT INTO APP.Temp (\"DATE\", \"TIME\", \"TEMP\" ) VALUES (?,?,?)";
	private String sqlGetTempOfDate		= "SELECT time, temp FROM APP.Temp WHERE date=?";
	
	private Connection        con      = null ;
	private PreparedStatement psGetAllTemps = null ;
	private PreparedStatement psNewTemp = null;
	private PreparedStatement psGetTempOfDate = null;


	public TempDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllTemps   = con.prepareStatement(sqlGetAllTemps);
			this.psNewTemp 		 = con.prepareStatement(sqlNewTemp);
			this.psGetTempOfDate = con.prepareStatement(sqlGetTempOfDate);

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
			if(!tableList.contains("TEMP")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.Temp (" +
						"DATE DATE," +
						"TIME VARCHAR(50)," +
						"TEMP VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		try {
			Calendar calendar = Calendar.getInstance();
			psNewTemp.clearParameters();
			psNewTemp.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewTemp.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
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

}
