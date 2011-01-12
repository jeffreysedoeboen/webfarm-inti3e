/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:14:05
 */
package com.analytics.model;

import javax.servlet.http.Cookie;

/**
 * The Class Helper.
 */
public class Helper {
	
	/**
	 * Gets the cookie time value.
	 *
	 * @param cookies the cookies
	 * @return the cookie time value
	 */
	public String getCookieTimeValue(Cookie[] cookies) {
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Time")) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the browser.
	 *
	 * @param userAgent the user agent
	 * @return the browser
	 */
	public String getBrowser(String userAgent) {
		userAgent = userAgent.toLowerCase();
		if (userAgent.contains("chrome")) {
			return "Chrome";
		}
		else if (userAgent.contains("firefox")) {
			return "Firefox";
		}
		else if (userAgent.contains("msie")) {
			return "Internet Explorer";
		}
		else if (userAgent.contains("opera")) {
			return "Opera";
		}
		else if (userAgent.contains("safari")) {
			return "Safari";
		}
		return "Unknown";
	}
	
	/**
	 * Gets the language.
	 *
	 * @param language the language
	 * @return the language
	 */
	public String getLanguage(String language) {
		language = language.toLowerCase();
		if (language.contains("en")) {
			return "English";
		}
		else if (language.contains("nl")) {
			return "Dutch";
		}
		else if (language.contains("de")) {
			return "German";
		}
		else if (language.contains("es")) {
			return "Spanish";
		}
		else if (language.contains("it")) {
			return "Italian";
		}
		else if (language.contains("ru")) {
			return "Russian";
		}
		else if (language.contains("fr")) {
			return "French";
		}
		return "Unknown";
	}
}
