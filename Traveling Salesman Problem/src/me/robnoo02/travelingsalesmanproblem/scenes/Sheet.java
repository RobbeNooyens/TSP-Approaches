package me.robnoo02.travelingsalesmanproblem.scenes;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import me.robnoo02.travelingsalesmanproblem.Algorithm;
import me.robnoo02.travelingsalesmanproblem.points.Point;
import me.robnoo02.travelingsalesmanproblem.utils.DebugUtil.DebugState;

public class Sheet {
	
	private static final int SIZE = 600, RADIUS = 2;

	private final VBox group;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Scene scene;
	private final LinkedList<Point> points = new LinkedList<>();
	private final Pencil pencil;
	private final Point center, origin;

	public Sheet() {
		this.group = new VBox();
		this.canvas = new Canvas(SIZE, SIZE);
		this.gc = canvas.getGraphicsContext2D();
		this.center = new Point(SIZE/2, SIZE/2);
		this.pencil = new Pencil(gc, center, RADIUS);
		this.origin = new Point(0,0);
		pencil.drawPoint(origin);
		Button button = new Button("start");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DebugState.DISTANCE_LOW_HIGH.debug("===============");
				pencil.clear();
				pencil.drawPoint(origin);
				double currentMax = 0;
				for (Point point : points) {
					double currentPointMax = point.calculateDistances(points);
					if (currentPointMax > currentMax) currentMax = currentPointMax;
				}
				Algorithm algo = new Algorithm(points, pencil);
				algo.start();
			}

		});
		Button clear = new Button("clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pencil.clear();
				points.clear();
				pencil.drawPoint(origin);
			}

		});
		group.getChildren().addAll(canvas, button, clear);
		this.scene = new Scene(group);
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Point point = new Point(event.getX() - SIZE / 2, event.getY() - SIZE / 2);
				points.add(point);
				pencil.drawPoint(point);
				//pencil.drawNumber(point);
			}
		});
	}
	
	public Scene getScene() {
		return scene;
	}

}
