package com.analytics.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticsDAO {
	private String sqlCreateVisit = "INSERT INTO APP.visits (website_id, ip, browser, language, date) VALUES (?, ?, ?, ?, ?)";
	private String sqlSelectId = "SELECT id FROM APP.websites WHERE website = ?";

	private Connection con = null;
	private PreparedStatement psCreateVisit = null;
	private PreparedStatement psSelectId = null;
	
	public AnalyticsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		try {
			this.psCreateVisit = con.prepareStatement(sqlCreateVisit);
			this.psSelectId = con.prepareStatement(sqlSelectId);
		}
		catch (SQLException se) {
			printSQLException(se);
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
