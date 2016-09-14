package votegui.voteapp;

public class MVCVote{
	public static void main(String args[]){
		VoteView theView = new VoteView();
		VoteModel theModel = new VoteModel();
		VoteController theController = new VoteController(theView, theModel);
		theView.setVisible(true);
	}

}
