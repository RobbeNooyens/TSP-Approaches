package me.robnoo.tsp.points;

public interface Point {
	
	public int getX();
	public int getY();
	public int getID();

	default double dist(Point point) {
		return Math.sqrt(Math.pow(point.getX() - getX(), 2) + Math.pow(point.getY() - getY(), 2));
	}

}
