package com.inti3e.model;

public class User {
	private String name;
	private String password;
	private boolean admin;

	public User(String name, String password, boolean admin) {
		this.name = name;
		this.password = password;
		this.admin = admin;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
}
