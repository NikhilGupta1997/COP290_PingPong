package Model;

import java.util.*;

public class RandomObj
{
	/** 0 : increase length or
	* 	1 : decrease length
	* 	2 : give extra life **/
	private int r_effect;
	private double center_x;
	private double center_y;
	private double vel_x;
	private double vel_y;

	public RandomObj(double vx, double vy, double cx, double cy, int effect)
	{
		vel_x = vx;
		vel_y = vy;
		// bcolor = color;
		center_x = cx;
		center_y = cy;
		r_effect = effect;
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

	public int getEffect()
	{
		return r_effect;
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

	public void setEffect(int e)
	{
		r_effect = e;
	}

}