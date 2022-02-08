//Author: Therese
package servlets;

import model.DatabaseManager;
import model.Course;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

public class GetListOfTeachers extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {



        res.setContentType("application/json");
        PrintWriter pw=res.getWriter();



        DatabaseManager db=new DatabaseManager();
        db.connect();
        ArrayList<String> teachers=db.getTeachersList();
        //Since more than one element is sent nrOfPrograms is sent in header (So that it doesn't appear in jsons)
        res.addHeader("nrOfTeachers", Integer.toString(teachers.size()) );

        StringBuilder json = new StringBuilder();	//Creating the json.
        //Since more than one element is sent it is made into json array (There is a class in Jackson that does this automatically)
        json.append("{\"teachers\": [");

        for(int i=0; i<teachers.size();i++) {
        	json.append("\"");          
            json.append(teachers.get(i));
        	json.append("\"");

            if(i!=teachers.size()-1) {
        		json.append(",");
        	}
        }
        json.append("]}");
        pw.println(json.toString());				
        return;

    }
}
