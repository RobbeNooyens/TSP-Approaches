package me.robnoo.tsp;

import java.util.Date;

public class Timer {
	
	private static Date date;
	
	public static void start() {
		date = new Date();
	}
	
	public static double stop() {
		return (new Date()).getTime() - date.getTime();
	}

}
