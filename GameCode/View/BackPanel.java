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


public class BackPanel extends JPanel
{

	private static final int Default_Height = 800;
	private static final int Default_Width = 1000;
	private static Color customColorBrown = new Color(100,50,0);
	private static String Name1;
 	private static String Name2;
 	private static String Name3;
 	private static String Name4;
 	private static int PaddleX;
	private static int PaddleY;
	private static int PClickX;
	private static int PClickY;

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
	
	public BackPanel()
	{
		/** Constructor **/
		super();
		
		// setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(Default_Width, Default_Height));
		this.setVisible(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseMotionListener(new CustomMouseMotionListener());
		this.addMouseListener(new CustomMouseListener());

	}

	public void paintComponent(Graphics g)
	{
		/** Renders the basic view of Game Panel **/
		super.paintComponent(g);
		Color c= new Color(200, 50, 50, 1);
		Graphics2D g2 = (Graphics2D) g;
		//g.setColor(Color.argb(0.8, 200, 50, 50));
		//g.fillRect(0,0,590,590);
		Color customColor = new Color(222,184,135);
 		
		g.setColor(customColor);
  		g.fillRect(0,0,590,590);
		g.setColor(Color.ORANGE);
		g.fillOval(240,240,120,120);
		g.setColor(Color.RED);
		g.fillOval(260,260,80,80);
		float thickness = 20;
		float thickness2 = 10;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke((float)(thickness)));
		g2.setColor(customColorBrown);
		g2.drawLine(0, 10, 580, 10);
		g2.drawLine(10, 0, 10, 580);
		g2.drawLine(0, 590, 580, 590);
		g2.drawLine(590, 0, 590, 590);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
 		g2.drawString(Name1, 750, 100);
 		g2.drawString(Name2, 750, 250);
 		g2.drawString(Name3, 750, 400);
 		g2.drawString(Name4, 750, 550);
		g2.setStroke(new BasicStroke((float)(thickness2)));
		g2.setColor(Color.BLUE);
		g2.drawLine(265,300,335,300);
		g2.drawLine(300,265,300,335);
		
		g2.setStroke(oldStroke);
		
		g.setColor(Color.ORANGE);
		Polygon p = new Polygon(new int[] {0,60,0}, new int[] {0,0,60},3);
		g.fillPolygon(p);

		Polygon p1 = new Polygon(new int[] {600,540,600}, new int []{0,0,60},3);
		g.fillPolygon(p1);

		Polygon p2 = new Polygon(new int[] {0,60,0}, new int [] {600,600,540},3);
		g.fillPolygon(p2);

		Polygon p3 = new Polygon(new int[] {600,540,600}, new int [] {600,600,540},3);
		g.fillPolygon(p3);

		Color newColor= new Color(200, 105, 0, 1);
		g.setColor(Color.BLACK);
		Polygon ps = new Polygon(new int[] {10,50,10}, new int[] {10,10,50},3);
		g.fillPolygon(ps);

		Polygon ps1 = new Polygon(new int[] {590,550,590}, new int []{10,10,50},3);
		g.fillPolygon(ps1);

		Polygon ps2 = new Polygon(new int[] {10,50,10}, new int [] {590,590,550},3);
		g.fillPolygon(ps2);

		Polygon ps3 = new Polygon(new int[] {590,550,590}, new int [] {590,590,550},3);
		g.fillPolygon(ps3);
	}	

		public static class CustomMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e)
		{
		// System.out.println("Mouse Dragged BACK ***: ("+e.getX()+", "+e.getY() +")");
        PaddleX = e.getX();
        PaddleY = e.getY();
      }

      public void mouseMoved(MouseEvent e) {
         // statusLabel.setText("Mouse Moved: ("+e.getX()+", "+e.getY() +")");
      }    
   }

	class CustomMouseListener implements MouseListener{
      public void mouseClicked(MouseEvent e) {
        // System.out.println("Mouse Clicked BACK ***: ("+e.getX()+", "+e.getY() +")");
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
