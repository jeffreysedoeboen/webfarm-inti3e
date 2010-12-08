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

import model.Movement;
import model.Temperature;
import model.User;

public class MovementDao {


	private String sqlGetAllMovements	= "SELECT date, time, movement FROM APP.Movement";
	private String sqlNewMovement		= "INSERT INTO APP.Movement (\"DATE \", \"TIME\", \"MOVEMENT\" ) VALUES (?,?,?)";

	private Connection        con      			= null ;
	private PreparedStatement psGetAllMovements = null ;
	private PreparedStatement psNewMovement		 = null;


	public MovementDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllMovements   = con.prepareStatement(sqlGetAllMovements);
			this.psNewMovement 		 = con.prepareStatement(sqlNewMovement);

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
			if(!tableList.contains("Movement")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.Movement (" +
						"date VARCHAR(10)" +
						"time VARCHAR(50)," +
						"movement VARCHAR(25)" +
				")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Movement> getAllMovements(){
		ArrayList<Movement> movements = new ArrayList<Movement>();
		try {
			ResultSet rs = psGetAllMovements.executeQuery();
			while (rs.next()){
				Date date = rs.getDate(1);
				String time = rs.getString(2);
				String move = rs.getString(3);
				Movement movement = new Movement(date, time, move);
				movements.add(movement);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return movements;
	}

	public void addNewMovement(boolean movement){
		try {
			psNewMovement.clearParameters();
			psNewMovement.setString(1, Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH );
			psNewMovement.setString(2, Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND);
			psNewMovement.setInt(3, movement ? 1:0);
			psNewMovement.executeUpdate();
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
