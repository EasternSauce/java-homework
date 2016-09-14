package jtp2.zad04.count;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FirstPastThePost implements VotingSystem{

	public void countVotes(){
		Map<String, Integer> votes = new TreeMap<String, Integer>();
		try{
			File ballots = new File("ballots");
			for(int i = 0; i < ballots.listFiles().length; i++){
				String file_name = "ballots/";
				file_name += ballots.listFiles()[i].getName();
				BufferedReader reader = new BufferedReader(new FileReader(file_name));
				if(!reader.readLine().equals("fptp")) throw new IOException();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				String candidate = reader.readLine();
				if(votes.containsKey(candidate)){
					votes.put(candidate, votes.get(candidate)+1);
				}else{
					votes.put(candidate, 1);
				}
				reader.close();
			}

		}catch(FileNotFoundException e){
			Count.log.error("Ballot file not found");
			return;
		}catch(UnsupportedEncodingException e){
			Count.log.error("Encoding of ballot file is unsupported");
			return;
		}catch(IOException e){
			Count.log.error("Problem with ballot file");
			return;
		}

		int max = 0;
		for(Map.Entry<String, Integer> entry : votes.entrySet()){
			if(entry.getValue() > max) max = entry.getValue();
		}
		try{
			PrintWriter writer = new PrintWriter("result.txt", "UTF-8");

			writer.println("Most number of votes:");
			Count.addResult("Most number of votes:\n");
			int number_of_winners = 0;
			for(Map.Entry<String, Integer> entry : votes.entrySet()){
				if(entry.getValue() == max){
					writer.println(entry.getKey());
					Count.addResult(entry.getKey() + "\n");
					number_of_winners++;
				}
			}
			if(number_of_winners != 1){
				writer.println("No decisive winner!");
				Count.addResult("No decisive winner!\n");
			}


			writer.close();

		}catch(FileNotFoundException e){
			Count.log.error("Results file not found");
			return;
		}catch(UnsupportedEncodingException e){
			Count.log.error("Encoding of results file is unsupported");
			return;
		}
	}
};
