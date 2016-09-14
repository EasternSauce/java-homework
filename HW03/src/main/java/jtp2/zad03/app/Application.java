package jtp2.zad03.app;
import jtp2.zad03.proc.*;

public class Application{
	public static void main(String[] args){
		IProcessor my_processor = new DefaultProcessor();
		System.out.println(my_processor.help());

		int dataset[] = {24, 66, 12, 36, 48, 24, 30, 54, 90, 60};
		
		Configuration conf[] = new Configuration[3];
		for(int i = 0; i < 3; i++) conf[i] = new Configuration();

		String sdataset[] = new String[dataset.length];
		for(int i = 0; i < dataset.length; i++) sdataset[i] = Integer.toString(dataset[i]);
		
		conf[0].setParam1(0);
		conf[0].setParam3(sdataset);
		conf[1].setParam1(1);
		conf[1].setParam2(3);
		conf[1].setParam3(sdataset);
		conf[2].setParam1(2);
		conf[2].setParam3(sdataset);

		for(int i = 0; i < conf.length; i++){
			System.out.println("Config number " + i);
			System.out.println(conf[i].toString());
		}
		for(int i = 0; i < conf.length; i++){
			System.out.println("Result number " + i);
			Result result = my_processor.process(conf[i]);
			System.out.println(result.toString());
		}
	}
}
