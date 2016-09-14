package votegui.voteapp;

import java.io.*;

public class VoteModel{
	public boolean placeVote(String voter, String[] candidates, String[] weights, String system){
		File unsecured = new File("unsecured");
		String ballot_name = "unsecured/ballot";
		ballot_name += unsecured.listFiles().length;
		ballot_name += ".txt";

		try{
			PrintWriter writer = new PrintWriter(ballot_name, "UTF-8");
			writer.println(system);
			writer.println("I am");
			writer.println(voter);
			writer.println("And I vote for");
			for(int i = 0; i < 5; i++){
				if(candidates[i] != null && !candidates[i].isEmpty()){
					writer.println(candidates[i]);
				}
				if(weights[i] != null && !weights[i].isEmpty()){
					writer.println(weights[i]);
				}
			}

			writer.close();

		}catch(FileNotFoundException ex){
			return false;
		}catch(UnsupportedEncodingException ex){
			return false;
		}

		return true;
	}
}
