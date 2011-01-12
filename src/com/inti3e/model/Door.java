/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:40:28
 */
package com.inti3e.model;

import java.util.Date;

/**
 * The Class Door.
 */
public class Door {
	
	/** The date. */
	private Date date;
	
	/** The time. */
	private String time;
	
	/** The door. */
	private int door;

	/**
	 * Instantiates a new door.
	 *
	 * @param date the date
	 * @param time the time
	 * @param door the door
	 */
	public Door(Date date, String time, int door) {
		this.date = date;
		this.time = time;
		this.door = door;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate( Date date ) {
		this.date = date;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime( String time ) {
		this.time = time;
	}

	/**
	 * Gets the door.
	 *
	 * @return the door
	 */
	public int getDoor() {
		return door;
	}

	/**
	 * Sets the door.
	 *
	 * @param door the new door
	 */
	public void setDoor( int door ) {
		this.door = door;
	}
}
