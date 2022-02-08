//Author: Vagif / John

package model;

import java.util.ArrayList;
import java.util.List;

class CourseOffering {
	private int year, expectedStu, reportedStu;
	private List<CourseTeacher> teachers;

	public CourseOffering(int year, int expectedStu, int reportedStu, List<CourseTeacher> teachers) {
		this.year = year;
		this.expectedStu = expectedStu;
		this.reportedStu = reportedStu;
		this.teachers = teachers;
	}

	public CourseOffering(int year, int expectedStu, int reportedStu) {
		this.year = year;
		this.expectedStu = expectedStu;
		this.reportedStu = reportedStu;
		teachers = new ArrayList<>();
	}

	public CourseOffering() {
		this(0, 0, 0);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getExpectedStu() {
		return expectedStu;
	}

	public void setExpectedStu(int expectedStu) {
		this.expectedStu = expectedStu;
	}

	public int getReportedStu() {
		return reportedStu;
	}

	public void setReportedStu(int reportedStu) {
		this.reportedStu = reportedStu;
	}

	public List<CourseTeacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<CourseTeacher> teachers) {
		this.teachers = teachers;
	}
	
	public boolean addTeacher(String t, int percentage) {
		if(!teachers.contains(new CourseTeacher(t, percentage))) {
			teachers.add(new CourseTeacher(t, percentage));
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "CourseOffering [year=" + year + ", expectedStu=" + expectedStu
				+ ", reportedStu=" + reportedStu + ", teachers=" + teachers + "]";
	}
}
