/*
 * Program: GUI programu ClientComunicator
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
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JComboBox<StringBuilder> nameBox = new JComboBox<StringBuilder>();
	private Color color = new Color(0, 43, 60);
	
	private String name;
	private static final int SERVER_PORT = 26000;
	private int CPORT;
	private String CHOST = null;
	private String serverHost = "";
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectOutputStream cout;
	private ObjectInputStream input;
	private DirecServer server;
	private Client client;
	
	private static final String ABOUT_SCREEN =
			"Program - ServerComunicator\n"+
			"Autor - Adam Krizar\n"+
			"Data 28 grudnia 2018\n";
	
	JMenuBar bar = new JMenuBar();
	JMenu options = new JMenu("Opcje");
	JMenuItem aboutItem = new JMenuItem("O programie");
	JMenuItem exitItem = new JMenuItem("Zakoñcz program");
	JButton connectButton = new JButton("Po³¹cz");
	
	JTextArea area = new JTextArea();
	JScrollPane scrollArea = new JScrollPane(area);
	
	JLabel tLabel = new JLabel(">:");
	JTextField inputField = new JTextField();
	
	JLabel label = new JLabel("U¿ytkownicy:");
	
	public void setCout(ObjectOutputStream cout) {this.cout = cout;}
	public void setEnabled(boolean set)
	{
		nameBox.setEnabled(set);
		connectButton.setEnabled(set);
		inputField.setEnabled(!set);
	}
	
	private void placeConetent()
	{
		bar.setBounds(0, 0, 340, 20);
		label.setBounds(10, 30, 100, 20);
		nameBox.setBounds(90, 30, 150, 20);
		connectButton.setBounds(250, 30, 75, 20);
		scrollArea.setBounds(10, 60, 315, 260);
		tLabel.setBounds(10, 330, 20, 20);
		inputField.setBounds(30, 330, 295, 20);
	}
	
	public User()
	{
		server = new DirecServer(area, this);
		options.setCursor(cursor);
		aboutItem.setCursor(cursor);
		exitItem.setCursor(cursor);
		nameBox.setCursor(cursor);
		connectButton.setCursor(cursor);
		
		setTitle("ClientComunicator");
		setSize(350,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		nameBox.addItem(new StringBuilder("-----"));
		area.setEditable(false);
		tLabel.setForeground(Color.WHITE);
		label.setForeground(Color.WHITE);
		area.setBackground(color);
		area.setForeground(Color.WHITE);
		inputField.setEnabled(false);
		
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		connectButton.addActionListener(this);
		
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
		panel.add(nameBox);
		panel.add(label);
		panel.add(connectButton);
		bar.add(options);
		options.add(aboutItem);
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
		if(event == exitItem) 
		{
			System.exit(0);
		}
		if(event == connectButton)
		{
			if(!nameBox.getSelectedItem().toString().equals("-----"))
			{
				connectButton.setEnabled(false);
				inputField.setEnabled(true);
				nameBox.setEnabled(false);
				try 
				{
					output.writeObject(nameBox.getSelectedItem().toString());
				} 
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(this, "B³¹d po³¹czenia");
				}
			}
		}
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
						setTitle("ClientComunicator - " + name);
						break;
					}
				}
		  		output.writeObject(serverHost + "#" + server.getPort().toString());
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
		 		StringBuilder message = (StringBuilder) input.readObject();
		 		String[] data;
		 		if(message.charAt(0) == '$')
		 		{
		 			message.deleteCharAt(0);
		 			if(!message.toString().equals(this.name)) nameBox.addItem(message);
		 		}
		 		else if(message.charAt(0) == '!')
		 		{
		 			message.deleteCharAt(0);
		 			for(int i = 0; i<nameBox.getItemCount(); i++)
		 			{
		 				if(nameBox.getItemAt(i).toString().equals(message.toString()))
		 				{
		 					if(!message.toString().equals(this.name)) nameBox.removeItem(nameBox.getItemAt(i));
		 				}
		 			}
		 		}
		 		else if(message.charAt(0) == '^')
		 		{
		 			message.deleteCharAt(0);
		 			data = message.toString().split("#");
		 			CPORT = Integer.parseInt(data[0]);
		 			CHOST = data[1];
		 		}
		 		if(CHOST != null) 
		 		{
		 			client = new Client(CPORT, CHOST, this, area);
		 			server.setActive(false);
		 			cout = client.getOutput();
		 			CHOST = null;
		 		}
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
				cout.writeObject(message);
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
