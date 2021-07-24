/*
 * Klasa przechowuje reprezenrtacje wêz³ów na p³aszczyznie
 * Plik Node.java
 * Autor Adam Krizar
 * Data 12.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * Klasa reprezentuj¹ca rozbudowane wêz³y grafu
 * 
 * Klasa zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li>Promieñ wêz³a
 * <li>Kolor wêz³¹ oraz u¿ytej czcionki
 * <li>Rodzaj u¿ytej czcionki
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p³aszczyznie
 * <li>Definiuje zapis Wêz³a przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class Node extends BasicNodes
{
	/**
	 * Wartoœæ SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Parametr r po³o¿enia wêz³¹ 
	 * <p>musi byæ od 20 i mniejszy od 50</p>
	 */
	private int rr;
	/**
	 * Kolor wêz³¹
	 */
	private Color color;
	/**
	 * Kolor tekstu na wezle (domyœlnie czarny)
	 */
	private Color fontColor = Color.BLACK;
	/**
	 * Rodzaj czcionki u¿ywanej na wêzle (nieedtowalne przez u¿ytkownika)
	 */
	private Font font = new Font("Aharoni", Font.PLAIN, 25);
	/**
	 * Konstruktor tworz¹cy obiekt wêz³a
	 * 
	 * @param x Wspó³rzêdna x po³o¿enia wêz³¹ musi byæ od 0 i mniejsza od 550 
	 * @param y Wspó³rzêdna y po³o¿enia wêz³¹ musi byæ od 0 i mniejsza od 550 
	 */

	public Node(int x, int y)
	{
		super(x, y);
		this.rr = 20;
		this.color = Color.WHITE;
	}
	
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan¹ wspó³rzêdna r wêz³a
	 */
	public int getRR() {return rr;}
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywany kolor wêz³a
	 */
	public Color getColor() {return color;}
	/**
	 *Ustawia parametr r nowego wêz³a
	 * @param r Promieñ musi byæ od 20 do 50 
	 */
	public void setRR(int r) {this.rr = r;}
	/**
	 * Kolor wêz³a
	 * @param c dowolny kolor wynrany przez u¿ytkownika
	 */
	public void setColor(Color c) {this.color = c;}
	/**
	 * Kolor wêz³a
	 * @param c dowolny kolor wynrany przez u¿ytkownika
	 */
	public void setTextColor(Color c) {this.fontColor = c;}
	/**
	 * Metoda wywo³ywana, s³u¿y do rozró¿nienia od klasy macierzystej
	 * @return Zawsze wartoœæ false gdy¿ jest klasa reprezentuj¹c zawansowane wêz³y
	 */
	public boolean isBasic() {return false;}
	/**
	 * 
	 * @param mousex wspó³rzêdne myszki x
	 * @param mousey wspólrzêdne myszki y
	 * @return zwraca true lub false w zale¿noœci od tego czy myszka znajduje siê nad wêz³em czy nie
	 */
	public boolean isMouseOver(int mousex, int mousey)
	{
		return (xx - mousex)*(xx - mousex) + (yy - mousey)*(yy - mousey) <= rr*rr;
		//twierdzenie pitagorasa
	}
	/**
	 * Funkcja definiuj¹ca sposób rysowania wêz³¹
	 * @param gtx parametr reprezentuj¹cy grafike
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
	 * Przedefiniowanie funkcji to string aby umo¿liwiæ przedstawienie wêz³a w formie tekstowej
	 * @return zwraca tekstow¹ forme wêz³a
	 */
	@Override
	public String toString()
	{
		return ("("+ setSymbol()+ "," + xx + "," + yy + "," + rr + ")");
	}
}
