package adminModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * class that represents the client that connecting to our server
 *
 */


public class client 
{
	private final SimpleIntegerProperty id; 
	private final SimpleStringProperty ip;

	public client( String name,Integer id  )
	{
		super();
		this.id =new SimpleIntegerProperty(id);
		this.ip= new SimpleStringProperty(name);		
	}

	public Integer getId() {
		return id.get();
	}

	public String getIp() {
		return ip.get();
	}	
}