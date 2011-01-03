package com.analytics.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.inti3e.database.DBmanager;

public class AnalyticsDAO {
	private String sqlCreateVisit = "INSERT INTO APP.visits (website_id, ip, browser, language, date) VALUES (?, ?, ?, ?, ?)";
	private String sqlSelectId = "SELECT id FROM APP.websites WHERE website = ?";

	private Connection con = null;
	private PreparedStatement psCreateVisit = null;
	private PreparedStatement psSelectId = null;
	
	public AnalyticsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try {
			this.psCreateVisit = con.prepareStatement(sqlCreateVisit);
			this.psSelectId = con.prepareStatement(sqlSelectId);
		}
		catch (SQLException se) {
			printSQLException(se);
		}
	}
	
	private void createTable() {
		try {
			//get the table listing
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, null,  new String[] {"TABLE"});
			ArrayList<String> tableList = new ArrayList<String>();
			while(rs.next()){
				tableList.add(rs.getString("TABLE_NAME"));
			}
			//check if the table does not already exists and then create them if needed
			if(!tableList.contains("VISITS")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.VISITS (" +
						"website_id INT," +
						"ip VARCHAR(50)," +
						"browser VARCHAR(50)," +
						"language VARCHAR(50)," +
						"date DATE" +
				")");
			}
			if(!tableList.contains("WEBSITES")){
				Statement stat = con.createStatement();
				stat.execute("CREATE TABLE APP.WEBSITES (" +
						"website varchar(50)," +
						"id INT" +
				")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createVisit(int websiteId, String ip, String browser, String language) {
		try {
			psCreateVisit.clearParameters();
			
			psCreateVisit.setInt(1, websiteId);
			psCreateVisit.setString(2, ip);
			psCreateVisit.setString(3, browser);
			psCreateVisit.setString(4, language);
			psCreateVisit.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			
			int resultaat = psCreateVisit.executeUpdate();
			if (resultaat == 0) {
				System.out.println("unsuccessfull insert");
			}
		} 
		catch (SQLException se) {
			printSQLException(se);
		}
	}
	
	@SuppressWarnings("unused")
	private int getWebsiteId(String website) {
		try {
			psSelectId.clearParameters();
			psSelectId.setString(1, website);
			
			ResultSet set = psSelectId.executeQuery();
			if (set.first()) {
				return set.getInt(1);
			}
		}
		catch (SQLException se) {
			printSQLException(se);
		}
		return 0;
	}

	private void printSQLException(SQLException se) {
		while (se != null) {
			System.out.println("SQLException: State: " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());

			se = se.getNextException();
		}
	}

}
