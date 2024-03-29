/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:16:05
 */
package com.analytics.web;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.analytics.data.AnalyticsDAO;
import com.analytics.model.Helper;
import com.inti3e.model.User;

/**
 * The Class Analytics.
 */
public class Analytics extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new analytics.
     */
    public Analytics() {
        super();
    }
    
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String page = (String) request.getParameter("page");
		
		//TODO assert(user != null);
		if (user == null) {
			user = new User(0, "", "", false);
		}
		
		AnalyticsDAO analyticsDAO = new AnalyticsDAO();
		Helper helper = new Helper();
		
		// create visit
		int id = 0;
		try {
			// if user is 15 min inactive, count again
			String time = helper.getCookieTimeValue(request.getCookies());
			
			if (time == null || (System.currentTimeMillis() / 1000) - Long.parseLong(time) > 900 ) {
				analyticsDAO.createVisit(id, request.getRemoteAddr(), user.getId(), helper.getBrowser(request.getHeader("User-Agent")),
						helper.getLanguage(request.getHeader("Accept-Language")));
			}
			
			response.setHeader("Set-Cookie", String.format("Time=%s;", System.currentTimeMillis() / 1000));
		}
		catch (NumberFormatException e) {}
		
		// create hit
		if (page != null) {
			analyticsDAO.createHit(id, request.getRemoteAddr(), user.getId(), page);
		}
		
		BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);  
		Graphics g = bufferedImage.getGraphics();
		g.setColor(Color.black);
		g.dispose();
		
		response.setContentType("image/jpeg");
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
	}
}
