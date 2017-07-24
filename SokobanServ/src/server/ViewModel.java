package server;

import java.util.Observable;
import java.util.Observer;
import controller.Command;
import javafx.beans.property.ListProperty;
import model.MyModel;

/**
 * The ViewModel class that represents the ViewModel layer in the MVVM pattern 
 * responsible to convey the information from the Model layer to the View layer 
 * and represents the server
 */

public class ViewModel extends Observable implements Observer 
{
	
	private MyModel m;
	private MyServer s;
	private int flag;


	public ListProperty<Command> tasks;
	
	public ViewModel(MyModel m) 
	{
		this.setFlag(0);
		this.m =m;
	}
	
	
	@Override
	public void update(Observable o, Object arg) 
	{
		if(o instanceof MyServer)
		{
			this.setFlag(1);
		}
		
		if(o instanceof RegularClientHandler)
		{
			this.setFlag(2);
			System.out.println(989898);
		}
			setChanged();
			notifyObservers(arg);	
	}


	public void startserver()
	{
		try {
			this.s.runServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stopserver()
	{
		this.s.stopServer();
	}
	
	
	public void changeclientcapacity(int newcapcity)
	{
		this.getS().changeclientcapc(newcapcity);
		
	}
	
	
	public void disconnectSpecificClient(String ip)
	{
		this.s.DisconnectClient(ip);
	}


	public MyServer getS() {
		return s;
	}


	public void setS(MyServer s) {
		this.s = s;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}

}
