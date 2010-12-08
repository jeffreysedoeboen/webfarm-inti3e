package model;

public class LightSwitch {
	private String light;
	private String date;
	private String time;

	public LightSwitch(String date, String time, String light) {
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
	
	

}
