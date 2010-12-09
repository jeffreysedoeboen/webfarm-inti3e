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

import model.Humidity;

public class HumidityDao {


	private String sqlGetAllTemps		= "SELECT date, time, humidity FROM APP.Humidity";
	private String sqlNewTemp 			= "INSERT INTO APP.Humidity (\"DATE\", \"TIME\", \"HUMIDITY\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllHumids = null ;
	private PreparedStatement psNewHumid = null;


	public HumidityDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllHumids   = con.prepareStatement(sqlGetAllTemps);
			this.psNewHumid 	  = con.prepareStatement(sqlNewTemp);

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
						"DATE VARCHAR(10)," +
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
				String date = rs.getString(1);
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
		try {
			psNewHumid.clearParameters();
			psNewHumid.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewHumid.setString(2,"" + Calendar.HOUR_OF_DAY);
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

}
