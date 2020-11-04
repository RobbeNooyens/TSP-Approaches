package me.robnoo02.travellingsalesman.sorting;

import java.util.Comparator;

import me.robnoo02.travellingsalesman.points.Point;

public class SortByDistance implements Comparator<Point>{

	private Point reference;
	
	public SortByDistance(Point reference) {
		this.reference = reference;
	}
	
	@Override
	public int compare(Point point1, Point point2) {
		return (int) (reference.distance(point1) - reference.distance(point2));
	}

}
