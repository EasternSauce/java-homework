package votegui.voteapp;

import java.awt.event.*;

public class VoteController{
	private final VoteView theView;
	private final VoteModel theModel;

	public VoteController(VoteView view, VoteModel model){
		this.theView = view;
		this.theModel = model;


		theView.setProperties();
		theView.addRadioListener();

		theView.addVoteListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(theModel.placeVote(theView.getName(), theView.getCandidates(), theView.getWeights(), theView.getVotingSystem(), theView.isXML())){
					theView.showMessage("Successfully voted");
					theView.clearFields();
				}
				else{
					theView.showMessage("Voting unsuccessful! Please try again");
				}
			}
		});
	}
}
