package application;

/**
 * this View Interface represents the View layer in the MVVM pattern
 * that responsible on the presentation and the presentation logic 
 * also this is the class that communicate with the user
 */

import java.util.List;
import entities.User;
import entities.UserRecord;

//View interface that defines a view to the user
public interface View {
	
	//displaying level function
	//public void displayLevel(Level l,Display d);
	
	//displaying message function(printing a String)
	public void displayMessage(String string);
	
	//starting the view(Thread)
	public void start();
	
	//stoping the view (Thread)
	public void stop();
	
	//set controls function
	public void setcontrols(String up ,String down ,String left ,String right);
	
	public void setPlayersList(List<User> p);
	
	public void setLevelScores(List<UserRecord> p);
	
	public void setUserScores(List<UserRecord> p);

}