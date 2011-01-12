/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:41:26
 */
package com.inti3e.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.inti3e.database.dao.DoorDao;
import com.inti3e.database.dao.HumidityDao;
import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.database.dao.TempDao;

/**
 * Servlet implementation class CurrentServlet.
 */
public class CurrentServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
     * Instantiates a new current servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public CurrentServlet() {
        super();
    }

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		
		//Light
		LightSensorDao light = new LightSensorDao();
		String lightstat = "";
		boolean currentLight = light.getLightOn();
		if(currentLight) {
			lightstat = "on";
		} else {
			lightstat = "off";
		}
		
		//Temperature
		TempDao temp = new TempDao();
		String currentTemp = temp.getCurrentTemp();
		
		//Humidity
		HumidityDao humid = new HumidityDao();
		int currentHumid = humid.getCurrentHumidity();
		
		//Door
		DoorDao door = new DoorDao();
		String doorstat = "";
		boolean currentDoor = door.getCurrentDoor();
		if(currentDoor) {
			doorstat = "open";
		} else {
			doorstat = "closed";
		}

		try {
			json.put("Light", lightstat);
			json.put("Temperature", currentTemp);
			json.put("Humidity", currentHumid);
			json.put("Door", doorstat);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		response.getWriter().print(json.toString());
	}

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
