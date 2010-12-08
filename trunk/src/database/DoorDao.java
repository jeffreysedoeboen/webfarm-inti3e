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

import model.Door;
import model.Temperature;
import model.User;

public class DoorDao {


	private String sqlGetAllDoor		= "SELECT date, time, door FROM APP.DOOR";
	private String sqlNewDoor			= "INSERT INTO APP.DOOR (\"DATE\", \"TIME\", \"DOOR\" ) VALUES (?,?,?)";

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
			if(!tableList.contains("DOOR")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.DOOR (" +
						"DATE VARCHAR(10)," +
						"TIME VARCHAR(50)," +
						"DOOR SMALLINT" +
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
				String date = rs.getString(1);
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

	public void addNewDoor( boolean open ){
		try {
			psNewDoor.clearParameters();
			psNewDoor.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewDoor.setString(2, Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND);
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

}
