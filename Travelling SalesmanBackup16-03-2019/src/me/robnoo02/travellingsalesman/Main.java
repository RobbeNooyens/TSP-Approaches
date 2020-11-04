package me.robnoo02.travellingsalesman;

import javafx.application.Application;
import javafx.stage.Stage;
import me.robnoo02.travellingsalesman.points.SimulationGenerator;
import me.robnoo02.travellingsalesman.scene.SimulationScene;
import me.robnoo02.travellingsalesman.utils.DebugUtil.DebugState;
import me.robnoo02.travellingsalesman.utils.TimerUtil;

public class Main extends Application {

	/*
	 * My Idea:
	 * + Create random points.
	 * + Save points and order based on distance.
	 * + (Comparable interface)
	 * - Get biggest distance.
	 * - Create circle in middle of farest points with 2 farest points on circle.
	 * - Set origin to new relative x and y.
	 * - calculate distances to new origin.
	 * - Iterate over points, from farest to closest to origin.
	 * - Connect farest with 1 closest.
	 * - Repeat, avoid doubles. 2 connections -> skip
	 * 
	 */

	private static final int SIZE = 600;
	private SimulationScene scene;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		TimerUtil timer = new TimerUtil();
		stage.setTitle("Travelling Salesman");
		stage.setResizable(false);
		scene = new SimulationScene(SimulationGenerator.randomPoints(60), SIZE);
		stage.setScene(scene.getScene());
		stage.show();
		DebugState.MAX_DISTANCE.debug("Biggest distance: " + scene.getPointCalculator().getBiggestDistance().getDistance());
		DebugState.TIME.debug("Total time: " + timer.read() + "ms");		
	}

}
