package controller;

import java.util.List;
import model.Model;
import server.RegularClientHandler;

public class CommandGetUsers implements Command {
	
	private Model m;
	private RegularClientHandler v;
	private Thread t;
	private boolean stop = false;
	@Override
	public void execute() throws Exception 
	{	
				v.setCommandParams(m.getUsers());	
	}
	

	@Override
	public void setParams(List<Object> params) 
	{
		this.m = (Model)params.get(0);
		this.v = (RegularClientHandler)params.get(1);
		
	}
	
}
