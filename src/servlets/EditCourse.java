
//Author: John
package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import model.Course;
import model.DatabaseManager;

public class EditCourse extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		
		String code=req.getParameter("code");	
		
		Cookie courseCookie = new Cookie("courseToshowOrEdit", code);
		res.addCookie(courseCookie);
		

		File file= new File("C:\\Users\\rami_\\OneDrive\\Documents\\GitHub\\MTHDB\\WebContent\\EditCourse.html");	 
		String content=Converter.convertFileToStr(file);	 

					
		pw.print(content);
		return;

	}
}
