//Author: Vagif

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.CourseInfo;
import model.DatabaseManager;
import model.Period;
import model.Program;

/**
 *
 * @author vagif
 */
@WebServlet(name = "buildProgram", urlPatterns = {"/buildProgram"})
public class buildProgram extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException 
    {
        res.setContentType("application/json");
	PrintWriter pw=res.getWriter();
        StringBuilder json = new StringBuilder();
        DatabaseManager db=new DatabaseManager();
        
        
        String[] courses = req.getParameter("courses").split(" ");
        String[] program = req.getParameter("program").split(" ");
        
        Program p = new Program(program[0], program[1], program[2], program[3], program[4]);
        

        try
        {
            db.connect();
            for (int i = 0; i < courses.length; i++) 
            {
               if(!(courses[i]).equalsIgnoreCase("empty"))
               {
                   
            	   CourseInfo tmp = new CourseInfo(courses[i], program[1], program[2], (i/10)+1,  true); 
                   Course c = db.findCourses(courses[i], "Code").get(0);
                   tmp.addPeriod(i%5, c.getPoints());
                   db.insertCourseInfoForProgram(tmp);
               }
            }
            db.addProgram(p);
            db.disconect();
            pw.println("true");
        }
        catch(Exception e)
        {
            pw.println("false");
            db.disconect();
        }
    }    
}
