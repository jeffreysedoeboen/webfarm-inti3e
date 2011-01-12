/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:40:37
 */
package com.inti3e.model;

import java.util.Date;

/**
 * The Class Humidity.
 */
public class Humidity {
	
	/** The date. */
	private Date date;
	
	/** The time. */
	private String time;
	
	/** The humidity. */
	private int humidity;
	
	/**
	 * Instantiates a new humidity.
	 *
	 * @param date the date
	 * @param time the time
	 * @param humidity the humidity
	 */
	public Humidity(Date date, String time, int humidity) {
		this.date = date;
		this.time = time;
		this.humidity = humidity;
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
	 * Gets the humidity.
	 *
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
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
	 * Sets the humidity.
	 *
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	/**
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public String getcoordinates() {
		return "[" + getTime() + ", " + getHumidity() + "]";
	}

}
