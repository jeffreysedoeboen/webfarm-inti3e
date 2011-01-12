/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:41:06
 */
package com.inti3e.model;

import java.sql.Date;

/**
 * The Class Temperature.
 */
public class Temperature {
	
	/** The date. */
	private Date date = null;
	
	/** The time. */
	private String time = "";
	
	/** The temp. */
	private String temp = "";
	
	/**
	 * Instantiates a new temperature.
	 *
	 * @param date the date
	 * @param time the time
	 * @param temp the temp
	 */
	public Temperature(Date date, String time, String temp) {
		this.date = date;
		this.time = time;
		this.temp = temp;
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
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Gets the temp.
	 *
	 * @return the temp
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Sets the temp.
	 *
	 * @param temp the temp to set
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	/**
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public String getcoordinates() {
		return "[" + getDate().getTime() + ", " + getTemp() + "]";
	}

}
