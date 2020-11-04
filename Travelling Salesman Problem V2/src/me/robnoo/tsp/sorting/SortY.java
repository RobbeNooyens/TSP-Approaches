package me.robnoo.tsp.sorting;

import java.util.Comparator;

import me.robnoo.tsp.points.CityPoint;

public class SortY implements Comparator<CityPoint> {

	@Override
	public int compare(CityPoint p1, CityPoint p2) {
		return (int) (p1.getY() - p2.getY());
	}

}
