package me.robnoo02.travelingsalesmanproblem.scenes;

import javafx.scene.canvas.GraphicsContext;
import me.robnoo02.travelingsalesmanproblem.points.Point;
import me.robnoo02.travelingsalesmanproblem.points.Segment;

public class Pencil {
	
	private final GraphicsContext gc;
	private final double oX, oY, r, d;
	
	public Pencil(GraphicsContext gc, Point center, int r) {
		this.gc = gc;
		this.oX = center.getX();
		this.oY = center.getY();
		this.r = r;
		this.d = r*2;
	}
	
	public void drawPoint(Point point) {
		gc.fillOval(point.getX() + oX - r, point.getY() + oY - r , d, d);
	}
	
	public void drawLine(Segment segment) {
		Point from = segment.getSegPoint1();
		Point to = segment.getSegPoint2();
		gc.strokeLine(from.getX() + oX, from.getY() + oY, to.getX() + oX, to.getY() + oY);
	}
	
	/*public void drawCircle(int x, int y, int size) {
		gc.strokeOval(halfSize + x - size/2, halfSize + y - size/2, size, size);
	}
	
	public void drawCircle(double x, double y, double size) {
		gc.strokeOval((double) halfSize + x - size/2, (double) halfSize + y - size/2, size, size);
	}*/
	
	public void drawNumber(double x, double y, String number) {
		gc.fillText(number, oX + x, oY + y + 20);
	}
	
	public void drawNumber(Point point) {
		gc.fillText(String.valueOf(point.getId()), oX + point.getX(), oY + point.getY() + 20);
	}
	
	public void clear() {
		gc.clearRect(0, 0, 2*oX, 2*oY);
	}

}
