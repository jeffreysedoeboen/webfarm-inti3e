package model;

import java.util.Date;

public class Movement {
	private Date date;
	private String time;
	private String move;

	public Movement(Date date, String time, String move) {
		this.date = date;
		this.time = time;
		this.move = move;
	}

}
