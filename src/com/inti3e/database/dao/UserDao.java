package com.inti3e.database.dao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.inti3e.database.DBmanager;
import com.inti3e.model.User;


public class UserDao {
	
	private String sqlGetAllUsers		= "SELECT NAME, PASSWORD FROM APP.USERS";
	private String sqlNewUser 			= "INSERT INTO APP.USERS (\"NAME\", \"PASSWORD\" ) VALUES (?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllUsers = null ;
	private PreparedStatement psNewUser = null;
	

	public UserDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try{

			this.psGetAllUsers   = con.prepareStatement(sqlGetAllUsers);
			this.psNewUser 		 = con.prepareStatement(sqlNewUser);

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
			if(!tableList.contains("USERS")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.USERS (" +
						"NAME VARCHAR(50) PRIMARY KEY," +
						"PASSWORD VARCHAR(50)" +
						")");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			ResultSet rs = psGetAllUsers.executeQuery();
			while (rs.next()){
				String name = rs.getString(1);
				String password = rs.getString(2);
				User s = new User(name, password, false);
				users.add(s);
			}
		} catch (SQLException se) {
			printSQLException(se) ;		
		} 
		return users;
	}
	
	public void addNewUser(String name, String password){
		try {
			psNewUser.clearParameters();
			psNewUser.setString(1, name);
			psNewUser.setString(2, password);
			psNewUser.executeUpdate();
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

	public boolean nameIsAvailable(String nickname) {
		boolean doesexists = false;
		ArrayList<User> allUsers = getAllUsers();
		for(User s: allUsers) {
			if(s.getName().equals(nickname)) {
				doesexists = true;
			}
		}
		return doesexists;
	}	

}
