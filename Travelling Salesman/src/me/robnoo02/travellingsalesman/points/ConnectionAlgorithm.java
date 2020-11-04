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

	public boolean isValidToConnect(CityPoint point1, CityPoint point2, boolean forIntersection) {
		if (point1.isConnected(point2)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Already connected");
			return false;
		} else if (!forIntersection && origin.distance(point2) > origin.distance(point1)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID()) + " FAILED: "
					+ origin.distance(point1) + " > " + origin.distance(point2));
			return false;
		} else if (point1.getConnectedCount() >= 2 || point2.getConnectedCount() >= 2) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Count1 = " + point1.getConnectedCount() + " & Count2 = " + point2.getConnectedCount());
			return false;
		} else if (formsSeperateShape(point1, point2)) {
			DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
					+ " FAILED: Seperate shape: " + point2.getID());
			return false;
		}
		return true;
	}

	public boolean formsSeperateShape(CityPoint start, CityPoint point) {
		if (point == null || start == null)
			return true;
		CityPoint next = null;
		CityPoint current = point;
		CityPoint previous = null;
		for (int i = 0; i < points.size() - 1; i++) {
			if (current.getConnected1() == start || current.getConnected2() == start) {
				if (i < points.size() - 2)
					return true;
			} else if (current.getConnected1() == null && current.getConnected2() == null) {
				return false;
			} else if (current.getConnected1() != null && current.getConnected1() != previous) {
				next = current.getConnected1();
			} else if (current.getConnected2() != null && current.getConnected2() != previous) {
				next = current.getConnected2();
			}
			previous = current;
			current = next;
		}
		return false;
	}

	public boolean isIntersection(CityPoint point1, CityPoint point2) {
		CityPoint connected1 = null;
		CityPoint connected2 = null;
		boolean intersected = false;
		for (CityPoint cPoint : points) {
			if(cPoint == point1 || cPoint == point2)
				continue;
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
				disconnectPoints(point1, point2);
				CityPoint connectP1With;
				CityPoint connectP2With;
				
				if(point1.isConnected(connected1))
					connectP1With = connected2;
				else if(point1.isConnected(connected2))
					connectP1With = connected1;
				else
					connectP1With = point1.getClosest(connected1, connected2);
				
				if(point2.isConnected(connected1))
					connectP2With = connected2;
				else if(point2.isConnected(connected2))
					connectP2With = connected1;
				else
					connectP2With = point2.getClosest(connected1, connected2);
				if (isValidToConnect(point1, connectP1With, true))
					connectPoints(point1, connectP1With);
				if (isValidToConnect(point2, connectP2With, true))
					connectPoints(point2, connectP2With);
				DebugState.VALID.debug(String.valueOf(point1.getID()) + " - " + String.valueOf(point2.getID())
						+ " INTERSECTED with " + String.valueOf(connected1.getID()) + " - " + String.valueOf(connected2.getID()));
			}
		}
		return intersected;
	}

	public void connectPointWithClosest(CityPoint startPoint) {
		for (CityPoint connectTo : startPoint.getDistances()) {
			if (isValidToConnect(startPoint, connectTo, false)) {
				if (!isIntersection(startPoint, connectTo)) {
					if (formsSeperateShape(startPoint, connectTo))
						continue;
					connectPoints(startPoint, connectTo);
					DebugState.VALID.debug(
							String.valueOf(startPoint.getID()) + " - " + String.valueOf(connectTo.getID()) + " PASSED");
				}
				break;
			}
		}
	}

	public boolean allConnected() {
		for (CityPoint point : points)
			if (point.getConnectedCount() < 2)
				return false;
		return true;
	}
	
	public void connectionAlgorithm() {
		
	}

	/*public void connectionAlgorithm() {
		TimerUtil timer = new TimerUtil(); // Starts timer
		//Collections.sort(points, new SortFarestFromOthers());
		//Collections.reverse(points);
		for (CityPoint point : points)
			DebugState.DISTANCE_TO_ALL.debug(String.valueOf(point.getID()));
		do {
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
		} while (!allConnected());
		System.out.println("Intersect Check");
		for(CityPoint point: points) {
			isIntersection(point, point.getConnected1());
			isIntersection(point, point.getConnected2());
		}
		DebugState.TIME.debug("Algorithm completed: " + timer.read() + "ms");
	}*/

	public boolean hasIntersection(Point point11, Point point12, Point point21, Point point22) {
		if(point11 == null || point12 == null || point21 == null || point22 == null)
			return false;
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
		if(point11.getX() < point12.getX() ? point11.getX() < x && x < point12.getX() : (point11.getX() > x && x > point12.getX()))
			return point21.getX() < point22.getX() ? (point21.getX() < x && x < point22.getX())
					: (point21.getX() > x && x > point22.getX());
		else
			return false;
	}
}
