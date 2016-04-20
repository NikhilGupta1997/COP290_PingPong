
public class Paddle
{
	private double P_length;
	private double paddle_x;
	private double paddle_x;
	private ArrayList<float> p_color;
	private int times_ball_missed;
	private boolean isAlive;

	public Paddle(ArrayList<float> color)
	{
		times_ball_missed = 0;
		isAlive = true;
		length = 10; // standard initial length
		// paddle_x = 
		p_color = color;
	}

	public double getPaddleX()
	{
		return paddle_x;
	}

	public double getPaddleY()
	{
		return paddle_y;
	}

	public int getBallMissed()
	{
		return times_ball_missed;
	}

	public boolean getAlive_Dead()
	{
		return isAlive;
	}

	public double getPaddleLength()
	{
		return P_length;
	}

	public ArrayList<float> getPaddleColor()
	{
		return p_color;
	}




	public void setPaddleX(double px)
	{
		paddle_x = px;
	}

	public void setPaddleY(double py)
	{
		paddle_y = py;
	}

	public void setBallMissed(int newb)
	{
		times_ball_missed = newb;
	}

	public void setAlive_Dead(boolean dead)
	{
		isAlive = dead;
	}

	public void setPaddleLength(double new_length)
	{
		P_length = new_length;
	}

}