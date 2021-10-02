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

/**
 * Servlet implementation class AddAdminToDB
 */
public class AddAdminToDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAdminToDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String user=request.getParameter("email");
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String pd=request.getParameter("pd");
		String retmes="";
		if(already(user))
			retmes="Already an admin";
		else {
			int st=addtoDB(user,fname,lname,pd);
			if(st<=0)
				retmes="Something went wrong! Please try again";
			else
				retmes="Successfully added";
		}
		PrintWriter out=response.getWriter();
		out.write(retmes);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public int addtoDB(String user,String fn,String ln,String pd) {
		int sts=0;
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="INSERT INTO covidadmin (user,password,FirstName,SecondName) VALUES (?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, user);
			ps.setString(2, pd);
			ps.setString(3, fn);
			ps.setString(4, ln);
			sts=ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return sts;
	}
	
	public boolean already(String user) {
		boolean flag=false;
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidadmin WHERE user=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, user);
			ResultSet rs=ps.executeQuery();
			flag=rs.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return flag;
	}

}
