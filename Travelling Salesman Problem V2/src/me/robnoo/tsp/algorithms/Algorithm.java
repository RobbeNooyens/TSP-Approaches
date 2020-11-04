package me.robnoo.tsp.algorithms;

import java.util.LinkedList;

import me.robnoo.tsp.Main;
import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.PointPair;

public interface Algorithm {

	public void initialize(LinkedList<CityPoint> points, LinkedList<PointPair> pairs);

	public void execute();

	public void setMain(Main main);

	public Main getMain();

	public LinkedList<CityPoint> getPoints();

	public LinkedList<PointPair> getPairs();

	default void showSteps() {
		getMain().getScene().getPencil().drawCurrentPath(getPoints(), getPairs());
	}

}
