package controller;

import java.util.List;

/**
 *Interface that defines command in the server like getting all the users and more
 */

//interface that defines command
public interface Command {
	

//function that all the commands that implement command interface have	
	public abstract void execute() throws Exception;

	public void setParams(List<Object> params);

}
