package jtp2.zad04.count;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import org.w3c.dom.*;


class Vote{
	String system;
	String voter;
	ArrayList<String> candidates = new ArrayList<String>();
	ArrayList<Integer> weights = new ArrayList<Integer>();
}

class SAXHandler extends DefaultHandler{
	String content = null;
	String weight = null;
	Vote vote = new Vote();


	public void startElement(String uri, String localName, String qName, Attributes attributes){
		if(qName.equals("candidate")){
			weight = attributes.getValue("weight");
			vote.weights.add(Integer.parseInt(weight));
			Count.log.info("weight " + Integer.parseInt(weight));
		}
	}

	public void endElement(String uri, String localName, String qName){
		if(qName.equals("candidate")){
			Count.log.info("candidate " + content);
			vote.candidates.add(content);
		}
		else if(qName.equals("system")){
			Count.log.info("system " + content);
			vote.system = content;
		}
		else if(qName.equals("voter")){
			Count.log.info("voter " + content);
			vote.voter = content;
		}

	}

	public void characters(char[] ch, int start, int length){
		content = String.copyValueOf(ch, start, length).trim();
	}
}

public class FirstPastThePost implements VotingSystem{

	public void countVotes(boolean xml, boolean sax){
		Map<String, Integer> votes = new TreeMap<String, Integer>();
		File ballots = new File("ballots");
		for(int i = 0; i < ballots.listFiles().length; i++){
			String file_name = "ballots/";
			file_name += ballots.listFiles()[i].getName();


			if(xml){
				if(sax){
					SAXParser parser = null;
					SAXParserFactory parserFactory = SAXParserFactory.newInstance();
					try{
						parser = parserFactory.newSAXParser();
					}catch(ParserConfigurationException e){
						Count.log.error("Sax parse error");
					}catch(SAXException e){
						Count.log.error("Sax error");
					}
					SAXHandler handler = new SAXHandler();
					try{
						parser.parse(file_name, handler);
					}catch(SAXException e){
						Count.log.error("Sax error");
					}catch(IOException e){
						Count.log.error("IO Exception with Sax");
					}

					try{
						if(!(handler.vote.system.equals("fptp"))){
							throw new IOException();
						}
					}catch(IOException e){
						Count.log.error("Ballot file is invalid");
					}

					String candidate = (handler.vote.candidates.get(0));
					if(votes.containsKey(candidate)){
						votes.put(candidate, votes.get(candidate)+1);
					}else{
						votes.put(candidate, 1);
					}
				}
				else{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					try{
						DocumentBuilder builder = factory.newDocumentBuilder();
						Document doc = builder.parse(file_name);

						NodeList sysList = doc.getElementsByTagName("system");
						Element sys = (Element)sysList.item(0);
						String system = sys.getTextContent();
						if(!system.equals("fptp")){
							throw new IOException();
						}

						NodeList candList = doc.getElementsByTagName("candidate");
						Element cand = (Element)candList.item(0);
						String candidate = cand.getTextContent();

						if(votes.containsKey(candidate)){
							votes.put(candidate, votes.get(candidate)+1);
						}else{
							votes.put(candidate, 1);
						}

					}catch(Exception e){
						Count.log.error("Problem with DOM");						
						e.printStackTrace();

					}
				}
			}
			else{
				BufferedReader  reader = null;
				try{
					reader = new BufferedReader(new FileReader(file_name));
				}catch(FileNotFoundException e){
					Count.log.error("Ballot file not found");
					return;
				}

				String candidate = null;
				try{
					if(!reader.readLine().equals("fptp")) throw new IOException();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					candidate = reader.readLine();
					if(votes.containsKey(candidate)){
						votes.put(candidate, votes.get(candidate)+1);
					}else{
						votes.put(candidate, 1);
					}
					reader.close();
				}catch(IOException e){
					Count.log.error("Problem with ballot file");
					return;
				}
			}




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
