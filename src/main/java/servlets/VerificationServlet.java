package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectionPool;
import data.UserDetailBean;

/**
 * Servlet implementation class VerificationServlet
 */
public class VerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("messageLogin", "");
		String url="Register";
		String OTP=request.getParameter("verifyOTP");
		UserDetailBean tempuser=(UserDetailBean) request.getSession().getAttribute("TempUser");
		String userid=tempuser.getEmailId();
		String[] otpdetails=getstoredOTP(userid);
		String placeholderotp="";
		try {
			placeholderotp=otpdetails[1];
		}
		catch(Exception e) {
			System.out.println(e);
		}
		if(OTP.compareTo(placeholderotp)==0) {
			UserDetailBean TempUserObjectToBeAdded=(UserDetailBean) request.getSession().getAttribute("TempUser");
			int check=addToDatabase(TempUserObjectToBeAdded.getEmailId(),TempUserObjectToBeAdded.getFirstName(),TempUserObjectToBeAdded.getLastName(),otpdetails[2]);
			if(check==0) {
				request.setAttribute("messageVerification", "Something went wrong");
			}
			else {
				url="Success";
				removeOTP(TempUserObjectToBeAdded.getEmailId());
				request.getSession().removeAttribute("TempUser");
				
			}			
		}
		else {
			request.setAttribute("messageVerification", "OTP is wrong");
		}
		request.getRequestDispatcher(url).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static int addToDatabase(String email,String FName,String LName,String Password) {
		int check=0;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="INSERT INTO logincovid (user,password,FirstName,SecondName) VALUES (?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, email);
			ps.setString(2, Password);
			ps.setString(3, FName);
			ps.setString(4, LName);
			check=ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return check;
	}
	
	public String[] getstoredOTP(String email) {
		String[] arr=new String[3];
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidotp WHERE userid=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			rs.next();
			arr[0]=rs.getString(2);
			arr[1]=rs.getString(3);
			arr[2]=rs.getString(4);
			System.out.println(arr[0]+" "+arr[1]+" "+arr[2]);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return arr;
	}
	
	public void removeOTP(String email) {
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="DELETE FROM covidotp WHERE userid=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, email);
			ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
	}
}
