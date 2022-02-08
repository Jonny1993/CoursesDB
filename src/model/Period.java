//Author: Vagif/John

package model;

public class Period {
	private int period;
	private double points;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public Period(int period, double points)
	{
		this.period = period;
		this.points = points;
	}
	public Period()
	{
		this.period = 0;
		this.points = 0.0;
	}

	@Override
	public String toString() {
		return "P" + period + ", " + points + "HP";
	}
}
