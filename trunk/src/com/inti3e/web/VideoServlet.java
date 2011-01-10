package com.inti3e.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class VideoServlet
 */
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> tableFiles = new ArrayList<String>();
		JSONObject json = new JSONObject();

		String date = request.getParameter("date1");
		System.out.println(date);
		
		File file = new File("C:/Users/Dennis/Desktop/RecordedVideos");
		System.out.println("File = " + file);
		if(file != null) {
			File[] files = file.listFiles();
			
			if (files != null) {
				for(File f: files) {
					System.out.println("File names: " + f.getName());
					if(f.getName().startsWith(date)) {
						System.out.println("f.getName(): " + f.getName());
						tableFiles.add("<a href=\"#\" onclick=\"showPlayer('" + f.getName() + "')\">" + f.getName() + "</a>");
					}
				}
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
