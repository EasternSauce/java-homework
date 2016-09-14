package jtp2.zad07.main;

public class PerfectNumber implements Runnable{
	public void run(){
		long start = Timer.getTime();

		isPerfectNumber(954825863);

		TaskData.addData(Thread.currentThread().getName(), 3, start, Timer.getTime());
	}

	public static boolean isPerfectNumber(int n){
		int temp = 0;
		for(int i=1;i<=n/2;i++){
			if(n%i == 0){
				temp += i;
			}
		}
		if(temp == n){
			return true;
		}else{
			return false;
		}
	}
};
