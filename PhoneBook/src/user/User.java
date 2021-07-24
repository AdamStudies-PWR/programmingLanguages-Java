/*
 * Program: GUI programu PhoneBookClient
 * Plik User.java
 * Autor Adam Krizar
 * Data 25 grudnia 2018
 */
package user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class User extends JFrame implements Runnable, ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	private Color color = new Color(0, 43, 60);
	
	private String name;
	private static final int SERVER_PORT = 24000;
	private String serverHost = "";
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private static final String ABOUT_SCREEN =
			"Program - Klient ksi¹¿ki telefonicznej - grupy\n"+
			"Autor - Adam Krizar\n"+
			"Data 25 grudnia 2018\n";
	private static final String INSTRUCTIONS =
			"„LOAD nazwa_pliku”  - wczytanie danych z pliku o podanej nazwie\n" + 
			"„SAVE nazwa_pliku”  - zapis danych do pliku o podanej nazwie\n" +
			"„GET imiê”  - pobranie numeru telefonu osoby o podanym imieniu\n" + 
			"„PUT imiê    numer”  - zapis numeru telefonu osoby o podanym imieniu\n" + 
			"„REPLACE imie    numer”  - zmiana numeru telefonu dla osoby o podanym mieniu\n" +
			"„DELETE imie”  - usuniêcie z kolekcji osoby o podanym imieniu\n" +
			"„LIST”  - przes³anie listy imion, które s¹ zapamiêtane w kolekcji\n" +
			"„CLOSE”  - zakoñczenie nas³uchu po³¹czeñ od nowych klientów i zamkniêcie gniazda serwera\n" + 
			"„BYE”  - zakoñczenie komunikacji klienta z  serwerem i zamkniêcie strumieni danych  oraz gniazda\n"+
			"Wybierz pole tekstowe (dolne) aby wprowadzaæ komendy\n"+
			"Gdy pole tekstowe jest aktywne ENTER zatwierdza komendy";
	
	JMenuBar bar = new JMenuBar();
	JMenu options = new JMenu("Opcje");
	JMenuItem aboutItem = new JMenuItem("O programie");
	JMenuItem helpItem = new JMenuItem("Instrukcje");
	JMenuItem exitItem = new JMenuItem("Zakoñcz program");
	
	JTextArea area = new JTextArea();
	JScrollPane scrollArea = new JScrollPane(area);
	
	JLabel tLabel = new JLabel(">:");
	JTextField inputField = new JTextField();
	
	private void placeConetent()
	{
		bar.setBounds(0, 0, 340, 20);
		scrollArea.setBounds(10, 30, 315, 290);
		tLabel.setBounds(10, 330, 20, 20);
		inputField.setBounds(30, 330, 295, 20);
	}
	
	public User()
	{
		options.setCursor(cursor);
		aboutItem.setCursor(cursor);
		exitItem.setCursor(cursor);
		helpItem.setCursor(cursor);
		
		setTitle("PhoneBookClient");
		setSize(350,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		area.setEditable(false);
		tLabel.setForeground(Color.WHITE);
		
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		helpItem.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(color);
		panel.setFocusable(true);
	    panel.requestFocus();
	    inputField.addKeyListener(this);
		
		panel.add(scrollArea);
		panel.add(inputField);
		panel.add(tLabel);
		panel.add(exitItem);
		panel.add(bar);
		bar.add(options);
		options.add(aboutItem);
		options.add(helpItem);
		options.add(exitItem);
		
		setContentPane(panel);
		placeConetent();
		setVisible(true);
		new Thread(this).start();
	}

	@Override
	public void actionPerformed(ActionEvent eventSource) 
	{
		Object event = eventSource.getSource();
		if(event == aboutItem) JOptionPane.showMessageDialog(this, ABOUT_SCREEN);
		if(event == helpItem) JOptionPane.showMessageDialog(this, INSTRUCTIONS);
		if(event == exitItem) System.exit(0);
	}

	@Override
	public void run() 
	{
		if (serverHost.equals("")) {serverHost = "localhost";}
		try
		{
				socket = new Socket(serverHost, SERVER_PORT);
		  		input = new ObjectInputStream(socket.getInputStream());
		  		output = new ObjectOutputStream(socket.getOutputStream());
		  		while(true)
				{
					name = JOptionPane.showInputDialog(this, "Podaj nazwe u¿ytkownika");
					if(name == null)
					{
						dispose();
						return;
					}
					output.writeObject(name);
					if(!(Boolean)input.readObject())
					{
						JOptionPane.showMessageDialog(this, "Nazwa zajêta!\nWybierz inn¹");
					}
					else
					{
						setTitle("PhoneBookClient - " + name);
						break;
					}
				}
		}
		catch(Exception error)
		{
			JOptionPane.showMessageDialog(this, "Polaczenie sieciowe dla klienta nie moze byc utworzone");
			dispose();
			return;
		} 
		try
		{
		 	while(true)
		 	{
		 		String message = (String)input.readObject();
		 		area.append(message + "\n");
		 		area.setCaretPosition(area.getDocument().getLength());
		 	}
		} 
		catch(Exception error)
		{
		   	JOptionPane.showMessageDialog(this, "Polaczenie sieciowe dla klienta zostalo przerwane");
		   	setVisible(false);
		   	dispose();
		}	
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		String message;
		if(event.getKeyCode() == KeyEvent.VK_ENTER)
		{
			message = inputField.getText();
			area.append(">: " + message + "\n");
			area.setCaretPosition(area.getDocument().getLength());
			try 
			{
				output.writeObject(message);
			} 
			catch (IOException error) 
			{
				JOptionPane.showMessageDialog(this, "B³¹d wysy³ania wiadomoœci");
			}
			inputField.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent event)	{}

	@Override
	public void keyTyped(KeyEvent event) {}
}
