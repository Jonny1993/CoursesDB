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

public class GetListOfBranches extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {



        res.setContentType("application/json");
        PrintWriter pw=res.getWriter();



        DatabaseManager db=new DatabaseManager();
        db.connect();
        ArrayList<String> branches=db.getBranchList();
        //Since more than one element is sent nrOfPrograms is sent in header (So that it doesn't appear in jsons)
        res.addHeader("nrOfBranches", Integer.toString(branches.size()) );

        StringBuilder json = new StringBuilder();	//Creating the json.
        //Since more than one element is sent it is made into json array (There is a class in Jackson that does this automatically)
        json.append("{\"branches\": [");

        for(int i=0; i<branches.size();i++) {
        	json.append("\"");          
            json.append(branches.get(i));
        	json.append("\"");

            if(i!=branches.size()-1) {
        		json.append(",");
        	}
        }
        json.append("]}");
        pw.println(json.toString());				
        return;

    }
}
