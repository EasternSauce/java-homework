package jtp2.zad07.main;

import java.util.Vector;


public class TaskData{
	private static TaskData instance = null;
	public  static Vector<Data> data = new Vector<Data>();

	public static synchronized TaskData getInstance(){
		if(instance == null){
			instance = new TaskData();
		}
		return instance;
	}

	public static synchronized void addData(String thread, int taskId, long startTime, long finishTime){
		data.add(new Data(thread, taskId, startTime, finishTime));
	}
};
