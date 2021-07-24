/*
 * Klasa Graph przechowuje informacje o grafie (w�z�y po��czenia itd.)
 * Autor Adam Krizar
 * Data 13.11.2018
 */
package graphs;

import java.awt.Graphics;
import java.awt.Window;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
/**
 * Klasa reprezentuj�ca graf
 * 
 * Klasa zawiera nast�puj�ce elementy:
 * <ul>
 * <li>Lista wszytkich w�z�o w grafie
 * <li>Lista wszytkich kraw�dzi w grafie
 * <li>Funkcje umo�liwiaj�ce dodawanie nowych w�z��w i kraw�dzi
 * <li>Funkcja wywo�uj�ca metode draw dla ka�dego w�z�a/kraw�dzi 
 * <li>Funkcja zapisu i odczytu grafu do pliku
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Graph implements Serializable 
{
	/**
	 * Warto�� SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lisat wszytkich w�z��w
	 */
	private List<BasicNodes> nod;
	/**
	 * Lista wszytkich kraw�dzi
	 */
	private List<Edges> edge;
	/**
	 * Konstruktor torz�cy obiekt klasy
	 */
	public Graph()
	{
		this.nod = new ArrayList<BasicNodes>();
		this.edge = new ArrayList<Edges>();
	}
	
	/**
	 * Metoda dodaj�ca nowy w�ze� do listy
	 * @param node w�ze� typu Basic lub normalny
	 */
	public void addNode(BasicNodes node){nod.add(node);}
	/**
	 * Metoda usuwaj�ca w�z�y z listy (usuwa tak�e wszystkie kraw�dzie po��czone z t� metod�)
	 * @param node w�ze� typu Basic lub normalny
	 */
	public void removeNode(BasicNodes node)
	{
		nod.remove(node);
		List<Edges> tedge = new ArrayList<Edges>();
		for(Edges edge: this.edge)
		{
			BasicNodes[] arr = edge.getNodes();
			if(arr[0].equals(node) || arr[1].equals(node)) tedge.add(edge); 
		}
		for(Edges edge: tedge)
		{
			removeEdge(edge);
		}
	}
	/**
	 * Metoda dodaj�ca now� kraw�dz
	 * @param edge Zmienna klasy Edges
	 */
	public void addEdge(Edges edge){this.edge.add(edge);}
	/**
	 * Metoda usuwuj�ca istniej�c� kraw�dz
	 * @param edge Zmienna klasy Edges
	 */
	public void removeEdge(Edges edge){this.edge.remove(edge);}
	/**
	 * Metoda zwracj�ca wszytkie w�z�y w formie tablcy
	 * @return Tablica zawieraj�ca wszytkie w�z�y grafu
	 */
	public BasicNodes[] getNod()
	{
		BasicNodes [] arr = new BasicNodes[0];
		return nod.toArray(arr);
	}
	/**
	 * Metoda zwracj�ca wszytkie kraw�dzie w formie tablcy
	 * @return Tablica zawieraj�ca wszytkie kraw�dzie grafu
	 */
	public Edges[] getEdge()
	{
		Edges [] arr = new Edges[0];
		return edge.toArray(arr);
	}
	/**
	 * Metoda wywy�uj�ca funkcje draw() dla ka�dego w�z�a/kraw�dzi
	 * @param gtx parametr reprezentuj�cy grafike
	 */
	public void draw(Graphics gtx)
	{
		for(Edges edge: this.edge)
		{
			edge.draw(gtx);
		}
		for(BasicNodes node: nod)
		{
			node.draw(gtx);
		}
	}
	
	/**
	 * Metoda zapisuj�ca graf do pliku
	 * @param filename nazwa pliku docelowego
	 * @param graph zapisywany graf
	 * @param parent Okno z kt�rego wywo�ana zosta�a ta metoda
	 */
	public static void writeObject(String filename, Graph graph, Window parent)
	{
		try
		(
				FileOutputStream writer = new FileOutputStream(filename);
				ObjectOutputStream obj = new ObjectOutputStream(writer)
		)
		{
			obj.writeObject(graph);
		}
		catch(IOException error)
		{
			JOptionPane.showMessageDialog(parent, "B��d zapisu pliku", "B��d", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(parent, "Zapisano pomy�lnie");
	}
	
	/**
	 * Metoda odczytuj�ca graf z pliku
	 * @param filename Nazwa otwieranego pliku
	 * @param parent Okno z kt�rego wywo�ana zosta�a ta metoda
	 * @return Graf, kt�ry zosat� odczytany z pliku lub w przypadku niepowdzenia warto�� null
	 */
	public static Graph readObject(String filename, Window parent)
	{
		Graph temp;
		try
		(
				FileInputStream reader = new FileInputStream(filename);
				ObjectInputStream obj = new ObjectInputStream(reader);
		)
		{
			temp = (Graph) obj.readObject();
		}
		catch(FileNotFoundException error)
		{
			JOptionPane.showMessageDialog(parent, "Nie znaleziono pliku " + filename, "B��d", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(IOException error)
		{
			JOptionPane.showMessageDialog(parent, "B��d odczytu pliku", "B��d", JOptionPane.ERROR_MESSAGE);
			return null ;
		} 
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
			return null;
		}
		JOptionPane.showMessageDialog(parent, "Wczytano pomy�lnie");
		return temp;
	}
}
