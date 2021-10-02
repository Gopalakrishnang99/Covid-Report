package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ChartAreaData;
import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class ChartDataUpdate
 */
public class ChartDataUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartDataUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		String isadmin=(String) request.getSession().getAttribute("admin");
		String url;
		if(isadmin!=null && isadmin.equals("true"))
			url="/WEB-INF/views/adminupdatetimelinedata.jsp";
		else
			url="Admin";
		if(url.equals("Admin"))
			response.sendRedirect(url);
		else {
			//request.getRequestDispatcher(url).forward(request, response);
		ArrayList<ChartAreaData> tableArray = populateAreaChartData();
		request.getSession().setAttribute("chartData", tableArray);
		request.getRequestDispatcher(url).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static ArrayList<ChartAreaData> populateAreaChartData(){
		ArrayList<ChartAreaData> arr=new ArrayList<>();
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT year,month,noofcases,recovered,death FROM covidtimeline";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ResultSet products=ps.executeQuery();
			while(products.next()) {
				ChartAreaData temp=new ChartAreaData();
				temp.setYear(products.getInt(1));
				temp.setMonth(products.getString(2));
				temp.setTotalCases(products.getInt(3));
				temp.setRecovered(products.getInt(4));
				temp.setDeathCases(products.getInt(5));
				arr.add(temp);
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return arr;
	}

}
