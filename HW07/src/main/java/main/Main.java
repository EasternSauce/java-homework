package jtp2.zad07.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.io.UnsupportedEncodingException;

public class Main{
	public static Logger log = Logger.getLogger(Main.class);
	public static void main(String args[]){
		BasicConfigurator.configure();
		if(args.length != 2){
			log.error("Wrong number of arguments");
			return;
		}
		Timer.reset(); Master master = new Master(Integer.parseInt(args[0])); Random generator = new Random();

		for(int i = 0; i < Integer.parseInt(args[1]); i++){
			Runnable task = null;
			int rand = generator.nextInt(6);
			switch(rand){
				case 0:
					task = new IsPrime();
					break;
				case 1:
					task = new ApproxPi();
					break;
				case 2:
					task = new PerfectNumber();
					break;
				case 3:
					task = new AmicableNumbers();
					break;
				case 4:
					task = new BubbleSort();
					break;
				case 5:
					task = new InsertionSort();
					break;
			}
			master.addTask(task);
		}
		master.terminate();

		boolean waitToFinish = true;
		while(waitToFinish){
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){
				log.error("Interrupted waiting till computing results ends!");
				return;
			}
			if(master.deadSlaves()){
				waitToFinish = false;
			}
		}

		PrintWriter writer = null;
		try{
			writer = new PrintWriter("results.csv", "UTF-8");
		}catch(FileNotFoundException e){
			log.error("Results file not found!");
			return;
		}catch(UnsupportedEncodingException e){
			log.error("Enconding of results file unsupported!");
			return;
		}
		for(int i = 0; i < TaskData.data.size(); i++){
			writer.println(TaskData.data.elementAt(i).thread + "," + TaskData.data.elementAt(i).taskId + "," + TaskData.data.elementAt(i).startTime + "," + TaskData.data.elementAt(i).finishTime);
		}
		writer.close();
		log.info("Successfully saved results in results.csv");
	}
};
