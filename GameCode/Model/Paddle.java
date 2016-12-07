package Model;

import java.util.*;

public class Paddle {
	private double P_length;
	private double paddle_x;
	private double paddle_y;
	private ArrayList<Float> p_color;
	private int times_ball_missed;
	private boolean isAlive;

	public Paddle(double plen, double cx, double cy, int miss, boolean life) {
		p_color = new ArrayList<Float>();
		P_length = plen;
		paddle_x = cx;
		paddle_y = cy;
		times_ball_missed = miss;
		isAlive = life;
	}

	public double getPaddleX() {
		return paddle_x;
	}

	public double getPaddleY() {
		return paddle_y;
	}

	public int getBallMissed() {
		return times_ball_missed;
	}

	public boolean getAlive_Dead() {
		return isAlive;
	}

	public double getPaddleLength() {
		return P_length;
	}

	public ArrayList<Float> getPaddleColor() {
		return p_color;
	}

	public void setPaddleX(double px) {
		paddle_x = px;
	}

	public void setPaddleY(double py) {
		paddle_y = py;
	}

	public void setBallMissed(int newb) {
		times_ball_missed = newb;
	}

	public void setAlive_Dead(boolean dead) {
		isAlive = dead;
	}

	public void setPaddleLength(double new_length) {
		P_length = new_length;
	}
}