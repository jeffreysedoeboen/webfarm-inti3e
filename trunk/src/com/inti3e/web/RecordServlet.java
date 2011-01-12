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
import com.inti3e.model.webcam.RecordManager;

/**
 * Servlet implementation class RecordServlet
 */
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RecordManager rm = RecordManager.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in recordServlet GET");
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("recordOn", rm.getRecording());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String record = request.getParameter("record");
		if (record != null) {
			if(record.equals("on")) {
				rm.startRecording();
			} else if(record.equals("off")) {
				rm.stopRecording();
			}
		}
	}

}
