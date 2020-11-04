package me.robnoo02.travellingsalesman.points;

import java.util.Collections;
import java.util.LinkedList;

import me.robnoo02.travellingsalesman.sorting.SortByDistance;

public final class CityPoint implements Point {

	private final double xPos, yPos;
	private CityPoint connect1, connect2;
	private final LinkedList<CityPoint> distances = new LinkedList<>();
	
	public CityPoint(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getConnectedCount() {
		if(connect1 == null)
			return 0;
		if(connect2 == null)
			return 1;
		return 2;
	}
	
	public CityPoint getConnected1() {
		return connect1;
	}
	
	public CityPoint getConnected2() {
		return connect2;
	}
	
	public void addConnected(CityPoint point) {
		if(connect1 == null)
			connect1 = point;
		else if(connect2 == null && connect1 != point)
			connect2 = point;
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
	
	public double calculateDistances(LinkedList<Point> points) {
		for(Point point: points) {
			if(!(point instanceof CityPoint))
				continue;
			distances.add((CityPoint) point);
		}
		Collections.sort(distances, new SortByDistance(this));
		distances.remove(0); // Same point -> d = 0
		return distances.getLast().distance(this);
	}
	
	public LinkedList<CityPoint> getDistances(){
		return distances;
	}
	
	public CityPoint getFarest() {
		return distances.getLast();
	}
	
	public CityPoint getPointAt(int index) {
		return index < distances.size() ? distances.get(index) : null;
	}

}
