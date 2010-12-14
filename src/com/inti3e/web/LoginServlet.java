package com.inti3e.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Redirect;

import com.inti3e.database.dao.UserDao;
import com.inti3e.model.SessionControl;
import com.inti3e.model.User;



/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ingelogd = false;
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		UserDao userdao = new UserDao();
		ArrayList<User> users = userdao.getAllUsers();
		for (User u : users){
			if (u.getName().equals(name)){
				if (u.getPassword().equals(password)){
					ingelogd = true;
				}
			}
		}
		if (ingelogd){
			User user = new User(name, password);
			String session = request.getSession().getId();
			SessionControl sessionControl = new SessionControl();
			sessionControl.addUser(session, user);
			response.sendRedirect("MainPage.jsp");
		} else {
			response.sendRedirect("RegisterError.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
