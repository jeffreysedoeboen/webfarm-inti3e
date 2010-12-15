package com.inti3e.web;

import java.io.IOException;
import java.util.ArrayList;
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
import com.inti3e.model.Door;
import com.inti3e.model.Humidity;
import com.inti3e.model.LightSensor;
import com.inti3e.model.Temperature;


/**
 * Servlet implementation class dataServlet
 */
public class DateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("id");
		String date = request.getParameter("date");
		
		JSONObject json = new JSONObject();
		
		if(type.equals("humidity")) {
			HumidityDao humidityDao = new HumidityDao();
			ArrayList<Humidity> humidity = humidityDao.getHumidsOfDate(date);
			
			try {
				json.put("Humidity", humidity);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		} else if(type.equals("temp")) {
			TempDao tempDao = new TempDao();
			ArrayList<Temperature> temp = tempDao.getTempsOfDate(date);
			
			try {
				json.put("temp", temp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if(type.equals("door")) {
			DoorDao doorDao = new DoorDao();
			ArrayList<Door> door = doorDao.getDoorsOfDate(date);
			
			try {
				json.put("door", door);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if(type.equals("light")) {
			LightSensorDao lightDao = new LightSensorDao();
			ArrayList<LightSensor> light = lightDao.getLightsOfDate(date);
			
			try {
				json.put("light", light);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(json.toString());
		response.getWriter().print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}