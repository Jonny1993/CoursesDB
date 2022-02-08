package model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.§
 */
//Author: Vagif & John 
import java.util.ArrayList;
import java.util.List;

public class Course 
{
	private String swName, enName, code, examiner, coOrd, progress, history;
	private double points;
	private ArrayList<CourseElement> elements;
	private ArrayList<CourseInfo> courseInfo;
	private ArrayList<CourseOffering> courseOfferings;

	public Course(String swName, String enName, String code, String examiner, 
			String coOrd, String history, double points, String progress)
	{
		this.swName = swName;
		this.enName = enName;
		this.code = code;
		this.examiner = examiner;
		this.coOrd = coOrd;
		this.history = history;
		this.points = points;
		this.progress = progress;
		this.elements = new ArrayList<CourseElement>();
		this.courseInfo = new ArrayList<CourseInfo>();
		this.courseOfferings = new ArrayList<CourseOffering>();
	}
	public Course()
	{
		this("Test", "",  "", "", "", "", 0, "");
	}

	public String getSwName() {
		return swName;
	}

	public String getEnName() {
		return enName;
	}

	public void setSwName(String swName) {
		this.swName = swName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getCoOrd() {
		return coOrd;
	}

	public void setCoOrd(String coOrdinator) {
		this.coOrd = coOrdinator;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public ArrayList<CourseElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<CourseElement> elements) {
		this.elements = elements;
	}
	public void setCourseInfo(ArrayList<CourseInfo> courseInfo) {
		this.courseInfo = courseInfo;
	}
	public void addElement(String name, double points) 
	{
		elements.add(new CourseElement(name, points));
	}

	public ArrayList<CourseInfo> getCourseInfo() {
		return courseInfo;
	}

	public ArrayList<CourseOffering> getCourseOfferings() {
		return courseOfferings;
	}
	public void setCourseOfferings(ArrayList<CourseOffering> courseOfferings) {
		this.courseOfferings = courseOfferings;
	}
	public void addInfo(String program, String branch, int year, boolean oblig
			, List<Period> periods) 
	{
		CourseInfo info = new CourseInfo(program, branch, year, oblig);
		for(Period p: periods) {
			info.addPeriod(p.getPeriod(), p.getPoints());
		}
		courseInfo.add(info);
	}

	public void addOffering(int year, int expectedStu, int reportedStu, List<CourseTeacher> teachers) {
		CourseOffering offering = new CourseOffering(year, expectedStu, reportedStu, teachers);
		courseOfferings.add(offering);
	}
	
	@Override
	public String toString()
	{
		return "Course:\n" + "SW name: " + swName + "\nEN name: " + enName + "\nCode: " 
				+ code + "\nExaminer: " +  examiner + "\nCo-ordinator: " + coOrd + "\nProgress: " 
				+  progress + "\nHistory: " + history + "\nPoints: " 
				+  points + "\nElements: " + elements + "\nCourse info: " + courseInfo 
				+ "\nCourse offerings: " + courseOfferings;
	}
}
