/*
 * Program: Main - wywo³anie g³ównego w¹tku programu
 * Plik InformationPanel.java
 * Autor Adam Krizar
 * Data 26 listopada 2018
 */
package main;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class InformationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Square> bufferDrawable = null;
	private Consumer [] consumers = null;
	private Producer [] producers = null;
	
	public void setDrawable(Buffer buffer, Consumer [] consumers, Producer [] producers)
	{
		bufferDrawable = buffer.getDrawable();
		this.consumers = consumers;
		this.producers = producers;
	}

	@Override
	protected void paintComponent(Graphics gtx)
	{
		super.paintComponent(gtx);
		drawLines(gtx);
		if(bufferDrawable != null)
		{
			for(Square square: bufferDrawable)
			{
				square.draw(gtx);
			}
			for(Producer producer: producers)
			{
				producer.draw(gtx);
			}
			for(Consumer consumer: consumers)
			{
				consumer.draw(gtx);
			}
		}
	}
	
	private void drawLines(Graphics gtx)
	{
		gtx.drawLine(0, 289, 105, 289);
		gtx.drawLine(35, 0, 35, 315);
		gtx.drawLine(70, 0, 70, 315);
	}
}

