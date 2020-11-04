package me.robnoo02.travellingsalesman.scene;

import javafx.scene.canvas.GraphicsContext;
import me.robnoo02.travellingsalesman.points.Point;

public class SceneShapesDrawer {
	
	private static final int POINT_SIZE = 5;
	private final int halfSize;
	private final GraphicsContext gc;
	
	public SceneShapesDrawer(int sceneSize, GraphicsContext gc) {
		this.halfSize = sceneSize/2;
		this.gc = gc;
	}

	public void drawPoint(Point point) {
		gc.fillOval(point.getX() + halfSize - POINT_SIZE/2, point.getY() + halfSize - POINT_SIZE/2 , POINT_SIZE, POINT_SIZE);
	}
	
	public void drawLine(Point from, Point to) {
		gc.strokeLine(from.getX() + halfSize, from.getY() + halfSize, to.getX() + halfSize, to.getY() + halfSize);
	}
	
	public void drawCircle(int x, int y, int size) {
		gc.strokeOval(halfSize + x - size/2, halfSize + y - size/2, size, size);
	}
	
	public void drawCircle(double x, double y, double size) {
		gc.strokeOval((double) halfSize + x - size/2, (double) halfSize + y - size/2, size, size);
	}
	
}
