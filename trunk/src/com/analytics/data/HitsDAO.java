/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:13:30
 */
package com.analytics.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.analytics.data.beans.PageHit;

/**
 * The Class HitsDAO.
 */
public class HitsDAO {
	/** The sql get pages by ip. */
	private String sqlGetPagesByIp = "SELECT PAGE, COUNT(PAGE) hits FROM APP.HITS WHERE IP = ? GROUP BY PAGE ORDER BY hits desc";
	
	/** The sql get all ip. */
	private String sqlGetAllIp = "SELECT ip FROM APP.HITS GROUP BY ip";

	/** The con. */
	private Connection con = null;
	
	/** The ps get pages by ip. */
	private PreparedStatement psGetPagesByIp = null;
	
	/** The ps get all ip. */
	private PreparedStatement psGetAllIp = null;
	
	/**
	 * Instantiates a new hits dao.
	 */
	public HitsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		try {
			this.psGetPagesByIp = con.prepareStatement(sqlGetPagesByIp);
			this.psGetAllIp = con.prepareStatement(sqlGetAllIp);
		}
		catch (SQLException se) {
			printSQLException(se);
		}
	}

	/**
	 * Prints the sql exception.
	 *
	 * @param se the sql exception
	 */
	private void printSQLException(SQLException se) {
		while (se != null) {
			System.out.println("SQLException: State: " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());

			se = se.getNextException();
		}
	}
	
	/**
	 * Gets the pages by ip.
	 *
	 * @param ip the ip
	 * @return the pages by ip
	 */
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
	
	/**
	 * Gets the all ip.
	 *
	 * @return the all ip
	 */
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