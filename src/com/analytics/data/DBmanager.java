/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:12:58
 */
package com.analytics.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Class DBmanager.
 */
public class DBmanager {
	
	/** The unique instance. */
	private static DBmanager uniqueInstance=null;
	
	/** The con. */
	private Connection con = null ;
	
	/** The driver. */
	private final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
	
	/** The dbname. */
	private final String dbname = "jdbc:derby:statisticsDB;create=true";
	
	/**
	 * Instantiates a new d bmanager.
	 */
	private DBmanager(){
		if(!dbExists(dbname))
		{	
			System.err.println("Er is geen database....") ;			
		}
	//	udao = new UrlDao(con);
	}
	
	

	/**
	 * Gets the single instance of DBmanager.
	 *
	 * @return single instance of DBmanager
	 */
	public static synchronized DBmanager getInstance() {
		if (uniqueInstance==null) {
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}
	
	/**
	 * Db exists.
	 *
	 * @param dataSource the data source
	 * @return the boolean
	 */
	private Boolean dbExists(String dataSource) {
		boolean exists = false;
		try{
			Class.forName(driver) ;
            con = DriverManager.getConnection(dataSource);
           
			exists = true ;
		} catch(Exception e){
			; // Do nothing, as DB does not (yet) exist
		}
		return(exists) ;
	}

	/**
	 * Close.
	 */
	public void close() {
		try {
			con = DriverManager.getConnection(dbname + ";shutdown=true") ;
		}catch(SQLException se) {
			; // Do Nothing. System has shut down.
		con = null ;
		}
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return con;
	}
}
