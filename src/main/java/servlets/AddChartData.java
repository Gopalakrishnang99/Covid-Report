package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectionPool;

/**
 * Servlet implementation class AddChartData
 */
public class AddChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddChartData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String retmes="";
		String month=request.getParameter("monthadd");
		Integer tot=Integer.parseInt(request.getParameter("Total"));
		Integer rec=Integer.parseInt(request.getParameter("Recovered"));
		Integer dea=Integer.parseInt(request.getParameter("Death"));
		String[] months= {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		List<String> lis=Arrays.asList(months);
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		PrintWriter out=response.getWriter();
		int mno = cal.get(Calendar.MONTH);
		if(lis.indexOf(month)>mno)
			retmes="Month has not come yet!!";
		else {
			
			Integer yr=Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()));
			if(already(yr,month))
				retmes="Already present! Use the update form";
			else {
			int sts=addtoDB(yr,month,tot,rec,dea);
			if(sts<=0)
				retmes="Some error occured!!";
			else
				retmes="Successful!!";
			}
		}
		out.write(retmes);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public int addtoDB(Integer yr,String month,Integer tot,Integer rec,Integer dea) {
		int status=0;
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="INSERT INTO covidtimeline (year,month,noofcases,recovered,death) VALUES (?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setInt(1, yr);
			ps.setString(2, month);
			ps.setInt(3, tot);
			ps.setInt(4, rec);
			ps.setInt(5, dea);
			status=ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	public boolean already(Integer yr,String mon) {
		boolean flag=false;
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidtimeline WHERE (year=? AND month=?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setInt(1, yr);
			ps.setString(2, mon);
			ResultSet rs=ps.executeQuery();
			flag=rs.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return flag;
	}

}
