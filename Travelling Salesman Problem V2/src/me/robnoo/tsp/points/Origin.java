package me.robnoo.tsp.points;

public final class Origin implements Point {
	
	private static final Origin origin = new Origin();
	
	private Origin() {}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}
	
	public static Origin get() {
		return origin;
	}

	@Override
	public int getID() {
		return 0;
	}

}
