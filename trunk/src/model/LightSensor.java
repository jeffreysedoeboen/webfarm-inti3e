package model;

import java.util.Date;

public class LightSensor {
	private String date;
	private String time;
	private String light;

	public LightSensor(String date, String time, String light) {
		this.date = date;
		this.time = time;
		this.light = light;
	}

	public String getDate() {
		return date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime( String time ) {
		this.time = time;
	}

	public String getLight() {
		return light;
	}

	public void setLight( String light ) {
		this.light = light;
	}
	
	

}
