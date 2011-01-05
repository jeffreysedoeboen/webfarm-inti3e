package com.inti3e.model;

import java.sql.Date;

public class Temperature {
	private Date date = null;
	private String time = "";
	private String temp = "";
	
	public Temperature(Date date, String time, String temp) {
		this.date = date;
		this.time = time;
		this.temp = temp;
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
	 * @return the temp
	 */
	public String getTemp() {
		return temp;
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
	 * @param temp the temp to set
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String getcoordinates() {
		return "[" + getDate().getTime() + ", " + getTemp() + "]";
	}

}
