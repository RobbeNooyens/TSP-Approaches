package me.robnoo02.travellingsalesman.points;

public final class OriginPoint implements Point {

	private final double xPos, yPos, radius;
	
	public OriginPoint(double x, double y, double radius) {
		this.xPos = x;
		this.yPos = y;
		this.radius = radius;
	}
	
	@Override
	public double getX() {
		return xPos;
	}

	@Override
	public double getY() {
		return yPos;
	}

	@Override
	public double distance(Point point) {
		return Math.sqrt(Math.pow(point.getX() - xPos, 2) + Math.pow(point.getY() - yPos, 2));
	}
	
	public double getRadius() {
		return radius;
	}

	
	
}
