package me.robnoo02.travelingsalesmanproblem.utils;

import java.util.Arrays;
import java.util.List;

public class DebugUtil {

	private static List<DebugState> debugState = Arrays.asList(DebugState.DISTANCE_LOW_HIGH, DebugState.CONNECT, DebugState.VALID, DebugState.TIME, DebugState.CLOSEST_SEGMENT);

	public static enum DebugState {
		NONE, TIME, DISTANCE_LOW_HIGH, MAX_DISTANCE, CIRCLE, INTERSECT, DISTANCE_TO_ALL, CONNECT, VALID, ALL, CLOSEST_SEGMENT;
		public boolean isEnabled() {
			return (debugState.contains(this) || debugState.contains(DebugState.ALL));
		}
		public void debug(String text) {
			if(isEnabled())
				System.out.println(text);
		}
	}

}
