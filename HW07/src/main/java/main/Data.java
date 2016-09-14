package jtp2.zad07.main;

public class Data{
	public String thread;
	public int taskId;
	public long startTime;
	public long finishTime;
	public Data(String thread, int taskId, long startTime, long finishTime){
		this.thread = thread;
		this.taskId = taskId;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}
};

