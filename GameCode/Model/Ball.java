package Model;

import java.util.*;

public class Ball
{
	private double radius;
	private double center_x;
	private double center_y;
	private double vel_x;
	private double vel_y;
	private ArrayList<Float> bcolor;

	public Ball(double vx, double vy, double cx, double cy, double r)
	{
		vel_x = vx;
		vel_y = vy;
		// bcolor = color;
		center_x = cx;
		center_y = cy;
		radius = r;
	}

	public double getVelX()
	{
		return vel_x;
	}

	public double getVelY()
	{
		return vel_y;
	}

	public double getCenterX()
	{
		return center_x;
	}

	public double getCenterY()
	{
		return center_y;
	}

	public ArrayList<Float> getBallColor()
	{
		return bcolor;
	}

	public double getRadius()
	{
		return radius;
	}


	public void setVelX(double vx)
	{
		vel_x = vx;
	}

	public void setVelY(double vy)
	{
		vel_y = vy;
	}

	public void setCenterX(double cx)
	{
		center_x = cx;
	}

	public void setCenterY(double cy)
	{
		center_y = cy;
	}

	public void setBallColor(ArrayList<Float> new_color)
	{
		bcolor = new_color;
	}

	public void setRadius(double new_Rad)
	{
		radius = new_Rad;
	}

}