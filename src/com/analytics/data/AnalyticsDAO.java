package com.analytics.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.analytics.data.DBmanager;

public class AnalyticsDAO {
	private String sqlCreateVisit = "INSERT INTO APP.visits (website_id, ip, user_id, browser, language, date) VALUES (?, ?, ?, ?, ?, ?)";
	private String sqlCreateHit = "INSERT INTO APP.hits (website_id, ip, user_id, page, date) VALUES (?, ?, ?, ?, ?)";
	private String sqlSelectId = "SELECT id FROM APP.websites WHERE website = ?";

	private Connection con = null;
	private PreparedStatement psCreateVisit = null;
	private PreparedStatement psCreateHit = null;
	private PreparedStatement psSelectId = null;
	
	public AnalyticsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		createTable();
		try {
			this.psCreateVisit = con.prepareStatement(sqlCreateVisit);
			this.psCreateHit = con.prepareStatement(sqlCreateHit);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createVisit(int websiteId, String ip, int userId, String browser, String language) {
		try {
			psCreateVisit.clearParameters();
			
			psCreateVisit.setInt(1, websiteId);
			psCreateVisit.setString(2, ip);
			psCreateVisit.setInt(3, userId);
			psCreateVisit.setString(4, browser);
			psCreateVisit.setString(5, language);
			psCreateVisit.setDate(6, new java.sql.Date(System.currentTimeMillis()));
			
			int resultaat = psCreateVisit.executeUpdate();
			if (resultaat == 0) {
				System.out.println("unsuccessfull insert");
			}
		} 
		catch (SQLException se) {
			printSQLException(se);
		}
	}
	
	public void createHit(int websiteId, String ip, int userId, String page) {
		try {
			psCreateHit.clearParameters();
			
			psCreateHit.setInt(1, websiteId);
			psCreateHit.setString(2, ip);
			psCreateHit.setInt(3, userId);
			psCreateHit.setString(4, page);
			psCreateHit.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			
			int resultaat = psCreateHit.executeUpdate();
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
