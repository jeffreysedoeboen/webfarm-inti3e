/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:13:54
 */
package com.analytics.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.analytics.data.DBmanager;
import com.analytics.data.beans.RowBean;

/**
 * The Class StatisticsDAO.
 */
public class StatisticsDAO {
	
	/** The sql hits. */
	private String sqlHits = "SELECT COUNT(*) FROM APP.visits";
	
	/** The sql get browsers. */
	private String sqlGetBrowsers = "SELECT browser, COUNT(browser) AS count FROM APP.visits GROUP BY browser ORDER BY count DESC";
	
	/** The sql get languages. */
	private String sqlGetLanguages = "SELECT language, COUNT(language) AS count FROM APP.visits GROUP BY language ORDER BY count DESC";
	
	/** The sql get ips. */
	private String sqlGetIps = "SELECT ip, COUNT(ip) AS count FROM APP.visits GROUP BY ip ORDER BY count DESC";
	
	/** The sql browser hits. */
	private String sqlBrowserHits = "SELECT COUNT(*) FROM APP.visits WHERE browser = ?";
	
	/** The sql language hits. */
	private String sqlLanguageHits = "SELECT COUNT(*) FROM APP.visits WHERE language = ?";
	
	/** The sql ip hits. */
	private String sqlIpHits = "SELECT COUNT(*) FROM APP.visits WHERE ip = ?";

	/** The con. */
	private Connection con = null;
	
	/** The ps hits. */
	private PreparedStatement psHits = null;
	
	/** The ps get browsers. */
	private PreparedStatement psGetBrowsers = null;
	
	/** The ps get languages. */
	private PreparedStatement psGetLanguages = null;
	
	/** The ps get ips. */
	private PreparedStatement psGetIps = null;
	
	/** The ps browser hits. */
	private PreparedStatement psBrowserHits = null;
	
	/** The ps language hits. */
	private PreparedStatement psLanguageHits = null;
	
	/** The ps ip hits. */
	private PreparedStatement psIpHits = null;
	
	/**
	 * Instantiates a new statistics dao.
	 */
	public StatisticsDAO() {
		DBmanager myDb = DBmanager.getInstance();
		con = myDb.getConnection();
		try {
			this.psHits = con.prepareStatement(sqlHits);
			this.psGetBrowsers = con.prepareStatement(sqlGetBrowsers);
			this.psGetLanguages = con.prepareStatement(sqlGetLanguages);
			this.psGetIps = con.prepareStatement(sqlGetIps);
			this.psBrowserHits = con.prepareStatement(sqlBrowserHits);
			this.psLanguageHits = con.prepareStatement(sqlLanguageHits);
			this.psIpHits = con.prepareStatement(sqlIpHits);
		}
		catch (SQLException se) {
			printSQLException(se);
		}
	}

	/**
	 * Prints the sql exception.
	 *
	 * @param se the sqlexception
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
	 * Gets the hits.
	 *
	 * @return the hits
	 */
	public int getHits() {
		try {
			ResultSet results = psHits.executeQuery();
			if (results.next()) return results.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Gets the hits per browser.
	 *
	 * @return the hits per browser
	 */
	public ArrayList<RowBean> getHitsPerBrowser() {
		ArrayList<RowBean> browsers = new ArrayList<RowBean>();
		try {
			ResultSet browsersSet = psGetBrowsers.executeQuery();
			while (browsersSet.next()) {
				String currentBrowser = browsersSet.getString(1);
				
				psBrowserHits.clearParameters();
				psBrowserHits.setString(1, currentBrowser);
				ResultSet browserSet = psBrowserHits.executeQuery();
				
				if (browserSet.next())
					browsers.add(new RowBean(browserSet.getInt(1), currentBrowser));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return browsers;
	}
	
	/**
	 * Gets the hits per language.
	 *
	 * @return the hits per language
	 */
	public ArrayList<RowBean> getHitsPerLanguages() {
		ArrayList<RowBean> languages = new ArrayList<RowBean>();
		try {
			ResultSet languagesSet = psGetLanguages.executeQuery();
			while (languagesSet.next()) {
				String currentLanguage = languagesSet.getString(1);
				
				psLanguageHits.clearParameters();
				psLanguageHits.setString(1, currentLanguage);
				ResultSet languageSet = psLanguageHits.executeQuery();
				
				if (languageSet.next())
					languages.add(new RowBean(languageSet.getInt(1), currentLanguage));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return languages;
	}
	
	/**
	 * Gets the hits per ip.
	 *
	 * @return the hits per ip
	 */
	public ArrayList<RowBean> getHitsPerIp() {
		ArrayList<RowBean> ips = new ArrayList<RowBean>();
		try {
			ResultSet ipsSet = psGetIps.executeQuery();
			while (ipsSet.next()) {
				String currentIp = ipsSet.getString(1);
				
				psIpHits.clearParameters();
				psIpHits.setString(1, currentIp);
				ResultSet ipSet = psIpHits.executeQuery();
				
				if (ipSet.next())
					ips.add(new RowBean(ipSet.getInt(1), currentIp));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ips;
	}
}
