package model;

import java.util.Date;

public class Movement {
	private String date;
	private String time;
	private String move;

	public Movement(String date, String time, String move) {
		this.date = date;
		this.time = time;
		this.move = move;
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
	 * @return the move
	 */
	public String getMove() {
		return move;
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
	 * @param move the move to set
	 */
	public void setMove(String move) {
		this.move = move;
	}

}
