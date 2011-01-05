package com.inti3e.model;

public class User {
	private int id;
	private String name;
	private String password;
	private boolean admin;

	public User(int id, String name, String password, boolean admin) {
		this.setId(id);
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
