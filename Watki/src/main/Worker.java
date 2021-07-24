/*
 * Program: Plik zawiera trzy klasy s³u¿¹ce do realizacji problemu Pracowników i konsumentów
 * Plik Worker.java
 * Autor Adam Krizar
 * Data 26 listopada 2018
 */
package main;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTextArea;

public abstract class Worker extends Square implements Runnable
{
	public Worker(int number, int i, String s) {super(number, i, s);}
	protected String name;
	protected Buffer buffer;
	protected JTextArea area;
	protected InformationPanel painting;
	
	public String getName() {return name;}

	public static void sleep(int msek)
	{
		try
		{
			Thread.sleep(msek);
		}
		catch (InterruptedException error) {}
	}
	public static void sleep(int min_msek, int max_msek)
	{
		sleep(ThreadLocalRandom.current().nextInt(min_msek, max_msek));
	}
	static int itemID = 0;
	//Producent
	public static int MIN_PRODUCER_TIME = 100;
	public static int MAX_PRODUCER_TIME = 1000;
	//Konsument
	public static int MIN_CONSUMER_TIME = 100;
	public static int MAX_CONSUMER_TIME = 1000;
}

class Producer extends Worker
{
	private boolean paused = false;
	
	public Producer(String name, Buffer buffer, JTextArea area, int number, InformationPanel painting, int i, String s)
	{ 
		super(number,i , s);
		this.name = name;
		this.buffer = buffer;
		this.area = area;
		this.painting = painting;
	}
	
	public void pause() {paused = !paused;}
	
	@Override
	public void run() 
	{
		int item;
		while(true)
		{
			while(paused)
			{
				try
				{
					Thread.sleep(5L);
				}
				catch (InterruptedException error) {}
			}
			item = itemID++;
			area.append("Producent " + name + " produkuje: " + item + "\n");
			this.setColor(Color.GREEN);
			painting.repaint();
			area.setCaretPosition(area.getDocument().getLength());
			sleep(MIN_PRODUCER_TIME, MAX_PRODUCER_TIME);
			buffer.put(this, item);
		}
	}
}

class Consumer extends Worker
{
	private boolean paused = false;
	
	public Consumer(String name, Buffer buffer, JTextArea area, int number, InformationPanel painting, int i, String s)
	{ 
		super(number, i, s);
		this.name = name;
		this.buffer = buffer;
		this.area = area;
		this.painting = painting;
	}
	
	public void pause() {paused = !paused;}
	
	@Override
	public void run() 
	{
		int item;
		while(true)
		{
			while(paused)
			{
				try
				{
					Thread.sleep(5L);
				}
				catch (InterruptedException error) {}
			}
			item = buffer.get(this);
			area.append("Konsument " + name + " konsumuje: " + item + "\n");
			this.setColor(Color.GREEN);
			painting.repaint();
			area.setCaretPosition(area.getDocument().getLength());
			sleep(MIN_CONSUMER_TIME, MAX_CONSUMER_TIME);
		}
	}
}