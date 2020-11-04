package me.robnoo.tsp;

import javafx.application.Application;
import javafx.stage.Stage;
import me.robnoo.tsp.visualization.SimulScene;

public class Main extends Application {

	public SimulScene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Travelling Salesman Problem - Robbe Nooyens");
		scene = new SimulScene(this, false);
		primaryStage.setScene(scene.getScene());
		primaryStage.show();
		AlgorithmThread algoThread = new AlgorithmThread(this);
		algoThread.start();
		/*
		 * int amount = 10; while(amount < 300) { startAlgorithm(amount);
		 * Thread.sleep(1000L); amount += 10; }
		 */
	}

	public SimulScene getScene() {
		return scene;
	}

}
