package adminModel;

/**
 *class that represents a task that running in the server 
 *
 */
public class Task {
	
	private String state; 
	private String name;

	public Task( String Name,String State )
	{

		this.state =State;
		this.name= Name;		
	}

	public String getState() {
		return state;
		
	}

	public String getName() {
		return name;
	}	
	

	public void setState(String state) {
		this.state = state;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString()
	{
		return state+name;
		
	}

}
