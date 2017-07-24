package controller;

import java.util.List;

import model.Model;
import server.RegularClientHandler;

public class CommandGetSolution implements Command {
	
	
	private Model m;
	private String LevelName;
	private String levelSTR;
	private RegularClientHandler v;
	
	@Override
	public void execute() throws Exception 
	{
		v.setCommandParams(m.getSolution(this.LevelName,this.levelSTR));
	}

	@Override
	public void setParams(List<Object> params) {
		this.m = (Model)params.get(0);
		this.LevelName = (String)params.get(1);
		this.levelSTR = (String)params.get(2);
		this.v = (RegularClientHandler)params.get(3);
	}

}
