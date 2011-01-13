/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:42:55
 */
package com.inti3e.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.inti3e.model.User;

/**
 * Servlet implementation class RedirectServlet.
 */
public class RedirectServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
     * Instantiates a new redirect servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public RedirectServlet() {
        super();
    }

	/**
	 * Check IP
	 * Local user gets a user set in his session + redirect to mainpage
	 * Extern user gets redirected to loginpage.jsp
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//asserts
		assert (session != null);
		
		String ip = request.getRemoteAddr();
			
		//asserts
		assert (ip != null);
		
		if(ip.startsWith("192.168.2.") || ip.startsWith("192.168.0.")) {
			session.setAttribute("user", new User(0, "admin", "admin", true));
			response.sendRedirect("mainpage.jsp");
		} else {
			response.sendRedirect("inlogpage.jsp");
		}
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
		// TODO Auto-generated method stub
	}

}
