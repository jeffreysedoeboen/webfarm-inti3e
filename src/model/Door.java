package model;

import java.util.Date;

public class Door {
	private Date date;
	private String time;
	private String door;

	public Door(Date date, String time, String door) {
		this.date = date;
		this.time = time;
		this.door = door;
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

	public String getDoor() {
		return door;
	}

	public void setDoor( String door ) {
		this.door = door;
	}
}
