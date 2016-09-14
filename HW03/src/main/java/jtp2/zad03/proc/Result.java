package jtp2.zad03.proc;

public class Result{
	private String[] result;

	public String[] getResult(){
		return result;
	}
	public void setResult(String[] new_res){
		result = new String[new_res.length];
		result = new_res;
	}

	public String toString(){
		String full_results = "";
		for(int i = 0; i < result.length; i++) full_results += result[i] + " ";
		return full_results;
	}
};
