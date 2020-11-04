package me.robnoo02.travellingsalesman.points;

public class PointPair {

	private final CityPoint point1;
	private final CityPoint point2;
	
	public PointPair(CityPoint point1, CityPoint point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public double getDistance() {
		return point1.distance(point2);
	}
	
	public CityPoint getFirst() {
		return point1;
	}
	
	public CityPoint getLast() {
		return point2;
	}
	
	public OriginPoint getMid() {
		double xCoord = ((point1.getX() + point2.getX()) / 2);
		double yCoord = ((point1.getY() + point2.getY()) / 2);
		return new OriginPoint(xCoord, yCoord, point1.distance(point2));
	}
	
}
