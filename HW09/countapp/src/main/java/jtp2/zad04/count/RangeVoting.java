package jtp2.zad04.count;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class RangeVoting implements VotingSystem{

	public void countVotes(boolean xml, boolean sax){
		Map<String, Integer> votes = new TreeMap<String, Integer>();
		try{

			File ballots = new File("ballots");
			for(int i = 0; i < ballots.listFiles().length; i++){
				String file_name = "ballots/";
				file_name += ballots.listFiles()[i].getName();
				BufferedReader reader = new BufferedReader(new FileReader(file_name));
				if(!reader.readLine().equals("rang")) throw new IOException();
				reader.readLine();
				reader.readLine();
				reader.readLine();

				String line;
				while((line = reader.readLine()) != null){
					String candidate = line;
					int score = Integer.parseInt(reader.readLine());
					if(votes.containsKey(candidate)){
						votes.put(candidate, votes.get(candidate)+score);
					}else{
						votes.put(candidate, score);
					}
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

			writer.println("Most score candidates (score = " + max + "):");
			Count.addResult("Most score candidates (score = " + max + "):\n");
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
