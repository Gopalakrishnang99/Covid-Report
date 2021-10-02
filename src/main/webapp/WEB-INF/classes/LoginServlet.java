package servlets;
import data.ConnectionPool;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginServlet
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
		String userid=request.getParameter("userEnteredEmail");
		String password=request.getParameter("userEnteredPassword");
		String messageLogin="";
		String url="index.jsp";
		if(isAExistingUser(userid)) {
			if(isThePasswordCorrect(userid,password)) {
				messageLogin="";
				request.getSession().setAttribute("userid", userid);
				url="index.jsp";
			}
			else {
				messageLogin="Entered credentials are not correct";
				url="login.jsp";
			}
		}
		else {
			messageLogin="Email-id not registered";
			url="login.jsp";
		}
		request.getSession().setAttribute("messageLogin", messageLogin);
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static boolean isAExistingUser(String userid) {
		Boolean flag=false;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("SELECT user "
	                     +"FROM login WHERE "
	                     +"user =  '"+userid+"'");
	         flag=rs.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return flag;
	}
	public static boolean isThePasswordCorrect(String userid,String password) {
		Boolean flag=false;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("SELECT * "
	                     +"FROM logincovid WHERE "
	                     +"user = '"+userid+"'");
	         rs.first();
	         if(rs.getString(2)==password)
	        	 flag=true;
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return flag;
	}

}
