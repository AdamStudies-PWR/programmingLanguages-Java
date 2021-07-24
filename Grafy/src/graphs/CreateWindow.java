/*
 * Kasa obs³uguj¹ca okno do edycji/tworzenia nowych wêz³ów
 * Plik: CreateWindow.java
 * Autor: Adam Krizar
 * Data 22.11.2018r.
 */
package graphs;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateWindow extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private int[] ipTab;
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	
	JTextField ip1 = new JTextField();
	JTextField ip2 = new JTextField();
	JTextField ip3 = new JTextField();
	JTextField ip4 = new JTextField();
	JComboBox<DeviceType> typeBox = new JComboBox<DeviceType>(DeviceType.values());
	JButton okButton = new JButton("Gotowe");
	JButton cancelButton = new JButton("Anuluj");
	JLabel ipLabel = new JLabel("IP:");
	JLabel typeLabel = new JLabel("Typ:");
	
	BasicNodes node;
	
	private void placeContent()
	{
		ipLabel.setBounds(19, 10, 25, 30);
		typeLabel.setBounds(10, 50, 25, 30);
		typeBox.setBounds(40, 50, 180, 30);
		okButton.setBounds(10, 90, 100, 30);
		cancelButton.setBounds(120, 90, 100, 30);
		ip1.setBounds(40, 10, 30, 30);
		ip2.setBounds(90, 10, 30, 30);
		ip3.setBounds(140, 10, 30, 30);
		ip4.setBounds(190, 10, 30, 30);
	}
	
	private void setIP(String ip1, String ip2, String ip3, String ip4)
	{
		if(ip1.length() == 1) this.ip1.setText("00" + ip1);
		else if(ip1.length() == 2) this.ip1.setText("0" + ip1);
		else this.ip1.setText(ip1);
		if(ip2.length() == 1) this.ip2.setText("00" + ip2);
		else if(ip2.length() == 2) this.ip2.setText("0" + ip2);
		else this.ip2.setText(ip2);
		if(ip3.length() == 1) this.ip3.setText("00" + ip3);
		else if(ip3.length() == 2) this.ip3.setText("0" + ip3);
		else this.ip3.setText(ip3);
		if(ip4.length() == 1) this.ip4.setText("00" + ip4);
		else if(ip4.length() == 2) this.ip4.setText("0" + ip4);
		else this.ip4.setText(ip4);
	}
	
	private CreateWindow(Window parent, BasicNodes node)
	{
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		typeBox.setCursor(cursor);
		okButton.setCursor(cursor);
		cancelButton.setCursor(cursor);
		this.node = node;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(245, 170);
		setLocationRelativeTo(parent);
		setResizable(false);
		setTitle("Edytuj wêze³");
		typeBox.setSelectedItem(node.getType());
		ipTab = node.getIP();
		setIP(Integer.toString(ipTab[0]), Integer.toString(ipTab[1]), Integer.toString(ipTab[2]),Integer.toString(ipTab[3]));
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		JPanel panel = new JPanel();;
		panel.setLayout(null);
		
		panel.add(typeBox);
		panel.add(okButton);
		panel.add(cancelButton);
		panel.add(ip1);
		panel.add(ip2);
		panel.add(ip3);
		panel.add(ip4);
		panel.add(ipLabel);
		panel.add(typeLabel);
		
		placeContent();
		setContentPane(panel);
		setVisible(true);
	}

	private boolean checkIP(String ip1, String ip2, String ip3, String ip4)
	{
		if((ip1.length()!=3) || (ip2.length()!=3) || (ip3.length()!=3) || (ip4.length()!=3))
		{
			JOptionPane.showMessageDialog(this, "IP musi mieæ d³ugoœæ 3", "B³¹d", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try
		{
			if(Integer.parseInt(ip1)>255 || Integer.parseInt(ip2)>255 || Integer.parseInt(ip3)>255 || Integer.parseInt(ip4)>255)
			{
				JOptionPane.showMessageDialog(this, "IP max 255", "B³¹d", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "IP nale¿y podaæ liczbowo", "B³¹d", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object eventSource = event.getSource();
		if(eventSource == cancelButton) dispose();
		if(eventSource == okButton)
		{
			if(!checkIP(ip1.getText(), ip2.getText(), ip3.getText(), ip4.getText())) return;
			ipTab[0] = Integer.parseInt(ip1.getText());
			ipTab[1] = Integer.parseInt(ip2.getText());
			ipTab[2] = Integer.parseInt(ip3.getText());
			ipTab[3] = Integer.parseInt(ip4.getText());
			node.setIP(ipTab);
			node.setType((DeviceType) typeBox.getSelectedItem());
			dispose();
		}
	}
	
	public static void changeNode(Window parent, BasicNodes node)
	{
		new CreateWindow(parent, node);
	}
}
