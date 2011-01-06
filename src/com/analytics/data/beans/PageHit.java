package com.analytics.data.beans;

public class PageHit {
	private String page;
	private int hits;
	
	public PageHit(String page, int hits) {
		this.setPage(page);
		this.setHits(hits);
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getHits() {
		return hits;
	}
}
