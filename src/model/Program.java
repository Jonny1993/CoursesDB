//Author: Vagif

package model;


import java.util.ArrayList;
import java.util.Arrays;

public class Program {
	private String code, name, branch, coOrdinator; 
	private String points;  //could be derived from courses
	private ArrayList<Grade> grades;

	public Program(String code, String name, String branch, String points, String coOrdinator) {
		this.code = code;
		this.name = name;
		this.branch = branch;
		this.points = points;
		this.coOrdinator = coOrdinator;
                grades = new ArrayList<>();
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPoints() {
		return points;
	}
	
	public void setPoints(String points) {
		this.points = points;
	}
	
	public void setName(String programNamn) {
		this.name = programNamn;
	}
	
	public void setBranch(String inriktning) {
		this.branch = inriktning;
	}
	
	public void setCoOrd(String programAnsvarig) {
		this.coOrdinator = programAnsvarig;
	}
	
        public ArrayList<Grade> getGrades()
        {
            return grades;
        }
        
	public String getName() {
		return name;
	}

	public String getBranch() {
		return branch;
	}

	public String getCoOrd() {
		return coOrdinator;
	}

	public String toString() {
		return "Program{" + "name=" + name + ", branch=" + branch + ", co-ordinator=" 
				+ "\n" +  coOrdinator;

	}
        
        public void addGrade(Grade grade)
        {
            this.grades.add(grade);
        }
        
}
