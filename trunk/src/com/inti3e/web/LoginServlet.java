/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:42:38
 */
package com.inti3e.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.inti3e.database.dao.UserDao;
import com.inti3e.helper.UserHelper;
import com.inti3e.model.User;

/**
 * Servlet implementation class LoginServlet.
 */
public class LoginServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The user dao. */
	private UserDao userDAO = new UserDao();
       
    /**
     * Instantiates a new login servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		boolean ingelogd = false;
		String name = request.getParameter("usernameLogin");
		String password = UserHelper.string2md5(request.getParameter("passwordLogin"));
		UserDao userdao = new UserDao();
		ArrayList<User> users = userdao.getAllUsers();
		
		//asserts
		assert (name != null);
		assert (password != null);
		assert (users != null);
		
		for (User u : users){
			if (u.getName().equals(name)){
				if (u.getPassword().equals(password)){
					request.getSession().setAttribute("user", u);
					ingelogd = true;
				}
			}
		}
		if (ingelogd){
			response.sendRedirect("mainpage.jsp");
		} else {
			response.sendRedirect("loginerror.html");
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
		HttpSession session = request.getSession();
		String nickname = request.getParameter("usernameCreate");
		String password = request.getParameter("passwordCreate");
		String repassword = request.getParameter("repasswordCreate");
		
		//asserts
		assert (nickname != null);
		assert (password != null);
		assert (repassword != null);
		
		if (userDAO.nameIsAvailable(nickname)) {
			session.setAttribute("errors", "Error: Username is not available.");
		}
		else if (!password.equals(repassword)) {
			session.setAttribute("errors", "Error: The passwords are not equal.");
		}
		else if (!nickname.equals("") && !password.equals("")) {
			userDAO.addNewUser(nickname, UserHelper.string2md5(password));
			session.setAttribute("success", "Account successfully created.");
		}
		response.sendRedirect("mainpage.jsp");
	}

}
