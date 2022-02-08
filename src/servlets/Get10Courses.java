//Author: Ramy

package servlets;

import model.DatabaseManager;
import model.Course;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

public class Get10Courses extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		

	
		res.setContentType("application/json");
		PrintWriter pw=res.getWriter();
		
		//Reads courseID value that has been added to the end of the url in JS
		String courseID=req.getParameter("courseID");	
		if(courseID==null)	//To avoid exceptions
			courseID="0";
		else if(Integer.parseInt(courseID)<0){
			courseID="0";
		}
		int currentCourseID=Integer.parseInt(courseID);
	
 		
		DatabaseManager db=new DatabaseManager();
		ArrayList<Course> courses=new ArrayList<Course>();
		db.connect();
		courses=db.getAllCourses();
		ArrayList<Course> tenCourses=new ArrayList<Course>();
		int i;
		for( i = currentCourseID ; i<currentCourseID+10 && i<courses.size();i++)
		{
			tenCourses.add(courses.get(i));
		
		}
		//Checks if last course reached in order to add isLastPage=1 as a header (Which in front end will make next button disappear)
		int isLastPage;
		if(i>=courses.size())
			isLastPage=1;
		else
			isLastPage=0;
        res.addHeader("isLastPage", Integer.toString(isLastPage) );


		 ObjectMapper Obj = new ObjectMapper(); 

	  
        // get object as a json string 
        String jsonArr = Obj.writeValueAsString(tenCourses); 
  
 
        
        StringBuilder json= new StringBuilder();
        json.append("{");
        json.append("\"courses\":");
        json.append(jsonArr);
		json.append("}");
	    				

		
		pw.println(json);				//Sends data (HTML) to user to be displayed in browser
		return;

	}
}
