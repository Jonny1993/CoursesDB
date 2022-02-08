//Author: John

package model;

class CourseTeacher {
	private String teacher;
	private int percentage;
	
	public CourseTeacher(String teacherName, int percentage) {
		this.teacher = teacherName;
		this.percentage = percentage;
	}
	
	public CourseTeacher( ) {
		this.teacher = "";
		this.percentage = 0;
	}
	
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacherName) {
		this.teacher = teacherName;
	}
	
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String toString() {
		return "Name: " + teacher + ", percentage: " + percentage;
	}
}
