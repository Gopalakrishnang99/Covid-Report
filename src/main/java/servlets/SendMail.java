package servlets;

import java.io.IOException;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.UserDetailBean;

/**
 * Servlet implementation class SendMail
 */
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetailBean user= (UserDetailBean) request.getSession().getAttribute("userIdentity");
		String toaddr=user.getEmailId();
		String choose=request.getParameter("which");
		System.out.println(choose);
		sendthemail(toaddr,choose);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void sendthemail(String toaddr,String choosen) {
			String to=toaddr;
		  final String user="covidreportdata@gmail.com";
		  final String password="gotohell13!!";
		   
		  //1) get the session object     
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
		  
		  try{  
			    MimeMessage message = new MimeMessage(session);  
			    message.setFrom(new InternetAddress(user));  
			    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			    message.setSubject("Covid Report");  
			      
			    //3) create MimeBodyPart object and set your message text     
			    BodyPart messageBodyPart1 = new MimeBodyPart();  
			    messageBodyPart1.setText("Covid Report file");
			    if(choosen.equals("timelineCSV"))
			    	messageBodyPart1.setText("Time line data CSV"); 
			    else if(choosen.equals("timelinePDF"))
				    messageBodyPart1.setText("Time line data PDF");  
			    else if(choosen.equals("stateCSV"))
				    messageBodyPart1.setText("State wise data CSV");  
			    else if(choosen.equals("statePDF"))
				    messageBodyPart1.setText("State wise data PDF");  
			      
			    //4) create new MimeBodyPart object and set DataHandler object to this object      
			    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
			    
			    String filename = "F:\\Statedata.pdf";//change accordingly
			    if(choosen.equals("timelineCSV"))
			    	filename = "F:\\Timeline_Export.csv";
			    else if(choosen.equals("timelinePDF"))
			    	filename = "F:\\Timelinedata.pdf";
			    else if(choosen.equals("stateCSV"))
			    	filename = "F:\\Statedata_Export.csv";
			    else if(choosen.equals("statePDF"))
			    	filename = "F:\\Statedata.pdf";
			    DataSource source = new FileDataSource(filename);  
			    messageBodyPart2.setDataHandler(new DataHandler(source));  
			    messageBodyPart2.setFileName("Covid report file");  
			    if(choosen.equals("timelineCSV"))
			    	messageBodyPart2.setFileName("Timeline CSV.csv");  
			    else if(choosen.equals("timelinePDF"))
			    	messageBodyPart2.setFileName("Timeline PDF.pdf"); 
			    else if(choosen.equals("statePDF"))
			    	messageBodyPart2.setFileName("Statedata PDF.pdf"); 
			    else if(choosen.equals("stateCSV"))
			    	messageBodyPart2.setFileName("Statedata CSV.csv"); 
			    //5) create Multipart object and add MimeBodyPart objects to this object      
			    Multipart multipart = new MimeMultipart();  
			    multipart.addBodyPart(messageBodyPart1);  
			    multipart.addBodyPart(messageBodyPart2);  
			  
			    //6) set the multiplart object to the message object  
			    message.setContent(multipart );  
			     
			    //7) send message  
			    Transport.send(message);  
			   
			   System.out.println("message sent....");  
			   }catch (MessagingException ex) {ex.printStackTrace();}  
	}
}
