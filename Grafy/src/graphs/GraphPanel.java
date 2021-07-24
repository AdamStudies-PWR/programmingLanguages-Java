/*
 * Promgram Grafy
 * Ta klasa implementuje podstawowe graficzne na panelu graficznym
 * Autor: Adam Krizar
 * Data: 12.11.2018
 */
package graphs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	private Color color = Color.LIGHT_GRAY;
	
	private Graph graph;
	private int XX = 0;
	private int YY = 0;
	private boolean mLeft;
	@SuppressWarnings("unused")
	private boolean mRight;
	private BasicNodes currentNode = null;
	private Edges currentEdge = null;
	private int cursor = Cursor.DEFAULT_CURSOR;
	private boolean selectionMode = false;
	private JColorChooser chooser = new JColorChooser();
	private Window parent;
	
	public GraphPanel(Window parent)
	{
		this.parent = parent;
		setLayout(null);
		setBackground(color);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();
		chooser.setPreviewPanel(new JPanel());
	}
	
	public void setGraph(Graph graph) {this.graph = graph;}
	
	private BasicNodes findNode(MouseEvent event)
	{
		for(BasicNodes node: graph.getNod())
		{
			if(node.isMouseOver(event.getX(), event.getY())) return node;
		}
		return null;
	}
	
	private Edges findEdge(MouseEvent event)
	{
		for(Edges edge: graph.getEdge())
		{
			if(edge.isMouseOver(event.getX(), event.getY())) return edge;
		}
		return null;
	}
	
	private void addEdge(BasicNodes node1, BasicNodes node2)
	{
		if(node1.equals(node2))
		{
			JOptionPane.showMessageDialog(this, "Nie mo¿na po³¹czyæ wêz³a z samym sob¹", "B³¹d wyboru", JOptionPane.ERROR_MESSAGE);
			selectionMode = false;
			return;
		}
		
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("1"); combo.addItem("2"); combo.addItem("3"); combo.addItem("4"); combo.addItem("5");
		Edges edge = new Edges(node1, node2);
		
		if(JOptionPane.showConfirmDialog(this, combo, "Wybierz gruboœæ", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null) != 0) edge.setStroke(1);
		else edge.setStroke(combo.getSelectedIndex());
		
		JComboBox<ConnectionType> typeBox = new JComboBox<ConnectionType>(ConnectionType.values());
		
		if(JOptionPane.showConfirmDialog(this, typeBox, "Wybierz typ", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null) != 0) edge.setType(ConnectionType.UNKOWN);
		else edge.setType((ConnectionType)typeBox.getSelectedItem());
		
		graph.addEdge(edge);
		JColorChooser.createDialog(this, "Wybierz kolor", true, chooser, null, null).setVisible(true);
		Color color = chooser.getColor();
		
		if(color != null) edge.setColor(color);
		selectionMode = false;
		
		repaint();
	}
	
	private void setMouseCursor(MouseEvent event)
	{
		BasicNodes node = null;
		if(selectionMode)
		{
			if(findNode(event) == null) return;
			node = currentNode;
		}
		currentNode = findNode(event);
		currentEdge = findEdge(event);
		if(selectionMode)
		{
			addEdge(node, currentNode);
		}
		if(currentNode != null && mLeft) cursor = Cursor.MOVE_CURSOR;
		else if(currentEdge != null && mLeft) cursor = Cursor.MOVE_CURSOR;
		else if(mLeft) cursor = Cursor.MOVE_CURSOR;
		else cursor = Cursor.DEFAULT_CURSOR;
		setCursor(Cursor.getPredefinedCursor(cursor));
		XX = event.getX();
		YY = event.getY();
	}
	
	private void moveNode(int xx, int yy, BasicNodes node)
	{
		node.setXX(node.getXX() + xx);
		node.setYY(node.getYY() + yy);
	}
	
	private void moveNodes(int xx, int yy)
	{
		for(BasicNodes node: graph.getNod())
		{
			moveNode(xx, yy, node);
		}
	}
	private void moveEdge(int xx, int yy, BasicNodes [] nodes)
	{
		nodes[0].setXX(nodes[0].getXX() + xx);
		nodes[0].setYY(nodes[0].getYY() + yy);
		nodes[1].setXX(nodes[1].getXX() + xx);
		nodes[1].setYY(nodes[1].getYY() + yy);
	}

	@Override
	public void mouseDragged(MouseEvent event) 
	{
		if(mLeft && !selectionMode)
		{
			if(currentNode != null)moveNode(event.getX() - currentNode.getXX(), event.getY() - currentNode.getYY(), currentNode);
			else if(currentEdge != null) moveEdge(event.getX() - XX, event.getY() - YY, currentEdge.getNodes());
			else moveNodes(event.getX() - XX, event.getY() - YY);
		}
		XX = event.getX();
		YY = event.getY();
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent event) 
	{
		if(event.getButton() == 1) mLeft = true;
		if(event.getButton() == 3) mRight = true;
		setMouseCursor(event);
	}

	@Override
	public void mouseReleased(MouseEvent event) 
	{
		if(event.getButton() == 1) mLeft = false;
		if(event.getButton() == 3)
		{
			mRight = false;
			if(currentNode != null) createPopupMenu(event, currentNode);
			else if(currentEdge != null) createPopupMenu(event, currentEdge);
			else createPopupMenu(event);
		}
		setMouseCursor(event);
	}
	
	private void createPopupMenu(MouseEvent event) 
	{
		JPopupMenu popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Dodaj nowy wêze³");
		item.addActionListener
		(
				(action) -> {
					BasicNodes nn;
					JComboBox<String> combo = new JComboBox<String>();
					combo.addItem("Prosty punkt");
					combo.addItem("Kolorowe ko³o");
					if(JOptionPane.showConfirmDialog(this, combo, "Wybierz rodzaj", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null) != 0) return;
					if(combo.getSelectedIndex() == 0) graph.addNode(nn = new BasicNodes(event.getX(), event.getY()));
					else
					{
						nn = new Node(event.getX(), event.getY());
						graph.addNode(nn);
						JColorChooser.createDialog(this, "Wybierz kolor", true, chooser, null, null).setVisible(true);
						Color color = chooser.getColor();
						if(color != null)((Node) nn).setColor(color);
					}
					CreateWindow.changeNode(parent, nn);
					repaint();
				}
		);
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		popup.add(item);
		popup.show(event.getComponent(), event.getX(), event.getY());
	}
	
	private void createPopupMenu(MouseEvent event, BasicNodes node)
	{
		JPopupMenu popup = new JPopupMenu();
		JMenuItem coloritem = new JMenuItem("Zmieñ kolor wêz³a");
		JMenuItem edgeitem = new JMenuItem("Dodaj now¹ krawêdz");
		JMenuItem wideitem = new JMenuItem("Zmieñ wielkoœæ");
		JMenuItem textitem = new JMenuItem("Zmieñ kolor tekstu");
		JMenuItem deleteitem = new JMenuItem("Usuñ wêze³");
		coloritem.addActionListener
		(
			(action) -> {
				JColorChooser.createDialog(this, "Wybierz kolor", true, chooser, null, null).setVisible(true);
				Color color = chooser.getColor();
				if(color != null)((Node)node).setColor(color);
				repaint();
				}
		);
		edgeitem.addActionListener
		(
			(action) -> {
				cursor = Cursor.HAND_CURSOR;
				setCursor(Cursor.getPredefinedCursor(cursor));
				selectionMode = true;
				}
		);
		deleteitem.addActionListener
		(
			(action) -> {
				graph.removeNode(node);
				repaint();
				}
		);
		textitem.addActionListener
		(
			(action) ->{
				JColorChooser.createDialog(this, "Wybierz kolor", true, chooser, null, null).setVisible(true);
				Color color = chooser.getColor();
				if(color != null) ((Node)node).setTextColor(color);
				repaint();
			}
		);
		wideitem.addActionListener
		(
			(action) -> {
				int rr;
				String choice = JOptionPane.showInputDialog(this, "Podaj now¹ wielkoœæ z zakresu 20 - 50");
				try
				{
					rr = Integer.parseInt(choice);
					if(rr <= 20) ((Node)node).setRR(20);
					else if(50 <= rr) ((Node)node).setRR(50);
					else ((Node)node).setRR(rr);
				}
				catch(Exception error)
				{
					JOptionPane.showMessageDialog(this, "Promieñ musi byæ liczb¹!", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				repaint();
				}
		);
		coloritem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		edgeitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		wideitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		textitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		popup.add(deleteitem);
		popup.add(edgeitem);
		if(!node.isBasic())
		{
			popup.add(coloritem);
			popup.add(wideitem);
			popup.add(textitem);
		}
		popup.show(event.getComponent(), event.getX(), event.getY());
	}
	
	private void createPopupMenu(MouseEvent event, Edges edge)
	{
		JPopupMenu popup = new JPopupMenu();
		JMenuItem coloritem = new JMenuItem("Zmieñ kolor krawêdzi");
		JMenuItem edititem = new JMenuItem("Zmieñ rodzaj po³¹czenia");
		JMenuItem wideitem = new JMenuItem("Zmieñ gruboœæ");
		JMenuItem deleteitem = new JMenuItem("Usuñ krawêdz");
		deleteitem.addActionListener
		(
			(action) -> {
				graph.removeEdge(edge);
				repaint();
			}
		);
		coloritem.addActionListener
		(
				(action) -> {
					JColorChooser.createDialog(this, "Œ kolor", true, chooser, null, null).setVisible(true);
					Color color = chooser.getColor();
					if(color != null) edge.setColor(color);
					repaint();
				}
		);
		wideitem.addActionListener
		(
				(action) -> {
					JComboBox<String> combo = new JComboBox<String>();
					combo.addItem("1"); combo.addItem("2"); combo.addItem("3"); combo.addItem("4"); combo.addItem("5");
					if(JOptionPane.showConfirmDialog(this, combo, "Wybierz gruboœæ", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null) != 0) return;
					else edge.setStroke(combo.getSelectedIndex());
					repaint();
				}
		);
		edititem.addActionListener
		(
				(action) -> {
					JComboBox<ConnectionType> typeBox = new JComboBox<ConnectionType>(ConnectionType.values());
					typeBox.setSelectedItem(edge.getType());
					if(JOptionPane.showConfirmDialog(this, typeBox, "Wybierz typ", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null) != 0) return;
					else edge.setType((ConnectionType)typeBox.getSelectedItem());
				}
		);
		deleteitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		wideitem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		coloritem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		edititem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		popup.add(coloritem);
		popup.add(wideitem);
		popup.add(edititem);
		popup.add(deleteitem);
		popup.show(event.getComponent(), event.getX(), event.getY());
	}

	@Override
	protected void paintComponent(Graphics gtx)
	{
		super.paintComponent(gtx);
		if(graph==null) return;
		graph.draw(gtx);
	}

	@Override
	public void mouseMoved(MouseEvent event) 
	{
		if(selectionMode) return;
		for(BasicNodes node: graph.getNod())
		{
			if(node.isMouseOver(event.getX(), event.getY())) 
			{ 
				cursor = Cursor.HAND_CURSOR;
				setCursor(Cursor.getPredefinedCursor(cursor));
				return;
			}
		}
		for(Edges edge: graph.getEdge())
		{
			if(edge.isMouseOver(event.getX(), event.getY())) 
			{ 
				cursor = Cursor.HAND_CURSOR;
				break;
			}
			else cursor = Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(cursor));
	}
	
	@Override
	public void mouseClicked(MouseEvent event) 
	{
		if(currentEdge != null) JOptionPane.showMessageDialog(this, currentEdge.getType().toString(), "Typ po³¹czenia", JOptionPane.INFORMATION_MESSAGE);
		if(currentNode != null) {CreateWindow.changeNode(parent, currentNode); repaint();}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
}
