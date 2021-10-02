package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ChartAreaData;
import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class Charts
 */
public class Charts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Charts() {
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
		ArrayList<TableDataBean> barChartData=populateBarChartData();
		ArrayList<ChartAreaData> areaChartData=populateAreaChartData();
		Integer[] CountArray=getQuickData();
		request.getSession().setAttribute("BarChartData", barChartData);
		request.getSession().setAttribute("AreaChartData", areaChartData);
		request.getSession().setAttribute("IndexPageValues", CountArray);
		request.getRequestDispatcher("/charts.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static ArrayList<TableDataBean> populateBarChartData(){
		ArrayList<TableDataBean> arr= new ArrayList<>();
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidindia ORDER BY totalcases DESC LIMIT 6";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ResultSet products=ps.executeQuery();
			while(products.next()) {
				TableDataBean temp=new TableDataBean();
				temp.setStateName(products.getString(1));
				temp.setTotalCases(products.getInt(2));
				temp.setRecoveredCases(products.getInt(3));
				arr.add(temp);
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return arr;
	}
	
	public static ArrayList<ChartAreaData> populateAreaChartData(){
		ArrayList<ChartAreaData> arr=new ArrayList<>();
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT month,noofcases,death FROM covidtimeline";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ResultSet products=ps.executeQuery();
			while(products.next()) {
				ChartAreaData temp=new ChartAreaData();
				temp.setMonth(products.getString(1));
				temp.setTotalCases(products.getInt(2));
				temp.setDeathCases(products.getInt(3));
				arr.add(temp);
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return arr;
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
