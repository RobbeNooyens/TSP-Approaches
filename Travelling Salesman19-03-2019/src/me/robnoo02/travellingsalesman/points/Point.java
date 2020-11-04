package me.robnoo02.travellingsalesman.points;

public interface Point {

	public double getX();
	public double getY();
	public double distance(Point point);
	public String toString();
	default public boolean isCityPoint() {
		return this instanceof CityPoint;
	}
	
}
