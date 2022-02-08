package model;
//Author: Vagif / John

class CourseElement {
	private String name;
	private double points;

	public CourseElement(String name, double points)
	{
		this.name = name;
		this.points = points;
	}
	
	public CourseElement()
	{
		this.name = "";
		this.points = 0.0;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Course element {" + "name=" + name + ", points=" + points + '}';
	}
}
