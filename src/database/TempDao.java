package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import model.Temperature;

public class TempDao {


	private String sqlGetAllTemps		= "SELECT date, time, temp FROM APP.Temp";
	private String sqlNewTemp 			= "INSERT INTO APP.Temp (\"DATE\", \"TIME\", \"TEMP\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllTemps = null ;
	private PreparedStatement psNewTemp = null;


	public TempDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllTemps   = con.prepareStatement(sqlGetAllTemps);
			this.psNewTemp 		 = con.prepareStatement(sqlNewTemp);

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
						"DATE VARCHAR(10)," +
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
				String date = rs.getString(1);
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
			psNewTemp.clearParameters();
			psNewTemp.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewTemp.setString(2, "" + Calendar.HOUR_OF_DAY) ;
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

}
