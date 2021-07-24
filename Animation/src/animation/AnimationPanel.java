/*
 * Program: Klasa zarz¹dza animacj¹
 * Plik AnimationPanel.java
 * Autor Adam Krizar
 * Data 29 listopada 2018
 */
package animation;

import java.awt.Graphics;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	Producer [] producers = new Producer [5];
	Consumer [] consumers = new Consumer [5];
	Buffer buffer;

	public AnimationPanel()
	{
		buffer = new Buffer();
		Thread tp1 = new Thread(producers[0] = new Producer(buffer, 60, 150, 20, this));
		Thread tp2 = new Thread(producers[1] = new Producer(buffer, 350, 100, 20, this));
		Thread tp3 = new Thread(producers[2] = new Producer(buffer, 50, 400, 20, this));
		Thread tp4 = new Thread(producers[3] = new Producer(buffer, 35, 100, 20, this));
		Thread tp5 = new Thread(producers[4] = new Producer(buffer, 350, 26, 20, this));
		Thread tk1 = new Thread(consumers[0] = new Consumer(buffer, 444, 444, 20, this));
		Thread tk2 = new Thread(consumers[1] = new Consumer(buffer, 380, 450, 20, this));
		Thread tk3 = new Thread(consumers[2] = new Consumer(buffer, 500, 200, 20, this));
		Thread tk4 = new Thread(consumers[3] = new Consumer(buffer, 38, 200, 20, this));
		Thread tk5 = new Thread(consumers[4] = new Consumer(buffer, 110, 44, 20, this));
		tp1.start(); tp2.start(); tp3.start(); tp4.start(); tp5.start();
		tk1.start(); tk2.start(); tk3.start(); tk4.start(); tk5.start();
	}
	
	@Override
	protected void paintComponent(Graphics gtx)
	{
		super.paintComponent(gtx);
		buffer.draw(gtx);
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
