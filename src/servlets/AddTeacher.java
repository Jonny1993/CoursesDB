//Author: Therese
package servlets;

import model.DatabaseManager;
import model.Teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;


public class AddTeacher extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setContentType("text/html");
		ObjectMapper mapper = new ObjectMapper();	//ObjectMapper class (From imported Jackson librar) that reads Json and converts it into Java object
		Teacher newTeacher = mapper.readValue((req.getReader()), Teacher.class);	//Mapper need two params, the json from the request (req.getReader) and class type to know how to map the info
		
		DatabaseManager db = new DatabaseManager();

		System.out.println(newTeacher.toString());
		
		db.connect();
		db.addTeacher(newTeacher);
		db.disconect();
		
		PrintWriter pw = res.getWriter();
		pw.println("1");		//This is sent to javascript which displayas an "Added" alert to user when it recieves 1	
	}
}
