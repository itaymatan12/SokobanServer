package controller;

import java.util.List;

import model.Model;
import server.RegularClientHandler;

public class CommandAddUser implements Command 
{
	
	private Model m;
	private String UserId;
	private RegularClientHandler v;

	@Override
	public void execute() throws Exception
	{
		
		v.setCommandParams(m.AddUser(UserId));

		
	}

	@Override
	public void setParams(List<Object> params)
	{
		this.m = (Model)params.get(0);
		this.UserId = (String)params.get(1);
		this.v = (RegularClientHandler)params.get(2);
	}
	
	

}
