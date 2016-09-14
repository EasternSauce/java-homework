package jtp2.zad04.count;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Count{
	public static Logger log = Logger.getLogger(Count.class);
	private static VotingSystem voting_system;
	public static void setSystem(VotingSystem system){
		voting_system = system;
	}
	public static void main(String args[]){
		BasicConfigurator.configure();
		try{
			String system = args[0];
			if(system.equals("fptp")) setSystem(new FirstPastThePost());
			if(system.equals("exhs")) setSystem(new ExhaustiveBallot());
			if(system.equals("rang")) setSystem(new RangeVoting());
			if(system.equals("aprv")) setSystem(new ApprovalVoting());
			voting_system.countVotes();
		}catch(ArrayIndexOutOfBoundsException e){
			log.error("Wrong number of arguments passed");
			return;
		}
		log.info("Counting votes is over! Results put in results.txt");
	}
};
