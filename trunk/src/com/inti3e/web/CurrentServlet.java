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
 * Servlet implementation class CurrentServlet
 */
public class CurrentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
