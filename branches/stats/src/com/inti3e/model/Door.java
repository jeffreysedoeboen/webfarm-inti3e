package com.inti3e.model;

import java.util.Date;

public class Door {
	private Date date;
	private String time;
	private int door;

	public Door(Date date, String time, int door) {
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

	public int getDoor() {
		return door;
	}

	public void setDoor( int door ) {
		this.door = door;
	}
}
