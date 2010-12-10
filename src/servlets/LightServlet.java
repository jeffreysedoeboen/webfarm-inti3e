package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DataManager;

import database.LightSensorDao;

/**
 * Servlet implementation class LightSwitch
 */
public class LightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LightSensorDao lsd = new LightSensorDao();
	DataManager dm = new DataManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LightServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String state = request.getParameter("light");
		
		if(state.equals("on") && !lsd.getLightOn()) {
			dm.setSwitchLight(true);
		} else if(state.equals("off") && lsd.getLightOn()) {
			dm.setSwitchLight(true);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
