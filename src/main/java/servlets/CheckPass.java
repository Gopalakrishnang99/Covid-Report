package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class CheckPass
 */
public class CheckPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckPass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String from=request.getParameter("fromLoc");
		String to=request.getParameter("toLoc");
		Integer fromno=getTotalcases(from);
		Integer tono=getTotalcases(to);
		PrintWriter out=response.getWriter();
		if(fromno<tono) {
			System.out.println("OK");
			out.write("Sorry, pass cannot be given to you");
		}
		else {
			System.out.println("NO");
			out.write("Yes, pass can be given to you");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static Integer getTotalcases(String inp){
		Integer tot=0;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidindia WHERE state=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, inp);
			ResultSet products=ps.executeQuery();
			products.next();
			tot=products.getInt(2);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return tot;		
	}

}
