//Author: Vagif

package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.CourseInfo;
import model.DatabaseManager;
import model.Grade;
import model.Program;


/**
 *
 * @author vagif
 */
public class GetProgramAsJson extends HttpServlet 
{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
        {
                DatabaseManager db=new DatabaseManager();
                StringBuilder json = new StringBuilder();
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
		String name=req.getParameter("name");		
		String code=req.getParameter("code");	
                ArrayList<Program> program = new ArrayList<Program>();
                if(name!=null)
		{
			db.connect();
			program=db.findPrograms(name,"Name");
                        buildProgram(program, db);
			db.disconect();
		}
		else if(code!=null) 
                {
			db.connect();
			program=db.findPrograms(code,"Code");
			db.disconect();
		}
                convertProgramToJson(json, program);
		pw.println(json.toString());				//Sends data (HTML) to user to be displayed in browser
	return;
	}
        
        private void addCourseToGrade(Grade g, Course c, CourseInfo cI)
        {
            for (int i = 0; i < cI.getPeriods().size(); i++)
            {
                 if(g.getRows().get(0).get(cI.getPeriods().get(i).getPeriod()).getEnName().isEmpty())
                 {
                     g.getRows().get(0).set(cI.getPeriods().get(i).getPeriod(), c);
                 }
                 else
                 {
                     g.getRows().get(1).set(cI.getPeriods().get(i).getPeriod(), c);
                 }           
            }  
        }
        
        private void convertProgramToJson(StringBuilder json, ArrayList<Program> program)
        {
            json.append("{");
		json.append("\"Program\" : ");
                json.append("[");
                for (int z = 0; z < program.size(); z++) 
                {
                
            
		json.append("{");
		Converter.jsonAppend(json, "programNamn", program.get(z).getName());
		Converter.jsonAppend(json, "ProgramCode", program.get(z).getCode());
		Converter.jsonAppend(json, "coOrd",  program.get(z).getCoOrd());
		Converter.jsonAppend(json, "Points", program.get(z).getPoints());
                json.append("\"Grade\" : ");
                json.append("[");
                for (int i = 0; i < program.get(z).getGrades().size(); i++) 
                {
                    json.append("[");   
                    for (int j = 0; j < program.get(z).getGrades().get(i).getRows().size(); j++) 
                    {
                        json.append("[");
                        json.append("{");
                        Converter.jsonAppend(json, "year", Integer.toString(program.get(z).getGrades().get(i).getYear()));
                        Converter.jsonAppend(json, "programKod", program.get(z).getCode());
                        Converter.jsonAppendLast(json, "antalStud", Integer.toString(program.get(z).getGrades().get(i).getStudents()));
                        json.append("},");
                        for (int k = 0; k < program.get(z).getGrades().get(i).getRows().get(j).size(); k++) 
                        {
                            json.append("{");
                            Converter.jsonAppend(json, "kursNamn", program.get(z).getGrades().get(i).getRows().get(j).get(k).getEnName());
                            Converter.jsonAppend(json, "hogskolePoangTotalt", Double.toString(program.get(z).getGrades().get(i).getRows().get(j).get(k).getPoints()));
                            Converter.jsonAppend(json, "kurskod", program.get(z).getGrades().get(i).getRows().get(j).get(k).getCode());
                            Converter.jsonAppend(json, "kursansvarig", program.get(z).getGrades().get(i).getRows().get(j).get(k).getCoOrd());
                            Converter.jsonAppendLast(json, "samlas", "el");
                            if(program.get(z).getGrades().get(i).getRows().get(j).size()-1== k)
                            {
                                json.append("}");
                            }
                            else
                            {
                                json.append("}, ");
                            }
                            
                        }
                        if(program.get(z).getGrades().get(i).getRows().size()-1== j)
                        {
                            json.append("]");
                        }
                        else
                        {
                            json.append("], ");                            
                        }
                    }
                    if(program.get(z).getGrades().size()-1== i)
                        {
                            json.append("]");
                        }
                        else
                        {
                            json.append("], ");                            
                        }
                }
                json.append("]");
                if(program.size()-1== z)
                {
                    json.append("}");
                }
                else
                {
                    json.append("}, ");                           
                }
                }
                json.append("]");
                json.append("}");
        }
        
        private void buildProgram(ArrayList<Program> program, DatabaseManager db)
        {
                        for (int i = 0; i < program.size(); i++) 
                        {
                            ArrayList<Grade> grades = new ArrayList<>();                           
                            for (int l = 0; l < 5; l++) 
                            {
                                ArrayList<Course> list = new ArrayList<>(5);
                                ArrayList<Course> list2 = new ArrayList<>(5);
                                for (int j = 0; j < 5; j++) 
                                {
                                    list.add(new Course());
                                    list2.add(new Course());
                                }
                                grades.add(new Grade(0, l+1));
                                grades.get(l).addRow(list);
                                grades.get(l).addRow(list2);
                            }

                            ArrayList<CourseInfo> info = db.findCourseInfos(program.get(i).getName());
                            for (int j = 0; j < info.size(); j++) 
                            {
                                CourseInfo cI = info.get(j);
                                Course c = db.findCourses(cI.getCode(), "Code").get(0);
                                if(cI.getYear() == 1)
                                {
                                    addCourseToGrade(grades.get(0), c, cI);
                                }
                                else if(cI.getYear() == 2)
                                {
                                    addCourseToGrade(grades.get(1), c, cI);
                                }
                                 else if(cI.getYear() == 3)
                                {
                                    addCourseToGrade(grades.get(2), c, cI);
                                }
                                  else if(cI.getYear() == 4)
                                {
                                    addCourseToGrade(grades.get(3), c, cI);
                                }
                                else
                                {
                                    addCourseToGrade(grades.get(4), c, cI);
                                }
                            } 
                            for (int j = 0; j < grades.size(); j++) 
                            {
                                if(grades.get(j).getRows().get(0).size() != 0)
                                {
                                    program.get(i).addGrade(grades.get(j));
                                }                    
                            }
                        }
        }
}
