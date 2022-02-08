//Author: Vagif / John

package model;

import java.util.ArrayList;

public class CourseInfo {
	private String program;
        private String courseCode;
	private String branch;
	private int year;
	private boolean obligatory;
	private ArrayList<Period> periods;

	public CourseInfo(String program, String branch, int year, boolean obligatory, ArrayList<Period> periods)
	{
		this.program = program;
		this.branch = branch;
		this.year = year;
		this.obligatory = obligatory;
		this.periods = periods;
	}

	public CourseInfo(String courseCode, String program, String branch, int year, boolean obligatory)
	{
                this.courseCode = courseCode;
		this.program = program;
		this.branch = branch;
		this.year = year;
		this.obligatory = obligatory;
		periods = new ArrayList<Period>();
	}
		public CourseInfo(String program, String branch, int year, boolean obligatory)
	{
		this.program = program;
		this.branch = branch;
		this.year = year;
		this.obligatory = obligatory;
		periods = new ArrayList<Period>();
	}

	public CourseInfo()
	{
		this("", "", 0, false,new ArrayList<Period>());
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}
        
        public String getCode()
        {
		return courseCode;
	}

	public void setCode(String code) {
		this.courseCode = code;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ArrayList<Period> getPeriods() {
		return periods;
	}

	public void addPeriod(int period, double points)
	{
		periods.add(new Period(period, points));
	}

	public void setPeriods(ArrayList<Period> periods) {
		this.periods = periods;
	}

	public boolean isObligatory() {
		return obligatory;
	}

	public void setObligatory(boolean obligatory) {
		this.obligatory = obligatory;
	}

	@Override
	public String toString() {
		return "Course info {" + "program=" + program + ", branch=" + branch + ", year=" + year + ", periods=" + periods + '}';
	}
}
