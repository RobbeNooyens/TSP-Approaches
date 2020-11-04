package me.robnoo02.travellingsalesman.scene;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import me.robnoo02.travellingsalesman.Main;
import me.robnoo02.travellingsalesman.points.CityPoint;
import me.robnoo02.travellingsalesman.points.Point;

public class DrawPointsScene {
	
	private SceneShapesDrawer sceneObjects;
	private final Group group;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Scene scene;
	private final LinkedList<CityPoint> points = new LinkedList<>();
	
	public DrawPointsScene(Stage stage, int size) {
		this.group = new Group();
		this.canvas = new Canvas(size, size);
		this.gc = canvas.getGraphicsContext2D();
		sceneObjects = new SceneShapesDrawer(size, gc);
		Button button = new Button("start");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double currentMax = 0;
				for (Point point : points) {
					if (!(point instanceof CityPoint))
						continue;
					double currentPointMax = ((CityPoint) point).calculateDistances(points);
					if(currentPointMax > currentMax)
						currentMax = currentPointMax;
				}
				Main.startAlgorithm(stage, points);
			}
			
		});
		group.getChildren().addAll(canvas, button);
		this.scene = new Scene(group);
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	CityPoint point = new CityPoint(event.getX() - size/2, event.getY() - size/2);
	            points.add(point);
	            sceneObjects.drawPoint(point);
	        }
	    });
	}
	
	public Scene getScene() {
		return scene;
	}

}
