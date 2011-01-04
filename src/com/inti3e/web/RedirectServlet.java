package com.inti3e.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.analytics.data.AnalyticsDAO;
import com.analytics.model.Helper;
import com.inti3e.model.User;

/**
 * Servlet implementation class RedirectServlet
 */
public class RedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String ip = request.getRemoteAddr();
//		ip = "192.168.2.";
		
		AnalyticsDAO analyticsDAO = new AnalyticsDAO();
		Helper helper = new Helper();
		
		try {
			int id = 0;
			// if user is 15 min inactive, count again
			String time = (String) session.getAttribute("lastvisit");
			
			if (time == null || (System.currentTimeMillis() / 1000) - Long.parseLong(time) > 900 ) {
				analyticsDAO.createVisit(id, request.getRemoteAddr(), helper.getBrowser(request.getHeader("User-Agent")),
						helper.getLanguage(request.getHeader("Accept-Language")));
			}
			
			session.setAttribute("lastvisit", String.format("Time=%s;", System.currentTimeMillis() / 1000));
		}
		catch (NumberFormatException e) {}
		
		if(ip.startsWith("192.168.2.") || ip.startsWith("192.168.0.")) {
			session.setAttribute("user", new User("admin", "admin", true));
			response.sendRedirect("mainpage.jsp");
		} else {
			response.sendRedirect("inlogpage.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
