package adminModel;

import server.MyServer;
import server.RegularClientHandler;

public class Run {

	public static void main(String[] args) throws Exception 
	{
		
		RegularClientHandler r = new RegularClientHandler();
		MyServer s = new MyServer(2577, r);
		
		s.runServer();
		/*
		List<Object> string = new ArrayList<Object>();;
		
		UserRecord u = new UserRecord();
		u.setLevelName("try");
		u.setSteps(5);
		u.setTime(4);
		u.setUserId(1111161);
		List<User> a =new ArrayList<User>();
		a.add(new User(209283506));
		a.add(new User(209284569));
		a.add(new User(218862154));
		//LinkedList<Object> p =new LinkedList<Object>( manager.getAllUsers());
	LinkedList<Object> p =new LinkedList<Object>(a);
	p.addFirst("string");
	for( Object o : p  )
	{
		System.out.println(o);
	}
	
	User d =(User)a.get(2);
	System.out.println(d);
	//	string.add(u);
	System.out.println(p);
		
		
		//ObjectOutputStream writer = new ObjectOutputStream());
//

 
		List<String> strings = new ArrayList<>(string.size());
		for (Object object : string) {
		    strings.add(Objects.toString(object, null));
		}
		

		//System.out.println(strings.get(0).substring(strings.get(0).indexOf('.'), strings.get(0).indexOf('@')));
		*/
	}

}
