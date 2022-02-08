//Author: Therese

package servlets;

import model.DatabaseManager;
import model.Teacher;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

public class Get10Teachers extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		res.setContentType("application/json");
		PrintWriter pw=res.getWriter();
		
		//Reads courseID value that has been added to the end of the url in JS
		String courseID=req.getParameter("courseID");	
		if(courseID == null)	//To avoid exceptions
			courseID = "0";
		else if(Integer.parseInt(courseID) < 0){
			courseID = "0";
		}
		int currentCourseID = Integer.parseInt(courseID);
	
		//Get teachers from database
		DatabaseManager db = new DatabaseManager();
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		
		db.connect();
		teachers = db.getAllTeachers();
		ArrayList<Teacher>  tenTeachers = new ArrayList<Teacher>();
		int placement;
		for(placement = currentCourseID; placement < currentCourseID+10 && placement < teachers.size(); placement++) {
			tenTeachers.add(teachers.get(placement));
		}


		
		//Checks if last course reached in order to add isLastPage=1 as a header (Which in front end will make next button disappear)
		int isLastPage;
		if(placement >= teachers.size())
			isLastPage = 1;
		else
			isLastPage = 0;
        res.addHeader("isLastPage", Integer.toString(isLastPage));


		ObjectMapper Obj = new ObjectMapper(); 
		 
        // get object as a json string 
        String jsonArr = Obj.writeValueAsString(tenTeachers); 
  
 
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"teachers\":");
        json.append(jsonArr);
		json.append("}");
		
		System.out.println("10 json:\n" + json);
	    				

		pw.println(json);				//Sends data (HTML) to user to be displayed in browser
	}
}
