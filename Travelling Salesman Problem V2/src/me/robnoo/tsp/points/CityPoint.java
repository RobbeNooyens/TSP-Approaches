package me.robnoo.tsp.points;

import java.util.ArrayList;
import java.util.List;

public final class CityPoint implements Point {
	
	private final int x, y, id;
	private final double dO;
	private double distances;
	public static int ID_COUNTER = 1;
	private List<PointPair> pairs = new ArrayList<>(4);
	
	public CityPoint(int x, int y) {
		this.x = x;
		this.y = y;
		this.id = ID_COUNTER++;
		this.dO = dist(Origin.get());
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public double dOrigin() {
		return dO;
	}
	
	public void setTotalDistances(List<CityPoint> points) {
		distances = 0;
		for(CityPoint p: points)
			distances += this.dist(p);
	}
	
	public double getTotalDistances() {
		return distances;
	}
	
	public void connect(PointPair pair) {
		pairs.add(pair);
	}
	
	public void disconnect(PointPair pair) {
		pairs.remove(pair);
	}
	
	public List<PointPair> connections(){
		return pairs;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CityPoint)) return false;
		CityPoint p = (CityPoint) o;
		return (p.getID() == id);
	}
	
	@Override
	public int hashCode() {
		int r = 31 * id;
		return r;
	}

}
