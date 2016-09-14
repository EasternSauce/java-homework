package jtp2.zad03.proc;

public class Configuration{
	private int param1;
	private double param2;
	private String[] param3;
	
	public int getParam1(){
		return param1;
	}
	public void setParam1(int param){
		param1 = param;
	}
	public double getParam2(){
		return param2;
	}
	public void setParam2(double param){
		param2 = param;
	}
	public String[] getParam3(){
		return param3;
	}
	public void setParam3(String[] param){
		param3 = new String[param.length];
		param3 = param;
	}

	public String toString(){
		String full_conf = "";
		full_conf += "param1: " + param1;
		full_conf += " param2: " + param2;
		full_conf += " param3: ";
		for(int i = 0; i < param3.length; i++) full_conf += param3[i] + " ";
		return full_conf;
	}
};
