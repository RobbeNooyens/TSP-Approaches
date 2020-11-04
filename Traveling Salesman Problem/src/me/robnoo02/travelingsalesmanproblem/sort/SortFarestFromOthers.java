package me.robnoo02.travelingsalesmanproblem.sort;

import java.util.Comparator;

import me.robnoo02.travelingsalesmanproblem.points.Point;

public class SortFarestFromOthers implements Comparator<Point> {

	@Override
	public int compare(Point point1, Point point2) {
		return (int) (point1.getDistToOthers() - point2.getDistToOthers());
	}


	
}
