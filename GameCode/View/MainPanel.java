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
	private static final int Default_Height = 1000;
	private static final int Default_Width = 1000;
	private static int PaddleX;
	private static int PaddleY;


	public MainPanel()
	{
		/** Constructor **/
		super();
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(500, 500, 500, 500), new EtchedBorder()));
		// setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(Default_Width, Default_Height));
		this.setVisible(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseMotionListener(new CustomMouseMotionListener());
	}

	public int getNewX()
	{
		return PaddleX;
	}

	public int getNewY()
	{
		return PaddleY;
	}


// upper left x, y, width, height
	public void paintComponent(Graphics g)
	{
		/** Renders the basic view of Game Panel **/
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(450,450,100,100);

		g.setColor(Color.BLUE);
		g.drawLine(450,500,550,500);
		g.drawLine(500,450,500,550);
		// basic design

		g.setColor(Color.ORANGE);
		Polygon p = new Polygon(new int[] {0,100,0}, new int[] {0,0,100},3);
		g.fillPolygon(p);

		Polygon p1 = new Polygon(new int[] {1000,900,1000}, new int []{0,0,100},3);
		g.fillPolygon(p1);

		Polygon p2 = new Polygon(new int[] {0,100,0}, new int [] {1000,1000,900},3);
		g.fillPolygon(p2);
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
			curr_g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y - 20), (int) (P_length), 40);
		}
			
	}

	public static class CustomMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged: ("+e.getX()+", "+e.getY() +")");
        PaddleX = e.getX();
        PaddleY = e.getY();
      }

      public void mouseMoved(MouseEvent e) {
         // statusLabel.setText("Mouse Moved: ("+e.getX()+", "+e.getY() +")");
      }    
   }

}