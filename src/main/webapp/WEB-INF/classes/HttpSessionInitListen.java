package listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
         String userid=null;
         se.getSession().setAttribute("messageLogin", "");
         se.getSession().setAttribute("userid", userid);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }
	
}
