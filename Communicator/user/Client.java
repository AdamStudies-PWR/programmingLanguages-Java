package user;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Client extends Thread
{
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private JTextArea area;
	private User user;
	
	public Client(int SERVER_PORT, String serverHost, User user, JTextArea area)
	{
		this.user = user;
		this.area = area;
		try
		{
				socket = new Socket(serverHost, SERVER_PORT);
				Thread.sleep(1000);
				if(!socket.isConnected())throw new Exception();
		  		input = new ObjectInputStream(socket.getInputStream());
		  		output = new ObjectOutputStream(socket.getOutputStream());
		  		JOptionPane.showMessageDialog(user, "Utworzono po³¹czenie!");
		  		user.setEnabled(false);
		  		user.setCout(output);
		}
		catch(Exception error)
		{
			JOptionPane.showMessageDialog(user, "U¿ytkownik zajêty");
			user.setEnabled(true);
			return;
		}
		start();
	}
	
	public ObjectOutputStream getOutput() {return output;}
	
	@Override
	public void run() 
	{
		try
		{
		 	while(true)
		 	{
		 			String message = (String) input.readObject();
		 			area.append(message + "\n");
		 	}
		} 
		catch(Exception error)
		{
		   	JOptionPane.showMessageDialog(user, "U¿ytkownik zakoñczy³ po³¹czenie");
		   	System.exit(0);
		}	
	}
}