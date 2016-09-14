package jtp2.zad04.count;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import votegui.countapp.MVCCount;

public class Count{
	private static String results = "";
	public static Logger log = MVCCount.log; 
	private static VotingSystem voting_system;
	public static void setSystem(VotingSystem system){
		voting_system = system;
	}

	public static void addResult(String str){
		results += str;
	}

	public static String mainless(String system){
		results = "";
		try{
			if(system.equals("fptp")) setSystem(new FirstPastThePost());
			else if(system.equals("exhs")) setSystem(new ExhaustiveBallot());
			else if(system.equals("rang")) setSystem(new RangeVoting());
			else if(system.equals("aprv")) setSystem(new ApprovalVoting());
			else return "Error";
			voting_system.countVotes();
		}catch(ArrayIndexOutOfBoundsException e){
			log.error("Wrong number of arguments passed");
			return "Error";
		}
		log.info("Counting votes is over! Results put in results.txt");
		return results;
	}
		

};
