package jtp2.zad03.proc;

public class DefaultProcessor implements IProcessor{
	public Result process(Configuration c){
		int dataset[] = new int[c.getParam3().length];
		String sdataset[] = new String[c.getParam3().length];
		sdataset = c.getParam3();
		for(int i = 0; i < dataset.length; i++) dataset[i] = Integer.parseInt(sdataset[i]);

		Result my_result = new Result();
		if(c.getParam1() == 0){
			String[] sresult = new String[c.getParam3().length];
			for(int i = dataset.length- 1; i >= 1; i--){
				for(int j = 0; j < i; j++){
					if(dataset[j] > dataset[j + 1]){
						int temp = dataset[j+1];
						dataset[j+1] = dataset[j];
						dataset[j] = temp;
					}
				}
			}
			for(int i = 0; i < sresult.length; i++) sresult[i] = Integer.toString(dataset[i]);

			my_result.setResult(sresult);
		}
		else if(c.getParam1() == 1){
			String[] sresult = new String[1];

			for(int i = dataset.length- 1; i >= 1; i--){
				for(int j = 0; j < i; j++){
					if(dataset[j] > dataset[j + 1]){
						int temp = dataset[j+1];
						dataset[j+1] = dataset[j];
						dataset[j] = temp;
					}
				}
			}
			sresult[0] = Integer.toString(dataset[dataset.length-(int)c.getParam2()]);
			my_result.setResult(sresult);
		}
			

		else if(c.getParam1() == 2){
			String[] sresult = new String[1];

			int k = dataset[0];

			for(int i = 1; i < dataset.length; i++){
				int t = 0;
				int a = dataset[i];
				int b = k;
				while(b != 0){
					t = a % b;
					a = b;
					b = t;
				}
				k = a;
			}

			sresult[0] = Integer.toString(k);
			my_result.setResult(sresult);
		}


		return my_result;
	}
	public String help(){
		return "This processor sorts the data provided in an ascending order, selects the k-th biggest element in an array or finds the greatest common divisor for all of the elements. Set parameter 1 to:\n0 for bubble sort\n1 for select k-th biggest\n2 for find greatest common divisor\nThe sorted data must be placed in a string array to parameter 3. Parameter 2 is only needed for the k in k-th element algorithm.";
	}
};
