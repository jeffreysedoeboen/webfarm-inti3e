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
import com.inti3e.database.dao.UserDao;
import com.inti3e.model.Door;
import com.inti3e.model.Humidity;
import com.inti3e.model.LightSensor;
import com.inti3e.model.Temperature;
import com.inti3e.model.User;


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
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		
		//asserts
		assert(date1 != null);
		assert(date2 != null);
		assert(time1 != null);
		assert(time2 != null);
		
		JSONObject json = new JSONObject();
		
		if(type.equals("humidity")) {
			HumidityDao humidityDao = new HumidityDao();
			ArrayList<Humidity> humidity = humidityDao.getHumiditysBetweenDates(date1, time1, date2, time2);
			
			try {
				json.put("humidity", humidity);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		} else if(type.equals("temp")) {
			TempDao tempDao = new TempDao();
			ArrayList<Temperature> temp = tempDao.getTempsBetweenDates(date1, time1, date2, time2);
			
			try {
				json.put("temp", temp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if(type.equals("door")) {
			DoorDao doorDao = new DoorDao();
			ArrayList<Door> door = doorDao.getDoorsBetweenDates(date1, time1, date2, time2);
			
			try {
				json.put("door", door);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if(type.equals("light")) {
			LightSensorDao lightDao = new LightSensorDao();
			ArrayList<LightSensor> light = lightDao.getLightsBetweenDates(date1, time1, date2, time2);
			
			try {
				json.put("light", light);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if(type.equals("users")) {
			UserDao userDao = new UserDao();
			ArrayList<User> users = userDao.getAllUsers();
			ArrayList<String> names = new ArrayList<String>();
			
			for(User u: users) {
				names.add(u.getName());//Dit om te voorkomen dat de wachtwoorden ook worden meegestuurd.
			}
			
			try {
				json.put("users", names);
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
