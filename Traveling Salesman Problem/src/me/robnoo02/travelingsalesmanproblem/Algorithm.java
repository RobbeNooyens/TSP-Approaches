package me.robnoo02.travelingsalesmanproblem;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import me.robnoo02.travelingsalesmanproblem.points.Point;
import me.robnoo02.travelingsalesmanproblem.points.Segment;
import me.robnoo02.travelingsalesmanproblem.scenes.Pencil;
import me.robnoo02.travelingsalesmanproblem.sort.SortByDistance;
import me.robnoo02.travelingsalesmanproblem.utils.DebugUtil.DebugState;

public class Algorithm extends Thread {

	/**
	 * Steps: 1) Create points
	 */

	private LinkedList<Point> pointsByDistance;
	private HashSet<Segment> segments;
	private final Point origin;
	private Pencil pencil;

	@SuppressWarnings("unchecked")
	public Algorithm(LinkedList<Point> points, Pencil pencil) {
		this.pencil = pencil;
		this.origin = new Point(0, 0);
		pointsByDistance = (LinkedList<Point>) points.clone();
		Collections.sort(pointsByDistance, new SortByDistance(origin));
		Collections.reverse(pointsByDistance);
		for (Point point : pointsByDistance) {
			DebugState.DISTANCE_LOW_HIGH.debug(point.getId() + ": " + point.getX() + "," + point.getY());
		}

	}
	
	@Override
	public void run() {
		if (pointsByDistance.size() >= 3) {
			Point point1 = pointsByDistance.getFirst();
			Point point2 = pointsByDistance.get(1);
			Point point3 = pointsByDistance.get(2);
			segments = new HashSet<>();
			segments.add(new Segment(point1, point2));
			segments.add(new Segment(point2, point3));
			segments.add(new Segment(point1, point3));
			Point segPoint1;
			Point segPoint2;
			Segment segment;
			Point point;
			for (int i = 3; i < pointsByDistance.size(); i++) {
				point = pointsByDistance.get(i);
				DebugState.DISTANCE_LOW_HIGH.debug("Connecting " + point.getId());
				segment = point.getClosest(segments);
				segPoint1 = segment.getSegPoint1();
				segPoint2 = segment.getSegPoint2();
				segments.remove(segment);
				segments.add(new Segment(point, segPoint1));
				segments.add(new Segment(point, segPoint2));
				pencil.clear();
				for (Segment segmentDraw : segments) {
					pencil.drawLine(segmentDraw);
				}
				for (Point pointDraw : pointsByDistance) {
					pencil.drawPoint(pointDraw);
					//pencil.drawNumber(pointDraw);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Segment segmentDraw : segments) {
				pencil.drawLine(segmentDraw);
			}
			for (Point pointDraw : pointsByDistance) {
				pencil.drawPoint(pointDraw);
				//pencil.drawNumber(pointDraw);
			}
		}
	}
}
