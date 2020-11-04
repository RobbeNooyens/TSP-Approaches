package me.robnoo.tsp;

public interface Debug {
	
	default void debug(String m) {
		if(Variables.DEBUG)
			System.out.println(m);
	}

}
