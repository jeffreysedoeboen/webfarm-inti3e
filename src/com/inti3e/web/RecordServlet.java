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
     * It puts the current state of recording
     * 
     * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	

		JSONObject json = new JSONObject();
		
		try {
			json.put("recordOn", rm.getRecording());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	 /**
     * Start/Stop recording.
     * 
     * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String record = request.getParameter("record");
		if (record != null) {
			if(record.equals("on")) {
				rm.startRecordingByUser();
			} else if(record.equals("off")) {
				rm.stopRecordingByUser();
			}
		}
	}

}
