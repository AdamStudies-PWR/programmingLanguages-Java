/*
 * Program Grafy
 * Plik Editor.java
 * Klasa Editor zarz�da g�ownym oknem programu
 * Autor: Adam Krizar
 * Data 12.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Editor extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	
	private static final String ABOUT_SCREEN =
			"Program - Grafy\n"+
			"Autor - Adam Krizar\n"+
			"Data - 12 listopada 2018\n";
	private static final String INSTRUCTIONS =
			"INSTRUKCUJA PROGRAMU\n\n"+
	"Obs�uga myszk�:\n"+
	"Przeci�ganie\n"+
	"Gdy myszka nad w�z�em lub krawedzi� przesuwanie odpowiedni danego w�z�a\n"+
	"Gdy mysz znajduje si� na pust� plansz� przesuwanie ca�ego grafu\n"+
	"PPM:\n"+
	"Edycja podstawowych parametr�w w�z��w i kraw�dzi gdy myszka znajduje si�\n"+ 
	"nad nimi lub dodawanie nowego w�z�a\n"+
	"DODAWANIE KRAW�DZI:\n"+
	"Po wybraniu odpowiedniej opcji na istniej�cym w�e program przejdze w tryb\n"+ 
	"wyboru drugiego w�z�a. Nalerzy klikn�� kolejny istniej�cy w�ze�";
	
	//Pasek + przyciski
	JMenuBar bar = new JMenuBar();
	JMenu file = new JMenu("Plik");
	JMenu graph = new JMenu("Graf");
	JMenu help = new JMenu("Pomoc");
	//file buttons
	JMenuItem clear = new JMenuItem("Wyczy��");
	JMenuItem save = new JMenuItem("Zapisz");
	JMenuItem load = new JMenuItem("Wczytaj");
	JMenuItem example = new JMenuItem("Domy�lny graf");
	JMenuItem exit = new JMenuItem("Zako�cz program");
	//graph buttons
	JMenuItem nodesList = new JMenuItem("Lista w�z��w");
	JMenuItem edgesList = new JMenuItem("Lista kraw�dzi");
	//help buttons
	JMenuItem about = new JMenuItem("O autorze");
	JMenuItem instructions = new JMenuItem("Instrukcje");
	
	private GraphPanel panel = new GraphPanel(this);
	private Graph currentGraph;
	
	public Editor()
	{
		file.setCursor(cursor);
		graph.setCursor(cursor);
		help.setCursor(cursor);
		clear.setCursor(cursor);
		save.setCursor(cursor);
		load.setCursor(cursor);
		example.setCursor(cursor);
		exit.setCursor(cursor);
		nodesList.setCursor(cursor);
		edgesList.setCursor(cursor);
		about.setCursor(cursor);
		instructions.setCursor(cursor);
		
		setTitle("Grafy");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(550, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		
		clear.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		example.addActionListener(this);
		exit.addActionListener(this);
		nodesList.addActionListener(this);
		edgesList.addActionListener(this);
		about.addActionListener(this);
		instructions.addActionListener(this);
		
		bar.add(file);
		bar.add(graph);
		bar.add(help);
		
		file.add(clear);
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(example);
		file.addSeparator();
		file.add(exit);
		graph.add(nodesList);
		graph.add(edgesList);
		help.add(about);
		help.add(instructions);
		
		deafaultGraph();
		setJMenuBar(bar);
		setContentPane(panel);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object eventSource = event.getSource();
		if(eventSource == exit) dispose();
		if(eventSource == about) JOptionPane.showMessageDialog(this, ABOUT_SCREEN);
		if(eventSource == example) deafaultGraph();
		if(eventSource == clear)
		{
			panel.setGraph(currentGraph = new Graph());
			repaint();
		}
		if(eventSource == save)
		{
			if(currentGraph == null)
			{
				JOptionPane.showMessageDialog(this, "Pusty graf!", "B��d", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String fileName = JOptionPane.showInputDialog("Podaj nazwe pliku");
			if(fileName == null || fileName.equals("")) return;
			Graph.writeObject(fileName, currentGraph, this);
		}
		if(eventSource == load)
		{
			if(JOptionPane.showConfirmDialog(this, "Czy chcesz kontynowa�?", "Ta operacja napisze obecny graf!", 2) != 0) return;
			String fileName = JOptionPane.showInputDialog("Podaj nazwe pliku");
			if(fileName == null || fileName.equals("")) return;
			currentGraph = Graph.readObject(fileName, this);
			if(currentGraph != null) panel.setGraph(currentGraph);
			repaint();
		}
		if(eventSource == nodesList)
		{
			String text ="";
			int i = 1;
			for(BasicNodes node: currentGraph.getNod())
			{
				text = text + i + ". " + node.toString() + "\n";
				i++;
			}
			JOptionPane.showMessageDialog(this, text, "Lista w�z��w", JOptionPane.INFORMATION_MESSAGE);
		}
		if(eventSource == edgesList)
		{
			String text ="";
			int i = 1;
			for(Edges edge: currentGraph.getEdge())
			{
				text = text + i + ". " + edge.toString();
				i++;
			}
			JOptionPane.showMessageDialog(this, text, "Lista w�z��w", JOptionPane.INFORMATION_MESSAGE);
		}
		if(eventSource == instructions) JOptionPane.showMessageDialog(this, INSTRUCTIONS);
	}
	
	private void deafaultGraph()
	{
		currentGraph = new Graph();
		Node d1 = new Node(60,80);
		BasicNodes d2 = new BasicNodes(100,80);
		Node d3 = new Node(275,240);
		Node d4 = new Node(275,400);
		d3.setRR(40);
		d3.setTextColor(Color.WHITE);
		d3.setType(DeviceType.ROUTER);
		d1.setType(DeviceType.COMPUTER);
		d2.setType(DeviceType.COMPUTER);
		d4.setType(DeviceType.COMPUTER);
		d3.setColor(new Color(0, 43, 60));
		d4.setColor(Color.MAGENTA);
		currentGraph.addNode(d1);
		currentGraph.addNode(d2);
		currentGraph.addNode(d3);
		currentGraph.addNode(d4);
		currentGraph.addEdge(new Edges(d3, d2));
		currentGraph.addEdge(new Edges(d3, d4));
		currentGraph.addEdge(new Edges(d3, d1));
		panel.setGraph(currentGraph);
		repaint();
	}
	
}
