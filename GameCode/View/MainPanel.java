package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import Model.*;
import java.util.*;
import java.awt.event.*;


public class MainPanel extends JPanel
{
	/** We use a JPanel because it is easy to draw 2D objects 
	* on a JPanel using paintComponent() method.
	* This is thw maijn game panel
	* It has a Timer object to render the UI according to backend data after a fixed amt of time **/
	private static final int Default_Height = 800;
	private static final int Default_Width = 800;
	private static int PaddleX;
	private static int PaddleY;
	private static int PClickX;
	private static int PClickY;


	public MainPanel()
	{
		/** Constructor **/
		super();
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(300, 300, 300, 300), new EtchedBorder()));
		// setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(Default_Width, Default_Height));
		this.setVisible(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseMotionListener(new CustomMouseMotionListener());
		this.addMouseListener(new CustomMouseListener());
	}

	public int getNewX()
	{
		return PaddleX;
	}

	public int getNewY()
	{
		return PaddleY;
	}

	public int getClickX()
	{
		return PClickX;
	}

	public int getClickY()
	{
		return PClickY;
	}


// upper left x, y, width, height
	public void paintComponent(Graphics g)
	{
		/** Renders the basic view of Game Panel **/
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(250,250,100,100);

		g.setColor(Color.BLUE);
		g.drawLine(250,300,350,300);
		g.drawLine(300,250,300,350);
		// basic design

		g.setColor(Color.ORANGE);
		Polygon p = new Polygon(new int[] {0,50,0}, new int[] {0,0,50},3);
		g.fillPolygon(p);

		Polygon p1 = new Polygon(new int[] {600,550,600}, new int []{0,0,50},3);
		g.fillPolygon(p1);

		Polygon p2 = new Polygon(new int[] {0,50,0}, new int [] {600,600,550},3);
		g.fillPolygon(p2);

		Polygon p3 = new Polygon(new int[] {600,550,600}, new int [] {600,600,550},3);
		g.fillPolygon(p3);
	}

	public void updateBoard(ArrayList<Ball> balls, ArrayList<Paddle> paddles)
	{
		Graphics curr_g = this.getGraphics();
		paintComponent(curr_g);
		int no_balls = balls.size();
		int no_paddles = paddles.size();
		for (int i = 0; i < no_balls; i++)
		{
			Ball ith = balls.get(i);
			double radius = ith.getRadius();
			double center_x = ith.getCenterX();
			double center_y = ith.getCenterY();
			curr_g.setColor(Color.YELLOW);
			curr_g.fillOval((int)(center_x-radius), (int)(center_y + radius), (int)(2.0*radius), (int)(2.0*radius));
		}
		for (int i = 0; i < no_paddles; i++)
		{
			// ith at bottom. ROTATE karo
			Paddle ith = paddles.get(i);
			double P_length = ith.getPaddleLength();
			double paddle_x = ith.getPaddleX();
			double paddle_y = ith.getPaddleY();
			curr_g.setColor(Color.BLUE);
			if(i == 0)
				{curr_g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y), (int) (P_length), 20);
				Polygon p1 = new Polygon(new int[] {(int)(paddle_x-20 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0))}, new int[] {20,0,20},3);
				curr_g.fillPolygon(p1);
				Polygon p2 = new Polygon(new int[] {(int)(paddle_x+20 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0))}, new int[] {20,0,20},3);
				curr_g.fillPolygon(p2);}
			else if(i ==1)
				{curr_g.fillRect((int)(paddle_x), (int)( paddle_y -(P_length/2.0)), 20, (int) (P_length));
				Polygon p1 = new Polygon(new int[] {20,0,20}, new int[] {(int)(paddle_y-20 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0))},3);
				curr_g.fillPolygon(p1);
				Polygon p2 = new Polygon(new int[] {20,0,20}, new int[] {(int)(paddle_y+20 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0))},3);
				curr_g.fillPolygon(p2);
				}
			else if(i ==2)
				{curr_g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y-20), (int) (P_length), 20);
				Polygon p1 = new Polygon(new int[] {(int)(paddle_x-20 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0)),(int)(paddle_x + 0 - (P_length/2.0))}, new int[] {580,600,580},3);
				curr_g.fillPolygon(p1);
				Polygon p2 = new Polygon(new int[] {(int)(paddle_x+20 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0)),(int)(paddle_x + 0 + (P_length/2.0))}, new int[] {580,600,580},3);
				curr_g.fillPolygon(p2);
				}
			else if(i ==3)
				{curr_g.fillRect((int)(paddle_x - 20), (int)( paddle_y -(P_length/2.0)), 20, (int) (P_length));
				Polygon p1 = new Polygon(new int[] {580,600,580}, new int[] {(int)(paddle_y-20 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0)),(int)(paddle_y + 0 - (P_length/2.0))},3);
				curr_g.fillPolygon(p1);
				Polygon p2 = new Polygon(new int[] {580,600,580}, new int[] {(int)(paddle_y+20 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0)),(int)(paddle_y + 0 + (P_length/2.0))},3);
				curr_g.fillPolygon(p2);
				}
		}
			
	}

	public static class CustomMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e)
		{
        System.out.println("Mouse Dragged: ("+e.getX()+", "+e.getY() +")");
        PaddleX = e.getX();
        PaddleY = e.getY();
      }

      public void mouseMoved(MouseEvent e) {
         // statusLabel.setText("Mouse Moved: ("+e.getX()+", "+e.getY() +")");
      }    
   }

	class CustomMouseListener implements MouseListener{
      public void mouseClicked(MouseEvent e) {
         System.out.println("Mouse Clicked: ("+e.getX()+", "+e.getY() +")");
         PClickX = e.getX();
         PClickY = e.getY();
         PaddleX = e.getX();
         PaddleY = e.getY();
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
   }

}