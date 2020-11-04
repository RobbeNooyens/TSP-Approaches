package me.robnoo.tsp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import me.robnoo.tsp.Variables.PointPicker;
import me.robnoo.tsp.algorithms.BreakLinesAlgorithm;
import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.PointManager;
import me.robnoo.tsp.points.PointPair;
import me.robnoo.tsp.sorting.ClosestPoint;
import me.robnoo.tsp.sorting.DistanceOrigin;
import me.robnoo.tsp.visualization.SimulScene;

public class AlgorithmThread extends Thread {

	private LinkedList<CityPoint> points;
	private LinkedList<PointPair> pairs;
	private Main main;

	public AlgorithmThread(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		while (!SimulScene.STOP) {
			main.scene.getPencil().clearScreen();
			if (Variables.POINTS == PointPicker.RANDOM)
				points = PointManager.generate(Variables.ITERATIONS);
			else if (Variables.POINTS == PointPicker.IMAGE_1)
				points = PointManager.image1();
			else
				return;
			Collections.sort(points, new DistanceOrigin());
			//drawPoints();
			//Collections.sort(points, new DistanceFromOthers());
			//Collections.reverse(points);
			pairs = PointManager.firstConnections(points);
			points.removeFirst();
			points.removeFirst();
			Collections.sort(points, new ClosestPoint(pairs.getFirst()));
			points.removeLast();
			//Collections.reverse(points);
			Collections.sort(points, new DistanceOrigin());
			//Collections.reverse(points);
			
			BreakLinesAlgorithm breakLinesAlgorithm = new BreakLinesAlgorithm();
			breakLinesAlgorithm.setMain(main);
			breakLinesAlgorithm.initialize(points, pairs);
			//long start = (new Date()).getTime();
			breakLinesAlgorithm.run();
			//long stop = (new Date()).getTime();
			//System.out.println("amount: " + Variables.ITERATIONS + "  - " + (stop-start) + "ms");
			try {
				TimeUnit.SECONDS.sleep(Variables.SECONDS_BETWEEN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
