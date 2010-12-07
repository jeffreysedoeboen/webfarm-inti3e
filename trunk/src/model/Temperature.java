package model;

import java.util.Date;

public class Temperature {
	private Date date = null;
	private String time = "";
	private String temp = "";
	
	public Temperature(Date date, String time, String temp) {
		this.date = date;
		this.time = time;
		this.temp = temp;
	}

}
