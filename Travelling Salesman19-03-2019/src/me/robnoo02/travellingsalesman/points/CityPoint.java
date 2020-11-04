package me.robnoo02.travellingsalesman.points;

import java.util.Collections;
import java.util.LinkedList;

import me.robnoo02.travellingsalesman.sorting.SortByDistance;

public final class CityPoint implements Point {
	
	public static int COUNTER = 0;

	private final double xPos, yPos;
	public CityPoint connect1, connect2;
	private final LinkedList<CityPoint> distances = new LinkedList<>();
	private double distToOthers;
	private final int id;

	public CityPoint(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = COUNTER++;
	}

	public int getConnectedCount() {
		if (connect1 == null)
			return 0;
		if (connect2 == null)
			return 1;
		return 2;
	}
	
	public int getID() {
		return id;
	}

	public CityPoint getConnected1() {
		return connect1;
	}

	public CityPoint getConnected2() {
		return connect2;
	}

	public void connect(CityPoint point) {
		if (connect1 == null)
			connect1 = point;
		else if (connect2 == null && connect1 != point)
			connect2 = point;
	}

	public void disconnect(CityPoint point) {
		if (connect1 == point)
			connect1 = null;
		else if (connect2 == point)
			connect2 = null;
	}

	public boolean isConnected(CityPoint point) {
		return connect1 == point || connect2 == point;
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

	@Override
	public String toString() {
		return xPos + "," + yPos;
	}
	
	public CityPoint getClosest(CityPoint point1, CityPoint point2) {
		return distances.indexOf(point1) > distances.indexOf(point2) ? point1 : point2; 
	}

	public double calculateDistances(LinkedList<CityPoint> points) {
		for (CityPoint point : points)
			distances.add(point);
		Collections.sort(distances, new SortByDistance(this));
		distances.remove(0); // Same point -> d = 0
		this.distToOthers = 0;
		for(CityPoint point: points)
			this.distToOthers += this.distance(point);
		return distances.getLast().distance(this);
	}

	public LinkedList<CityPoint> getDistances() {
		return distances;
	}

	public CityPoint getFarest() {
		return distances.getLast();
	}

	public CityPoint getPointAt(int index) {
		return index < distances.size() ? distances.get(index) : null;
	}

	public double getDistToOthers() {
		return this.distToOthers;
	}
}
