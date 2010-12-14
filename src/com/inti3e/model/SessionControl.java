package com.inti3e.model;

import java.util.HashMap;

public class SessionControl {
	private static HashMap<String, User> activeUsers = new HashMap<String, User>();

	public SessionControl() {

	}

	public synchronized void addUser(String session, User user) {
		activeUsers.put(session, user);
	}

	public synchronized void removeUser(String session) {
		activeUsers.remove(session);
	}

	public String getSessionByUser(User u) {
		String session = null;
		for (String s : activeUsers.keySet()) {
			User temp = activeUsers.get(s);
			if (temp.equals(u)) {
				session = s;
			}
		}
		return session;
	}

	public User getUserBySession(String session) {
		return activeUsers.get(session);
	}

	public HashMap<String, User> getAll() {
		return activeUsers;
	}
}
