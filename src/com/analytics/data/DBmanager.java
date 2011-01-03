package com.analytics.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {
	
	private static DBmanager uniqueInstance=null;
	
	private Connection con = null ;
	private final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private final String dbname = "jdbc:derby:/home/marco/workspace/db";
	
	private DBmanager(){
		if(!dbExists(dbname)) {
			System.err.println("de database bestaat niet....") ;			
		}
	}

	public static synchronized DBmanager getInstance() {
		if (uniqueInstance==null) {
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}
	
	private Boolean dbExists(String dataSource) {
		Boolean exists = false ;
		try {
			Class.forName(driver);
            con = DriverManager.getConnection(dataSource, "root", "");
           
			exists = true ;
		} catch(Exception e) {}
		
		return(exists) ;
	}

	public void close() {
		try {
			con = DriverManager.getConnection(dbname + ";shutdown=true") ;
		}catch(SQLException se) {
			con = null ;
		}
	}
	
	public Connection getConnection() {
		return con;
	}
}
