package me.robnoo.tsp.algorithms;

import java.util.LinkedList;

import me.robnoo.tsp.Debug;
import me.robnoo.tsp.Main;
import me.robnoo.tsp.Timer;
import me.robnoo.tsp.Variables;
import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.Point;
import me.robnoo.tsp.points.PointPair;

public class BreakLinesAlgorithm extends Thread implements Algorithm, Debug {

	// Given:
	LinkedList<CityPoint> points;
	LinkedList<PointPair> pairs;
	Main main;
	double route = 0;
	
	CityPoint p;

	@Override
	public void execute() {
		/*
		 * 1) connect 3 furthest points from origin 2) iterate over points from 4th to
		 * closest to origin 3) check which lines is closest to point -> break line,
		 * connect point with two points
		 */
		if (points.size() <= 3) return;
		Timer.start();
		route = 0;
		route += pairs.get(0).dist();
		route += pairs.get(1).dist();
		route += pairs.get(2).dist();
		for (int i = 0; i < points.size(); i++) {
			if (Variables.SHOW_STEPS) {
				showSteps();
				try {
					Thread.sleep(Variables.DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			p = points.get(i);
			//Collections.sort(pairs, new ClosestToPoint(p));
			// TODO: custom compare methods instead of comparators
			PointPair closest = getShortest(); // getClosest(); // getClosestPair();
			//debug("Point: " + p.getID());
			//debug("Closest point 1: " + closest.getPoint1().getID());
			//debug("Closest point 2: " + closest.getPoint2().getID());
			pairs.add(new PointPair(p, closest.getPoint1()));
			pairs.add(new PointPair(p, closest.getPoint2()));
			closest.disconnect();
			pairs.remove(closest);
			//debug("===============================");
		}
		for(int i = 0; i < points.size(); i++) {
			p = points.get(i);
			PointPair p1 = p.connections().get(0);
			PointPair p2 = p.connections().get(1);
			double distOther = p1.getOther(p).dist(p2.getOther(p));
			double hypDist = route - p1.dist() - p2.dist() + distOther;
			for(PointPair pair: pairs) {
				if(p1.equals(pair) || p2.equals(pair)) continue;
				if(Double.compare(hypDist + pair.getPoint1().dist(p) + pair.getPoint2().dist(p) - pair.dist(), route) < 1) {
					pairs.add(new PointPair(pair.getPoint1(), p));
					pairs.add(new PointPair(pair.getPoint2(), p));
					pairs.add(new PointPair(p1.getOther(p), p2.getOther(p)));
					pair.disconnect();
					p1.disconnect();
					p2.disconnect();
					pairs.remove(pair);
					pairs.remove(p1);
					pairs.remove(p2);
					break;
				}
			}
		}
		System.out.println("Time: " + (int) Timer.stop() + "ms");
		showSteps();
	}
	
	private PointPair getShortest() {
		PointPair closest = pairs.get(0);
		double distance = hypotheticalDistance(closest);
		for(PointPair pair: pairs) {
			double hypDist = hypotheticalDistance(pair);
			if(Double.compare(hypDist, distance) < 1) {
				closest = pair;
				distance = hypDist;
			}
		}
		route = distance;
		return closest;
	}
	
	private double hypotheticalDistance(PointPair pair) {
		return route - pair.dist() + pair.getPoint1().dist(p) + pair.getPoint2().dist(p);
	}
	
	private PointPair getClosest() {
		if(pairs.size() < 1) return null;
		PointPair closestPair = pairs.get(0);
		double closestDist = closestPair.distance(p);
		for(PointPair pair: pairs) {
			if(pair.equals(closestPair)) continue;
			double distance = pair.distance(p);
			if(Double.compare(distance, closestDist) < 0) {
				closestPair = pair;
				closestDist = distance;
			} else if(Double.compare(distance, closestDist) == 0) {
				//debug("Same distance: " + distance);
				closestPair = getClosestPair(closestPair, pair);
				closestDist = closestPair.distance(p);
			}
		}
		return closestPair;
	}

	/*private PointPair getClosestPair() {
		return getClosestPair(pairs.get(0), pairs.get(1));
		
	}*/
	
	private PointPair getClosestPair(PointPair pair0, PointPair pair1) {
		if (Double.compare(pair0.distance(p), pair1.distance(p)) != 0) return pair0;
		//debug("Same distance: " + pairs.get(0).distance(p));
		Point twice = pair0.getDoublePoint(pair1);
		if (twice == null) return pair0;
		//debug("Dubbel point: " + twice.getID());
		//debug("--------------");
		//debug("Pairs 0: " + pairs.get(0).getOther(twice).getID());
		//debug("Pairs 1: " + pairs.get(1).getOther(twice).getID());
		double dist0 = 0;
		dist0 += pair0.getOther(twice).dist(p);
		dist0 += pair1.dist();
		double dist1 = 0;
		dist1 += pair1.getOther(twice).dist(p);
		dist1 += pair0.dist();
		//debug("Distance 0: " + dist0);
		//debug("Distance 1: " + dist1);
		//debug("====================================");
		return (dist0 > dist1) ? pair1 : pair0;
		
	}

	@Override
	public LinkedList<PointPair> getPairs() {
		return pairs;
	}

	@Override
	public void initialize(LinkedList<CityPoint> points, LinkedList<PointPair> pairs) {
		this.points = points;
		this.pairs = pairs;
	}

	@Override
	public void setMain(Main main) {
		this.main = main;
		;
	}

	@Override
	public Main getMain() {
		return main;
	}

	@Override
	public LinkedList<CityPoint> getPoints() {
		return points;
	}

	@Override
	public void run() {
		execute();
	}

}
