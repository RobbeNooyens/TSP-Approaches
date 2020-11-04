package me.robnoo.tsp.sorting;

import java.util.Comparator;

import me.robnoo.tsp.points.CityPoint;

/**
 * Sort from furthest from Origin to closest to Origin
 * @author Robnoo02
 *
 */
public class DistanceOrigin implements Comparator<CityPoint> {

	@Override
	public int compare(CityPoint p1, CityPoint p2) {
		return (int) (p2.dOrigin() - p1.dOrigin());
	}

}
