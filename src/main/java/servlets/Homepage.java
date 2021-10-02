package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectionPool;

/**
 * Servlet implementation class Homepage
 */
public class Homepage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer[] CountArray=getQuickData();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		request.getSession().setAttribute("IndexPageValues", CountArray);
		request.getSession().setAttribute("messageLogin", "");
		request.getRequestDispatcher("/index.jsp").forward(request, response);		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static Integer[] getQuickData() {
		Integer[] arr=new Integer[3];
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
	         ResultSet rs=stmt.executeQuery("SELECT SUM(totalcases),SUM(recovered),SUM(deaths) FROM covidindia");
	         rs.first();
	         arr[0]=rs.getInt(1);
	         arr[1]=rs.getInt(2);
	         arr[2]=rs.getInt(3);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return arr;
	}

}
