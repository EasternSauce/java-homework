package jtp2.zad07.main;

public class Timer{
	private static long startTime = System.currentTimeMillis();
	public static long getTime(){
		return System.currentTimeMillis() - startTime;
	}
	public static void reset(){
		startTime = System.currentTimeMillis();
	}
}
