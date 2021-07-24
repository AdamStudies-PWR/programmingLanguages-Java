/*
 * Klasa przechowuje reprezenrtacje obiektów p³aszczyznie
 * Plik Node.java
 * Autor Adam Krizar
 * Data 29 listopada 2018
 */
package animation;

import java.awt.Color;
import java.awt.Graphics;

public class Circle 
{
	protected int xx;
	protected int yy;
	private int rr;
	private Color color;
	
	public Circle(int x, int y, int r, Color color)
	{
		this.xx = x;
		this.yy = y;
		this.rr = r;
		this.color = color;
	}
	public int getXX() {return xx;}
	public int getYY() {return yy;}
	public int getRR() {return rr;}
	
	public void setRR(int rr) {this.rr = rr;}
	public void setXX(int xx) {this.xx = xx;}
	public void setYY(int yy) {this.yy = yy;}
	public void setColor(Color color) {this.color = color;}
	
	void draw(Graphics gtx)
	{
		gtx.setColor(color);
		gtx.fillOval(xx-rr, yy-rr, 2*rr, 2*rr);
		gtx.setColor(Color.BLACK);
		gtx.drawOval(xx-rr, yy-rr, 2*rr, 2*rr);
	}
}
