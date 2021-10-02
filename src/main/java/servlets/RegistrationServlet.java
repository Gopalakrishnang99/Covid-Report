package servlets;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectionPool;
import data.UserDetailBean;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="Verify";
		String TempEmailId=request.getParameter("EmailId");
		String TempFirstName=request.getParameter("FirstName");
		String TempLastName=request.getParameter("LastName");
		String TempPassword=request.getParameter("Password");
		if(isAlreadyExistingUser(TempEmailId)) {
			url="Register";
			request.setAttribute("registrationMessage", "Email-Id already registered");
		}
		else {
			UserDetailBean TempUserObject=new UserDetailBean(TempEmailId,TempFirstName,TempLastName);
			request.getSession().setAttribute("TempUser", TempUserObject);
			String otp=generateOTP();
			addtoOTPtable(TempUserObject.getEmailId(),TempPassword,otp);
			sendmail(TempUserObject.getEmailId(),otp);
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
	
	public static boolean isAlreadyExistingUser(String emailId) {
		Boolean flag=false;
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
	         ResultSet rs=stmt.executeQuery("SELECT * "+"FROM logincovid WHERE "+"user = '"+emailId+"'");
	         flag=rs.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return flag;
	}
	
	public void sendmail(String emailid,String otp) {
		
		String host="smtp.gmail.com";  
		  final String user="covidreportdata@gmail.com";
		  final String password="gotohell13!!";
		  String to=emailid;
		  
		  Properties properties = System.getProperties();  
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");
		  properties.setProperty("mail.smtp.host", "smtp.gmail.com");  
		  properties.put("mail.smtp.auth", "true");
		     
		   Session session = Session.getDefaultInstance(properties,  
		    new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		    try {  
		     MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(user));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		     message.setSubject("Covid-Report");  
		     message.setText("This is the OTP : "+otp);  
		      
		     Transport.send(message);  
		  
		     System.out.println("message sent successfully...");  
		   
		     } catch (MessagingException e) {e.printStackTrace();}  
		
	}
	
	public String generateOTP() {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000);
		String formatted = String.format("%05d", num); 
		return formatted;
	}
	
	public int addtoOTPtable(String user, String password, String otp) {
		int check=0;
		if(isOTPalready(user)) {
			
			try (Connection con = ConnectionPool.getInstance()
		            .getConnection()) {
				String preparedSQL="UPDATE covidotp SET otp=?,password=? WHERE userid=?";
				PreparedStatement ps=con.prepareStatement(preparedSQL);
				ps.setString(1, otp);
				ps.setString(2, password);
				ps.setString(3, user);
				check=ps.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println(e);
			}
			
		}
		else {
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="INSERT INTO covidotp (userid,otp,password) VALUES (?,?,?)";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, user);
			ps.setString(2, otp);
			ps.setString(3, password);
			check=ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		}
		return check;
	}
	
	public boolean isOTPalready(String email) {
		boolean flag=false;
		
		try (Connection con = ConnectionPool.getInstance()
	            .getConnection()) {
			String preparedSQL="SELECT * FROM covidotp WHERE userid=?";
			PreparedStatement ps=con.prepareStatement(preparedSQL);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			flag=rs.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return flag;
	}

}
