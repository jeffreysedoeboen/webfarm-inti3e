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
	
	private String sqlGetAllUsers		= "SELECT ID, NAME, PASSWORD FROM APP.USERS";
	private String sqlNewUser 			= "INSERT INTO APP.USERS (\"NAME\", \"PASSWORD\" ) VALUES (?,?)";

	private Connection        con      = null ;
	private PreparedStatement psGetAllUsers = null ;
	private PreparedStatement psNewUser = null;
	

	public UserDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllUsers   = con.prepareStatement(sqlGetAllUsers);
			this.psNewUser 		 = con.prepareStatement(sqlNewUser);

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			ResultSet rs = psGetAllUsers.executeQuery();
			while (rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				User s = new User(id, name, password, false);
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