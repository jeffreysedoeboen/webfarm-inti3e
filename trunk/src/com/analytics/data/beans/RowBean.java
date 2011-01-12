/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:11:14
 */
package com.analytics.data.beans;

/**
 * The Class RowBean.
 */
public class RowBean {
	
	/** The count. */
	private int count = 0;
	
	/** The value. */
	private String value = "";
	
	/**
	 * Instantiates a new row bean.
	 *
	 * @param count the count
	 * @param value the value
	 */
	public RowBean(int count, String value) {
		this.setCount(count);
		this.setValue(value);
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
