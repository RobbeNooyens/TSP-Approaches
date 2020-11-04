package me.robnoo02.travellingsalesman.points;

import java.util.Collections;
import java.util.List;

import me.robnoo02.travellingsalesman.utils.DebugUtil.DebugState;
import me.robnoo02.travellingsalesman.utils.TimerUtil;

public class ConnectionAlgorithm {

	private List<CityPoint> points;
	private final OriginPoint origin;

	public ConnectionAlgorithm(List<CityPoint> points, OriginPoint origin) {
		this.points = points;
		this.origin = origin;
		Collections.reverse(points);
	}

	public void connectPoints(CityPoint point1, CityPoint point2) {
		point1.connect(point2);
		point2.connect(point1);
	}

	public void disconnectPoints(CityPoint point1, CityPoint point2) {
		point1.disconnect(point2);
		point2.disconnect(point1);
	}

	public boolean isValidToConnect(CityPoint point1, CityPoint point2) {
		if (point1.isConnected(point2)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Already connected");
			return false;
		} else if (origin.distance(point2) > origin.distance(point1)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: " + origin.distance(point1) + " > " + origin.distance(point2));
			return false;
		} else if (point1.getConnectedCount() >= 2 || point2.getConnectedCount() >= 2) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Count1 = " + point1.getConnectedCount() + " & Count2 = " + point2.getConnectedCount());
			return false;
		} else if (formsSeperateShape(point2)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Seperate shape: " + point2.getID());
			return false;
		}
		return true;
	}

	public boolean formsSeperateShape(CityPoint point) {
		CityPoint next = point;
		CityPoint previous;
		for (int i = 0; i < points.size(); i++) {
			previous = next;
			if (previous.connect1 == null && previous.connect2 == null)
				return false;
			if (previous.connect1 == null)
				next = previous.connect2;
			else
				next = previous.connect1;
			if (previous == point || previous == point)
				if (i == 1)
					return true;
				else
					return false;
			if (previous.connect1 != null && previous.connect2 != null)
				return false;
		}
		return false;
	}

	public boolean isIntersection(CityPoint point1, CityPoint point2) {
		CityPoint connected1 = null;
		CityPoint connected2 = null;
		boolean intersected = false;
		for (CityPoint cPoint : points) {
			if (cPoint.getConnected1() != null && hasIntersection(cPoint, cPoint.getConnected1(), point1, point2)) {
				connected1 = cPoint;
				connected2 = cPoint.getConnected1();
				intersected = true;
			} else if (cPoint.getConnected2() != null
					&& hasIntersection(cPoint, cPoint.getConnected2(), point1, point2)) {
				connected1 = cPoint;
				connected2 = cPoint.getConnected2();
				intersected = true;
			}
			if (intersected) {
				disconnectPoints(connected1, connected2);
				if (point1.getConnectedCount() < 2 && point1.getClosest(connected1, connected2).getConnectedCount() < 2)
					connectPoints(point1, point1.getClosest(connected1, connected2));
				if (point2.getConnectedCount() < 2 && point2.getClosest(connected1, connected2).getConnectedCount() < 2)
					connectPoints(point2, point2.getClosest(connected1, connected2));
				DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
						+ " INTERSECTED with " + String.valueOf(connected1) + " - " + String.valueOf(connected2));
			}
		}
		return intersected;
	}

	public void connectPointWithClosest(CityPoint startPoint) {
		for (CityPoint connectTo : startPoint.getDistances()) {
			if (isValidToConnect(startPoint, connectTo)) {
				//if (!isIntersection(startPoint, connectTo)) {
				connectPoints(startPoint, connectTo);
				DebugState.VALID.debug(
						String.valueOf(startPoint.getID()) + " - " + String.valueOf(connectTo.getID()) + " PASSED");
				//}
				break;
			}
		}
	}

	public boolean allConnected() {
		for (CityPoint point : points)
			if (point.getConnectedCount() < 2)
				return false;
		return false;
	}

	public void connectionAlgorithm() {
		TimerUtil timer = new TimerUtil(); // Starts timer
		//Collections.sort(points, new SortFarestFromOthers());
		//Collections.reverse(points);
		for (CityPoint point : points)
			DebugState.DISTANCE_TO_ALL.debug(String.valueOf(point.getID()));
		for (int i = 0; i < points.size(); i++) { // For loop to repeat task multiple times
			int notConnectedCount = 0; // This variable checks wether all points are connected (NOT ACCURATE!)
			for (CityPoint cPoint : points) { // Iterating over points for distance
				if (cPoint.getConnectedCount() >= 2)
					continue;
				notConnectedCount++;
				connectPointWithClosest(cPoint);
			}
			if (notConnectedCount == 0)
				break;
		}
		DebugState.TIME.debug("Algorithm completed: " + timer.read() + "ms");
	}

	public boolean hasIntersection(Point point11, Point point12, Point point21, Point point22) {
		// y = mx + b
		// y = mx - mx1 + y1
		// m = (y1 - y2) / (x1 - x2)
		double m1 = ((point11.getY() - point12.getY()) / (point11.getX() - point12.getX()));
		// b = -mx1 + y1
		double b1 = -m1 * point11.getX() + point11.getY();
		double m2 = ((point21.getY() - point22.getY()) / (point21.getX() - point22.getX()));
		double b2 = -m2 * point21.getX() + point21.getY();
		// intersect = (b2 - b1) / (m1 - m2)
		double x = ((b2 - b1) / (m1 - m2));
		x = Math.round(x);
		if ((point11.getX() < x && x < point12.getX()) || (point11.getX() > x && x > point12.getX()))
			DebugState.INTERSECT.debug("int: " + point11.toString() + " " + point12.toString() + " "
					+ point21.toString() + " " + point22.toString() + " x=" + x);
		return point11.getX() < point12.getX() ? (point11.getX() < x && x < point12.getX())
				: (point11.getX() > x && x > point12.getX());
	}
}
