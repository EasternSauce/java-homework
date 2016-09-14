package jtp2.zad04.secure;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import votegui.countapp.MVCCount;
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
			Secure.log.info("weight " + Integer.parseInt(weight));
		}
	}

	public void endElement(String uri, String localName, String qName){
		if(qName.equals("candidate")){
			Secure.log.info("candidate " + content);
			vote.candidates.add(content);
		}
		else if(qName.equals("system")){
			Secure.log.info("system " + content);
			vote.system = content;
		}
		else if(qName.equals("voter")){
			Secure.log.info("voter " + content);
			vote.voter = content;
		}
		
	}

	public void characters(char[] ch, int start, int length){
		content = String.copyValueOf(ch, start, length).trim();
	}
}

public class Secure{
	public static Logger log = MVCCount.log;

	public static String getChecksum(String file_name){
		StringBuffer sb = new StringBuffer("");
		try{
			MessageDigest msg_digest = MessageDigest.getInstance("MD5");

			FileInputStream file_input = new FileInputStream(file_name);

			byte[] data = new byte[1024];
			int bytes_read = 0;
			while((bytes_read = file_input.read(data)) != -1){
				msg_digest.update(data, 0, bytes_read);
			}

			byte[] digest_bytes = msg_digest.digest();
			for(int i = 0; i < digest_bytes.length; i++){
				sb.append(Integer.toString((digest_bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			file_input.close();

		}catch(NoSuchAlgorithmException e){
			log.error("Wrong checksum algorithm");			
		}catch(FileNotFoundException e){
			log.error("File to make a checksum of is invalid");			
		}catch(IOException e){
			log.error("Problem with getting checksum from file");
		}

		return sb.toString();
	}

	public static void mainless(String file_name, boolean xml, boolean sax){
		if(xml){
			if(sax){
				SAXParser parser = null;
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				try{
					parser = parserFactory.newSAXParser();
				}catch(ParserConfigurationException e){
					Secure.log.error("ERROR");
				}catch(SAXException e){
					Secure.log.error("ERROR");
				}
				SAXHandler handler = new SAXHandler();
				try{
					parser.parse(file_name, handler);
				}catch(SAXException e){
					Secure.log.error("ERROR");
				}catch(IOException e){
					Secure.log.error("ERROR");
				}

				try{
					if(!(handler.vote.system.equals("fptp") || handler.vote.system.equals("exhs") || handler.vote.system.equals("rang") || handler.vote.system.equals("aprv"))){
						throw new IOException();
					}
					for(int i = 0; i < handler.vote.weights.size(); i++){
						int weight = handler.vote.weights.get(i);
						if(weight < 0 || weight > 99) throw new IOException();
					}
				}catch(IOException e){
					log.error("Ballot file is invalid");
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
					if(!(system.equals("fptp") || system.equals("exhs") || system.equals("rang") || system.equals("aprv"))){
						throw new IOException();
					}
					NodeList candList = doc.getElementsByTagName("candidate");
					for(int i = 0; i < candList.getLength(); i++){
						Element el = (Element)candList;
						String attr = el.getAttribute("weight");
						int weight = Integer.parseInt(attr);
						if(weight < 0 || weight > 99) throw new IOException();
					}
				}catch(Exception e){

				}
			}

		}
		else{
			try{
				BufferedReader reader = new BufferedReader(new FileReader(file_name));
				String sys = reader.readLine();
				if(!(sys.equals("fptp") || sys.equals("exhs") || sys.equals("rang") || sys.equals("aprv"))){
					throw new IOException();
				}
				if(!reader.readLine().equals("I am")) throw new IOException();
				reader.readLine();
				if(!reader.readLine().equals("And I vote for")) throw new IOException();
				if(sys.equals("rang")){
					String line1, line2;
					while((line1 = reader.readLine()) != null && (line2 = reader.readLine()) != null){
						int score = Integer.parseInt(line2);
						if(score < 0 || score > 99) throw new IOException();
					}
				}

				reader.close();
			}catch(FileNotFoundException e){
				log.error("Ballot file not found");
				return;
			}catch(IOException e){
				log.error("Ballot file is invalid");
				return;
			}catch(ArrayIndexOutOfBoundsException e){
				log.error("Problem with the amount of arguments passed");
				return;
			}
		
		}


		
		String checksum = getChecksum(file_name);



		boolean unique = true;
		try{
			BufferedReader reader = new BufferedReader(new FileReader("control.txt"));

			String line;
			while((line = reader.readLine()) != null){
				if(line.equals(checksum)){
					unique = false;
					break;
				}
			}
			reader.close();
		}catch(FileNotFoundException e){
			log.error("Did not find control file to read from");
			return;
		}catch(IOException e){
			log.error("A problem occured with control file while reading from it");
			return;
		}

		if(unique == true){
			File ballots = new File("ballots");
			String ballot_name = "ballots/ballot";
			ballot_name += ballots.listFiles().length;
			ballot_name += ".txt";
			log.info("Processing a file: " + ballot_name);

			try{
				PrintWriter writer = new PrintWriter(ballot_name, "UTF-8");
				BufferedReader reader = new BufferedReader(new FileReader(file_name));
				String line;
				while((line = reader.readLine()) != null){
					writer.println(line);
				}
				writer.close();
				reader.close();

			}catch(FileNotFoundException e){
				log.error("Did not find ballot file");
				return;
			}catch(UnsupportedEncodingException e){
				log.error("Encoding of ballot file unsupported");
				return;
			}catch(IOException e){
				log.error("A problem with ballot file");
				return;
			}
			log.info("Ballot correctly filled!");


			try{
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("control.txt", true)));
				log.info("File checksum: " + checksum);
				writer.println(checksum);
				writer.close();
			}catch(FileNotFoundException e){
				log.error("Control file not found");
				return;
			}catch(UnsupportedEncodingException e){
				log.error("Encoding of control file unsupported");
				return;
			}catch(IOException e){
				log.error("A problem with control file while appending checksum");
				return;
			}
		}
		else{
			log.error("A duplicate vote identified! Ballot is invalid");
		}

	}
};
