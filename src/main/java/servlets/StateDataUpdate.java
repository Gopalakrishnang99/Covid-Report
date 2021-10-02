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

import data.ConnectionPool;
import data.TableDataBean;

/**
 * Servlet implementation class StateDataUpdate
 */
public class StateDataUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StateDataUpdate() {
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
			url="/WEB-INF/views/adminupdatestatedata.jsp";
		else
			url="Admin";
		ArrayList<TableDataBean> tableArray =populateTable();
		request.getSession().setAttribute("tableData", tableArray);
		if(url.equals("Admin"))
			response.sendRedirect(url);
		else
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static ArrayList<TableDataBean> populateTable(){
		ArrayList<TableDataBean> arr=new ArrayList<>();
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidindia";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ResultSet products=ps.executeQuery();
			while(products.next()) {
				TableDataBean temp=new TableDataBean();
				temp.setStateName(products.getString(1));
				temp.setTotalCases(products.getInt(2));
				temp.setRecoveredCases(products.getInt(3));
				temp.setDeathCases(products.getInt(4));
				Float tempcheck=products.getFloat(5);
				if(tempcheck==null)
					temp.setDeathRate(0F);
				else
					temp.setDeathRate(tempcheck);
				tempcheck=products.getFloat(6);
				if(tempcheck==null)
					temp.setRecoveryRate(0F);
				else
					temp.setRecoveryRate(tempcheck);
				arr.add(temp);
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return arr;
	}

}
