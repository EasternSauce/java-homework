package jtp2.zad07.main;

import java.util.Random;
public class InsertionSort implements Runnable{
	public void run(){
		long start = Timer.getTime();

		int tab[] = new int [10000];
		Random gen = new Random();
		for(int i = 0; i < 10000; i++){
			tab[i] = gen.nextInt();
		}
		insertionSort(tab);

		TaskData.addData(Thread.currentThread().getName(), 6, start, Timer.getTime());
	}

	void insertionSort(int tab[]){
		for(int i = 1; i < tab.length; i++){
			int temp = tab[i];
			int j;
			for(j = i - 1; j >= 0 && temp < tab[j]; j--)
				tab[j + 1] = tab[j];
			tab[j + 1] = temp;
		}
	}
};
