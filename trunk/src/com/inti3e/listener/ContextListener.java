/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:36:41
 */
package com.inti3e.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.inti3e.database.CreateDBTables;
import com.inti3e.database.DBmanager;
import com.inti3e.model.DataManager;

// TODO: Auto-generated Javadoc
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

	private Process process;
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		new CreateDBTables();
		new com.analytics.data.CreateDBTables();
		
		// nodig voor receiving DK51 !!!
		(DataManager.getInstance()).start();
		
		// start webcam streaming
//		try {
//			process = Runtime.getRuntime().exec("cvlc -vvv v4l:///dev/video0 --sout '#transcode{vcodec=theo,vb=800,scale=1,acodec=none}:http{mux=ogg,dst=:8088/}' --no-sout-rtp-sap --no-sout-standard-sap --sout-all --sout-keep &");
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//			boolean test = true;
//			while (test) {
//				String line = reader.readLine();
//				if (line != null) {
//					System.out.println(line);
//				} else {
//					test = false;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		(DataManager.getInstance()).shutdown();
		DBmanager.getInstance().close();
		com.analytics.data.DBmanager.getInstance().close();
		
		process.destroy();
	}
}
