package controller;

import java.util.List;
import model.Model;
import server.RegularClientHandler;

public class CommandUserScoresSortBySteps implements Command {
	
	private Model m;
	private RegularClientHandler v;
	private String userid;

	@Override
	public void execute() throws Exception {
		v.setCommandParams(m.getUserScoresSortBySteps(userid));
		
	}

	@Override
	public void setParams(List<Object> params) {
		this.m = (Model)params.get(0);
		this.userid = (String)params.get(1);
		this.v = (RegularClientHandler)params.get(2);
		
	}

}
