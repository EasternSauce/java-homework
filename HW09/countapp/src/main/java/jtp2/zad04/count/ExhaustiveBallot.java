package jtp2.zad04.count;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ExhaustiveBallot implements VotingSystem{

	public void countVotes(boolean xml, boolean sax){
		Map<String, Integer> votes = new TreeMap<String, Integer>();
		try{

			File ballots = new File("ballots");
			for(int i = 0; i < ballots.listFiles().length; i++){
				String file_name = "ballots/";
				file_name += ballots.listFiles()[i].getName();
				BufferedReader reader = new BufferedReader(new FileReader(file_name));
				if(!reader.readLine().equals("exhs")) throw new IOException();
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

		int count = 0;
		int max = 0;
		String max_candidate = new String();
		for(Map.Entry<String, Integer> entry : votes.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				max_candidate = entry.getKey();
			}
			count += entry.getValue();
		}
		try{
			PrintWriter writer = new PrintWriter("result.txt", "UTF-8");

			if(max > Math.ceil(count/2)){
				writer.println("Majority voted for:");
				Count.addResult("Majority voted for:\n");
				writer.println(max_candidate);
				Count.addResult(max_candidate + "\n");
			}
			else{
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
