/*
 * Klasa przechowuje reprezenrtacje prostych wêz³ów wêz³ów na p³aszczyznie
 * Plik BasicNodes.java
 * Autor Adam Krizar
 * Data 14.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Enum zawieraj¹cy mo¿liwe typy urz¹dzeñ reprezentowane przez wêze³.
 * 
 * Enum zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li> Konstruktor przypisuj¹y nazwy do obiektu enuma
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
	 * Metoda wykonywana wewn¹trz enuma tworzy jego obiekty
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
 * Klasa reprezentuj¹ca podstawowe wêz³y grafu
 * 
 * Klasa zawiera nastêpuj¹ce elementy:
 * <ul>
 * <li>Typ urz¹dzenia reprezentowanego przez wêze³
 * <li>Ustawianie oraz zwracanie informacji o po³o¿eniu grafu
 * <li>Adres IP reprezentowanego urz¹dzenia
 * <li>Wykorzystanie biblioteki java.awt.Graphics do rysowania grafu na p³aszczyznie
 * <li>Definiuje zapis Wêz³a przy pomocy tekstu
 * </ul>
 * 
 *  @author Adam Krizar
 *  @version 24 listopada 2018 r.
 */
public class BasicNodes implements Serializable
{
	/**
	 * Wartoœæ SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Parametr x po³o¿enia wêz³¹ 
	 *
	 */
	protected int xx;
	/**
	 * Parametr y po³o¿enia wêz³¹ 
	 * <p>musi byæ od 0 i mniejsza od 550</p>
	 */
	protected int yy;
	/**
	 * tablica przechowuj¹ca ip urz¹dzenia 
	 * <p>ka¿da komurka musi byæ wiêksza od 0 i mniejsza od 256</p>
	 */
	protected int ip[] = {0,0,0,0};
	/**
	 * Typ urz¹dzenia definiowany w Enumie DeviceType
	 */
	protected DeviceType type;
	
	/**
	 * Konstruktor tworz¹cy obiekt wêz³a
	 * 
	 * @param x Wspó³rzêdna x po³o¿enia wêz³¹ musi byæ od 0 i mniejsza od 550 
	 * @param y Wspó³rzêdna y po³o¿enia wêz³¹ musi byæ od 0 i mniejsza od 550 
	 */
	public BasicNodes(int x, int y)
	{
		this.xx = x;
		this.yy = y;
		this.type = DeviceType.UNKOWN;
	}
	


	public int getXX() {return xx;}
	
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan¹ wspó³rzêdna y wêz³a
	 */
	public int getYY() {return yy;}
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan¹ tablice z adresem ip
	 */
	public int[] getIP() {return ip;}
	/**
	 * Getter umo¿liwiwaj¹cy dostêp do zmiennych przechowywanych w klasie
	 * @return zwraca przechowywan¹ typ urz¹dzenia przechowywanego w klasie
	 */
	public DeviceType getType() {return type;}
	
	/**
	 * Setter umo¿liwaj¹u ustawienie wspó³rzêdnej x przechowywanego we¿³a
	 * @param x musi byæ od 0 i mniejsza od 550
	 */
	public void setXX(int x) {this.xx = x;}
	/**
	 * Setter umo¿liwaj¹u ustawienie wspó³rzêdnej y przechowywanego we¿³a
	 * @param y musi byæ od 0 i mniejsza od 550
	 */
	public void setYY(int y) {this.yy = y;}
	/**
	 * Setter umo¿liwaj¹u ustawienie adresu ip obiektu reprezentowanego przez wêze³
	 * @param ip musi byæ d³ugoœci 4, a dane w niej przechowywane znajdowaæ siê w przedziale od 0 do 255
	 */
	public void setIP(int[] ip) {this.ip = ip;}
	/**
	 * Setter umo¿liwaj¹u ustawienie rodzaju urz¹dzenia reprezentowanego przez wêze³
	 * @param type musi byæ definiowanego w enumie DeviceType
	 */
	public void setType(DeviceType type) {this.type = type;}
	/**
	 * Wywo³ywane gdy obiekt jest wêz³em typu prostegop
	 * @return zawsze true gdy¿ jest to wêze³ typu prostego
	 */
	public boolean isBasic() {return true;}
	/**
	 * 
	 * @param mousex wspó³rzêdne myszki x
	 * @param mousey wspólrzêdne myszki y
	 * @return zwraca true lub false w zale¿noœci od tego czy myszka znajduje siê nad wêz³em czy nie.
	 */
	public boolean isMouseOver(int mousex, int mousey)
	{
		if(((xx-4) <= mousex) && (mousex <= (xx+4)) && ((yy-4) <= mousey) && (mousey <= (yy+4))) return true;
		return false;
	}
	/**
	 * Funkcja definiuj¹ca sposób rysowania wêz³¹
	 * @param gtx parametr reprezentuj¹cy grafike
	 */
	void draw(Graphics gtx)
	{
		gtx.setColor(Color.BLACK);
		gtx.fillRect(xx-2, yy-2, 4, 4);
		gtx.setColor(Color.BLACK);
		gtx.drawRect(xx-2, yy-2, 4, 4);
	}
	/**
	 * Funkcja ustawia odpowiedni symbol grafu w zae¿noœci od typu urz¹dzenia
	 * @return R,S,K,W,N  w zale¿noœci od rodzaju urz¹dzniea
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
	 * Przedefiniowanie funkcji to string aby umo¿liwiæ przedstawienie wêz³a w formie tekstowej
	 * @return zwraca tekstow¹ forme wêz³a
	 */
	@Override
	public String toString()
	{
		return ("("+ setSymbol()+ "," + xx + "," + yy + ")");
	}
}
