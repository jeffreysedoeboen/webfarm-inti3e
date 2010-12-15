package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.inti3e.model.Movement;

import com.inti3e.database.DBmanager;


public class MovementDao {


	private String sqlGetAllMovements	= "SELECT date, time, movement FROM APP.MOVEMENT";
	private String sqlNewMovement		= "INSERT INTO APP.MOVEMENT (\"DATE\", \"TIME\", \"MOVEMENT\" ) VALUES (?,?,?)";

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
			if(!tableList.contains("MOVEMENT")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.MOVEMENT (" +
						"DATE DATE," +
						"TIME VARCHAR(50)," +
						"MOVEMENT VARCHAR(25)" +
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
			se.printStackTrace();	
		} 
		return movements;
	}

	public void addNewMovement(boolean movement){
		try {
			Calendar calendar = Calendar.getInstance();
			psNewMovement.clearParameters();
			psNewMovement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			psNewMovement.setString(2, "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
			psNewMovement.setInt(3, movement ? 1:0);
			psNewMovement.executeUpdate();
		} catch (SQLException se) {
//			printSQLException(se) ;
			se.printStackTrace();
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
