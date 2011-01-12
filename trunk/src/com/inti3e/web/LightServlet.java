/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:42:01
 */
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
 * Servlet implementation class LightSwitch.
 */
public class LightServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The lsd. */
	LightSensorDao lsd = new LightSensorDao();
	
	/** The dm. */
	DataManager dm = DataManager.getInstance();
       
    /**
     * Instantiates a new light servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public LightServlet() {
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
		String param = request.getParameter("param");
		
		//asserts
		assert(param != null);
		
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
				response.getWriter().print(json.toString());
			}
		}
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
		String param = request.getParameter("param");
		
		//asserts
		assert(param != null);
		
		if (param != null) {
			if (param.equals("turn")) {
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
}
