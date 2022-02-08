//Author: Therese

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import model.DatabaseManager;
import model.Teacher;

public class GetTeacherAsJson extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
		
		
		//Reads the values that has been added to the end of the url in JS
		String courseID = req.getParameter("courseID");	
		if(courseID == null)	//To avoid exceptions
			courseID = "0";
		else if(Integer.parseInt(courseID) < 0){
			courseID = "0";
		}
		int currentCourseID = Integer.parseInt(courseID);
		
		String phrase = req.getParameter("phrase");
		
		
		
		//Get matching results from database
		DatabaseManager db = new DatabaseManager();
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();

		
		db.connect();
		if(phrase.contains("@"))
			teachers = db.findTeachers(phrase, "Email");
		else
			teachers = db.findTeachers(phrase, "Name");
		db.disconect();
		
		
		if(!teachers.isEmpty()) {
			Teacher teacher = teachers.get(currentCourseID);

		
			String isLast;	//y (=yes) if last, and n if not. Used to hide next button in web page when reaching last result
			if(currentCourseID == teachers.size()-1)
				isLast="y";
			else
				isLast="n";
			
			
		
			StringBuilder json = new StringBuilder();	//Creating the json.
			json.append("{");
			Converter.jsonAppend(json, "Name", teacher.getName());
			Converter.jsonAppend(json, "Signature", teacher.getSignature());
			Converter.jsonAppend(json, "Email", teacher.getEmail());
			Converter.jsonAppend(json, "History",  teacher.getHistory());
			Converter.jsonAppend(json, "DevelopmentPlan", teacher.getDevPlan());
			Converter.jsonAppend(json, "Username", teacher.getUsername());
			Converter.jsonAppend(json, "Password", teacher.getPassword());

			
			//Add course offering array
			ObjectMapper Obj = new ObjectMapper(); 
	        String jsonArr = Obj.writeValueAsString(teacher.getCourseOff()); 
	  
	        json.append("\"CourseOfferings\":");
	        json.append(jsonArr);
	        json.append(",");

			
	        
			Converter.jsonAppendLast(json, "isLast", isLast);	//AppendLast will not add , at the end
			json.append("}");
				
			pw.println(json.toString());	//Sends data (HTML) to user to be displayed in browser
		}
		pw.println("");
	}
}
