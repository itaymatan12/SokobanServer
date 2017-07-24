package controller;

import java.util.List;

import model.Model;
import server.RegularClientHandler;

public class CommandLevelScoresSortBySteps implements Command
{
	private Model m;
	private String LevelName;
	private RegularClientHandler v;

	@Override
	public void execute() throws Exception 
	{
		v.setCommandParams(m.sortLevelBySteps(LevelName));
		
	}

	@Override
	public void setParams(List<Object> params)
	{
		this.m = (Model)params.get(0);
		this.LevelName = (String)params.get(1);
		this.v = (RegularClientHandler)params.get(2);
	}

}
