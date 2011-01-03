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

import com.analytics.data.AnalyticsDAO;
import com.analytics.model.Helper;

public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Analytics() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnalyticsDAO analyticsDAO = new AnalyticsDAO();
		Helper helper = new Helper();
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			// if user is 15 min inactive, count again
			String time = helper.getCookieTimeValue(request.getCookies());
			
			if (time == null || (System.currentTimeMillis() / 1000) - Long.parseLong(time) > 900 ) {
				analyticsDAO.createVisit(id, request.getRemoteAddr(), helper.getBrowser(request.getHeader("User-Agent")),
						helper.getLanguage(request.getHeader("Accept-Language")));
			}
			
			response.setHeader("Set-Cookie", String.format("Time=%s;", System.currentTimeMillis() / 1000));
		}
		catch (NumberFormatException e) {}
		
		BufferedImage bufferedImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);  
		Graphics g = bufferedImage.getGraphics();
		g.setColor(Color.black);
		g.dispose();
		
		response.setContentType("image/jpeg");
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
	}
}
