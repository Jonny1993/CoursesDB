//Author: Ramy

package servlets;

import model.DatabaseManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartSession extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		System.out.println("Start sess.");
		res.setContentType("text/html");
		//Reads users input
		String providedUsername=req.getParameter("username");
		String providedPassword=req.getParameter("password");
		
		DatabaseManager db=new DatabaseManager();
		//Checks if user name and password exist in the database
		if(db.correctLogIn(providedUsername, providedPassword)) {
			//Creates time=now in order to be able to create a unique session id usning hash
			Date loginTime=new Date();
			//Creates the unique session id and converts it into String (Since it is easier to work with strings in mongo)
			String sessionID = Integer.toString(providedUsername.hashCode()+loginTime.hashCode());
			//Adds session to DB
			db.addSession(sessionID, providedUsername, providedPassword);
			//Adds sessionID to cookies in response in order for the browser to update/save it
			Cookie sessionCookie = new Cookie("sessionID", sessionID);
			res.addCookie(sessionCookie);
			//1 for success, 0 to inform use that his log in data do not exist in the database
			PrintWriter pw=res.getWriter();
			pw.println("1");	
		}else {
			//0 to inform use that his log in data do not exist in the database
			PrintWriter pw=res.getWriter();
			pw.println("0");	
		}
	}
}
