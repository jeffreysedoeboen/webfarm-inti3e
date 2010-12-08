package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.LightSensor;

public class LightSensorDao {


	private String sqlGetAllLights		= "SELECT date, time, light FROM APP.LIGHTSENSOR";
	private String sqlNewLight 			= "INSERT INTO APP.LIGHTSENSOR (\"DATE\", \"TIME\", \"LIGHT\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllLights = null ;
	private PreparedStatement psNewLight = null;


	public LightSensorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllLights   = con.prepareStatement(sqlGetAllLights);
			this.psNewLight		 = con.prepareStatement(sqlNewLight);

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
			if(!tableList.contains("LIGHTSENSOR")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.LIGHTSENSOR (" +
						"date VARCHAR(10)," +
						"time VARCHAR(50)," +
						"light VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LightSensor> getAllLights(){
		ArrayList<LightSensor> lightSensors = new ArrayList<LightSensor>();
		try {
			ResultSet rs = psGetAllLights.executeQuery();
			while (rs.next()){
				String date = rs.getString(1);
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

	public void addNewLight(boolean light) {
		try {
			psNewLight.clearParameters();
			psNewLight.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewLight.setString(2, Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND);
			psNewLight.setInt(3, light ? 1:0);
			psNewLight.executeUpdate();
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

	
	

}

