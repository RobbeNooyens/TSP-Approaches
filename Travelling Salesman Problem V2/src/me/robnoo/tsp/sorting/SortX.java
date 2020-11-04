package me.robnoo.tsp.sorting;

import java.util.Comparator;

import me.robnoo.tsp.points.CityPoint;

public class SortX implements Comparator<CityPoint> {

	@Override
	public int compare(CityPoint p1, CityPoint p2) {
		return (int) (p1.getX() - p2.getX());
	}

}
