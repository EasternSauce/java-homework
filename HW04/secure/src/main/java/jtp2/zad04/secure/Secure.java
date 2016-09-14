package jtp2.zad04.secure;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Secure{
	public static Logger log = Logger.getLogger(Secure.class);

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

	public static void main(String args[]){
		BasicConfigurator.configure();
		String file_name;
		try{
			file_name = args[0];
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
