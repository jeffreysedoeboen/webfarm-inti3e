/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:21:32
 */
package com.inti3e.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.inti3e.database.DBmanager;
import com.inti3e.model.User;

/**
 * The Class UserDao.
 */
public class UserDao {
	
	/** The sql get all users. */
	private String sqlGetAllUsers		= "SELECT ID, NAME, PASSWORD FROM APP.USERS ORDER BY ID ASC";
	
	/** The sql new user. */
	private String sqlNewUser 			= "INSERT INTO APP.USERS (\"NAME\", \"PASSWORD\" ) VALUES (?,?)";
	
	/** The sql remove user. */
	private String sqlRemoveUser		= "DELETE FROM APP.USERS WHERE NAME=?";

	/** The con. */
	private Connection        con      = null ;
	
	/** The ps get all users. */
	private PreparedStatement psGetAllUsers = null ;
	
	/** The ps new user. */
	private PreparedStatement psNewUser = null;
	
	/** The ps remove user. */
	private PreparedStatement psRemoveUser = null;
	

	/**
	 * Instantiates a new user dao.
	 */
	public UserDao(){
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		
		try{

			this.psGetAllUsers   = con.prepareStatement(sqlGetAllUsers);
			this.psNewUser 		 = con.prepareStatement(sqlNewUser);
			this.psRemoveUser	 = con.prepareStatement(sqlRemoveUser);

		} catch(SQLException se) {
			printSQLException(se) ;
		}
	}
	
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
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
	
	/**
	 * Adds the new user.
	 *
	 * @param name the name
	 * @param password the password
	 */
	public void addNewUser(String name, String password){
		//asserts
		assert (name != null);
		assert (password != null);
		
		try {
			psNewUser.clearParameters();
			psNewUser.setString(1, name);
			psNewUser.setString(2, password);
			psNewUser.executeUpdate();
		} catch (SQLException se) {
			printSQLException(se) ;
		}
	}
	
	/**
	 * Removes the user.
	 *
	 * @param name the name
	 */
	public void removeUser(String name) {
		//asserts
		assert (name != null);
		
		try {
			psRemoveUser.clearParameters();
			psRemoveUser.setString(1, name);
			psRemoveUser.executeUpdate();
		} catch (SQLException se) {
			printSQLException(se) ;
		}
	}
	
	/**
	 * Prints the sql exception.
	 *
	 * @param se the se
	 */
	private void printSQLException(SQLException se) {
		while(se != null) {

			System.out.print("SQLException: State:   " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());			

			se = se.getNextException();
		}
	}

	/**
	 * Name is available.
	 *
	 * @param nickname the nickname
	 * @return true, if successful
	 */
	public boolean nameIsAvailable(String nickname) {
		
		//asserts
		assert(nickname != null);
		
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
