package me.robnoo.tsp.points;

public final class PointPair {

	private final CityPoint p1, p2;
	private final double dist;
	private static int ID_COUNTER = 0;
	private final int id;

	public PointPair(CityPoint p1, CityPoint p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.id = ID_COUNTER++;
		this.dist = p1.dist(p2);
		p1.connect(this);
		p2.connect(this);
	}

	public CityPoint getPoint1() {
		return p1;
	}

	public CityPoint getPoint2() {
		return p2;
	}

	public double dist() {
		return dist;
	}
	
	public boolean contains(Point p) {
		return (p1.equals(p) || p2.equals(p));
	}
	
	public CityPoint getDoublePoint(PointPair other) {
		if(other.contains(p1)) return p1;
		if(other.contains(p2)) return p2;
		return null;
	}
	
	public CityPoint getOther(Point p) {
		if(p1.equals(p)) return p2;
		if(p2.equals(p)) return p1;
		return null;
	}
	
	public void disconnect() {
		p1.disconnect(this);
		p2.disconnect(this);
	}

	public double distance(Point p) {
		float x1 = p1.getX();
		float y1 = p1.getY();
		float x2 = p2.getX();
		float y2 = p2.getY();
		float x3 = p.getX();
		float y3 = p.getY();
		float px = x2 - x1;
		float py = y2 - y1;
		float temp = (px * px) + (py * py);
		float u = ((x3 - x1) * px + (y3 - y1) * py) / (temp);
		if (u > 1) {
			u = 1;
		} else if (u < 0) {
			u = 0;
		}
		float x = x1 + u * px;
		float y = y1 + u * py;

		float dx = x - x3;
		float dy = y - y3;
		double dist = Math.sqrt(dx * dx + dy * dy);
		return dist;

	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PointPair)) return false;
		PointPair pp = (PointPair) o;
		return pp.id == id;
		//Point pp1 = pp.getPoint1();
		//Point pp2 = pp.getPoint2();
		//return ((pp1.equals(p1) && pp2.equals(p2)) || ((pp1.equals(p2) || pp2.equals(p1))));
	}

	@Override
	public int hashCode() {
		int r = 31 * id;
		return r;
	}

}
