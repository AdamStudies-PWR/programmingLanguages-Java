/*
 * Program: Klasa reprezentuj¹ca bufor w którym przechowyane s¹ "produkty producentów"
 * Plik Buffer.java
 * Autor Adam Krizar
 * Data 29 listopada 2018
 */
package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Buffer
{
	Circle inner;
	Circle outer;
	private ArrayList<Integer> contents = new ArrayList<Integer>();
	private final int size = 4;
	private int busy = 0;
	private final int step = 25;
	private boolean taken = false;
	
	public Buffer()
	{
		inner = new Circle(265, 255, 0, Color.DARK_GRAY);
		outer = new Circle(265 ,255 , 100, Color.WHITE);
	}
	
	public synchronized void setTaken(boolean taken) {this.taken = taken;}
	public synchronized boolean getTaken() {return taken;}
	
	public synchronized boolean canTake()
	{
		if(busy == size) return false;
		else return true;
	}
	
	public boolean canGive()
	{
		if(busy == 0) return false;
		else return true;
	}
	
	public boolean isTouchingBuffer(int xx, int yy, int rr)
	{
		double location =  Math.sqrt((xx - 265) * (xx - 265) + (yy - 255) * (yy - 255));
		if (location < 100 + rr) return true;
		else return false;
	}
	public boolean isInside(int xx, int yy, int rr)
	{
		double location =  Math.sqrt((xx - 265) * (xx - 265) + (yy - 255) * (yy - 255));
		if (location < 100 - rr) return true;
		else return false;
	}
	
	public boolean isOutsideBuffer(int xx, int yy, int rr) 
	{
	    double location = Math.sqrt((xx - 265) * (xx - 265) + (yy - 255) * (yy - 255));
	    if (location > 100 + rr) return true;
	    else return false;
	  }
	
	public void waiting(Worker obj)
	{
		int i=0;
		while (taken)
		{
			i++;
			Worker.sleep(15);
			if(i == 200) return;
		}
	}
	
	public void draw(Graphics gtx)
	{
		outer.draw(gtx);
		inner.draw(gtx);
	}
	
	public synchronized int get(Consumer consumer)
	{
		inner.setRR(inner.getRR() - step);
		int item = contents.get(contents.size()-1);
		contents.remove(contents.size()-1);
		busy--;
		notifyAll();
		return item;
	}
	
	public synchronized void put(Producer producer, int item)
	{
		inner.setRR(inner.getRR() + step);
		contents.add(item);
		busy++;
		notifyAll();
	}


}
