/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:11:47
 */
package com.analytics.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class CreateDBTables.
 */
public class CreateDBTables {
	//Attributen
	/** The connection. */
	private Connection conn;
	
	//Constructors
	/**
	 * Instantiates a new creates the db tables.
	 */
	public CreateDBTables(){
		DBmanager dbm = DBmanager.getInstance();
		conn = dbm.getConnection();
		try {
			createTablesIfNotExists();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Methoden
	/**
	 * Creates the tables if they do not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createTablesIfNotExists() throws SQLException {
		createVisitsTableIfNotExists();
		createHitsTableIfNotExists();
		createWebsitesTableIfNotExists();
	}
	
	/**
	 * Creates the users table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createVisitsTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("VISITS")) {
			System.err.println("Table APP.VISITS not yet created, it will be created.");
			
			//Create table APP.VISITS
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE APP.VISITS (" +
					"website_id INT," +
					"ip VARCHAR(50)," +
					"user_id INT," +
					"browser VARCHAR(50)," +
					"language VARCHAR(50)," +
					"date DATE" +
			")");
			stmt.close();
		}
	}
	
	/**
	 * Creates the games table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createHitsTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("HITS")) {
			System.err.println("Table APP.HITS not yet created, it will be created.");
			
			//Create table APP.HITS
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE APP.HITS (" +
					"website_id INT," +
					"ip VARCHAR(50)," +
					"user_id INT," +
					"page VARCHAR(255)," +
					"date DATE" +
			")");
			stmt.close();
		}
	}
	
	/**
	 * Creates the results table if it does not exist.
	 *
	 * @throws SQLException the sQL exception
	 */
	private void createWebsitesTableIfNotExists() throws SQLException {
		if (!checkIfTableExists("WEBSITES")) {
			System.err.println("Table APP.WEBSITES not yet created, it will be created.");

			//Create table APP.WEBSITES
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE APP.WEBSITES (" +
					"website varchar(50)," +
					"id INT" +
			")");
			stmt.close();
		}
	}
	
	/**
	 * Check if the table exists.
	 *
	 * @param name the name of the table
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 */
	private boolean checkIfTableExists(String name) throws SQLException {
		ResultSet tableNames = conn.getMetaData().getTables( null, null, name, new String[] {"TABLE"});
		boolean containsTable = false;
		while(tableNames.next()) {
			String tableName = tableNames.getString("TABLE_NAME");
			if (tableName.equals(name)) {
				containsTable = true;
			}
		}
		return containsTable;
	}
}