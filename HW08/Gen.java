import java.io.*;
import java.util.Random;

public class Gen{
	public static void main(String args[]){
	
		File unsecured = new File("unsecured");

		Random r = new Random();
		for(int i = 0; i < 1000; i++){
			String system = "fptp";
			String voter = "";
			String candidate = ""; for(int j = 0; j < 3; j++){
				voter += (char)(r.nextInt(26) + 'a');
				candidate += (char)(r.nextInt(26) + 'a');
			}

			try{
				String ballot_name = "unsecured/ballot";
				ballot_name += unsecured.listFiles().length;
				ballot_name += ".txt";

				PrintWriter writer = new PrintWriter(ballot_name, "UTF-8");

				writer.println(system);
				writer.println("I am");
				writer.println(voter);
				writer.println("And I vote for");
				writer.println(candidate);

				writer.close();

			}catch(FileNotFoundException ex){
			}catch(UnsupportedEncodingException ex){
			}
		}
	
	}

}
