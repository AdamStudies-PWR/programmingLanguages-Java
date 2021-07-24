/*
 * Program: GUI programu ServerComunicator programu PhoneBookServer
 * Plik Server.java
 * Autor Adam Krizar
 * Data 25 grudnia 2018
 */
package server;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable, ActionListener
{
	private static final long serialVersionUID = 1L;
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	private Color color = new Color(0, 43, 60);
	private static final int SERVER_PORT = 26000;
	private ArrayList<ConnectedUsers> cuList = new ArrayList<ConnectedUsers>();
	private String host;
	
	private static final String ABOUT_SCREEN =
			"Program - Serwer programu komunikacyjnego - grupy\n"+
			"Autor - Adam Krizar\n"+
			"Data 28 grudnia 2018\n";
	
	JMenuBar bar = new JMenuBar();
	JMenu options = new JMenu("Opcje");
	JMenuItem aboutItem = new JMenuItem("O programie");
	JMenuItem exitItem = new JMenuItem("Zako�cz program");
	
	JTextArea area = new JTextArea();
	JScrollPane scrollArea = new JScrollPane(area);
	
	JLabel label = new JLabel("Lista u�ytkownik�w:");
	
	private void placeConetent()
	{
		bar.setBounds(0, 0, 340, 20);
		label.setBounds(10, 30, 120, 20);
		scrollArea.setBounds(10, 60, 315, 290);
	}
	
	public Server()
	{
		options.setCursor(cursor);
		aboutItem.setCursor(cursor);
		exitItem.setCursor(cursor);
		
		setTitle("ServerComunicator");
		setSize(350,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		label.setForeground(Color.WHITE);
		area.setEditable(false);
		area.setBackground(color);
		area.setForeground(Color.WHITE);
		
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(color);
		
		panel.add(label);
		panel.add(scrollArea);
		panel.add(exitItem);
		panel.add(bar);
		bar.add(options);
		options.add(aboutItem);
		options.add(exitItem);
		
		placeConetent();
		setContentPane(panel);
		setVisible(true);
		new Thread(this).start();
	}
	
	public void removeClient(ConnectedUsers cu)
	{
		String text = "";
		cuList.remove(cu);
		text = "Serwer uruchomiono na: " + host + "\n";
		for(ConnectedUsers ctl: cuList)
		{
			text = text + ctl.returnName() + "\n";
			ctl.removeUser(cu.returnName());
		}
		area.setText(text);
	}
	
	public boolean isNameTaken(String name)
	{
		for(ConnectedUsers ct: cuList)
		{
			if(ct.returnName().equals(name)) return true;
		}
		return false;
	}
	
	public void addToList(ConnectedUsers cu)
	{
		area.append(cu.returnName() + "\n");
		for(ConnectedUsers ct: cuList)
		{
			ct.addUser(cu.returnName());
		}
	}

	@Override
	public void run() 
	{
		boolean connection = false;
		try(ServerSocket server = new ServerSocket(SERVER_PORT)) 
		{
			host = InetAddress.getLocalHost().getHostName();
			area.append("Serwer uruchomiono na: " + host + "\n");
			connection = true;
			while(true)
			{
				Socket socket = server.accept();
				if(socket != null)
				{
					ConnectedUsers temp = new ConnectedUsers(this, socket, cuList);
					cuList.add(temp);
				}
				socket = null;
			}
		} 
		catch (IOException error) 
		{
			error.printStackTrace();
			if (!connection) 
			{
				JOptionPane.showMessageDialog(this, "Gniazdko dla serwera nie mo�e by� utworzone");
				System.exit(0);
			}
			else 
			{
				JOptionPane.showMessageDialog(this, "BLAD SERWERA: Nie mozna polaczyc sie z klientem ");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent eventSource) 
	{
		Object event = eventSource.getSource();
		if(event == aboutItem) JOptionPane.showMessageDialog(this, ABOUT_SCREEN);
		if(event == exitItem) System.exit(0);
	}
}
