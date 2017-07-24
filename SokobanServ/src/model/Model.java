package model;

import java.util.List;
import controller.Exit;
import server.MyServer;

/**
 * Model Interface that represent the Model layer in the MVVM pattern 
 * that responsible on the logic that represents all the commands that we can from the server
 * in order to get the proper information to the clients request and returns the answer to the client  
 */

public interface Model {
	
	public void exitlevel(Exit ex,MyServer s);
	
	//adding a level to our DB
	public void AddLevel(String LevelName);
	
	//adding a user to our DB
	public List<Object> AddUser(String UserId);
	
	//adding a user record to our DB
	public void AddUserRecord(String LevelName,String UserId,String Steps,String Time);
	
	public List<Object> getUsers();
	
	public List<Object> sortLevelBySteps(String LevelName);

	public List<Object> sortLevelByTime(String LevelName);
	
	public List<Object> getLevelScores(String LevelName);
	
	
	public List<Object> getUserScores(String userid);
	
	public List<Object> getUserScoresSortBySteps(String userid);
	
	public List<Object> getUserScoresSortByTime(String userid);
	
	public List<Object> getUserScoresSortByLexicalOrder(String userid);
	
	public  List<Object> getSolution(String LevelName,String levelStr);
	
} 