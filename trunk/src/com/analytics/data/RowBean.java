package com.analytics.data;

public class RowBean {
	private int count = 0;
	private String value = "";
	
	public RowBean(int count, String value) {
		this.setCount(count);
		this.setValue(value);
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
