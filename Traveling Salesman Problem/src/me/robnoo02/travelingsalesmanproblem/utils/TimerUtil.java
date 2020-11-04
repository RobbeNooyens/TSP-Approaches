package me.robnoo02.travelingsalesmanproblem.utils;

import java.util.Date;

public class TimerUtil {

	private final Date start;
	private Date paused;
	private long pauseTime;
	
	public TimerUtil() {
		this.start = new Date();
	}
	
	public void pause() {
		Date current = new Date();
		if(paused != null)
			pauseTime += current.getTime() - paused.getTime();
		this.paused = current;
	}
	
	public void contin() {
		if(paused != null)
			pauseTime += (new Date()).getTime() - paused.getTime();
		paused = null;
	}
	
	public long read() {
		return (new Date()).getTime() - start.getTime() - pauseTime;
	}
	
}
