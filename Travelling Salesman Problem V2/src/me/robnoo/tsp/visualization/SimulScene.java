package me.robnoo.tsp.visualization;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import me.robnoo.tsp.Main;
import me.robnoo.tsp.points.CityPoint;

public class SimulScene {

	private Scene scene;
	private Group group;
	private Canvas canvas;
	private final GraphicsContext gc;
	private final Pencil pencil;
	private final Button refresh;
	
	public static boolean STOP = false;

	public static final int S = 500; // Screen size

	public SimulScene(Main main, boolean drawGrid) {
		this.group = new Group();
		this.canvas = new Canvas(S, S);
		this.gc = canvas.getGraphicsContext2D();
		this.pencil = new Pencil(S, gc);
		gc.setLineWidth(1);
		if (drawGrid) {
			pencil.drawLine(new CityPoint(0, S), new CityPoint(0, -S));
			pencil.drawLine(new CityPoint(S, 0), new CityPoint(-S, 0));
			pencil.drawCircle(0, 0, S);
		}
		this.refresh = new Button("Stop");
		refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				STOP = true;
				
			}
			
		});
		group.getChildren().add(canvas);
		group.getChildren().add(refresh);
		scene = new Scene(group);
	}

	public Scene getScene() {
		return scene;
	}

	public Pencil getPencil() {
		return pencil;
	}
	
	public void eraser() {
		gc.clearRect(0, 0, S, S);
	}

}
