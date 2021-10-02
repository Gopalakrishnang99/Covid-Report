package listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import data.ConnectionPool;
import data.UserDetailBean;

/**
 * Application Lifecycle Listener implementation class HttpSessionInitListen
 *
 */
public class HttpSessionInitListen implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public HttpSessionInitListen() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         UserDetailBean indexStarting=new UserDetailBean();
         Integer[] CountArray=getQuickData();
         se.getSession().setAttribute("userIdentity", indexStarting);
 		 se.getSession().setAttribute("IndexPageValues", CountArray);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
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
