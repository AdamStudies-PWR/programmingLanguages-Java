package main;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class Buffer
{
	private ArrayList<Integer> contents = new ArrayList<Integer>();
	private ArrayList<Square> drawable = new ArrayList<Square>();
	private JTextArea area;
	private int size;
	private int busy = 0;
	private InformationPanel painting;
	public ArrayList<Square> getDrawable() {return drawable;}
	
	private synchronized void draw()
	{
		if(busy == 0)
		{
			for(Square square: drawable)
			{
				square.setColor(Color.BLUE);
			}
		}
		else if(busy == size)
		{
			for(Square square: drawable)
			{
				square.setColor(Color.RED);
			}
		}
		else if(busy == (size-1))
		{
			for(int i=0; i<busy; i++)
			{
				drawable.get(i).setColor(Color.YELLOW);
			}
			if(size != 1 && size != 5)drawable.get(busy + 1).setColor(Color.BLUE);
			else drawable.get(busy).setColor(Color.BLUE);
		}
		else
		{
			int i=0;
			for(; i<busy; i++)
			{
				drawable.get(i).setColor(Color.GREEN);
			}
			for(; i<size; i++)
			{
				drawable.get(i).setColor(Color.BLUE);
			}
		}
	}
	
	public Buffer(JTextArea area, int size, InformationPanel painting)
	{
		this.area = area;
		this.size = size;
		this.painting = painting;
		for(int i=0; i<5; i++)
		{
			drawable.add(new Square(size, i, "B"));
		}
	}
	
	public synchronized int get(Consumer consumer)
	{
		area.append("Konsument " + consumer.getName() + " chce zabraæ\n");
		consumer.setColor(Color.YELLOW);
		painting.repaint();
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException error) {}
		area.setCaretPosition(area.getDocument().getLength());
		while (busy == 0)
		{
			try
			{
				area.append("Konsument " + consumer.getName() + " bufor pusty - czekam\n");
				consumer.setColor(Color.RED);
				painting.repaint();
				area.setCaretPosition(area.getDocument().getLength());
				wait();
			}
			catch(InterruptedException error) {}
		}
		int item = contents.get(contents.size()-1);
		contents.remove(contents.size()-1);
		busy--;
		draw();
		painting.repaint();
		try 
		{
			Thread.sleep(100);
		}
		catch (InterruptedException error) {}
		area.append("Konsument " + consumer.getName() + " zabra³: " + item + "\n");
		area.setCaretPosition(area.getDocument().getLength());
		notifyAll();
		return item;
	}
	
	public synchronized void put(Producer producer, int item)
	{
		area.append("Producent " + producer.getName() + " chce oddaæ: " + item + "\n");
		producer.setColor(Color.YELLOW);
		painting.repaint();
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException error) {}
		area.setCaretPosition(area.getDocument().getLength());
		while(busy == size)
		{
			try
			{
				area.append("Producent " + producer.getName() + " bufor pe³ny - czekam\n");
				producer.setColor(Color.RED);
				painting.repaint();
				area.setCaretPosition(area.getDocument().getLength());
				wait();
			}
			catch(InterruptedException error) {}
		}
		contents.add(item);
		busy++;
		draw();
		painting.repaint();
		try 
		{
			Thread.sleep(100);
		}
		catch (InterruptedException error) {}
		area.append("Producent " + producer.getName() + " odda³: " + item + "\n");
		area.setCaretPosition(area.getDocument().getLength());
		notifyAll();
	}


}
