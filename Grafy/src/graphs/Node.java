/*
 * Klasa przechowuje reprezenrtacje w�z��w na p�aszczyznie
 * Plik Node.java
 * Autor Adam Krizar
 * Data 12.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * Klasa reprezentuj�ca rozbudowane w�z�y grafu
 * 
 * Klasa zawiera nast�puj�ce elementy:
 * <ul>
 * <li>Promie� w�z�a
 * <li>Kolor w�z�� oraz u�ytej czcionki
 * <li>Rodzaj u�ytej czcionki
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p�aszczyznie
 * <li>Definiuje zapis W�z�a przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Node extends BasicNodes
{
	/**
	 * Warto�� SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Parametr r po�o�enia w�z�� 
	 * <p>musi by� od 20 i mniejszy od 50</p>
	 */
	private int rr;
	/**
	 * Kolor w�z��
	 */
	private Color color;
	/**
	 * Kolor tekstu na wezle (domy�lnie czarny)
	 */
	private Color fontColor = Color.BLACK;
	/**
	 * Rodzaj czcionki u�ywanej na w�zle (nieedtowalne przez u�ytkownika)
	 */
	private Font font = new Font("Aharoni", Font.PLAIN, 25);
	/**
	 * Konstruktor tworz�cy obiekt w�z�a
	 * 
	 * @param x Wsp�rz�dna x po�o�enia w�z�� musi by� od 0 i mniejsza od 550 
	 * @param y Wsp�rz�dna y po�o�enia w�z�� musi by� od 0 i mniejsza od 550 
	 */

	public Node(int x, int y)
	{
		super(x, y);
		this.rr = 20;
		this.color = Color.WHITE;
	}
	
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan� wsp�rz�dna r w�z�a
	 */
	public int getRR() {return rr;}
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywany kolor w�z�a
	 */
	public Color getColor() {return color;}
	/**
	 *Ustawia parametr r nowego w�z�a
	 * @param r Promie� musi by� od 20 do 50 
	 */
	public void setRR(int r) {this.rr = r;}
	/**
	 * Kolor w�z�a
	 * @param c dowolny kolor wynrany przez u�ytkownika
	 */
	public void setColor(Color c) {this.color = c;}
	/**
	 * Kolor w�z�a
	 * @param c dowolny kolor wynrany przez u�ytkownika
	 */
	public void setTextColor(Color c) {this.fontColor = c;}
	/**
	 * Metoda wywo�ywana, s�u�y do rozr�nienia od klasy macierzystej
	 * @return Zawsze warto�� false gdy� jest klasa reprezentuj�c zawansowane w�z�y
	 */
	public boolean isBasic() {return false;}
	/**
	 * 
	 * @param mousex wsp�rz�dne myszki x
	 * @param mousey wsp�lrz�dne myszki y
	 * @return zwraca true lub false w zale�no�ci od tego czy myszka znajduje si� nad w�z�em czy nie
	 */
	public boolean isMouseOver(int mousex, int mousey)
	{
		return (xx - mousex)*(xx - mousex) + (yy - mousey)*(yy - mousey) <= rr*rr;
		//twierdzenie pitagorasa
	}
	/**
	 * Funkcja definiuj�ca spos�b rysowania w�z��
	 * @param gtx parametr reprezentuj�cy grafike
	 */
	void draw(Graphics gtx)
	{
		gtx.setColor(color);
		gtx.fillOval(xx-rr, yy-rr, 2*rr, 2*rr);
		gtx.setColor(Color.BLACK);
		gtx.drawOval(xx-rr, yy-rr, 2*rr, 2*rr);
		gtx.setColor(fontColor);
		gtx.setFont(font);
		gtx.drawString(setSymbol(), xx - 8, yy + 8);
	}
	/**
	 * Przedefiniowanie funkcji to string aby umo�liwi� przedstawienie w�z�a w formie tekstowej
	 * @return zwraca tekstow� forme w�z�a
	 */
	@Override
	public String toString()
	{
		return ("("+ setSymbol()+ "," + xx + "," + yy + "," + rr + ")");
	}
}
