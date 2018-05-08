package br.ufpe.cin.siscom.dfsa.util;

import java.util.Arrays;

public class Logger {
	
	/**
	 * Logs a single object (same as calling method from {@link System}
	 * @author jvos
	 * */
	public static void log(Object object) {
		System.out.println(object);
	}
	
	/**
	 * Logs an undetermined number of objects as parameter
	 * @author jvos
	 * */
	public static void log(Object ... objects) {
		int i = 0;
		for (Object o : objects) {
			System.out.print(o);
			if (i < objects.length-1) {
				System.out.print(", ");
			}
			i++;
		}
		System.out.println();
	}
	
	/**
	 * Logs an array, casting it to a List
	 * @author jvos
	 * */
	public static void logArray(int[] array) {
		System.out.println(Arrays.toString(array));
	}
	
	/**
	 * Logs a determined milissecond timestamp to a formated hour, minute, second, milissecond String
	 * @author jvos
	 * */
	public static void logFinishTime(String log, long endTime) {
		
		System.out.print(log+" finished running in: ");
		long hours = ((endTime / (1000 * 60 * 60)) % 24);
		long minutes = ((endTime / (1000 * 60)) % 60);
		long seconds = ((endTime / 1000) % 60);
		
		if (hours > 0) {			
			System.out.print(hours+" h ");
		}
		if (minutes > 0) {			
			System.out.print(minutes+" min ");
		}
		if (seconds > 0) {			
			System.out.print(seconds+" s ");
		}
		else {
			System.out.print(endTime+" ms");
		}
		System.out.println();
	}

}
