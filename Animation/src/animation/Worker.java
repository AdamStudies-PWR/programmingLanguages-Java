/*
 * Program: Plik zawiera trzy klasy s³u¿¹ce do realizacji problemu Pracowników i konsumentów
 * Plik Worker.java
 * Autor Adam Krizar
 * Data 29 listopada 2018
 */
package animation;

import java.awt.Color;
import java.util.Random;

public abstract class Worker extends Circle implements Runnable
{
	protected Buffer buffer;
	protected Random generator = new Random();
	protected AnimationPanel painting;
	protected boolean inside = false;
	
	public Worker(int xx, int yy, int rr, Color color) {super(xx, yy, rr, color);}

	public static void sleep(int msek)
	{
		try
		{
			Thread.sleep(msek);
		}
		catch (InterruptedException error) {}
	}
	static int itemID = 0;
}

class Producer extends Worker
{
	private boolean full = false;
	protected int mx, my;
	private int item;
	private static Color empty = new Color(224, 151, 151);
	
	private synchronized void isTouchingBorder()
	{
		if(((xx + 20) == 535) || ((xx - 20) == 0))
		{
			mx = -mx;
			if(!full)
			{
				item = itemID++;
				this.setColor(Color.RED);
				full = true;
			}
		}
		if(((yy + 20) == 510) || ((yy - 20) == 0))
		{
			my = -my;
			if(!full)
			{
				item = itemID++;
				this.setColor(Color.RED);
				full = true;
			}
		}
	}
	
	public Producer(Buffer buffer, int xx, int yy, int rr, AnimationPanel painting)
	{ 
		super(xx, yy, rr, empty);
		this.buffer = buffer;
		if(generator.nextBoolean())
		{
			mx = 1;
		}
		else mx = -1;
		if(generator.nextBoolean())
		{
			my = 1;
		}
		else my = -1;
		this.painting = painting;
	}
	
	private void move()
	{
		setXX(getXX() + mx);
		setYY(getYY() + my);
		painting.repaint();
		try
		{
			Thread.sleep(15);
		}
		catch (InterruptedException error) {}
	}
	
	@Override
	public synchronized void run() 
	{
		while(true)
		{
			isTouchingBorder();
			if(buffer.isTouchingBuffer(xx, yy, 20))
			{
				buffer.waiting(this);
				while(!buffer.canTake()) buffer.waiting(this);
				sleep(generator.nextInt(100));
			}
			else move();
			if(!buffer.getTaken())
			{
				while(!buffer.isOutsideBuffer(xx, yy, 20))
				{
					inside = true;
					buffer.setTaken(true);
					move();
					if(buffer.isInside(xx, yy, 20) && full && buffer.canTake())
					{
						full = false;
						buffer.put(this, item);
						setColor(empty);
					}
				}
			}
			if(inside == true)
			{
				inside = false;
				buffer.setTaken(false);
			}
		}
	}
}

class Consumer extends Worker
{
	protected int mx, my;
	private boolean empty = false;
	@SuppressWarnings("unused")
	private Integer consumable = null;
	
	private static Color full = new Color(147, 230, 151);
	
	public Consumer(Buffer buffer, int xx, int yy, int rr, AnimationPanel painting)
	{ 
		super(xx, yy, rr, full);
		this.buffer = buffer;
		if(generator.nextBoolean())
		{
			mx = 1;
		}
		else mx = -1;
		if(generator.nextBoolean())
		{
			my = 1;
		}
		else my = -1;
		this.painting = painting;
	}
	
	private synchronized void isTouchingBorder()
	{
		if(((xx + 20) == 535) || ((xx - 20) == 0))
		{
			mx = -mx;
			if(!empty)
			{
				this.setColor(full);
				consumable = null;
				empty = true;
			}
		}
		if(((yy + 20) == 510) || ((yy - 20) == 0))
		{
			my = -my;
			if(!empty)
			{
				this.setColor(full);
				consumable = null;
				empty = true;
			}
		}
	}
	
	private void move()
	{
		setXX(getXX() + mx);
		setYY(getYY() + my);
		painting.repaint();
		try
		{
			Thread.sleep(15);
		}
		catch (InterruptedException error) {}
	}

	@Override
	public synchronized void run() 
	{
		int i;
		while(true)
		{
			isTouchingBorder();
			if(buffer.isTouchingBuffer(xx, yy, 20))
			{
				i = 0;
				buffer.waiting(this);
				while(!buffer.canGive()) {buffer.waiting(this); i++; if(i == 20) break;}
				sleep(generator.nextInt(100));
			}
			else move();
			if(!buffer.getTaken())
			{
				while(!buffer.isOutsideBuffer(xx, yy, 20))
				{
					inside = true;
					buffer.setTaken(true);
					move();
					if(buffer.isInside(xx, yy, 20) && empty && buffer.canGive())
					{
						empty = false;
						consumable = buffer.get(this);
						setColor(Color.GREEN);
					}
				}
			}	
			if(inside == true)
			{
				inside = false;
				buffer.setTaken(false);
			}
		}
	}
}