/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:20:50
 */
package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import com.inti3e.model.LightSwitch;

import com.inti3e.database.DBmanager;

/**
 * The Class SwitchDao.
 */
public class SwitchDao {


	/** The sql get all light switches. */
	private String sqlGetAllLightSwitches 	= "SELECT date, time, LightSwitch FROM APP.LIGHTSWITCHES ORDER BY date,time ASC";
	
	/** The sql new light switch. */
	private String sqlNewLightSwitch		= "INSERT INTO APP.LIGHTSWITCHES (\"DATE\", \"TIME\", \"LIGHTSWITCH\" ) VALUES (?,?,?)";

	/** The con. */
	private Connection        con      = null ;
	
	/** The ps get all light switches. */
	private PreparedStatement psGetAllLightSwitches = null ;
	
	/** The ps new light switch. */
	private PreparedStatement psNewLightSwitch = null;
	
	/**
	 * Instantiates a new switch dao.
	 */
	public SwitchDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllLightSwitches   = con.prepareStatement(sqlGetAllLightSwitches);
			this.psNewLightSwitch 		 = con.prepareStatement(sqlNewLightSwitch);

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}

	/**
	 * Get all light switches.
	 *
	 * @return the all light switches
	 */
	public ArrayList<LightSwitch> getAllLightSwitches(){
		ArrayList<LightSwitch> lights = new ArrayList<LightSwitch>();
		try {
			ResultSet rs = psGetAllLightSwitches.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				String light = rs.getString(3);
				LightSwitch l = new LightSwitch(date, time, light);
				lights.add(l);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return lights;
	}

	/**
	 * Adds the new light switch.
	 *
	 * @param light the switch
	 */
	public void addNewLightSwitch(boolean light){
		String switchHour = "";
		String switchMin = "";
		String switchSec = "";
		try {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) < 10) { switchHour = "0" + calendar.get(Calendar.HOUR_OF_DAY); }
			else { switchHour = "" + calendar.get(Calendar.HOUR_OF_DAY); }
			if(calendar.get(Calendar.MINUTE) < 10) { switchMin = "0" + calendar.get(Calendar.MINUTE); }
			else { switchMin = "" + calendar.get(Calendar.MINUTE); }
			if(calendar.get(Calendar.SECOND) < 10) { switchSec = "0" + calendar.get(Calendar.SECOND); }
			else { switchSec = "" + calendar.get(Calendar.SECOND); }
			
			psNewLightSwitch.clearParameters();
			psNewLightSwitch.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewLightSwitch.setString(2, "" + switchHour + ":" + switchMin + ":" + switchSec);
			psNewLightSwitch.setInt(3, light ? 1:0);
			psNewLightSwitch.executeUpdate();
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

}
