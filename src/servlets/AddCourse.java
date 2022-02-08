//Author: Ramy

package servlets;

import model.DatabaseManager;
import model.Course;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;


public class AddCourse extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setContentType("text/html");
		ObjectMapper mapper = new ObjectMapper();	//ObjectMapper class (From imported Jackson librar) that reads Json and converts it into Java object
		Course newCourse = mapper.readValue((req.getReader()), Course.class);	//Mapper need two params, the json from the request (req.getReader) and class type to know how to map the info
		DatabaseManager db = new DatabaseManager(); 
		db.addCourse(newCourse);
		PrintWriter pw=res.getWriter();
		pw.println("1");		//This is sent to javascript which displayas an "Added" alert to user when it recieves 1	
	}
}
