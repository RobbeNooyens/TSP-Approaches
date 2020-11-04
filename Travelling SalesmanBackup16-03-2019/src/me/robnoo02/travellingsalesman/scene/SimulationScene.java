package me.robnoo02.travellingsalesman.scene;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import me.robnoo02.travellingsalesman.points.CityPoint;
import me.robnoo02.travellingsalesman.points.OriginPoint;
import me.robnoo02.travellingsalesman.points.Point;
import me.robnoo02.travellingsalesman.points.PointCalculator;
import me.robnoo02.travellingsalesman.sorting.SortByDistance;
import me.robnoo02.travellingsalesman.utils.DebugUtil.DebugState;
import me.robnoo02.travellingsalesman.utils.TimerUtil;

public class SimulationScene {

	private Scene scene;
	private final SceneShapesDrawer sceneObjects;
	private List<Point> points;
	private final Group group;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final PointCalculator pointCalc;
	private OriginPoint origin;

	public SimulationScene(LinkedList<Point> points, int size) {
		Objects.requireNonNull(points, "HashMap 'points' can't be null!");
		this.points = points;
		this.group = new Group();
		this.canvas = new Canvas(size, size);
		this.gc = canvas.getGraphicsContext2D();
		this.sceneObjects = new SceneShapesDrawer(size, gc);
		this.pointCalc = new PointCalculator(points);
		gc.setLineWidth(2);
		sceneObjects.drawCircle(0, 0, size);
		drawShapes();
		group.getChildren().add(canvas);
		scene = new Scene(group);
	}

	public void drawShapes() {
		for (Point point : points)
			sceneObjects.drawPoint(point);
		//connectWithLines(gc);
		origin = getPointCalculator().getBiggestDistance().getMid();
		sceneObjects.drawPoint(origin);
		//sceneObjects.drawCircle(origin.getX(), origin.getY(), origin.getRadius());
		for (Point p : points) {
			//sceneObjects.drawCircle(origin.getX(), origin.getY(), p.distance(origin) * 2);
			DebugState.CIRCLE.debug(String.valueOf(p.distance(origin)));
		}
		Collections.sort(points, new SortByDistance(origin));
		connectionAlgorithm();

	}

	public void connectionAlgorithm() {
		TimerUtil timer = new TimerUtil();
		Collections.reverse(points);
		for (int i = 0; i < points.size(); i++) {
			int count = 0;
			for (Point point : points) {
				if (!(point instanceof CityPoint))
					continue;
				CityPoint cPoint = (CityPoint) point;
				if(cPoint.getConnectedCount() >=2)
					continue;
				count++;
				boolean escape = false;
				for (CityPoint connectTo : cPoint.getDistances()) {
					if (origin.distance(connectTo) <= origin.distance(cPoint)) {
						if (connectTo.getConnectedCount() < 2) {
							cPoint.addConnected(connectTo);
							connectTo.addConnected(cPoint);
							escape = true;
						}
					}
					if (escape)
						break;
				}
			}
			if(count == 0)
				break;
		}
		for (Point point : points) {
			if (!(point instanceof CityPoint))
				continue;
			CityPoint cPoint = (CityPoint) point;
			if (cPoint.getConnected1() != null)
				sceneObjects.drawLine(cPoint, cPoint.getConnected1());
			if (cPoint.getConnected2() != null)
				sceneObjects.drawLine(cPoint, cPoint.getConnected2());
		}
		DebugState.TIME.debug("Algorithm completed: " + timer.read() + "ms");
	}

	public void connectWithLines(GraphicsContext gc) {
		TimerUtil timer = new TimerUtil();
		for (Point point : points) {
			if (!(point instanceof CityPoint))
				continue;
			if (DebugState.DISTANCE_LOW_HIGH.isEnabled()) {
				System.out.println("X: " + point.getX() + " | Y: " + point.getY());
				for (Point point2 : ((CityPoint) point).getDistances())
					System.out.println(point.distance(point2));
				System.out.println("===================");
			}
			CityPoint somePlace = ((CityPoint) point).getPointAt(1);
			sceneObjects.drawLine(point, somePlace);
		}
		DebugState.TIME.debug("Connected random lines: " + timer.read() + "ms");
	}

	public Scene getScene() {
		return scene;
	}

	public PointCalculator getPointCalculator() {
		return pointCalc;
	}

}
