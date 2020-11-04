package me.robnoo.tsp.points;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import me.robnoo.tsp.sorting.ClosestPoint;
import me.robnoo.tsp.visualization.SimulScene;

public class PointManager {

	public static LinkedList<CityPoint> generate(int amount) {
		LinkedList<CityPoint> result = new LinkedList<>();
		Random random = new Random();
		int s = SimulScene.S;
		while (result.size() < amount) {
			double d = 2*s;
			CityPoint p = null;
			//while(d > s/2) {
				p = new CityPoint(random.nextInt(s) - (s / 2), random.nextInt(s) - (s / 2));
				//p = new CityPoint(random.nextInt(s/2), -1*random.nextInt(s / 2));
				d = p.dOrigin();
			//}
			result.add(p);
		}
		for(CityPoint p: result)
			p.setTotalDistances(result);
		return result;
	}
	
	public static LinkedList<PointPair> firstConnections(LinkedList<CityPoint> points){
		LinkedList<PointPair> pairs = new LinkedList<>();
		
		// Method 1: 3 closest to origin
		//pairs.add(new PointPair(points.get(0), points.get(1)));
		//pairs.add(new PointPair(points.get(1), points.get(2)));
		//pairs.add(new PointPair(points.get(2), points.get(0)));
		
		// Method 2: 2 Closest to origin + closest to segment
		pairs.add(new PointPair(points.get(0), points.get(1)));
		Collections.sort(points, new ClosestPoint(pairs.getFirst()));
		pairs.add(new PointPair(points.get(0), points.getLast()));
		pairs.add(new PointPair(points.get(1), points.getLast()));
		return pairs;
	}
	
	// https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/GLPK_solution_of_a_travelling_salesman_problem.svg/1200px-GLPK_solution_of_a_travelling_salesman_problem.svg.png
	public static LinkedList<CityPoint> image1(){
		LinkedList<CityPoint> result = new LinkedList<>();
		result.add(get(273,4));
		result.add(get(314, 103));
		result.add(get(392, 182));
		result.add(get(400, 240));
		result.add(get(475, 130));
		result.add(get(494, 208));
		result.add(get(494, 300));
		result.add(get(464, 357));
		result.add(get(445, 492));
		result.add(get(365, 419));
		result.add(get(336, 434));
		result.add(get(320, 380));
		result.add(get(226, 361));
		result.add(get(260, 450));
		result.add(get(244, 486));
		result.add(get(106, 490));
		result.add(get(40, 440));
		result.add(get(90, 450));
		result.add(get(126, 434));
		result.add(get(102, 404));
		result.add(get(95, 300));
		result.add(get(12, 255));
		result.add(get(22, 100));
		result.add(get(15, 70));
		result.add(get(70, 110));
		result.add(get(140, 50));
		result.add(get(170, 110));
		result.add(get(203, 218));
		result.add(get(204, 242));
		result.add(get(215, 277));
		result.add(get(242, 263));
		result.add(get(265, 208));
		result.add(get(267, 188));
		result.add(get(266, 155));
		result.add(get(225, 130));
		return result;
	}
	
	private static CityPoint get(int x, int y) {
		return new CityPoint(-(SimulScene.S / 2) + x, -((SimulScene.S / 2) - y));
	}

}
