/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:41:12
 */
package com.inti3e.model;

/**
 * The Class User.
 */
public class User {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The password. */
	private String password;
	
	/** The admin. */
	private boolean admin;

	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param name the name
	 * @param password the password
	 * @param admin the admin
	 */
	public User(int id, String name, String password, boolean admin) {
		this.setId(id);
		this.name = name;
		this.password = password;
		this.admin = admin;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Checks if is admin.
	 *
	 * @return true, if is admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
