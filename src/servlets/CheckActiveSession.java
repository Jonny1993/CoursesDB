//Author: Ramy
package servlets;

import model.DatabaseManager;
import model.Course;
import model.Session;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class CheckActiveSession extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		res.setContentType("text/html");
		//Reads cookies and looks for session ID
		String sessionID=null;
		Cookie[] cookies=req.getCookies();
		if (cookies != null) {   
			for (Cookie cookie : cookies) {   
				if(cookie.getName().equals("sessionID")){
					sessionID = cookie.getValue();
					break;
				}
			}
		}
		PrintWriter pw=res.getWriter();
		if(sessionID==null) {
			pw.print(0);			//Interpreted as false by frontend
		}else {
			//Look up the session ID in the database
			DatabaseManager db=new DatabaseManager();
			Session session=db.findSession(sessionID);
			//If DB does not return null (meaning session ID was  found)
			if(session!=null)
			{
					pw.print(1);			//Interpreted as true by frontend
			}else {
				pw.print(0);			//Interpreted as false by frontend
			}

		}
	}
}

