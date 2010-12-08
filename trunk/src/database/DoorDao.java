package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import model.Door;
import model.Temperature;
import model.User;

public class DoorDao {


	private String sqlGetAllDoor		= "SELECT date, time, door FROM APP.Door";
	private String sqlNewDoor			= "INSERT INTO APP.Door (\"DATE \", \"TIME\", \"DOOR\" ) VALUES (?,?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllDoor = null ;
	private PreparedStatement psNewDoor = null;


	public DoorDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllDoor   = con.prepareStatement(sqlGetAllDoor);
			this.psNewDoor 		 = con.prepareStatement(sqlNewDoor);

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
			if(!tableList.contains("Door")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.Door (" +
						"date DATE," +
						"time VARCHAR(50)," +
						"door VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Door> getAllTemps(){
		ArrayList<Door> doors = new ArrayList<Door>();
		try {
			ResultSet rs = psGetAllDoor.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				String door = rs.getString(3);
				Door d = new Door(date, time, door);
				doors.add(d);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return doors;
	}

	public void addNewTemp(Date date, String time, String temperature){
		try {
			psNewDoor.clearParameters();
			psNewDoor.setDate(1, new java.sql.Date(date.getTime()));
			psNewDoor.setString(2, time);
			psNewDoor.setString(3, temperature);
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

}
