package votegui.countapp;

public class CountController{
	private final CountView theView;
	private final CountModel theModel;
	private boolean canPressSecure = true;
	private boolean canPressCount = true;

	public CountController(CountView view, CountModel model){
		this.theView = view;
		this.theModel = model;
		theView.setProperties();
		theView.addRadioListener();

		theView.addSecureListener(new MyButtonListener(){
			public void workerJob(){
				theView.progressBarOn(true);
				theModel.secureVotes(theView.isXML(), theView.isSAX());	
			}

			public void done(){
				theView.progressBarOn(false);
				theView.showMessage("Securing votes is over");
				MVCCount.log.info("Votes secured");
			}
		});

		theView.addCountListener(new MyButtonListener(){
			public void workerJob(){
				theView.progressBarOn(true);
				String result = theModel.countVotes(theView.getSystem(), theView.isXML(), theView.isSAX());
				theView.showResult(result);
			}

			public void done(){
				theView.progressBarOn(false);
				theView.showMessage("Counting is over. Results shown.");
				MVCCount.log.info("Votes counted");
			}

		});
	}
}
