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
		dim_x = 1000;
		dim_y = 1000;
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

}