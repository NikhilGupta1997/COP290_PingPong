		package Model;

import java.util.*;

public class Board
{
	/** Back end Board class that holds all the data. **/
	private float dim_x;
	private float dim_y;
	private int no_balls;
	private ArrayList<Ball> vector_balls;
	private RandomObj obj;
	private int no_paddles;
	private ArrayList<Paddle> vector_paddles;
	private ArrayList<Boolean> dead_Alive;

	public Board()
	{
		dim_x = 600;
		dim_y = 600;
		no_balls = 0;
		vector_balls = new ArrayList<Ball>();
		no_paddles = 0;
		vector_paddles = new ArrayList<Paddle>();
		dead_Alive = new ArrayList<Boolean>();
		obj = new RandomObj(5.0, 6.0, 220.0, 250.0, 0);
	}

	public 	ArrayList<Ball> getBalls()
	{
		return vector_balls;
	}

	public ArrayList<Paddle> getPaddles()
	{
		return vector_paddles;
	}

	public RandomObj getObjects()
	{
		return obj;
	}

	public void addBall(Ball b)
	{
		vector_balls.add(b);
	}

	public void removeBall(int i)
	{
		vector_balls.remove(i);
		dead_Alive.remove(i);
	}

	public void addPaddle(Paddle p)
	{
		vector_paddles.add(p);
		dead_Alive.add(true);
	}

	public void removePaddle(int i)
	{
		vector_paddles.remove(i);
	}

	public void addObject(RandomObj r)
	{
		obj = r;
	}

	public void removeObj()
	{
		obj = null;
	}

	public void movePaddle(int i, double new_px,double new_py,double new_len,int times_missed, boolean life)
	{
		Paddle p = new Paddle(new_len, new_px, new_py, times_missed, life);
		vector_paddles.set(i,p);
	}

	public void moveBall(int i,double vx, double vy, double cx, double cy, double r)
	{
		Ball b = new Ball(vx, vy, cx, cy, r);
		vector_balls.set(i, b);
	}

	public void moveObj(double vx, double vy, double cx, double cy, int inc_len)
	{
		RandomObj r = new RandomObj(vx, vy, cx, cy, inc_len);
		obj = r;
	}

	public Paddle getIthpaddle(int i)
	{
		return vector_paddles.get(i);
	}

}