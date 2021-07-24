/*
 * Program: Main Window - s³u¿y do tworzenia g³ównego okna programu
 * Plik MainWindow.java
 * Autor Adam Krizar
 * Data 29 listopada 2018
 */
package animation;

import javax.swing.JFrame;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;

	private AnimationPanel panel = new AnimationPanel();
	
	public MainWindow()
	{
		setTitle("Animacja - w¹tki");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(550, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setContentPane(panel);
		setVisible(true);
	}
}
