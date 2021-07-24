/*
 * Klasa przechowuje reprezenrtacje krawêdzi na p³aszczyie
 * Plik Edges.java
 * Autor Adam Krizar
 * Data 14.11.2018
 */
package graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.lang.Math.*;
/**
 * Enum zawieraj¹cy mo¿liwe typy po³¹czeñ reprezentowane przez krawêdz.
 * 
 * Enum zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li> Konstruktor przypisuj¹y nazwy do obiektu enuma
 * <li> Przedefiniowanie metody to String
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
enum ConnectionType
{
	WIFI("Bezprzewodowe"),
	WIRED("Przewodowe"),
	UNKOWN("Inne");
	
	String name;
	
	/**
	 * Metoda wykonywana wewn¹trz enuma tworzy jego obiekty
	 * @param String przekazywany do konstruktora
	 */
	private ConnectionType(String name)
	{
		this.name = name;
	}
	
	/**
	 * Przedefiniowanie funkcji to string aby umo¿liwiæ przedstawienie typu w formie tekstowej
	 * @return zwraca tekstow¹ forme wynranego typu
	 */
	@Override
	public String toString() 
	{
		return name;
	}
}

/**
 * Klasa reprezentuj¹ca krawêdzie grafu
 * 
 * Klasa zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li>Typ po³¹czenia reprezentowanego przez krawêdz
 * <li>Ustawianie gruboœci lini, jej koloru oraz typu
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p³aszczyznie
 * <li>Definiuje zapis Krawêdzi przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Edges implements Serializable
{
	/**
	 * Wartoœæ SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pierwszy wêze³ który ³¹czy krawêdz
	 *  <p>musi byæ ró¿ne od n2</p>
	 */
	private BasicNodes n1;
	/**
	 * Drugi wêze³ który ³¹czy krawêdz
	 *  <p>musi byæ ró¿ne od n1</p>
	 */
	private BasicNodes n2;
	/**
	 * Typ po³¹czenia definiowany w Enumie ConnectionType
	 */
	private ConnectionType type = ConnectionType.UNKOWN;
	/**
	 * Kolor krawêdzi 
	 * <p>Domyœlnie czarny</p>
	 */
	private Color color = Color.BLACK;
	/**
	 * Gruboœæ krawêdzi 
	 * <p>Domyœlnie czarny</p>
	 */
	private int width = 2;
	
	/**
	 * Konstruktor tworz¹cy obiekt krawêdzi <p>Domyœlnie od drugiego</p>
	 * @param n1 Parametr pierwszego wêz³ <p>Domyœlnie od drugiego</p>
	 * @param n2 Parametr drugiego wêz³a<p>Domyœlnie od pierwszego</p>
	 */
	public Edges(BasicNodes n1, BasicNodes n2) 
	{
		this.n1 = n1;
		this.n2 = n2;
	}
	
	/**
	 * Getter umozliwiaj¹cy dostêp do zapisanych wêz³ów
	 * @return zwraca tablice w której przekazywane s¹ oba parametry wêz³ów
	 */
	public BasicNodes[] getNodes()
	{
		BasicNodes [] arr = new BasicNodes[2]; 
		arr [0] = n1; arr [1] = n2;
		return arr;
	}
	/**
	 * Setter umozliwaj¹cy zmiane parametrów koloru krawêdzi
	 * @param color Dowolny kolor wybrany przez u¿ytkownika
	 */
	public void setColor(Color color) {this.color = color;}
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return Zwraca przechowywany kolor
	 */
	public Color getColor() {return color;}
	
	/**
	 * Setter umo¿liwaj¹cy ustawienie gruboœci lini krawêdzi
	 * @param stroke wbrane przez u¿ytkownika z zakresu 1 do 5 
	 */
	public void setStroke(int stroke) {width = stroke;}
	
	/**
	 * Setter umo¿liwiaj¹cy ustawienie typu po³¹czenia
	 * @param type Typ po³¹czenia definiowany w enumie ConnectionType
	 */
	public void setType(ConnectionType type) {this.type = type;}
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return Zwraca przechowywany typ po³¹czenia
	 */
	public ConnectionType getType() {return type;}
	
	/**
	 * 
	 * @param mousex wspó³rzêdne myszki x
	 * @param mousey wspólrzêdne myszki y
	 * @return zwraca true lub false w zale¿noœci od tego czy myszka znajduje siê nad krawêdzi¹ czy nie.
	 */
	public boolean isMouseOver(int mousex, int mousey)
	{
		double mx = (double)mousex;
		double my = (double)mousey;
		double nsx = (double)n1.xx;
		double nsy = (double)n1.yy;
		double nex = (double)n2.xx;
		double ney = (double)n2.yy;
		
		double AC = Math.sqrt(Math.pow(mx, nsx) + Math.pow(my, nsy));
		double BC = Math.sqrt(Math.pow(mx, nex) + Math.pow(my, ney));
		double AB = Math.sqrt(Math.pow(nsx, nex) + Math.pow(nsy, ney));
		
		System.out.print("AC: " + AC + ", BC: " + BC + ", AB: " + AB + "\n");
		
		//return AC + BC == AB;
		return false;
	}
	
	/**
	 * Funkcja definiuj¹ca sposób rysowania wêz³¹
	 * @param gtx parametr reprezentuj¹cy grafike
	 */
	void draw(Graphics gtx)
	{
		gtx.setColor(color);
		Graphics2D rtx = (Graphics2D) gtx;
		rtx.setStroke(new BasicStroke(width));
		rtx.drawLine(n1.getXX(), n1.getYY(), n2.getXX(), n2.getYY());
		rtx.setStroke(new BasicStroke(1));
	}
	/**
	 * Przedefiniowanie funkcji to string aby umo¿liwiæ przedstawienie krawêdzi w formie tekstowej
	 * @return zwraca tekstow¹ forme krawêdzi
	 */
	@Override
	public String toString()
	{
		return ("(" + type.toString() + ", " + n1.toString() + "=>" + n2.toString() + ")\n");
	}
}
