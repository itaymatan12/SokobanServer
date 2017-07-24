package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import adminModel.Task;
import controller.Command;
import controller.CommandAddLevel;
import controller.CommandAddUser;
import controller.CommandAddUserRecord;
import controller.CommandExit;
import controller.CommandGetChosenUserScores;
import controller.CommandGetScores;
import controller.CommandGetSolution;
import controller.CommandGetUsers;
import controller.CommandLevelScoresSortBySteps;
import controller.CommandLevelScoresSortByTime;
import controller.CommandUserScoresSortByLexicalOrder;
import controller.CommandUserScoresSortBySteps;
import controller.CommandUserScoresSortByTime;
import model.MyModel;


public class RegularClientHandler extends Observable implements ClientHandler
{

	private List<Object> paramsToClient;
	private BufferedReader reader;
	private ObjectOutputStream writer;
	private boolean stop = false;
	private MyModel m;
	private HashMap<String,Command> com_choise;
	private List<Task> Tasks;
	
	public RegularClientHandler ()
	{
		Tasks = new LinkedList<Task>();
		m = new MyModel();
		com_choise = new HashMap<String,Command>();
		//com_choise.put("display",new CommandDisplay());
		com_choise.put("exit",new CommandExit());
	    com_choise.put("AddLevelDB", new CommandAddLevel());
		com_choise.put("AddUser", new CommandAddUser());
		com_choise.put("AddUserScore", new CommandAddUserRecord());
		com_choise.put("GetAllUsersDB", new CommandGetUsers());
		com_choise.put("LevelScoreSortTime", new CommandLevelScoresSortByTime());
		com_choise.put("LevelScoreSortSteps", new CommandLevelScoresSortBySteps());
		com_choise.put("GetAllScoresDB", new CommandGetScores());
		com_choise.put("GetChosenPlayerScores", new CommandGetChosenUserScores());
		com_choise.put("UserScoreSortSteps", new CommandUserScoresSortBySteps());
		com_choise.put("UserScoreSortTime", new CommandUserScoresSortByTime());
		com_choise.put("UserScoreSortLexical",new CommandUserScoresSortByLexicalOrder());
		com_choise.put("GetSolution", new CommandGetSolution());
	}

	
		
		
		
		public void aSyncReadInputsAndSend(InputStream in, OutputStream out) 
		{ 
		    reader =new BufferedReader(new InputStreamReader(in));
		    try {
				writer = new ObjectOutputStream(out);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   String comndLine;
	
		   String commandLine = "";
			while(!stop) {
				//writer.write("Enter command: " );
				//writer.flush();
				
				try {
					//commandLine=null;
					//commandLine =reader.readLine();
					//if(commandLine==null){
						
					while((commandLine=reader.readLine())==null)
					{
					
					}
					

					String[] arr = commandLine.split(" ");
					String commandkey =arr[0];
					System.out.println(commandkey);
					LinkedList<Object> params = new LinkedList<Object>();
					
					
					for (int i =1;i<arr.length;++i)
					{
						params.add(arr[i]);
					}
					
					params.addFirst(m);
					params.addLast(this);
					
					System.out.println(commandkey);
					Command noecommand = com_choise.get(commandkey);
					noecommand.setParams(params);
					noecommand.execute();
					
					//the Task is starting
					Tasks.add(new Task(commandkey,"Running"));
					setChanged();
					notifyObservers(Tasks);
				//	Pasks.clear();

					writer.writeObject(this.paramsToClient);
					//the Task is finished 
					Tasks.add(new Task(commandkey,"Finished"));
					setChanged();
					notifyObservers(Tasks);
					
					//writer.writeObject(this.paramsToClient);
					
					if(params.get(0).equals("exit")) {
						stop=true;
						
						
					}
					
					if (!stop) {
						setChanged();
						notifyObservers(params);
					}
				} 
				
				catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  	
				stop = false;
		}
	
		}		
		
		
		public void setCommandParams(List<Object> p)
		{
			this.paramsToClient = p;
		}
		
		
		
}


