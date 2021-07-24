/*
 * Klasa Graph przechowuje informacje o grafie (wêz³y po³¹czenia itd.)
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
 * Klasa reprezentuj¹ca graf
 * 
 * Klasa zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li>Lista wszytkich wêz³o w grafie
 * <li>Lista wszytkich krawêdzi w grafie
 * <li>Funkcje umo¿liwiaj¹ce dodawanie nowych wêz³ów i krawêdzi
 * <li>Funkcja wywo³uj¹ca metode draw dla ka¿dego wêz³a/krawêdzi 
 * <li>Funkcja zapisu i odczytu grafu do pliku
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Graph implements Serializable 
{
	/**
	 * Wartoœæ SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lisat wszytkich wêz³ów
	 */
	private List<BasicNodes> nod;
	/**
	 * Lista wszytkich krawêdzi
	 */
	private List<Edges> edge;
	/**
	 * Konstruktor torz¹cy obiekt klasy
	 */
	public Graph()
	{
		this.nod = new ArrayList<BasicNodes>();
		this.edge = new ArrayList<Edges>();
	}
	
	/**
	 * Metoda dodaj¹ca nowy wêze³ do listy
	 * @param node wêze³ typu Basic lub normalny
	 */
	public void addNode(BasicNodes node){nod.add(node);}
	/**
	 * Metoda usuwaj¹ca wêz³y z listy (usuwa tak¿e wszystkie krawêdzie po³¹czone z t¹ metod¹)
	 * @param node wêze³ typu Basic lub normalny
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
	 * Metoda dodaj¹ca now¹ krawêdz
	 * @param edge Zmienna klasy Edges
	 */
	public void addEdge(Edges edge){this.edge.add(edge);}
	/**
	 * Metoda usuwuj¹ca istniej¹c¹ krawêdz
	 * @param edge Zmienna klasy Edges
	 */
	public void removeEdge(Edges edge){this.edge.remove(edge);}
	/**
	 * Metoda zwracj¹ca wszytkie wêz³y w formie tablcy
	 * @return Tablica zawieraj¹ca wszytkie wêz³y grafu
	 */
	public BasicNodes[] getNod()
	{
		BasicNodes [] arr = new BasicNodes[0];
		return nod.toArray(arr);
	}
	/**
	 * Metoda zwracj¹ca wszytkie krawêdzie w formie tablcy
	 * @return Tablica zawieraj¹ca wszytkie krawêdzie grafu
	 */
	public Edges[] getEdge()
	{
		Edges [] arr = new Edges[0];
		return edge.toArray(arr);
	}
	/**
	 * Metoda wywy³uj¹ca funkcje draw() dla ka¿dego wêz³a/krawêdzi
	 * @param gtx parametr reprezentuj¹cy grafike
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
	 * Metoda zapisuj¹ca graf do pliku
	 * @param filename nazwa pliku docelowego
	 * @param graph zapisywany graf
	 * @param parent Okno z którego wywo³ana zosta³a ta metoda
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
			JOptionPane.showMessageDialog(parent, "B³¹d zapisu pliku", "B³¹d", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(parent, "Zapisano pomyœlnie");
	}
	
	/**
	 * Metoda odczytuj¹ca graf z pliku
	 * @param filename Nazwa otwieranego pliku
	 * @param parent Okno z którego wywo³ana zosta³a ta metoda
	 * @return Graf, który zosat³ odczytany z pliku lub w przypadku niepowdzenia wartoœæ null
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
			JOptionPane.showMessageDialog(parent, "Nie znaleziono pliku " + filename, "B³¹d", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(IOException error)
		{
			JOptionPane.showMessageDialog(parent, "B³¹d odczytu pliku", "B³¹d", JOptionPane.ERROR_MESSAGE);
			return null ;
		} 
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
			return null;
		}
		JOptionPane.showMessageDialog(parent, "Wczytano pomyœlnie");
		return temp;
	}
}
