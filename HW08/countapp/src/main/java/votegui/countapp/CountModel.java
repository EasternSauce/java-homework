package votegui.countapp;

import java.io.*;
import jtp2.zad04.secure.Secure;
import jtp2.zad04.count.Count;

public class CountModel{
	public void secureVotes(){
		File ballots = new File("unsecured");

		for(int i = 0; i < ballots.listFiles().length; i++){
			Secure.mainless("unsecured/" + ballots.listFiles()[i].getName());
		}
	}

	public String countVotes(String system){
		return Count.mainless(system);
	}
}
