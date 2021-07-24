/*
 * Program: GUI programu ProducerConsumerTest
 * Plik UserInterface.java
 * Autor Adam Krizar
 * Data 26 listopada 2018
 */
package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class UserInterface extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Color color = new Color(0, 43, 60);
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	private Integer values[] = {1,2,3,4,5};
	private Thread [] tConsumers;
	private Thread [] tProducers;
	private Producer [] producers;
	private Consumer [] consumers;
	
	private static final String ABOUT_SCREEN =
			"Program - Archiwum Biblioteki - grupy\n"+
			"Autor - Adam Krizar\n"+
			"Data 28 pazdziernika 2018\n";
	private static final String HELP =
			"Program zawiera dodatkowe pausy po wsywietleniu niektórych informacji\n" +
			"aby zwiêkszyæ ich czytelnoœæ\n\n" + 
			"Kolumna producent:\n"+
			"czerwony - oznacza ¿e bufor by³ pe³ny i obiekt oczekuje na jego zwolnienie\n"+
			"zó³ty - oznazcza chêæ oddania produktu\n" +
			"zielony - oznacza ¿e producent jest zajêty produkcj¹\n\n"+
			"Kolumna konsument:\n"+
			"czerwony - oznacza ¿e bufor by³ usty i obiekt oczekuje na jego zape³nienie\n"+
			"zó³ty - oznazcza chêæ skonsumowania produktu\n" +
			"zielony - oznacza ¿e konsument jest zajêty konsumpcj¹\n\n"+
			"Kolumna bufor:\n"+
			"czerwony - oznacza ¿e bufor jest pe³ny\n"+
			"zó³ty - oznazcza, ¿e pozosta³o jedno wolne miejsce\n" +
			"zielony - oznacza zape³nione miejsce\n"+
			"niebieski - oznacza puste miejsce";
	
	//Górny pasek
	JMenuBar bar = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenuItem restartItem = new JMenuItem("Restart");
	JMenuItem exitItem = new JMenuItem("Zakoñcz");
	JMenu about = new JMenu("Pomoc");
	JMenuItem authorItem = new JMenuItem("O autorze");
	JMenuItem helpItem = new JMenuItem("Instrukcje");
	//Panel tekstowy
	JTextArea area = new JTextArea();
	JScrollPane scrollArea = new JScrollPane(area);
	//Bufor
	JLabel buforLabel = new JLabel("Rozmiar bufora:");
	JComboBox<Integer> buforBox = new JComboBox<Integer>(values);
	//Producenci
	JLabel producerLabel = new JLabel("Iloœæ producentów:");
	JComboBox<Integer> producerBox = new JComboBox<Integer>(values);
	//Konsumeci
	JLabel consumerLabel = new JLabel("Iloœæ konsumentów:");
	JComboBox<Integer> consumerBox = new JComboBox<Integer>(values);
	//Przyciksi
	JButton startButton = new JButton("Rozpocznij symulacje");
	JButton pauseButton = new JButton("Wstrzymaj symulacje");
	//Wskaznik bufora + informacyjne labele
	JLabel informationLabel = new JLabel("<html><p>K - konsumenci</p><p>P - producenci</p><p>B - bufor</p></html>");
	InformationPanel informationPanel = new InformationPanel();
	JLabel kLabel = new JLabel("K");
	JLabel pLabel = new JLabel("P");
	JLabel bLabel = new JLabel("B");
	
	private void placeContent()
	{
		bar.setBounds(0, 0, 650, 20);
		scrollArea.setBounds(145, 60, 465, 305);
		buforLabel.setBounds(145, 30, 90, 20);
		buforBox.setBounds(240, 30, 40, 20);
		consumerBox.setBounds(570, 30, 40, 20);
		consumerLabel.setBounds(450, 30, 120, 20);
		producerBox.setBounds(405, 30, 40, 20);
		producerLabel.setBounds(290, 30, 120, 20);
		startButton.setBounds(155, 375, 160, 25);
		pauseButton.setBounds(440, 375, 160, 25);
		informationLabel.setBounds(20, 350, 125, 60);
		informationPanel.setBounds(20, 40, 105, 315);
		kLabel.setBounds(15, 292, 10, 20);
		pLabel.setBounds(50, 292, 10, 20);
		bLabel.setBounds(84, 292, 10, 20);
	}
	
	public UserInterface()
	{
		menu.setCursor(cursor);
		about.setCursor(cursor);
		restartItem.setCursor(cursor);
		exitItem.setCursor(cursor);
		authorItem.setCursor(cursor);
		helpItem.setCursor(cursor);
		consumerBox.setCursor(cursor);
		producerBox.setCursor(cursor);
		buforBox.setCursor(cursor);
		startButton.setCursor(cursor);
		pauseButton.setCursor(cursor);
		
		setTitle("ProducerConsumerTest");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(650, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		buforLabel.setForeground(Color.WHITE);
		producerLabel.setForeground(Color.WHITE);
		consumerLabel.setForeground(Color.WHITE);
		informationLabel.setForeground(Color.WHITE);
		area.setEditable(false);
		pauseButton.setEnabled(false);
		
		restartItem.addActionListener(this);
		exitItem.addActionListener(this);
		authorItem.addActionListener(this);
		helpItem.addActionListener(this);
		startButton.addActionListener(this);
		pauseButton.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		informationPanel.setLayout(null);
		panel.setBackground(color);
		
		panel.add(scrollArea);
		panel.add(bar);
		panel.add(consumerBox);
		panel.add(producerBox);
		panel.add(buforBox);
		panel.add(buforLabel);
		panel.add(consumerLabel);
		panel.add(producerLabel);
		panel.add(startButton);
		panel.add(pauseButton);
		panel.add(informationLabel);
		panel.add(informationPanel);
		informationPanel.add(kLabel);
		informationPanel.add(pLabel);
		informationPanel.add(bLabel);
		bar.add(menu);
		bar.add(about);
		menu.add(restartItem);
		menu.add(exitItem);
		about.add(authorItem);
		about.add(helpItem);
		
		placeContent();
		setContentPane(panel);
		setVisible(true);
	}
	
	public void start(Buffer buffer)
	{
		for(int i=0; i<consumers.length; i++)
		{
			consumers[i] = new Consumer("#"+(i+1), buffer, area, consumers.length, informationPanel, i, "K");
			tConsumers[i] = new Thread(consumers[i]);
		}
		for(int i=0; i<producers.length; i++)
		{
			producers[i] = new Producer("#"+(i+1), buffer, area, producers.length, informationPanel, i, "P");
			tProducers[i] = new Thread(producers[i]);
		}
		informationPanel.setDrawable(buffer, consumers, producers);
		for(Thread thread: tProducers)
		{
			thread.start();
		}
		for(Thread thread: tConsumers)
		{
			thread.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object eventSource = event.getSource();
		if(eventSource == exitItem) dispose();
		if(eventSource == authorItem) JOptionPane.showMessageDialog(this, ABOUT_SCREEN);
		if(eventSource == startButton)
		{
			Buffer buffer = new Buffer(area, (int) buforBox.getSelectedItem(), informationPanel);
			tConsumers = new Thread [(int) consumerBox.getSelectedItem()];
			tProducers = new Thread [(int) producerBox.getSelectedItem()];
			consumers = new Consumer [(int) consumerBox.getSelectedItem()];
			producers = new Producer [(int) producerBox.getSelectedItem()];
			start(buffer);
			buforBox.setEnabled(false);
			consumerBox.setEnabled(false);
			producerBox.setEnabled(false);
			startButton.setEnabled(false);
			pauseButton.setEnabled(true);
		}
		if(eventSource == pauseButton)
		{
				for(Producer producer: producers)
				{
					producer.pause();
				}
				for(Consumer consumer: consumers)
				{
					consumer.pause();
				}
				if(pauseButton.getText().equals("Wstrzymaj symulacje")) pauseButton.setText("Wznów symulacje");
				else pauseButton.setText("Wstrzymaj symulacje");
		}
		if(eventSource == restartItem)
		{
			dispose();
			new UserInterface();
		}
		if(eventSource == helpItem) JOptionPane.showMessageDialog(this, HELP);
	}
}
