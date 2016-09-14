package jtp2.zad07.main;

import java.util.Collections;
import java.util.ArrayList;
import java.lang.Thread;
import java.util.List;

public class Master{
	private List<Thread> threadPool = Collections.synchronizedList(new ArrayList<Thread>());
	private List<Runnable> taskPool = Collections.synchronizedList(new ArrayList<Runnable>());
	private volatile boolean running = true;
	private boolean toBeTerminated = false;
	private volatile int numOfTasksLeft = 0;
	public Master(int numOfThreads){
		for(int i = 0; i < numOfThreads; i++){
			Thread t = null;
			try{
				if(numOfThreads > 1000) throw new OutOfMemoryError();
				t = new SlaveThread();
			}catch(OutOfMemoryError e){
				Main.log.error("Way too many threads");
				System.exit(1);
			}
			threadPool.add(t);
			t.start();
		}
	}
	public void addTask(Runnable task){
		taskPool.add(task);
		synchronized(taskPool){
			taskPool.notifyAll();
		}
		numOfTasksLeft++;
	}
	
	public void terminate(){
		toBeTerminated = true;
	}

	public boolean deadSlaves(){
		for(Thread t : threadPool){
			if(t.getState() != Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}
	
	class SlaveThread extends Thread{
		public void run(){
			Runnable toDo = null;
			while(true){
				synchronized(taskPool){
					while(taskPool.size() == 0){
						if(toBeTerminated && numOfTasksLeft == 0){
							running = false;
							break;
						}
						try{
							taskPool.wait();
						}catch(InterruptedException e){
							running = false;
							Main.log.error("Interrupted waiting while running slave thread!");
						}
					}
					if(!running) break;
					toDo = taskPool.remove(0);
				}
				toDo.run();
				synchronized(taskPool){
					numOfTasksLeft--;
					taskPool.notifyAll();
				}
			}
		}
	};

};
