package votegui.countapp;

import jtp2.zad04.secure.Secure;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class MVCCount{
	public static Logger log = Logger.getLogger(MVCCount.class);
	public static void main(String args[]){
		BasicConfigurator.configure();
		CountView theView = new CountView();
		CountModel theModel = new CountModel();
		CountController theController = new CountController(theView, theModel);
		theView.setVisible(true);
	}

}
