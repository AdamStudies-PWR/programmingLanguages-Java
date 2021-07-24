/*
 * Program: Obs³uga w¹tków klientów programu PhoneBookServer
 * Plik ClientThread.java
 * Autor Adam Krizar
 * Data 25 grudnia 2018
 */
package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread
{
	private Server server;
	private Socket socket;
	private String name = "temporary";
	private PhoneBook pBook;
	private boolean active = true;
	
	public ClientThread(Server server, Socket socket, PhoneBook pBook)
	{
		this.server = server;
		this.socket = socket;
		this.pBook = pBook;
		start();
	}
	
	public String returnName() {return name;}
	
	public String command(String message)
	{
		message = message.toLowerCase();
		String[] data = message.split(" ");
		switch(data[0])
		{
			case "bye":
			{
				server.removeClient(this);
				active = false;
				return "OK";
			}
			case "put":
			{
				if(data.length != 3) return "ERROR b³¹d komendy put";
				return pBook.PUT(data[1], data[2]);
			}
			case "get":
			{
				if(data.length != 2) return "ERROR b³¹d komendy get";
				return pBook.GET(data[1]);
			}
			case "replace":
			{
				if(data.length != 3) return "ERROR b³¹d komendy replace";
				return pBook.REPLACE(data[1], data[2]);
			}
			case "delete":
			{
				if(data.length != 2) return "ERROR b³¹d komendy delete";
				return pBook.DELETE(data[1]);
			}
			case "list":
			{
				return pBook.LIST();
			}
			case "save":
			{
				if(data.length != 2) return "ERROR b³¹d komendy save";
				return pBook.SAVE(data[1]);
			}
			case "load":
			{
				if(data.length != 2) return "ERROR b³¹d komendy load";
				return pBook.LOAD(data[1]);
			}
			default: return "ERROR Nie znanana komenda";
		}
	}

	@Override
	public void run()
	{
		String message;
		String temp;
		try
		(
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		)
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
			server.addToList(this);
			while(active)
			{
				message = (String)input.readObject();
				if(message.equals("close") || message.equals("CLOSE"))
				{
					output.writeObject("OK");
					System.exit(0);
				}
				else
				{
					output.writeObject(command(message));
				}
			}
		}
		catch(Exception error) 
		{
			server.removeClient(this);
		}
	}

}
