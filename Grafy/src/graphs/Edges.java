/*
 * Klasa przechowuje reprezenrtacje kraw�dzi na p�aszczyie
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
 * Enum zawieraj�cy mo�liwe typy po��cze� reprezentowane przez kraw�dz.
 * 
 * Enum zawiera nast�puj�ce elementy:
 * <ul>
 * <li> Konstruktor przypisuj�y nazwy do obiektu enuma
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
	 * Metoda wykonywana wewn�trz enuma tworzy jego obiekty
	 * @param String przekazywany do konstruktora
	 */
	private ConnectionType(String name)
	{
		this.name = name;
	}
	
	/**
	 * Przedefiniowanie funkcji to string aby umo�liwi� przedstawienie typu w formie tekstowej
	 * @return zwraca tekstow� forme wynranego typu
	 */
	@Override
	public String toString() 
	{
		return name;
	}
}

/**
 * Klasa reprezentuj�ca kraw�dzie grafu
 * 
 * Klasa zawiera nast�puj�ce elementy:
 * <ul>
 * <li>Typ po��czenia reprezentowanego przez kraw�dz
 * <li>Ustawianie grubo�ci lini, jej koloru oraz typu
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p�aszczyznie
 * <li>Definiuje zapis Kraw�dzi przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Edges implements Serializable
{
	/**
	 * Warto�� SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pierwszy w�ze� kt�ry ��czy kraw�dz
	 *  <p>musi by� r�ne od n2</p>
	 */
	private BasicNodes n1;
	/**
	 * Drugi w�ze� kt�ry ��czy kraw�dz
	 *  <p>musi by� r�ne od n1</p>
	 */
	private BasicNodes n2;
	/**
	 * Typ po��czenia definiowany w Enumie ConnectionType
	 */
	private ConnectionType type = ConnectionType.UNKOWN;
	/**
	 * Kolor kraw�dzi 
	 * <p>Domy�lnie czarny</p>
	 */
	private Color color = Color.BLACK;
	/**
	 * Grubo�� kraw�dzi 
	 * <p>Domy�lnie czarny</p>
	 */
	private int width = 2;
	
	/**
	 * Konstruktor tworz�cy obiekt kraw�dzi <p>Domy�lnie od drugiego</p>
	 * @param n1 Parametr pierwszego w�z� <p>Domy�lnie od drugiego</p>
	 * @param n2 Parametr drugiego w�z�a<p>Domy�lnie od pierwszego</p>
	 */
	public Edges(BasicNodes n1, BasicNodes n2) 
	{
		this.n1 = n1;
		this.n2 = n2;
	}
	
	/**
	 * Getter umozliwiaj�cy dost�p do zapisanych w�z��w
	 * @return zwraca tablice w kt�rej przekazywane s� oba parametry w�z��w
	 */
	public BasicNodes[] getNodes()
	{
		BasicNodes [] arr = new BasicNodes[2]; 
		arr [0] = n1; arr [1] = n2;
		return arr;
	}
	/**
	 * Setter umozliwaj�cy zmiane parametr�w koloru kraw�dzi
	 * @param color Dowolny kolor wybrany przez u�ytkownika
	 */
	public void setColor(Color color) {this.color = color;}
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return Zwraca przechowywany kolor
	 */
	public Color getColor() {return color;}
	
	/**
	 * Setter umo�liwaj�cy ustawienie grubo�ci lini kraw�dzi
	 * @param stroke wbrane przez u�ytkownika z zakresu 1 do 5 
	 */
	public void setStroke(int stroke) {width = stroke;}
	
	/**
	 * Setter umo�liwiaj�cy ustawienie typu po��czenia
	 * @param type Typ po��czenia definiowany w enumie ConnectionType
	 */
	public void setType(ConnectionType type) {this.type = type;}
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return Zwraca przechowywany typ po��czenia
	 */
	public ConnectionType getType() {return type;}
	
	/**
	 * 
	 * @param mousex wsp�rz�dne myszki x
	 * @param mousey wsp�lrz�dne myszki y
	 * @return zwraca true lub false w zale�no�ci od tego czy myszka znajduje si� nad kraw�dzi� czy nie.
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
	 * Funkcja definiuj�ca spos�b rysowania w�z��
	 * @param gtx parametr reprezentuj�cy grafike
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
	 * Przedefiniowanie funkcji to string aby umo�liwi� przedstawienie kraw�dzi w formie tekstowej
	 * @return zwraca tekstow� forme kraw�dzi
	 */
	@Override
	public String toString()
	{
		return ("(" + type.toString() + ", " + n1.toString() + "=>" + n2.toString() + ")\n");
	}
}
