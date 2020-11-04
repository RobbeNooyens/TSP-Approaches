package me.robnoo02.travellingsalesman.points;

public class PointPair {

	private final Point point1;
	private final Point point2;
	
	public PointPair(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public double getDistance() {
		return point1.distance(point2);
	}
	
	public Point getFirst() {
		return point1;
	}
	
	public Point getLast() {
		return point2;
	}
	
	public OriginPoint getMid() {
		double xCoord = ((point1.getX() + point2.getX()) / 2);
		double yCoord = ((point1.getY() + point2.getY()) / 2);
		return new OriginPoint(xCoord, yCoord, point1.distance(point2));
	}
	
}
