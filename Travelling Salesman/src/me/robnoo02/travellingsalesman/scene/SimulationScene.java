package me.robnoo02.travellingsalesman.scene;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.robnoo02.travellingsalesman.points.CityPoint;
import me.robnoo02.travellingsalesman.points.ConnectionAlgorithm;
import me.robnoo02.travellingsalesman.points.OriginPoint;
import me.robnoo02.travellingsalesman.points.Point;
import me.robnoo02.travellingsalesman.points.PointCalculator;
import me.robnoo02.travellingsalesman.points.SimulationGenerator;
import me.robnoo02.travellingsalesman.sorting.SortByDistance;
import me.robnoo02.travellingsalesman.utils.DebugUtil.DebugState;

public class SimulationScene {

	private Scene scene;
	private final SceneShapesDrawer sceneObjects;
	private List<CityPoint> points;
	private final Group group;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final PointCalculator pointCalc;
	private final boolean lines, ids;
	private OriginPoint origin;

	public SimulationScene(LinkedList<CityPoint> points, int size, Stage stage, boolean lines, boolean ids) {
		Objects.requireNonNull(points);
		this.points = points;
		this.group = new Group();
		this.canvas = new Canvas(size, size);
		this.gc = canvas.getGraphicsContext2D();
		this.sceneObjects = new SceneShapesDrawer(size, gc);
		this.pointCalc = new PointCalculator(points);
		this.lines = lines;
		this.ids = ids;
		gc.setLineWidth(1);
		sceneObjects.drawLine(new CityPoint(0, size), new CityPoint(0, -size));
		sceneObjects.drawLine(new CityPoint(size, 0), new CityPoint(-size, 0));
		sceneObjects.drawCircle(0, 0, size);
		drawShapes();
		group.getChildren().add(canvas);

		TextField number = new TextField();
		number.setTranslateY(25);
		number.setPrefWidth(75);
		number.setText(String.valueOf(points.size()));
		TextField linesB = new TextField();
		linesB.setTranslateY(50);
		linesB.setPrefWidth(75);
		linesB.setText(String.valueOf(lines));
		TextField idB = new TextField();
		idB.setTranslateY(75);
		idB.setPrefWidth(75);
		idB.setText(String.valueOf(ids));
		Button button = new Button("Refresh");
		button.setCursor(Cursor.OPEN_HAND);
		button.setPrefWidth(75);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CityPoint.COUNTER = 0;
				//Main.drawPoints(stage);
				stage.setScene(
						(new SimulationScene(SimulationGenerator.randomCityPoints(Integer.valueOf(number.getText())),
								size, stage, Boolean.valueOf(linesB.getText()), Boolean.valueOf(linesB.getText())))
										.getScene());
			}

		});
		group.getChildren().addAll(number, linesB, button, idB);
		scene = new Scene(group);
	}

	public void drawShapes() {
		for (Point point : points)
			sceneObjects.drawPoint(point);
		//connectWithLines(gc);
		origin = getPointCalculator().getBiggestDistance().getMid();
		//sceneObjects.drawPoint(origin);
		//sceneObjects.drawCircle(origin.getX(), origin.getY(), origin.getRadius());
		for (Point p : points) {
			if (lines)
				sceneObjects.drawCircle(origin.getX(), origin.getY(), p.distance(origin) * 2);
			DebugState.CIRCLE.debug(String.valueOf(p.distance(origin)));
		}
		Collections.sort(points, new SortByDistance(origin));
		//Collections.sort(points, new SortFarestFromOthers());
		ConnectionAlgorithm alg = new ConnectionAlgorithm(points, origin);
		alg.connectionAlgorithm();
		for (CityPoint cPoint : points) {
			if (ids)
				sceneObjects.drawNumber(cPoint.getX(), cPoint.getY(), String.valueOf(cPoint.getID()));
			if (cPoint.getConnected1() != null)
				sceneObjects.drawLine(cPoint, cPoint.getConnected1());
			if (cPoint.getConnected2() != null)
				sceneObjects.drawLine(cPoint, cPoint.getConnected2());
		}

		// For debug
		for (CityPoint point : points) {
			DebugState.DISTANCE_TO_ALL.debug(String.valueOf(point.getID()) + ": " + point.getDistToOthers() + " - "
					+ String.valueOf(point.getConnectedCount()));
			try {
				DebugState.CONNECT.debug(String
						.valueOf(point.getID() + " connected with " + String.valueOf(point.getConnected1().getID())
								+ " & " + String.valueOf(point.getConnected2().getID())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DebugState.DISTANCE_TO_ALL.debug("=======================");

	}

	/*
	public void connectionAlgorithm() {
		TimerUtil timer = new TimerUtil();
		Collections.reverse(points);
		for (int i = 0; i < points.size(); i++) {
			int count = 0;
			for (Point point : points) {
				if (!(point instanceof CityPoint))
					continue;
				CityPoint cPoint = (CityPoint) point;
				if (cPoint.getConnectedCount() >= 2)
					continue;
				count++;
				boolean escape = false;
				for (CityPoint connectTo : cPoint.getDistances()) {
					if (origin.distance(connectTo) <= origin.distance(cPoint)) {
						if (connectTo.getConnectedCount() < 2) {
							hasAnyIntersection(cPoint, connectTo);
							cPoint.addConnected(connectTo);
							connectTo.addConnected(cPoint);
							escape = true;
						}
					}
					if (escape)
						break;
				}
				if (!escape) {
					for (CityPoint connectTo : cPoint.getDistances()) {
						if (connectTo.getConnectedCount() < 2) {
							cPoint.addConnected(connectTo);
							connectTo.addConnected(cPoint);
							escape = true;
						}
						if (escape)
							break;
					}
				}
			}
			if (count == 0)
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
	
	public boolean hasAnyIntersection(Point point1, Point point2) {
		for (Point p : points) {
			if (!(p instanceof CityPoint))
				continue;
			CityPoint cPoint = (CityPoint) p;
			if (cPoint.getConnected1() != null && hasIntersection(cPoint, cPoint.getConnected1(), point1, point2))
				return true;
			if (cPoint.getConnected2() != null && hasIntersection(cPoint, cPoint.getConnected2(), point1, point2))
				return true;
		}
		return false;
	}
	
	public boolean hasIntersection(Point point11, Point point12, Point point21, Point point22) {
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
		if((point11.getX() < x && x < point12.getX()) || (point11.getX() > x && x > point12.getX()))
			System.out.println("int: " + point11.toString() + " " + point12.toString() + " " + point21.toString() + " " + point22.toString() + " x=" + x);
		return point11.getX() < point12.getX() ? (point11.getX() < x && x < point12.getX())
				: (point11.getX() > x && x > point12.getX());
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
	}*/

	public Scene getScene() {
		return scene;
	}

	public PointCalculator getPointCalculator() {
		return pointCalc;
	}

}
