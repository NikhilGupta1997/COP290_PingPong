package Model;

import java.util.*;

public class Board
{
	private float dim_x;
	private float dim_y;
	private int no_balls;
	private ArrayList<Ball> vector_balls;
	private int no_paddles;
	private ArrayList<Paddle> vector_paddles;
	private ArrayList<Boolean> dead_Alive;

	public Board()
	{
		dim_x = 800;
		dim_y = 800;
		no_balls = 0;
		vector_balls = new ArrayList<Ball>();
		no_paddles = 0;
		vector_paddles = new ArrayList<Paddle>();
		dead_Alive = new ArrayList<Boolean>();
	}

	public 	ArrayList<Ball> getBalls()
	{
		return vector_balls;
	}

	public ArrayList<Paddle> getPaddles()
	{
		return vector_paddles;
	}

	public void addBall(Ball b)
	{
		vector_balls.add(b);
	}

	public void removeBall(int i)
	{
		vector_balls.remove(i);
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

}