/*
 * Program: Klasa przechowyuj¹ca dane u¿ytkowników programu ServerComunicator
 * Plik ConnectedUsers.java
 * Autor Adam Krizar
 * Data 25 grudnia 2018
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectedUsers extends Thread
{
	private Server server;
	private Socket socket;
	private String serverHost = "";
	private String name = "temporary";
	private Integer SERVER_PORT;
	private boolean active = true;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ArrayList<ConnectedUsers> cuList;
	
	public ConnectedUsers(Server server, Socket socket, ArrayList<ConnectedUsers> cuList)
	{
		this.server = server;
		this.socket = socket;
		this.cuList = cuList;
		start();
	}
	
	public void addUser(String name)
	{
		try 
		{
			output.writeObject(new StringBuilder("$" + name));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void removeUser(String name)
	{
		try 
		{
			output.writeObject(new StringBuilder("!" + name));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String returnName() {return name;}
	public Integer getPort() {return SERVER_PORT;}
	public String getHost() {return serverHost;}
	
	@Override
	public void run()
	{
		String message;
		String temp;
		try
		{
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception error)
		{
			server.removeClient(this);
		}
		try
		{
			while(true)
			{
				temp = (String)input.readObject();
				if(server.isNameTaken(temp))
				{
					output.writeObject(false);
					continue;
				}
				else 
				{
					name = temp;
					output.writeObject(true);
					break;
				}
			}
			for(ConnectedUsers cu: cuList)
			{
				this.addUser(cu.returnName());
			}
			server.addToList(this);
			temp = (String)input.readObject();
			String data[] = temp.split("#");
			serverHost = data[0];
			SERVER_PORT = Integer.parseInt(data[1]);
			while(active)
			{
				message = (String)input.readObject();
				for(ConnectedUsers cu: cuList)
				{
					if(cu.returnName().equals(message))
					{
						output.writeObject(new StringBuilder("^" + cu.getPort().toString()+ "#" + cu.getHost()));
						server.removeClient(this);
						server.removeClient(cu);
						break;
					}
				}
			}
		}
		catch(Exception error) 
		{
			server.removeClient(this);
		}
	}
}
