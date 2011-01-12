/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:40:59
 */
package com.inti3e.model;

import java.util.Date;

/**
 * The Class Movement.
 */
public class Movement {
	
	/** The date. */
	private Date date;
	
	/** The time. */
	private String time;
	
	/** The move. */
	private String move;

	/**
	 * Instantiates a new movement.
	 *
	 * @param date the date
	 * @param time the time
	 * @param move the move
	 */
	public Movement(Date date, String time, String move) {
		this.date = date;
		this.time = time;
		this.move = move;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Gets the move.
	 *
	 * @return the move
	 */
	public String getMove() {
		return move;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Sets the move.
	 *
	 * @param move the move to set
	 */
	public void setMove(String move) {
		this.move = move;
	}

}
