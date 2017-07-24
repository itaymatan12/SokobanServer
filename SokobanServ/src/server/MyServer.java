package server;

//Model layer in MVVM pattern 
import javafx.application.Platform;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import adminModel.client;



/**
 * class that represents the server that we can get connection from more than one client
 * and from the server socket we talking with each client	
 */
public class MyServer extends Observable
{
    private int port =2932;
    private ClientHandler ch;
    private boolean isStopped = false;
    private ExecutorService s = null;
    private int clientcounter;
    private List<client> allClients;
    private HashMap<String, Socket> clientsdis;

    public MyServer(int port, ClientHandler clientHandler) 
    {
    	clientsdis = new HashMap<String,Socket>();
    	this.allClients =new ArrayList<client>();
    	this.clientcounter =1;
        this.port = port;
        this.ch = clientHandler;
        this.s = Executors.newFixedThreadPool(3);
    }

    public void runServer() throws Exception
    {
       
        ServerSocket server=new ServerSocket(port);
        server.setSoTimeout(1000);
        while(!isStopped)
        {
            try
            {                
                Socket aClient=server.accept();
                s.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        	
                        	client c = new client(aClient.getRemoteSocketAddress().toString(),clientcounter);
                        	clientcounter++;
                        	allClients.add(c);
                        	
                        	System.out.println(aClient.getRemoteSocketAddress());
                        	
                        	clientsdis.put(c.getIp(), aClient);
                        	
                
                        	setChanged();
                        	notifyObservers(allClients);
                            System.out.println("Client CONNECTED, Client IP:"+aClient.getRemoteSocketAddress().toString());
                            ch.aSyncReadInputsAndSend(aClient.getInputStream(),aClient.getOutputStream());
                            aClient.getInputStream().close();
                            aClient.getOutputStream().close();
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (SocketTimeoutException ignored){}
        }
        
        
        stopServer();
        
        if(isStopped)
        {
        	server.close();
        }
        
        Platform.exit();
    }

    public void stopServer() 
    {
        System.out.println("Shutting Down Server..");
        s.shutdown();
        try{
            s.awaitTermination(5, TimeUnit.SECONDS);
            
            for(String s : clientsdis.keySet())
            {
            	clientsdis.get(s).close();
            }
            
            
        } catch (InterruptedException e ) 
        {e.printStackTrace();} 
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
            isStopped=true;
        }
    }
    
    public void changeclientcapc(int capcity)
    {
    	
    	this.s = Executors.newFixedThreadPool(capcity); 
    }
    
    public void DisconnectClient(String ClientAdrress)
    {
    	try {
			clientsdis.get(ClientAdrress).close();
			clientsdis.remove(ClientAdrress);
			
			for(int i=0;i<this.allClients.size();i++)
			{
				if (allClients.get(i).getIp()==ClientAdrress)
				{
					allClients.remove(i);
                	setChanged();
                	notifyObservers(allClients);
					return;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
	public ExecutorService getS() {
		return s;
	}

	public void setS(ExecutorService s) {
		this.s = s;
	}

	public List<client> getAllClients() {
		return allClients;
	}

	public void setAllClients(List<client> allClients) {
		this.allClients = allClients;
	}

}