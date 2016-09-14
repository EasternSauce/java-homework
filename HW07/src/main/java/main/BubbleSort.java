package jtp2.zad07.main;

import java.util.Random;
public class BubbleSort implements Runnable{
	public void run(){
		long start = Timer.getTime();

		int tab[] = new int [10000];
		Random gen = new Random();
		for(int i = 0; i < 10000; i++){
			tab[i] = gen.nextInt();
		}
		bubbleSort(tab);


		TaskData.addData(Thread.currentThread().getName(), 5, start, Timer.getTime());
	}

	void bubbleSort(int tab[]){
		for(int i = tab.length- 1; i >= 1; i--){
			for(int j = 0; j < i; j++){
				if(tab[j] > tab[j + 1]){
					int temp = tab[j+1];
					tab[j+1] = tab[j];
					tab[j] = temp;
				}
			}
		}
	}
};
