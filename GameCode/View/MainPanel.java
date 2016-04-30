package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import Model.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JOptionPane;


public class MainPanel extends JPanel
{
	/** We use a JPanel because it is easy to draw 2D objects 
	* on a JPanel using paintComponent() method.
	* This is thw maijn game panel
	* It has a Timer object to render the UI according to backend data after a fixed amt of time **/
	private static ArrayList<Ball> theBalls;
	private static ArrayList<Paddle> thePaddles;
	private static ArrayList<RandomObj> theObjects;
	private static final int Default_Height = 800;
	private static final int Default_Width = 600;
	
	private static String Name1;
 	private static String Name2;
 	private static String Name3;
 	private static String Name4;
 	private static Color customColorBrown = new Color(100,50,0);
 	public static int Paddles_out = 0;
 	public static int first1 = 0;
 	public static int first2 = 0;
 	public static int first3 = 0;
 	public static int first4 = 0;
 	public static int winner = -1;
 	private static int M = 100;

	public MainPanel()
	{
		/** Constructor **/
		super();
		
		// setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(Default_Width, Default_Height));
		this.setVisible(true);
		theBalls = new ArrayList<Ball>();
		thePaddles = new ArrayList<Paddle>();
		theObjects = new ArrayList<RandomObj>();
		
	}

	

	public int getWinner()
	{
		return winner;
	}

	public void setWinner(int newwinner)
	{
		winner = newwinner;
		//System.out.println("The new value of winner is  :  " + winner); 
	}

// upper left x, y, width, height
	public void paintComponent(Graphics g)
	{
		/** Renders the basic view of Game Panel **/
		super.paintComponent(g);

		int no_balls = theBalls.size();
		int no_paddles = thePaddles.size();
		for (int i = 0; i < no_balls; i++)
			{
				Ball ith = theBalls.get(i);
				double radius = ith.getRadius();
				double center_x = ith.getCenterX();
				double center_y = ith.getCenterY();
				g.setColor(Color.YELLOW);
				g.fillOval((int)(center_x-radius), (int)(center_y + radius), (int)(2.0*radius), (int)(2.0*radius));
			}
		for (int i = 0; i < no_paddles; i++)
		{
			if(winner == -1)
				{
				// ith at bottom. ROTATE karo
				Paddle ith = thePaddles.get(i);
				double P_length = ith.getPaddleLength();
				double paddle_x = ith.getPaddleX();
				double paddle_y = ith.getPaddleY();
				int loves = 5 - ith.getBallMissed();

				if(i == 0)
					{ 
					if(loves <= 0)
					g.setColor(Color.RED);
					else
					g.setColor(Color.BLUE);
					g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y), (int) (P_length), 20);
					Polygon p1 = new Polygon(new int[] {(int)(paddle_x-20 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0))}, new int[] {20,0,20},3);
					g.fillPolygon(p1);
					Polygon p2 = new Polygon(new int[] {(int)(paddle_x+20 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0))}, new int[] {20,0,20},3);
					g.fillPolygon(p2);
					g.setColor(Color.RED);
					if(Paddles_out == 3 && first1 == 0)
						{
							g.setFont(new Font("Verdana", Font.BOLD, 30)); 
			 					g.drawString("WINNER", 700, 50);
			 					first1 = 2;
			 					
						}
					for (int j = 1; j<= loves; j++)
					g.fillOval(50*j + 640,50,20,20);
					if(loves == 0 && first1 == 0)
						Paddles_out = Paddles_out + 1;
					if(loves <= 0)
		 				{
		 					g.setFont(new Font("Verdana", Font.BOLD, 30)); 
		 					g.drawString("GAME OVER", 700, 50);
		 					first1 = 1;
		 				}
					}
				else if(i ==1)
					{
					if(loves <= 0)
					g.setColor(Color.RED);
					else
					g.setColor(Color.BLUE);	
					g.fillRect((int)(paddle_x), (int)( paddle_y -(P_length/2.0)), 20, (int) (P_length));
					Polygon p1 = new Polygon(new int[] {20,0,20}, new int[] {(int)(paddle_y-20 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0))},3);
					g.fillPolygon(p1);
					Polygon p2 = new Polygon(new int[] {20,0,20}, new int[] {(int)(paddle_y+20 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0))},3);
					g.fillPolygon(p2);
					g.setColor(Color.RED);
					if(Paddles_out == 3 && first2 == 0)
						{
							g.setFont(new Font("Verdana", Font.BOLD, 30)); 
			 					g.drawString("WINNER", 700, 200);
			 					first2 = 2;
						}
					for (int j = 1; j<=loves; j++)
					g.fillOval(50*j + 640,200,20,20);
					if(loves == 0 && first2 == 0)
						Paddles_out = Paddles_out + 1;
					if(loves <= 0)
		 				{
		 					g.setFont(new Font("Verdana", Font.BOLD, 30)); 
		 					g.drawString("GAME OVER", 700, 200);
		 					first2 = 1;
		 				}
					}
				else if(i ==2)
					{
					if(loves <= 0)
					g.setColor(Color.RED);
					else
					g.setColor(Color.BLUE);
					g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y-20), (int) (P_length), 20);
					Polygon p1 = new Polygon(new int[] {(int)(paddle_x-20 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0))}, new int[] {580,600,580},3);
					g.fillPolygon(p1);
					Polygon p2 = new Polygon(new int[] {(int)(paddle_x+20 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0))}, new int[] {580,600,580},3);
					g.fillPolygon(p2);
					g.setColor(Color.RED);
					if(Paddles_out == 3 && first3 == 0)
						{
							g.setFont(new Font("Verdana", Font.BOLD, 30)); 
			 					g.drawString("WINNER", 700, 350);
			 					first3 = 2;
						}
					for (int j = 1; j<= loves; j++)
					g.fillOval(50*j + 640,350,20,20);
					if(loves == 0 && first3 == 0)
						Paddles_out = Paddles_out + 1;
					if(loves <= 0) 
		 				{
		 					g.setFont(new Font("Verdana", Font.BOLD, 30)); 
		 					g.drawString("GAME OVER", 700, 350);
		 					first3 = 1;
		 				}
					}
				else if(i ==3)
					{
					if(loves <= 0)
					g.setColor(Color.RED);
					else
					g.setColor(Color.BLUE);
					g.fillRect((int)(paddle_x - 20), (int)( paddle_y -(P_length/2.0)), 20, (int) (P_length));
					Polygon p1 = new Polygon(new int[] {580,600,580}, new int[] {(int)(paddle_y-20 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0))},3);
					g.fillPolygon(p1);
					Polygon p2 = new Polygon(new int[] {580,600,580}, new int[] {(int)(paddle_y+20 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0))},3);
					g.fillPolygon(p2);
					g.setColor(Color.RED);
					if(Paddles_out == 3 && first4 == 0)
						{
							g.setFont(new Font("Verdana", Font.BOLD, 30)); 
			 					g.drawString("WINNER", 700, 500);
			 					first4 = 2;
						}
					for (int j = 1; j<=loves; j++)
					g.fillOval(50*j + 640,500,20,20);
					if(loves == 0 && first4 == 0)
						Paddles_out = Paddles_out + 1;
					if(loves <= 0)
		 				{
		 					g.setFont(new Font("Verdana", Font.BOLD, 30)); 
		 					g.drawString("GAME OVER", 700, 500);
		 					first4 = 1;
		 				}
					}
				}

			if(i == 3)
			{
				if (first1 == 2 )
	 			{winner = -1;
	 				first1 = 0;
	 			}
	 			else if (first2 == 2 )
	 			{winner = -1;
	 				first2 = 0;
	 			}
	 			else if (first3 == 2 )
	 			{winner = -1;
	 				first3 = 0;
	 			}
	 			else if (first4 == 2 )
	 			{winner = -1;
	 				first4 = 0;
	 			}
	 			else{} 
 			}
 		}

 	// 	for (int j = 0; j < theObjects.size(); j ++)
		// {
		// 	RandomObj ith = theObjects.get(j);
		// 	double center_x = ith.getCenterX();
		// 	double center_y = ith.getCenterY();
		// 	boolean effect = ith.getEffect();
		// 	if (effect)
		// 	{
		// 		g.drawImage(Image.getProperty("expand.png",this), center_x - 10,center_y - 10,20,20,this);
		// 	}
		// 	else
		// 	{
		// 		g.drawImage(Image.getProperty("shrink.png",this), center_x - 10,center_y - 10,20,20,this);
		// 	}
		// }
		
	}

/** Takes input the updated lists of Balls, Paddles, Random Objects. **/
	public void updateBoard(ArrayList<Ball> balls, ArrayList<Paddle> paddles, ArrayList<RandomObj> objs, ArrayList<String> names)
	{//System.out.println("winner is : " + winner);
		
		theBalls = balls;
		thePaddles = paddles;
		theObjects = objs;
		int no_pl = names.size();
		Name1 = names.get(0);
		if (no_pl >= 2)
			Name2 = names.get(1);
		else
		{
			Name2 = "AI";
			Name3 = "AI";
			Name4 = "AI";
		}
		if (no_pl >= 3)
			Name3 = names.get(2);
		else
		{
			Name3 = "AI";
			Name4 = "AI";
		}
		if (no_pl >= 4)
			Name4 = names.get(3);
		else
		{
			Name4 = "AI";
		}

	}



}