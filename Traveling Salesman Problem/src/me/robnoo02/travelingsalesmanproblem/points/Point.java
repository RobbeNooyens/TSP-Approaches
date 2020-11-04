package me.robnoo02.travelingsalesmanproblem.points;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import me.robnoo02.travelingsalesmanproblem.sort.SortByDistance;
import me.robnoo02.travelingsalesmanproblem.utils.DebugUtil.DebugState;

public final class Point {

	public static int ID = 0;
	private static final double TRESHOLD = Math.PI * 2;

	private final double x, y;
	private LinkedList<Point> distances;
	private double distToOthers;
	private int id;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.id = ID;
		ID++;
	}

	public double distance(Point point) {
		if (point == null) return 0;
		return Math.sqrt(Math.pow((x - point.x), 2) + Math.pow((y - point.y), 2));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public double distance(Segment segment) {
		return segment.distance(this);
	}

	public Segment getClosest(Set<Segment> segments) {
		if (segments.isEmpty()) return null;
		double currentDist = 0;
		Segment currentBest = null;
		boolean intersect;
		for (Segment segment : segments) {
			double distance = distance(segment);
			DebugState.CLOSEST_SEGMENT.debug(
					"Check " + segment.getSegPoint1().getId() + "-" + segment.getSegPoint2().getId() + ": " + distance);
			intersect = anyIntersect(this, getClosest(segment), segments)
					|| anyIntersect(this, getFarest(segment), segments);
			if (currentBest == null) {
				if (!intersect) {
					currentBest = segment;
					currentDist = distance;
				}
			} else {
				if (distance <= currentDist) {
					DebugState.CLOSEST_SEGMENT.debug("Closer!");
					if (!intersect) {
						currentDist = distance;
						currentBest = segment;
					}
				}

				/*
				 * else if(distance == currentDist) { double distCurrent =
				 * distance(currentBest.getSegPoint1()) + distance(currentBest.getSegPoint2());
				 * double newCurrent = distance(segment.getSegPoint1()) +
				 * distance(segment.getSegPoint2()); if(newCurrent < distCurrent) {
				 * DebugState.CLOSEST_SEGMENT.debug("Closer!"); currentDist = distance;
				 * currentBest = segment; } }
				 */
			}
		}
		DebugState.CLOSEST_SEGMENT.debug("Connected " + getId() + " to " + currentBest.getSegPoint1().getId() + "-"
				+ currentBest.getSegPoint2().getId());
		return currentBest;
	}

	private boolean anyIntersect(Point point1, Point point2, Set<Segment> segments) {
		for (Segment segment : segments) {
			if (intersect(segment.getSegPoint1(), segment.getSegPoint2(), point1, point2)) {
				return true;
			}
		}
		return false;
	}

	private boolean intersect(Point point11, Point point12, Point point21, Point point22) {
		double sum = 0.0;
		sum += angle(point11, point21, point12);
		sum += angle(point21, point12, point22);
		sum += angle(point12, point22, point11);
		sum += angle(point12, point22, point11);
		return sum >= TRESHOLD;
	}

	private double angle(Point vertex, Point point2, Point point3) {
		double angle = Math.acos((Math.sqrt(vertex.distance(point2)) + Math.sqrt(vertex.distance(point3))
				- Math.sqrt(point2.distance(point3))) / (2 * vertex.distance(point2) * vertex.distance(point3)));
		return angle;
	}

	/*
	 * private boolean intersect(Segment segment, Point point1, Point point2) { if
	 * ((segment.getSegPoint1().getX() == segment.getSegPoint2().getX()) ||
	 * point1.getX() == point2.getX()) return false; double m1 =
	 * (segment.getSegPoint2().getY() - segment.getSegPoint1().getY()) /
	 * (segment.getSegPoint2().getX() - segment.getSegPoint1().getX()); double m2 =
	 * (point2.getY() - point1.getY()) / (point2.getX() - point1.getX()); double x =
	 * (-m2 * point2.getX() + point2.getY() + m1 * segment.getSegPoint1().getX() -
	 * segment.getSegPoint1().getY()) / (m1 - m2);
	 * 
	 * if (x > segment.getSegPoint1().getX() != x > segment.getSegPoint2().getX()) {
	 * if (!(x > point1.getX() && x > point2.getX())) { // between boundaries if
	 * (isPoint(x, point1, point2, segment.getSegPoint1(), segment.getSegPoint2()))
	 * return false; DebugState.CLOSEST_SEGMENT.debug("Intersect between " +
	 * point1.getId() + "-" + point2.getId() + " and " +
	 * segment.getSegPoint1().getId() + "-" + segment.getSegPoint2().getId() +
	 * " at " + x); return true; } } return false; }
	 * 
	 * private boolean isPoint(double intersect, Point point1, Point point2, Point
	 * point3, Point point4) { int x = (int) Math.round(intersect); if (x == (int)
	 * point1.getX()) return true; if (x == (int) point2.getX()) return true; if (x
	 * == (int) point3.getX()) return true; if (x == (int) point4.getX()) return
	 * true; return false; }
	 */

	/*
	 * private boolean bounds(Point point1, Point point2, Point point3, Point
	 * point4) {
	 * 
	 * }
	 */

	public Point getClosest(Segment segment) {
		return (distance(segment.getSegPoint1()) <= distance(segment.getSegPoint2()) ? segment.getSegPoint1()
				: segment.getSegPoint2());
	}

	public Point getFarest(Segment segment) {
		return (distance(segment.getSegPoint1()) >= distance(segment.getSegPoint2()) ? segment.getSegPoint1()
				: segment.getSegPoint2());
	}

	@SuppressWarnings("unchecked")
	public double calculateDistances(LinkedList<Point> points) {
		distances = (LinkedList<Point>) points.clone();
		Collections.copy(distances, points);
		Collections.sort(distances, new SortByDistance(this));
		distances.remove(0); // Same point -> d = 0
		this.distToOthers = 0;
		for (Point point : distances)
			this.distToOthers += this.distance(point);
		return distances.getLast().distance(this);
	}

	public double getDistToOthers() {
		return this.distToOthers;
	}
}
