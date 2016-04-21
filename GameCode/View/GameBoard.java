package View;

import javax.swing.*;
import Model.*;
import java.util.*;
import java.awt.event.*;


public class GameBoard extends JFrame
{
	/** This class is a JFrame, that will contain all other JPanels.	**/
	private MainPanel GamePanel;

	public GameBoard()
	{
		super("PingPong");
		GamePanel = new MainPanel();
		startBoard();
	}

	public void startBoard()
	{
		this.setSize(1000,1000);
		this.setVisible(true);
		addGamePanel();
	}

	public void addGamePanel()
	{
		this.add(GamePanel);
	}

	public void reDraw(ArrayList<Ball> balls, ArrayList<Paddle> paddles)
	{
		GamePanel.updateBoard(balls, paddles);
	}

	public int getPaddleX()
	{
		return GamePanel.getNewX();
	}

	public int getPaddleY()
	{
		return GamePanel.getNewY();
	}

}