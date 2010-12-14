package com.inti3e.model;

import java.util.Date;

public class Humidity {
	private Date date;
	private String time;
	private int humidity;
	
	public Humidity(Date date, String time, int humidity) {
		this.date = date;
		this.time = time;
		this.humidity = humidity;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	public String getcoordinates() {
		return "[" + getTime() + ", " + getHumidity() + "]";
	}

}
