package jtp2.zad07.main;

public class IsPrime implements Runnable{
	public void run(){
		long start = Timer.getTime();

		isPrime(2038074743);

		TaskData.addData(Thread.currentThread().getName(), 1, start, Timer.getTime());
	}

	public static boolean isPrime(int n){
		for(int i = 2; i < n; i++){
			if(n%i == 0) return false; 
		}
		return true;
	}
};
