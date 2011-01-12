/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:40:44
 */
package com.inti3e.model;

import java.util.Date;

/**
 * The Class LightSensor.
 */
public class LightSensor {
	
	/** The date. */
	private Date date;
	
	/** The time. */
	private String time;
	
	/** The light. */
	private String light;

	/**
	 * Instantiates a new light sensor.
	 *
	 * @param date the date
	 * @param time the time
	 * @param light the light
	 */
	public LightSensor(Date date, String time, String light) {
		this.date = date;
		this.time = time;
		this.light = light;
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
	 * Gets the light.
	 *
	 * @return the light
	 */
	public String getLight() {
		return light;
	}

	/**
	 * Sets the light.
	 *
	 * @param light the new light
	 */
	public void setLight( String light ) {
		this.light = light;
	}
	
	

}
