package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import model.Temperature;
import model.User;

public class TempDao {


	private String sqlGetAllTemps		= "SELECT name, password FROM APP.Temp";
	private String sqlNewTemp 			= "INSERT INTO APP.Temp (\"DATE \", \"TIME\", \"TEMP\" ) VALUES (?,?,?)";

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
			if(!tableList.contains("Users")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.Temp (" +
						"date DATE," +
						"time VARCHAR(50)," +
						"temp VARCHAR(25)" +
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

	public void addNewTemp(Date date, String time, String temperature){
		try {
			psNewTemp.clearParameters();
			psNewTemp.setDate(1, new java.sql.Date(date.getTime()));
			psNewTemp.setString(2, time);
			psNewTemp.setString(3, temperature);
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
