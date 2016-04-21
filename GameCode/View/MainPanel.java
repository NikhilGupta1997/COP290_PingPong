package View;

import javax.swing.*;
import java.awt.*;
import Model.*;
import java.util.*;

public class MainPanel extends JPanel
{
	/** We use a JPanel because it is easy to draw 2D objects 
	* on a JPanel using paintComponent() method.
	* This is thw maijn game panel
	* It has a Timer object to render the UI according to backend data after a fixed amt of time **/
	private static final int Default_Height = 100;
	private static final int Default_Width = 100;


	public MainPanel()
	{
		super();
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(Default_Width, Default_Height));
		this.setVisible(true);
		this.setBackground(Color.LIGHT_GRAY);
	}


// upper left x, y, width, height
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(450,450,100,100);

		g.setColor(Color.BLUE);
		g.drawLine(450,500,550,500);
		g.drawLine(500,450,500,550);
		// basic design
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
			Paddle ith = paddles.get(i);
			double P_length = ith.getPaddleLength();
			double paddle_x = ith.getPaddleX();
			double paddle_y = ith.getPaddleY();
			curr_g.setColor(Color.BLUE);
			curr_g.fillRect((int)(paddle_x - (P_length/2.0)), (int)( paddle_y - 20), (int) (P_length), 40);
		}
			
	}

	// public void reDraw()
	// {
	// 	// this.rePaint();
	// }
}