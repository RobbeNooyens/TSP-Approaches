package me.robnoo02.travellingsalesman.points;

import java.util.LinkedList;

public class PointCalculator {
	
	private final LinkedList<Point> points;

	public PointCalculator(LinkedList<Point> points) {
		this.points = points;
	}
	
	public PointPair getBiggestDistance() {
		double currentMax = 0;
		CityPoint currentMaxPoint = null;
		for(Point point: points) {
			if(!(point instanceof CityPoint))
				continue;
			CityPoint p = (CityPoint) point;
			double distance = p.distance(p.getFarest());
			if(distance > currentMax) {
				currentMax = distance;
				currentMaxPoint = p;
			}
		}
		if(currentMaxPoint == null)
			return null;
		return new PointPair(currentMaxPoint, currentMaxPoint.getFarest());
	}
	
}
