//Author: Ramy

package servlets;

import model.DatabaseManager;
import model.Course;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

public class GetListOfProgs extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {



        res.setContentType("application/json");
        PrintWriter pw=res.getWriter();



        DatabaseManager db=new DatabaseManager();
        db.connect();
        ArrayList<String> progs=db.getProgList();
        //Since more than one element is sent nrOfPrograms is sent in header (So that it doesn't appear in jsons)
        res.addHeader("nrOfPrograms", Integer.toString(progs.size()) );

        StringBuilder json = new StringBuilder();	//Creating the json.
        //Since more than one element is sent it is made into json array (There is a class in Jackson that does this automatically)
        json.append("{\"programs\": [");

        for(int i=0; i<progs.size();i++) {
        	json.append("\"");          
            json.append(progs.get(i));
        	json.append("\"");

            if(i!=progs.size()-1) {
        		json.append(",");
        	}
        }
        json.append("]}");
        pw.println(json.toString());				
        return;

    }
}
