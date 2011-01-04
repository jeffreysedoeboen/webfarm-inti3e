package com.analytics.model;

public class Helper {
	
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
