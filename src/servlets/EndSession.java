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

public class EndSession extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		res.setContentType("text/html");
		Cookie[] cookies=req.getCookies();
		String sessionID = "";
		 if (cookies != null) {   
		        for (Cookie cookie : cookies) {   
		            if(cookie.getName().equals("sessionID")){
		               sessionID = cookie.getValue();
		               break;
		            }
		        }
		 }
		DatabaseManager db=new DatabaseManager();
		db.connect();
		db.deleteSession(sessionID);
			PrintWriter pw=res.getWriter();
			pw.println("1");
	}
}

