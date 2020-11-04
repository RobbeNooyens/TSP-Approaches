package me.robnoo.tsp.sorting;

import java.util.Comparator;

import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.PointPair;

public class ClosestPoint implements Comparator<CityPoint> {
	
	private PointPair pair;
	
	public ClosestPoint(PointPair pair) {
		this.pair = pair;
	}

	@Override
	public int compare(CityPoint p1, CityPoint p2) {
		return (int) (pair.distance(p1) - pair.distance(p2));
	}
	

}
