package servlets;
import data.ConnectionPool;
import data.UserDetailBean;
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
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		UserDetailBean userIdentityObject = new UserDetailBean();
		String userid=request.getParameter("userEnteredEmail");
		String password=request.getParameter("userEnteredPassword");
		String messageLogin="";
		String url="Homepage";
		if(isAExistingUser(userid)) {
			if(isThePasswordCorrect(userid,password)) {
				String[] tempArray=new String[2];
				tempArray=getFirstAndLastName(userid);
				messageLogin="";
				userIdentityObject.setEmailId(userid);
				userIdentityObject.setFirstname(tempArray[0]);
				userIdentityObject.setLastName(tempArray[1]);
				request.getSession().setAttribute("userIdentity", userIdentityObject);
				url="Homepage";
				request.getSession().setAttribute("messageLogin", "");
			}
			else {
				messageLogin="Entered credentials are not correct";
				url="Login";
			}
		}
		else {
			messageLogin="Email-id not registered";
			url="Login";
		}
		request.getSession().setAttribute("messageLogin", messageLogin);
		if(url.equals("Login"))
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
	
	public static boolean isAExistingUser(String userid) {
		Boolean flag=false;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
	         ResultSet rs=stmt.executeQuery("SELECT * "+"FROM logincovid WHERE "+"user = '"+userid+"'");
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
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
	         ResultSet rs=stmt.executeQuery("SELECT * "
	                     +"FROM logincovid WHERE "
	                     +"user = '"+userid+"'");
	         rs.first();
	         String testPd=rs.getString(2);
	         if(testPd.equals(password)) {
	        	 flag=true;
	         }
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return flag;
	}
	
	public static String[] getFirstAndLastName(String userid) {
		String[] temp=new String[2];
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
	         ResultSet rs=stmt.executeQuery("SELECT * "
	                     +"FROM logincovid WHERE "
	                     +"user = '"+userid+"'");
	         rs.first();
	         String firstNameFromDatabase=rs.getString(3);
	         String secondNameFromDatabase=rs.getString(4);
	         temp[0]=firstNameFromDatabase;
	         temp[1]=secondNameFromDatabase;
		}
		catch(SQLException e) {
			System.out.println(e);
		}		
		return temp;
	}

}
