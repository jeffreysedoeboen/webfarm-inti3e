package com.analytics.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.inti3e.database.DBmanager;

public class StatisticsDAO {
	private String sqlHits = "SELECT COUNT(*) FROM APP.visits";
	
	private String sqlGetBrowsers = "SELECT browser, COUNT(browser) AS count FROM APP.visits GROUP BY browser ORDER BY count DESC";
	private String sqlGetLanguages = "SELECT language, COUNT(language) AS count FROM APP.visits GROUP BY language ORDER BY count DESC";
	private String sqlGetIps = "SELECT ip, COUNT(ip) AS count FROM APP.visits GROUP BY ip ORDER BY count DESC";
	
	private String sqlBrowserHits = "SELECT COUNT(*) FROM APP.visits WHERE browser = ?";
	private String sqlLanguageHits = "SELECT COUNT(*) FROM APP.visits WHERE language = ?";
	private String sqlIpHits = "SELECT COUNT(*) FROM APP.visits WHERE ip = ?";

	private Connection con = null;
	private PreparedStatement psHits = null;
	private PreparedStatement psGetBrowsers = null;
	private PreparedStatement psGetLanguages = null;
	private PreparedStatement psGetIps = null;
	private PreparedStatement psBrowserHits = null;
	private PreparedStatement psLanguageHits = null;
	private PreparedStatement psIpHits = null;
	
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
	
	

	private void printSQLException(SQLException se) {
		while (se != null) {
			System.out.println("SQLException: State: " + se.getSQLState());
			System.out.println("Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());

			se = se.getNextException();
		}
	}
	
	public int getHits() {
		try {
			ResultSet results = psHits.executeQuery();
			if (results.next()) return results.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
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
