/*
 * Klasa przechowuje reprezenrtacje prostych w�z��w w�z��w na p�aszczyznie
 * Plik BasicNodes.java
 * Autor Adam Krizar
 * Data 14.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Enum zawieraj�cy mo�liwe typy urz�dze� reprezentowane przez w�ze�.
 * 
 * Enum zawiera nast�puj�ce elementy:
 * <ul>
 * <li> Konstruktor przypisuj�y nazwy do obiektu enuma
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
enum DeviceType
{
	UNKOWN("Nieznane"),
	ROUTER("Router"),
	SWITCH("Switch"),
	COMPUTER("Komputer"),
	REPEATER("Repeater");
	
	String name;
	
	/**
	 * Metoda wykonywana wewn�trz enuma tworzy jego obiekty
	 * @param String przekazywany do konstruktora
	 */
	private DeviceType(String name)
	{
		this.name = name;
	}
	@Override
	public String toString() 
	{
		return name;
	}
}

/**
 * Klasa reprezentuj�ca podstawowe w�z�y grafu
 * 
 * Klasa zawiera nast�puj�ce elementy:
 * <ul>
 * <li>Typ urz�dzenia reprezentowanego przez w�ze�
 * <li>Ustawianie oraz zwracanie informacji o po�o�eniu grafu
 * <li>Adres IP reprezentowanego urz�dzenia
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p�aszczyznie
 * <li>Definiuje zapis W�z�a przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class BasicNodes implements Serializable
{
	/**
	 * Warto�� SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Parametr x po�o�enia w�z�� 
	 *
	 */
	protected int xx;
	/**
	 * Parametr y po�o�enia w�z�� 
	 * <p>musi by� od 0 i mniejsza od 550</p>
	 */
	protected int yy;
	/**
	 * tablica przechowuj�ca ip urz�dzenia 
	 * <p>ka�da komurka musi by� wi�ksza od 0 i mniejsza od 256</p>
	 */
	protected int ip[] = {0,0,0,0};
	/**
	 * Typ urz�dzenia definiowany w Enumie DeviceType
	 */
	protected DeviceType type;
	
	/**
	 * Konstruktor tworz�cy obiekt w�z�a
	 * 
	 * @param x Wsp�rz�dna x po�o�enia w�z�� musi by� od 0 i mniejsza od 550 
	 * @param y Wsp�rz�dna y po�o�enia w�z�� musi by� od 0 i mniejsza od 550 
	 */
	public BasicNodes(int x, int y)
	{
		this.xx = x;
		this.yy = y;
		this.type = DeviceType.UNKOWN;
	}
	


	public int getXX() {return xx;}
	
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan� wsp�rz�dna y w�z�a
	 */
	public int getYY() {return yy;}
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan� tablice z adresem ip
	 */
	public int[] getIP() {return ip;}
	/**
	 * Getter umo�liwiwaj�cy dost�p do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan� typ urz�dzenia przechowywanego w klasie
	 */
	public DeviceType getType() {return type;}
	
	/**
	 * Setter umo�liwaj�u ustawienie wsp�rz�dnej x przechowywanego we��a
	 * @param x musi by� od 0 i mniejsza od 550
	 */
	public void setXX(int x) {this.xx = x;}
	/**
	 * Setter umo�liwaj�u ustawienie wsp�rz�dnej y przechowywanego we��a
	 * @param y musi by� od 0 i mniejsza od 550
	 */
	public void setYY(int y) {this.yy = y;}
	/**
	 * Setter umo�liwaj�u ustawienie adresu ip obiektu reprezentowanego przez w�ze�
	 * @param ip musi by� d�ugo�ci 4, a dane w niej przechowywane znajdowa� si� w przedziale od 0 do 255
	 */
	public void setIP(int[] ip) {this.ip = ip;}
	/**
	 * Setter umo�liwaj�u ustawienie rodzaju urz�dzenia reprezentowanego przez w�ze�
	 * @param type musi by� definiowanego w enumie DeviceType
	 */
	public void setType(DeviceType type) {this.type = type;}
	/**
	 * Wywo�ywane gdy obiekt jest w�z�em typu prostegop
	 * @return zawsze true gdy� jest to w�ze� typu prostego
	 */
	public boolean isBasic() {return true;}
	/**
	 * 
	 * @param mousex wsp�rz�dne myszki x
	 * @param mousey wsp�lrz�dne myszki y
	 * @return zwraca true lub false w zale�no�ci od tego czy myszka znajduje si� nad w�z�em czy nie.
	 */
	public boolean isMouseOver(int mousex, int mousey)
	{
		if(((xx-4) <= mousex) && (mousex <= (xx+4)) && ((yy-4) <= mousey) && (mousey <= (yy+4))) return true;
		return false;
	}
	/**
	 * Funkcja definiuj�ca spos�b rysowania w�z��
	 * @param gtx parametr reprezentuj�cy grafike
	 */
	void draw(Graphics gtx)
	{
		gtx.setColor(Color.BLACK);
		gtx.fillRect(xx-2, yy-2, 4, 4);
		gtx.setColor(Color.BLACK);
		gtx.drawRect(xx-2, yy-2, 4, 4);
	}
	/**
	 * Funkcja ustawia odpowiedni symbol grafu w zae�no�ci od typu urz�dzenia
	 * @return R,S,K,W,N  w zale�no�ci od rodzaju urz�dzniea
	 */
	protected String setSymbol()
	{
		switch(type)
		{
		case ROUTER: return "R";
		case SWITCH: return "S";
		case COMPUTER: return "K";
		case REPEATER: return "W";
		default: return "N";
		}
	}
	/**
	 * Przedefiniowanie funkcji to string aby umo�liwi� przedstawienie w�z�a w formie tekstowej
	 * @return zwraca tekstow� forme w�z�a
	 */
	@Override
	public String toString()
	{
		return ("("+ setSymbol()+ "," + xx + "," + yy + ")");
	}
}
