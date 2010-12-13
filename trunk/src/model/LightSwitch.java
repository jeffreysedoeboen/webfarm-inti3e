package model;

import java.util.Date;

public class LightSwitch {
	private String light;
	private Date date;
	private String time;

	public LightSwitch(Date date, String time, String light) {
		this.light = light;
		this.date = date;
		this.light = light;
	}

	public String getLight() {
		return light;
	}

	public void setLight( String light ) {
		this.light = light;
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime( String time ) {
		this.time = time;
	}
	
	

}
