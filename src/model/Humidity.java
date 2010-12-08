package model;

import java.util.Date;

public class Humidity {
	private String date;
	private String time;
	private int humidity;
	
	public Humidity(String date, String time, int humidity) {
		this.date = date;
		this.time = time;
		this.humidity = humidity;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
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
	public void setDate(String date) {
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

}
