/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:43:23
 */
package com.inti3e.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class VideoServlet.
 */
public class VideoServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new video servlet.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoServlet() {
		super();
	}

	/**
	 * Get date
	 * Check file names in folder with date
	 * Files found put in json object
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> tableFiles = new ArrayList<String>();
		JSONObject json = new JSONObject();
		
		String date = request.getParameter("date1");
		
		//asserts
		assert (date !=null);
		
		String[] splittedDate = date.split("-");
		
		String day = splittedDate[0];
		String month = splittedDate[1];
		String year = splittedDate[2];
		
		String newDate = year + "-" + month + "-" + day;
		
		
		File file = new File("C:/Users/Dennis/Desktop/RecordedVideos");//Path to the recorded files
		if(file != null) {
			File[] files = file.listFiles();
			
			if (files != null) {
				for(File f: files) {
					if(f.getName().startsWith(newDate)) {
						tableFiles.add("<a href=\"#\" onclick=\"showPlayer('" + f.getName() + "')\">" + f.getName() + "</a>");
					}
				}
				Collections.sort(tableFiles);
				try {
					json.put("Files", tableFiles);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		response.getWriter().print(json.toString());
	}

	/**
	 * NOT USED
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
