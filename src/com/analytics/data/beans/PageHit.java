/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:10:37
 */
package com.analytics.data.beans;

/**
 * The Class PageHit.
 */
public class PageHit {
	
	/** The page. */
	private String page;
	
	/** The hits. */
	private int hits;
	
	/**
	 * Instantiates a new page hit.
	 *
	 * @param page the page
	 * @param hits the hits
	 */
	public PageHit(String page, int hits) {
		this.setPage(page);
		this.setHits(hits);
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Sets the hits.
	 *
	 * @param hits the new hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}

	/**
	 * Gets the hits.
	 *
	 * @return the hits
	 */
	public int getHits() {
		return hits;
	}
}
