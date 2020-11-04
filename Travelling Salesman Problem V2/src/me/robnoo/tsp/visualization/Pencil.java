package me.robnoo.tsp.visualization;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import me.robnoo.tsp.Variables;
import me.robnoo.tsp.points.CityPoint;
import me.robnoo.tsp.points.Point;
import me.robnoo.tsp.points.PointPair;

public class Pencil {
	
	private static final int SIZE = 5;
	private final double halfWidth;
	private final GraphicsContext gc;
	
	public Pencil(int screenWidth, GraphicsContext gc) {
		this.halfWidth = screenWidth/2;
		this.gc = gc;
	}
	
	public void drawPoint(Point point) {
		gc.fillOval(pointXCorner(point), pointYCorner(point), SIZE, SIZE);
	}
	
	public void drawLine(Point from, Point to) {
		gc.strokeLine(from.getX() + halfWidth, from.getY() + halfWidth, to.getX() + halfWidth, to.getY() + halfWidth);
	}
	
	public void drawCircle(int x, int y, int size) {
		gc.strokeOval(halfWidth + x - size/2, halfWidth + y - size/2, size, size);
	}
	
	public void drawCircle(double x, double y, double size) {
		gc.strokeOval((double) halfWidth + x - size/2, (double) halfWidth + y - size/2, size, size);
	}
	
	public void drawNumber(double x, double y, String number) {
		gc.fillText(number, halfWidth + x, halfWidth + y + 20);
	}
	
	private double pointXCorner(Point p) {
		return p.getX() + halfWidth - SIZE/2;
	}
	
	private double pointYCorner(Point p) {
		return p.getY() + halfWidth - SIZE/2;
	}
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	public void clearScreen() {
		getGraphicsContext().clearRect(0, 0, SimulScene.S, SimulScene.S);
	}
	
	public void drawCurrentPath(List<CityPoint> points, List<PointPair> pairs) {
		clearScreen();
		for(CityPoint point: points) {
			drawPoint(point);
			if(Variables.PRINT_ID) drawNumber(point.getX(), point.getY(), String.valueOf(point.getID()));
		}
		for(PointPair pair: pairs)
			drawLine(pair.getPoint1(), pair.getPoint2());
	}

}
