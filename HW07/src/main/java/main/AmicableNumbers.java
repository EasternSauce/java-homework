package jtp2.zad07.main;

public class AmicableNumbers implements Runnable{
	public void run(){
		long start = Timer.getTime();

		areAmicable(224324234,730230230);

		TaskData.addData(Thread.currentThread().getName(), 4, start, Timer.getTime());
	}

	public static boolean areAmicable(int a, int b){
		int sum1 = 0, sum2 = 0;
		for(int i = 1; i <= a; i++)
			if(a % i == 0) sum1 += i;
	
		for(int i = 1; i <= b; i++)
			if(b % i == 0) sum2 += i;

		if(sum1 == sum2) return true;
		else return false;

	}
};
