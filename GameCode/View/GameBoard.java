package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
// TODO: setIconImage to remove coffee cup

public class GameBoard extends JFrame
{
	/** This class is a JFrame, that will contain the main JPanel.	**/
	private MainPanel GamePanel;
	private BackPanel BackGamePanel;

	public GameBoard(int playerNo)
	{
		/** Constructor. Sets visibility true. **/
		super("PingPong");
		GamePanel = new MainPanel(playerNo);
		BackGamePanel = new BackPanel();
		GamePanel.setBorder(BorderFactory.createEmptyBorder(100,0,40,0));
		GamePanel.setBackground(Color.BLACK);
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setResizable(true);
		this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
		});
		addGamePanel();
		this.pack();
	}

	public void addGamePanel()
	{
		BackGamePanel.setLayout(new OverlayLayout(BackGamePanel));
		GamePanel.setOpaque(false);
		JButton button = new JButton("Small");
	    button.setMaximumSize(new Dimension(25, 25));
	    button.setBackground(Color.white);
	    BackGamePanel.add(GamePanel);
		this.add(BackGamePanel);
		
		
	}

	public void reDraw(ArrayList<Ball> balls, ArrayList<Paddle> paddles, ArrayList<RandomObj> objects, ArrayList<String> names)
	{
		/** Calls the update function of MainPanel class to render the
		* whole board with new arrays of balls and paddles **/
		// BackGamePanel.remove(GamePanel);
		// GamePanel = new MainPanel();
		// GamePanel.setOpaque(false);
		// GamePanel.removeAll();
		GamePanel.repaint();
		GamePanel.updateBoard(balls, paddles, objects, names);
		BackGamePanel.add(GamePanel);
		// this.add(BackGamePanel);
	}

	public int getPaddleX()
	{
		/** Returns new X after user movement by mouse/keyboard **/
		return BackGamePanel.getNewX();
	}

	public int getPaddleY()
	{
		/** Returns new Y after user movement by mouse/keyboard **/
		return BackGamePanel.getNewY();
	}

	public int getPClickX()
	{
		return BackGamePanel.getClickX();
	}

	public int getPClickY()
	{
		return BackGamePanel.getClickY();
	}

	public int getWinner()
	{
		return GamePanel.getWinner();
	}

	public void setReset()
	{
		GamePanel.winner = -1;
		GamePanel.Paddles_out = 0;
 		GamePanel.first1 = 0;
 		GamePanel.first2 = 0;
 		GamePanel.first3 = 0;
 		GamePanel.first4 = 0;
 	
	}



}