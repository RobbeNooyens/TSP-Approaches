package me.robnoo02.travellingsalesman.sorting;

import java.util.Comparator;

import me.robnoo02.travellingsalesman.points.CityPoint;

public class SortFarestFromOthers implements Comparator<CityPoint> {

	@Override
	public int compare(CityPoint point1, CityPoint point2) {
		return (int) (point1.getDistToOthers() - point2.getDistToOthers());
	}


	
}
