package com.inti3e.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {
	
	private static DBmanager uniqueInstance=null;
	
	private Connection con = null ;
	private final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
	private final String dbname = "jdbc:derby:var/lib/tomcat6/webfarmdb;create=true" ;
        
	
	private DBmanager(){
		if(!dbExists(dbname))
		{	
			System.err.println("Er is geen database....") ;			
		}
	//	udao = new UrlDao(con);
	}
	
	

	public static synchronized DBmanager getInstance() {
		if (uniqueInstance==null) {
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}
	
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

	public void close() {
		try {
			con = DriverManager.getConnection(dbname + ";shutdown=true") ;
		}catch(SQLException se) {
			; // Do Nothing. System has shut down.
		con = null ;
		}
	}
	
	public Connection getConnection() {
		return con;
	}
}
