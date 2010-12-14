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

import com.inti3e.model.Humidity;
import com.inti3e.model.Temperature;

import com.inti3e.database.DBmanager;


public class HumidityDao {


	private String sqlGetAllTemps		= "SELECT date, time, humidity FROM APP.Humidity";
	private String sqlNewTemp 			= "INSERT INTO APP.Humidity (\"DATE\", \"TIME\", \"HUMIDITY\" ) VALUES (?,?,?)";
	private String sqlGetHumidOfDate	= "SELECT time, humidity FROM APP.Humidity WHERE date=?";

	private Connection        con      = null ;
	private PreparedStatement psGetAllHumids = null ;
	private PreparedStatement psNewHumid = null;
	private PreparedStatement psGetHumidOfDate = null;


	public HumidityDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllHumids   = con.prepareStatement(sqlGetAllTemps);
			this.psNewHumid 	  = con.prepareStatement(sqlNewTemp);
			this.psGetHumidOfDate = con.prepareStatement(sqlGetHumidOfDate);

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
			if(!tableList.contains("HUMIDITY")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.HUMIDITY (" +
						"DATE DATE," +
						"TIME VARCHAR(50)," +
						"HUMIDITY INTEGER" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		String humidMin = "";
		try {
			Calendar calendar = Calendar.getInstance();
			psNewHumid.clearParameters();
			psNewHumid.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			if(calendar.get(Calendar.MINUTE) < 10) {
				humidMin = "0" + calendar.get(Calendar.MINUTE);
			} else {
				humidMin = "" + calendar.get(Calendar.MINUTE);
			}
			psNewHumid.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + humidMin + ":" + calendar.get(Calendar.SECOND));
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
		int year 	= Integer.parseInt(splittedDateFormat[2]);
		int month 	= Integer.parseInt(splittedDateFormat[1]);
		int day 	= Integer.parseInt(splittedDateFormat[0]);
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

}
