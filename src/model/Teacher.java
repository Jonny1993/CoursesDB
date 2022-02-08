//Author: Therese

package model;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vagif
 */
public class Teacher {
    
	private String email, name, signature, history, devPlan, username, password;
    private ArrayList<TeacherCourseOffering> courseOff;
    
    
    public Teacher(String email, String name, String signature, String history, String devPlan, 
    		String username, String password) {
    	this.email = email;
        this.name = name;
        this.signature = signature;
        this.history = history;
        this.devPlan = devPlan;
        
        this.courseOff = new ArrayList<TeacherCourseOffering>();
        
        this.username = username;
        this.password = password;
    }
    public Teacher() {
    	this("", "", "", "", "", "", "");
    }
    
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    

    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    

    public String getDevPlan() {
        return devPlan;
    }
    public void setDevPlan(String devPlan) {
        this.devPlan = devPlan;
    }
    
    
    public ArrayList<TeacherCourseOffering> getCourseOff() {
    	return courseOff;
    }
    public void addCourseOff(String code, int year) {
    	TeacherCourseOffering c = new TeacherCourseOffering(code, year);
    	courseOff.add(c);
    }
    public void addCourseOff(TeacherCourseOffering courseOff) {
    	this.courseOff.add(courseOff);
    }
    
    
    public String getUsername() {
    	return username;
    }
    public void setUsername(String username) {
    	this.username = username;
    }
    
    
    public String getPassword() {
    	return password;
    }
    public void setPassword(String password) {
    	this.password = password;
    }
    
    
    
    
    public static class TeacherCourseOffering {
    	private String code;
    	private int year;
    
    	public TeacherCourseOffering(String code, int year) {
    		this.code = code;
    		this.year = year;
    	}
    	
    	public TeacherCourseOffering() {
    	}
    	
    	public String getCode() {
    		return code;
    	}
    	public void setCode(String code) {
    		this.code = code;
    	}
    	
    	public int getYear() {
    		return year;
    	}
    	public void setYear(int year) {
    		this.year = year;
    	}
    	
    	
    	public String toString() {
    		return "code=" + code + ", year=" + year;
    	}
    }
    
    
    /*
    protected class CourseTeacher {
    	private String teacherName;
    	private int percentage;
    	
    	public CourseTeacher(String teacherName, int percentage) {
    		this.teacherName = teacherName;
    		this.percentage = percentage;
    	}
    	
    	public String getTeacherName() {
    		return teacherName;
    	}
    	public void setTeacherName(String teacherName) {
    		this.teacherName = teacherName;
    	}
    	
    	public int getPercentage() {
    		return percentage;
    	}
    	public void setPercentage(int percentage) {
    		this.percentage = percentage;
    	}
    	
    	public String toString() {
    		return "Name: " + teacherName + ", percentage: " + percentage;
    	}
    }
    */
    
    
    
    @Override
    public String toString() {	
        return "email=" + email + ", name= " + name + ", signature= " + signature + ", history= " + history + 
        		", development plan= " + devPlan + ", course offerings {" + courseOff + "}" + ", username=" +
        		username + ", password=" + password;
    }
}
