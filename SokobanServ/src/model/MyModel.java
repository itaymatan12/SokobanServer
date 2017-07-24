package model;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.Exit;
import controller.RegularExit;
import server.MyServer;
import solver.SokobanSolver;
import entities.DbManager;
import entities.HLevel;
import entities.LevelSolution;
import entities.User;
import entities.UserRecord;

public class MyModel extends Observable implements Model {
	
	//data members
	private HashMap<String, Exit>exit;//hash map that mapping string that represent a exit type object to the appropriate exit object 
	private DbManager manager;
	private SokobanSolver s;
	
	//default constructor
	public MyModel()
	{
		s = new SokobanSolver();
		manager = new DbManager();
		exit = new HashMap<String, Exit>();
		this.exit.put("regularexit", new RegularExit());
	}

    @Override
	public void AddLevel(String LevelName)
	{	

		if(!manager.LevelisExist((LevelName)))
		{
			manager.addLevel(new HLevel(LevelName));
		}	
	}

    @Override
	public List<Object> AddUser(String UserId)
	{
    	int userid = Integer.parseInt(UserId);
		manager.addUser(new User(userid));
		LinkedList<Object> a =new LinkedList<Object>( manager.getAllUsers());
		a.addFirst("addUser");
		return a;
	}

	@Override
	public void AddUserRecord(String LevelName, String UserId, String Steps, String Time) 
	{
		int userid = Integer.parseInt(UserId);
		int step = Integer.parseInt(Steps);
		int time = Integer.parseInt(Time);
		
		manager.addRecord(new UserRecord(LevelName,userid,step,time));
	}

	@Override
	public List<Object> getUsers() 
	{   

				LinkedList<Object> a =new LinkedList<Object>(manager.getAllUsers());
				a.addFirst("getUsers");
				return a;
	}

	@Override
	public List<Object> sortLevelBySteps(String LevelName)
	{
		LinkedList<Object> a =new LinkedList<Object>( manager.sortLevelScoresBySteps(LevelName));
		a.addFirst("sortlevelbysteps");
		
		return a;
		
	}
	
	public void exitlevel(Exit ex,MyServer s)
	{  
		if(s!=null)
		{
			s.stopServer();;//stoping the server thread
		}
	
		
		ex.exit();//exiting the game
	}
		 
	 @Override
	public List<Object> sortLevelByTime(String LevelName) 
	{
		 LinkedList<Object> a =new LinkedList<Object>( manager.sortLevelScoresByTime(LevelName));
			a.addFirst("sortlevelbytime");
		 return a;
		
	}
	
	@Override
	public List<Object> getLevelScores(String LevelName) 
	{
		LinkedList<Object> a =new LinkedList<Object>( manager.sortLevelScoresBySteps(LevelName));
		a.addFirst("getlevelscores");
		return a;
	}

	@Override
	public List<Object> getUserScores(String userid) 
	{

		LinkedList<Object> a =new LinkedList<Object>( manager.getUserScores(userid));
		a.addFirst("getuserscores");
		
		return a;

	}

	@Override
	public List<Object> getUserScoresSortBySteps(String userid) 
	{

		LinkedList<Object> a =new LinkedList<Object>( manager.sorUserScoresBySteps(userid));
		a.addFirst("getuserscoresbysteps");
		
		return a;
	}

	@Override
	public List<Object> getUserScoresSortByTime(String userid) 
	{

		LinkedList<Object> a =new LinkedList<Object>( manager.sorUserScoresByTime(Integer.parseInt(userid)));
		a.addFirst("getuserscoresbytime");
		
		return a;
	}

	@Override
	public List<Object> getUserScoresSortByLexicalOrder(String userid) {

		
		 LinkedList<Object> a =new LinkedList<Object>(manager.sorUserScoresByLexicalOrder(Integer.parseInt(userid)));
		 
			a.addFirst("getuserscoresbylexicalorder");
		 
		 return a;
	}

	@Override
	public  List<Object> getSolution(String LevelName,String levelStr) 
	{
		int count=0;
		
		 String url ="http://localhost:8080/JerseyServer/webapi/employees";
		 Client client = ClientBuilder.newClient();
		 WebTarget webTarget = client.target(url);
		 Response response = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
		 if (response.getStatus() == 200) 
		 {
			List<LevelSolution> employees = response.readEntity(new GenericType<List<LevelSolution>>() {});
			
			String solve = new String();
			LinkedList<Object> a =new LinkedList<Object>();
			
			for (LevelSolution emp: employees) 
			{
				 if(emp.getLevelName() == LevelName)
				 {
					 a.add(emp);
					 count++;
					 return a;
				 }
		    }
			
			if(count==0)
			{

				String tri = levelStr.replace('7', ' ');
				

				solve= s.solve(tri);
				
				LevelSolution sol = new LevelSolution(LevelName,solve);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				Response response2 =invocationBuilder.post(Entity.json(sol));

				if (response2.getStatus() == 204) 
				{
					System.out.println("Solution added successfully");
				}
				
				
				else 
				{
					System.out.println(response.getHeaderString("errorResponse"));
				}
				

			}
				
			LinkedList<Object> b =new LinkedList<Object>();
			b.add(solve);
			return b;
		}
		 
		else
		{
			System.out.println(response.getHeaderString("errorResponse"));
			return null;
		}
	}
}