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

public class GetCourseAsJson extends HttpServlet {

	
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
		//Reads values that have been added to the end of the url in JS
		String enName=req.getParameter("en");	
		String svName=req.getParameter("sv");	
		String code=req.getParameter("code");	
 		
		DatabaseManager db=new DatabaseManager();
		ArrayList<Course> courses=new ArrayList<Course>();

		if(enName!=null&&enName!="")
		{
			db.connect();
			courses=db.findCourses(enName,"English Course Name");
			db.disconect();
		}
		else if(svName!=null&&svName!="") {
			db.connect();
			courses=db.findCourses(svName,"Swedish Course Name");
			db.disconect();
			
		}
		else if(code!=null&&code!="") {
			db.connect();
			courses=db.findCourses(code,"Code");
			db.disconect();
		}
		Course course=courses.get(currentCourseID);
		
		String isLast;							//y (=yes) if last, and n if not. Used to hide next button in web page when reaching last result
		if(currentCourseID==courses.size()-1)
			isLast="y";
		else
			isLast="n";
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(course); 
		json = json.substring(0, json.length() - 1);//REMOVE LAST } IN ORDER TO ADD MORE DATA
		StringBuilder stB=new StringBuilder();
		stB.append(json);
		stB.append(", ");
		Converter.jsonAppendLast(stB, "isLast", isLast);	//AppendLast will not add , att the end
		stB.append("}");
		
		String jsonResponse=stB.toString();

		pw.println(jsonResponse);				//Sends data (HTML) to user to be displayed in browser
	return;

	}
}
