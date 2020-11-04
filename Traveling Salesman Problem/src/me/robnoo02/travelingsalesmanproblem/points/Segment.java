package me.robnoo02.travelingsalesmanproblem.points;

public class Segment {

	private Point point1, point2;
	private double line_dist;
	private double degree;

	public Segment(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
		this.line_dist = point1.distance(point2);
	}

	public double distance(Point point) {
		if (line_dist == 0) return point.distance(point1);
		double distance = Math.abs((point2.getY() - point1.getY()) * point.getX() - (point2.getX() - point1.getX()) * point.getY()
				+ point2.getX() * point1.getY() - point2.getY() * point1.getX()) / line_dist;
		if(distance < point1.distance(point) && distance < point2.distance(point)) {
			double a = point.distance(point.getClosest(this));
			double b = point.distance(point.getFarest(this));
			double c = line_dist;
			degree = Math.acos((-(Math.pow(b, 2)) + Math.pow(a, 2) + Math.pow(c, 2)) / (2*a*c));
			if(degree > Math.PI / 2) {
				return point.distance(point.getClosest(this));
			} else {
				return distance;
			}
		}
		return distance;
	}
	
	public Point getSegPoint1() {
		return point1;
	}
	
	public Point getSegPoint2() {
		return point2;
	}

}
