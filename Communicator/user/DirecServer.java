/*
 * Program: Obs³uga serwera pomiêdzy romówcami ClientComunicator
 * Plik DirecServer.java
 * Autor Adam Krizar
 * Data 25 grudnia 2018
 */
package user;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class DirecServer extends Thread
{
	private Integer SERVER_PORT;
	private JTextArea area;
	private ServerSocket server;
	private Socket socket = null;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean active = true;
	private User user;
	
	public void setActive(boolean active) {this.active = active;}
	
	public DirecServer(JTextArea area, User user)
	{
		boolean connection = false;
		this.area = area;
		this.user = user;
		try
		{
			server = new ServerSocket(0);
			SERVER_PORT = server.getLocalPort();
			connection = true;
		}
		catch(Exception error)
		{
			error.printStackTrace();
			if (!connection) 
			{
				JOptionPane.showMessageDialog(user, "Gniazdko dla serwera nie mo¿e byæ utworzone");
				System.exit(0);
			}
			else 
			{
				JOptionPane.showMessageDialog(user, "BLAD SERWERA: Nie mozna polaczyc sie z klientem ");
			}
		}
		start();
	}
	
	public Integer getPort() {return SERVER_PORT;}
	public ObjectOutputStream getOutput() {return output;}
	
	@Override
	public void run() 
	{
		String message;
		while(active)
		{
			try 
			{
				socket = server.accept();
				if(socket != null)
				{
					JOptionPane.showMessageDialog(user, "Nadchodz¹ce po³¹czenie!");
					output = new ObjectOutputStream(socket.getOutputStream());
					input = new ObjectInputStream(socket.getInputStream());
					user.setEnabled(false);
					user.setCout(output);
					break;
				}
				
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(user, "BLAD SERWERA: Nie mozna polaczyc sie z klientem ");
				break;
			}
		}
		try
		{
			while(active)
			{
				message = (String)input.readObject();
				area.append(message + "\n");
			}
		}
		
		catch(Exception error) 
		{
			JOptionPane.showMessageDialog(user, "BLAD SERWERA: U¿ytkownik roz³¹czy³ siê");
			System.exit(0);
		}
	} 
		
}
