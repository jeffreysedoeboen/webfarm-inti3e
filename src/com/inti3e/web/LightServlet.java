package com.inti3e.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.inti3e.database.dao.LightSensorDao;
import com.inti3e.model.DataManager;



/**
 * Servlet implementation class LightSwitch
 */
public class LightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LightSensorDao lsd = new LightSensorDao();
	DataManager dm = DataManager.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LightServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		if (param != null) {
			if (param.equals("state")) {
				LightSensorDao lsd = new LightSensorDao();
				boolean lightOn = lsd.getLightOn();
				
				JSONObject json = new JSONObject();
				try {
					json.put("lightOn", lightOn);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				System.out.println(json.toString());
				response.getWriter().print(json.toString());
			} else if (param.equals("turn")) {
				String light = request.getParameter("light");
				if (light != null) {
					if(light.equals("on")) {
						dm.turnLight(true);
					} else if(light.equals("off")) {
						dm.turnLight(false);
					}
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
