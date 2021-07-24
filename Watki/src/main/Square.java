package main;

import java.awt.Graphics;
import java.awt.Color;

public class Square 
{
	protected int number;
	protected int xx; 
	protected int yy;
	protected int lenght;
	private Color color = Color.BLUE;
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Square(int number, int where, String k)
	{
		this.number = number;
		if(k == "B") xx = 72; 
		else if(k == "P") xx = 37;
		else xx = 2;
		switch(number)
		{
			case 1: {yy = 2; lenght = 284;} break;
			case 2:
			{
				lenght = 140;
				if(where == 0) yy = 145;
				else yy = 2;
			} break;
			case 3:
			{
				lenght = 92;
				switch(where)
				{
					case 0: yy = 194; break;
					case 1: yy = 98; break;
					default: yy = 2;
				}
			} break;
			case 4:
			{
				lenght = 70;
				switch(where)
				{
					case 0: yy = 217; break;
					case 1: yy = 145; break;
					case 2: yy = 73; break;
					default: yy = 1;
				}
			} break;
			default:
			{
				lenght = 55;
				switch(where)
				{
					case 0: yy = 232; break;
					case 1: yy = 174; break;
					case 2: yy = 116; break;
					case 3: yy = 58; break;
					default: yy = 1;
				}
			}
		}
	}
	
	void draw(Graphics gtx)
	{
		gtx.setColor(Color.BLACK);
		gtx.drawRect(xx, yy, 30, lenght);
		gtx.setColor(color);
		gtx.fillRect(xx + 1, yy + 1, 29, lenght - 1);
	}
}
