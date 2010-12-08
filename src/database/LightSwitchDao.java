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

import model.LightSwitch;



public class LightSwitchDao {


	private String sqlGetAllLightSwitches 	= "SELECT date, time, LightSwitch FROM APP.LIGHTSWITCHES";
	private String sqlNewLightSwitch		= "INSERT INTO APP.LIGHTSWITCHES (\"DATE\", \"TIME\", \"LIGHTSWITCH\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllLightSwitches = null ;
	private PreparedStatement psNewLightSwitch = null;


	public LightSwitchDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllLightSwitches   = con.prepareStatement(sqlGetAllLightSwitches);
			this.psNewLightSwitch 		 = con.prepareStatement(sqlNewLightSwitch);

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
			if(!tableList.contains("LIGHTSWITCHES")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.LIGHTSWITCHES (" +
						"date VARCHAR(10)," +
						"time VARCHAR(50)," +
						"LightSwitch VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LightSwitch> getAllLightSwitches(){
		ArrayList<LightSwitch> lights = new ArrayList<LightSwitch>();
		try {
			ResultSet rs = psGetAllLightSwitches.executeQuery();
			while (rs.next()){
				String date = rs.getString(1);
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

	public void addNewLightSwitch(boolean light){
		try {
			psNewLightSwitch.clearParameters();
			psNewLightSwitch.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewLightSwitch.setString(2, Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND);
			psNewLightSwitch.setInt(3, light ? 1:0);
			psNewLightSwitch.executeUpdate();
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
