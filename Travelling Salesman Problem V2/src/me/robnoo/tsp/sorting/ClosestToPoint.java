package me.robnoo.tsp.sorting;

import java.util.Comparator;

import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.PointPair;

public class ClosestToPoint implements Comparator<PointPair> {
	
	private CityPoint cP;
	
	public ClosestToPoint(CityPoint p) {
		this.cP = p;
	}

	@Override
	public int compare(PointPair p1, PointPair p2) {
		return (int) (p1.distance(cP) - p2.distance(cP));
	}

}
