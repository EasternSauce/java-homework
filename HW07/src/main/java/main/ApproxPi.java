package jtp2.zad07.main;

public class ApproxPi implements Runnable{
	public void run(){
		long start = Timer.getTime();
		
		getPi(1000000000.0);

		TaskData.addData(Thread.currentThread().getName(), 2, start, Timer.getTime());
	}

	public static double getPi(double n){
		double pi = 1.0;
		int s = 1;
		for(double j = 3.0; j < n; j=j+2){
			if(s%2 == 0)
				pi = pi + (1/j);
			else
				pi = pi - (1/j);
			s++;
		}
		return 4*pi;
	}
};
