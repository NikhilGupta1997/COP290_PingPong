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
	private static int lastBwall = 0;
	private static int lastpaddle= 0;
	private static int lastBcorner = 0;
	//1 for above wall,2 for right wall and so on...... 
	// 5 for top paddle and so on 

	public Player()
	{
		// a Board object
		Board_backend = new Board();
		Ball b = new Ball(3.5*0.4, -4.5*.4, 200.0, 250.0, 10);
		Board_backend.addBall(b);
		Paddle p = new Paddle(100.0, 400.0, 0.0, 0,true);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 0.0, 400.0, 0,true);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 400.0, 600.0, 0,true);
		Board_backend.addPaddle(p);
		 p = new Paddle(100.0, 600.0, 400.0, 0,true);
		Board_backend.addPaddle(p);
		Board_UI = new GameBoard();
		timerDelay = 20;
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
		//	System.out.println("Timer working!");
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
			// System.out.println("Click Diff : " + ClickDiff);
			// System.out.println("Click posn : " + click_pos);
			// System.out.println("New posn : " + new_paddlePos);
			// // if ((Math.abs(new_paddlePos - last_x)) < (0.0001))
			// {

			// 	Board_backend.movePaddle(0,last_x,100, 100.0, 0, true);
			// }
			// else
			// {
			double final_posn_x = new_paddlePos - ClickDiff;
			if (final_posn_x < (myPaddle.getPaddleLength()/2.0) || final_posn_x > (600.0 - (myPaddle.getPaddleLength()/2.0) ))
			{
				// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
			}
			else
			{
				if(new_paddlePos - ClickDiff > 490)
				Board_backend.movePaddle(0,490,0, 100.0, 0, true);
				else if(new_paddlePos - ClickDiff <110)
				Board_backend.movePaddle(0,110,0, 100.0, 0, true);
				else
				Board_backend.movePaddle(0,new_paddlePos - ClickDiff,0, 100.0, 0, true);

				if(myBall.getCenterY() > 490)
				Board_backend.movePaddle(1,0,490, 100.0, 0, true);
				else if(myBall.getCenterY() <110)
				Board_backend.movePaddle(1,0,110, 100.0, 0, true);
				else
				Board_backend.movePaddle(1,0,myBall.getCenterY(), 100.0, 0, true);

				if(myBall.getCenterX() > 490)
				Board_backend.movePaddle(2,490,600, 100.0, 0, true);
				else if(myBall.getCenterX() <110)
				Board_backend.movePaddle(2,110,600, 100.0, 0, true);
				else
				Board_backend.movePaddle(2,myBall.getCenterX(),600, 100.0, 0, true);

				if(myBall.getCenterY() > 490)
				Board_backend.movePaddle(3,600,490, 100.0, 0, true);
				else if(myBall.getCenterY() <110)
				Board_backend.movePaddle(3,600,110, 100.0, 0, true);
				else
				Board_backend.movePaddle(3,600,myBall.getCenterY(), 100.0, 0, true);
			}		
			// }
			M += 1;
			// System.out.println("Ball Position : " + ball_y);
			Board_backend.moveBall(0,myBall.getVelX(), myBall.getVelY(), myBall.getVelX() + lastX, myBall.getVelY() + lastY, 10);
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
			boolean b2paddle = PEngine.collision_paddle(center_x, center_y, myLen+40, 600, radius, myX, myY,20,Paddle_No + 1);
			boolean check_wall_paddle=(lastBwall+4 == Paddle_No + 5) ;
			// b2paddle=false;
			if (b2paddle && lastpaddle!=Paddle_No+5&& !check_wall_paddle)
			{ lastpaddle=Paddle_No+ 5;
				lastBwall=0; lastBcorner=0;
				System.out.println("collision with myPaddle.");
				Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
			}
			else
			{
				// detect B2Wall or b2Corner?
				//System.out.println("Radius is \n\n\n\n"+radius+"\n\n\n");
				int b2wall = PEngine.collision_wall(center_x, center_y, radius,600.0);
				boolean check_paddle_wall=(b2wall+4== lastpaddle) ;
				if(b2wall > 0 && b2wall != lastBwall && !check_paddle_wall)
				{   lastpaddle=0; lastBcorner=0;
					System.out.println("collision of this ball with wall " + b2wall + "\t" + b2wall);
					lastBwall = b2wall;
					if(b2wall == 1)
					{Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);}
					else if(b2wall == 2)
					{Board_backend.moveBall(0,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);}
					else if(b2wall == 3)
					{Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);}
					else if(b2wall == 4)
					{Board_backend.moveBall(0,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);}
					
				}
				
				else
				{
					double l = 0.0; // TODO : whats this?
					int b2corner = PEngine.collision_corner(center_x, center_y, radius, 600.0, 60.0);
					//b2corner=0
					//System.out.println(l);
					if (b2corner > 0 && b2corner!=lastBcorner)
					{ lastBcorner=b2corner;
					lastBwall=0;
					lastpaddle=0;	
					if(b2corner%2==1)
						Board_backend.moveBall(i,-vel_cy , -vel_cx, center_x ,center_y ,radius);
					else	
						Board_backend.moveBall(i,vel_cy , vel_cx, center_x ,center_y ,radius);
						System.out.println("colln with corner.-- "+ b2corner);
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
							//System.out.println("No collision, ball moved fwd.");
							Board_backend.moveBall(i,vel_cx , vel_cy, center_x + vel_cx,center_y + vel_cy,radius);
						}
					}
				}
			}

		}
	}


}