package com.analytics.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.analytics.data.beans.PageHit;
import com.inti3e.model.User;

public class HitsDAO {
	private String sqlGetPagesByUserId = "SELECT page, COUNT(page) FROM APP.hits WHERE user_id = ? GROUP BY PAGE";
	private String sqlGetPagesByIp = "SELECT PAGE, COUNT(PAGE) hits FROM APP.HITS WHERE IP = ? GROUP BY PAGE ORDER BY hits desc";
	private String sqlGetAllIp = "SELECT ip FROM APP.HITS GROUP BY ip";

	private Connection con = null;
	private PreparedStatement psGetPagesByUserId = null;
	private PreparedStatement psGetPagesByIp = null;
	private PreparedStatement psGetAllIp = null;
	
	public HitsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		try {
			this.psGetPagesByUserId = con.prepareStatement(sqlGetPagesByUserId);
			this.psGetPagesByIp = con.prepareStatement(sqlGetPagesByIp);
			this.psGetAllIp = con.prepareStatement(sqlGetAllIp);
		}
		catch (SQLException se) {
			printSQLException(se);
		}
	}

	private void printSQLException(SQLException se) {
		while (se != null) {
			System.out.println("SQLException: State: " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());

			se = se.getNextException();
		}
	}
	
	public ArrayList<PageHit> getPagesByUserId(int userId) {
		ArrayList<PageHit> pageHits = new ArrayList<PageHit>();
		
		try {
			psGetPagesByUserId.setInt(1, userId);
			
			ResultSet results = psGetPagesByUserId.executeQuery();
			while (results.next()) {
				String page = results.getString(1);
				int count = results.getInt(2);
				
				pageHits.add(new PageHit(page, count));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pageHits;
	}
	
	public ArrayList<PageHit> getPagesByIp(String ip) {
		ArrayList<PageHit> pageHits = new ArrayList<PageHit>();
		try {
			psGetPagesByIp.setString(1, ip);
			
			ResultSet results = psGetPagesByIp.executeQuery();
			while (results.next()) {
				String page = results.getString(1);
				int count = results.getInt(2);
				
				pageHits.add(new PageHit(page, count));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pageHits;
	}
	
	public ArrayList<String> getAllIp() {
		ArrayList<String> IpList = new ArrayList<String>();
		try {
			ResultSet results = psGetAllIp.executeQuery();
			while (results.next()) {
				String ip = results.getString(1);

				IpList.add(ip);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return IpList;
	}
}