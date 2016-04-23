import java.awt.*;
import Model.*;
import View.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.*;
import Physics.*;

public class Player
{
	/** This class acts as view model, linking model and view. **/
	private static Board Board_backend;
	private static GameBoard Board_UI;
	private static int timerDelay;
	private static Timer gameTimer;
	private static int Paddle_No;
	private static int M;
	private static int ClickDiff;
	private static double new_paddlePos;
	private static int LastClick = 0;
	private static physics PEngine;

	public Player()
	{
		// a Board object
		Board_backend = new Board();
		Ball b = new Ball(1.0, -3.0, 200.0, 250.0, 25);
		Paddle p = new Paddle(100.0, 400.0, 100.0, 0,true);
		// a GameBoard object
		// add a player to board!
		Board_backend.addBall(b);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 400.0, 200.0, 0,true);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 400.0, 130.0, 0,true);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 400.0, 300.0, 0,true);
		Board_backend.addPaddle(p);
		Board_UI = new GameBoard();
		timerDelay = 50;
		gameTimer = new Timer(timerDelay, timerAction);
		gameTimer.start();
		M =0;
		PEngine = new physics();

	}

	public static void main(String[] args)
	{
		Player p1 = new Player();
	}

	static ActionListener timerAction = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			System.out.println("Timer working!");
			// call Board_UI ka update function.
			update_Phy();
			ArrayList<Ball> updatedBalls = Board_backend.getBalls();
			ArrayList<Paddle> updatedPaddles = Board_backend.getPaddles();
			Board_UI.reDraw(updatedBalls, updatedPaddles);

			Paddle myPaddle = Board_backend.getPaddles().get(0);
			Double last_x = myPaddle.getPaddleX();
			Ball myBall = Board_backend.getBalls().get(0);
			Double lastY = myBall.getCenterY();
			Double lastX = myBall.getCenterX();
			// Double ball_y = myBall.getCenterY();
			new_paddlePos = Board_UI.getPaddleX();
			int click_pos = Board_UI.getPClickX();
			if(LastClick != click_pos)
			{
				ClickDiff = ((Double)(click_pos - last_x)).intValue();
			}
			// Double x = 
			LastClick = click_pos;
			System.out.println("Click Diff : " + ClickDiff);
			System.out.println("Click posn : " + click_pos);
			System.out.println("New posn : " + new_paddlePos);
			// if ((Math.abs(new_paddlePos - last_x)) < (0.0001))
			// {

			// 	Board_backend.movePaddle(0,last_x,100, 100.0, 0, true);
			// }
			// else
			// {
			double final_posn_x = new_paddlePos - ClickDiff;
			if (final_posn_x < (myPaddle.getPaddleLength()/2.0) || final_posn_x > (1000.0 - (myPaddle.getPaddleLength()/2.0) ))
			{
				// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
			}
			else
			{
				Board_backend.movePaddle(0,new_paddlePos - ClickDiff,100, 100.0, 0, true);
			}		
			// }
			M += 1;
			// System.out.println("Ball Position : " + ball_y);
			Board_backend.moveBall(0,myBall.getVelX(), myBall.getVelY(), myBall.getVelX() + lastX, myBall.getVelY() + lastY, 25);
		};
	};

	public static void update_Phy()  
	{
		// TODO: check info from other clients first!
		// check B2B, B2W, B2MyPaddle
		ArrayList<Ball> curr_Balls = Board_backend.getBalls();
		int no_balls = curr_Balls.size();
		Paddle myPaddle = Board_backend.getPaddles().get(Paddle_No);
		double myX = myPaddle.getPaddleX();
		Ball myBall = Board_backend.getBalls().get(0);
		double myY = myPaddle.getPaddleY();
		double myLen = myPaddle.getPaddleLength();
		for (int i = 0; i < no_balls ; i ++)
		{
			Ball ith = curr_Balls.get(i);
			double radius = ith.getRadius();
			double center_x = ith.getCenterX();
			double center_y = ith.getCenterY();
			double vel_cx = ith.getVelX();
			double vel_cy = ith.getVelY();
			// check B2MyPaddle
			boolean b2paddle = PEngine.collision_paddle(center_x, center_y, myLen, 1000, radius, myX, myY,40,Paddle_No + 1);
			if (b2paddle)
			{
				System.out.println("collision with myPaddle.");
				Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 25);
			}
			else
			{
				// detect B2Wall or b2Corner?
				int b2wall = PEngine.collision_wall(center_x, center_y, radius,1000.0);
				if (b2wall > 0)
				{
					System.out.println("collision of this ball with wall i.");
				}
				else
				{
					double l = 0.0; // TODO : whats this?
					int b2corner = PEngine.collision_corner(center_x, center_y, radius, 1000.0, l);
					if (b2corner > 0)
					{
						System.out.println("colln with corner.");
					}
					else
					{
						// now check b2b collision.
						boolean any_colln = false;
						for (int j = 0; j < i; j ++)
						{
							// distance b/w centers.
							Ball jth = curr_Balls.get(i);
							double rad2 = jth.getRadius();
							double center_x2 = jth.getCenterX();
							double center_y2 = jth.getCenterY();
							double cc_dist = Math.sqrt( (center_x - center_x2)*(center_x - center_x2) + (center_y - center_y2)*(center_y - center_y2) );
							if (Math.abs(cc_dist - rad2 - radius) < 0.001)
							{
								// ball 2 ball
								any_colln = true;
								// vel_b2b new_vel_b1_b2 = 
							}
						}
						if (!any_colln)
						{
							// NO COLLISION.
							System.out.println("No collision, ball moved fwd.");
							Board_backend.moveBall(i,vel_cx , vel_cy, center_x + vel_cx,center_y + vel_cy,radius);
						}
					}
				}
			}

		}
	}


}