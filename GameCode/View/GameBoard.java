package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.*;
import java.util.*;
import java.awt.event.*;
// TODO: setIconImage to remove coffee cup

public class GameBoard extends JFrame
{
	/** This class is a JFrame, that will contain the main JPanel.	**/
	private MainPanel GamePanel;

	public GameBoard()
	{
		/** Constructor. Sets visibility true. **/
		super("PingPong");
		GamePanel = new MainPanel();
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setResizable(false);
		addGamePanel();
		this.pack();
	}

	public void addGamePanel()
	{
		/** Adds the game JPanel to JFrame **/
		this.add(GamePanel);
	}

	public void reDraw(ArrayList<Ball> balls, ArrayList<Paddle> paddles)
	{
		/** Calls the update function of MainPanel class to render the
		* whole board with new arrays of balls and paddles **/
		GamePanel.updateBoard(balls, paddles);
	}

	public int getPaddleX()
	{
		/** Returns new X after user movement by mouse/keyboard **/
		return GamePanel.getNewX();
	}

	public int getPaddleY()
	{
		/** Returns new X after user movement by mouse/keyboard **/
		return GamePanel.getNewY();
	}

}