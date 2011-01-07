/*
 * Project: pestenmarcobart
 * Created By: Marco Beierer and Bart Toersche
 * Created At: 2-dec-2010 22:44:34
 */
package com.inti3e.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.inti3e.database.CreateDBTables;
import com.inti3e.model.DataManager;

/**
 * The listener interface for receiving context events.
 * The class that is interested in processing a context
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addContextListener<code> method. When
 * the context event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ContextEvent
 */
public class ContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		new CreateDBTables();
		new com.analytics.data.CreateDBTables();
		
		// nodig voor receiving DK51 !!!
		(DataManager.getInstance()).start();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		(DataManager.getInstance()).shutdown();
	}
}
