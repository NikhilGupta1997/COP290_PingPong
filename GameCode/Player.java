import java.awt.*;
import Model.*;
import View.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.*;
import Physics.*;
import java.net.*;

public class Player
{
	/** This class acts as view model, linking model and view. Array of Boards! **/
	private static Board Board_backend;
	private static Game MyGame;
	private static GameBoard Board_UI;
	private static int timerDelay;
	private static Timer gameTimer;
	private static int Paddle_No;
	private static String PlayerName;
	private static int GameLevel;
	private static int player_no=0;	
	private static int M;
	private static int ClickDiff;
	private static int ClickDiff_Y;
	private static double new_paddlePos;
	private static double new_paddlePos_Y;

	private static int LastClick = 0;
	private static int LastClick_Y = 0;
	private static physics PEngine;
	private static int lastBwall = 0; // array for multiple balls
	private static int lastpaddle= 0;
	private static int lastBcorner = 0;
	private static int ball_missed = 0;	
	private static int [] player_desc= new int[4];//Player_desc for all players 
	private static int Collide_paddle = 0;// we need to make array when no of balls grow
	//1 for above wall,2 for right wall and so on...... 
	// 5 for top paddle and so on 
		private static String sentence1;
			private static byte[] sendData = new byte[1024];    
			private static DatagramSocket clientSocket;//
			private static InetAddress IPAddress;
			private static DatagramPacket sendPacket;  
			private static String destPort;
			//clientSocket = new DatagramSocket();


				   // wE NEED TO BIND IT TO ANOTHER MACHINE FROM HERE
			private static int port1=180;
			 private static DatagramSocket serverSocket;//
				   private static byte[] receiveData = new byte[1024]; 
				   private static DatagramPacket receivePacket;
				   private static String sentence;
				   private static String servPort;
				   private static boolean packetStatus;
				   

	public Player(String pname, int plevel)
	{
		// a Board object
		Board_backend = new Board();
		Ball b;
		//if(player_no==0)
		 b = new Ball(3.5*1.4, -4.5*1.4, 200.0, 250.0, 10);
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
		timerDelay = 70;
		gameTimer = new Timer(timerDelay, timerAction);
		gameTimer.start();
		M =0;
		PEngine = new physics();
		PlayerName = pname;
		GameLevel = plevel;
		MyGame = new Game();
		// need to put IPs, Ports in Game object.
		try
		{
			serverSocket = new DatagramSocket(port1);
			clientSocket=new DatagramSocket();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	static ActionListener timerAction = new ActionListener()
	{ 
		
				   



		@Override
		public void actionPerformed(ActionEvent event)
		{ try {
		//	System.out.println("Timer working!");
			// call Board_UI ka update function.

			player_no=0;
			int player_d0=player_desc[0],player_d1=player_desc[1],player_d2=player_desc[2],player_d3=player_desc[3];
			player_d0=3;player_d1=3;player_d2=3;player_d3=1;
			for(int ik=0;ik<4;ik++)
			{
				if(player_desc[ik]==1) 
				{player_no=ik;
					break;
				} 
			}
			// player_desc is 1 for current player,2 for other and 3 for computer

			update_Phy();// parameters: Collision with paddle ,we need to ignore other collisions of the same wall
			ArrayList<Ball> updatedBalls = Board_backend.getBalls();
			ArrayList<Paddle> updatedPaddles = Board_backend.getPaddles();
			Board_UI.reDraw(updatedBalls, updatedPaddles);

			Paddle myPaddle2 = Board_backend.getPaddles().get(1);
			Paddle myPaddle3 = Board_backend.getPaddles().get(2);
			Paddle myPaddle4 = Board_backend.getPaddles().get(3);
			Paddle myPaddle = Board_backend.getPaddles().get(0);
			
			// TODO: Important to change the paddle no 

			Double last_x = myPaddle.getPaddleX();
			Double last_y = myPaddle.getPaddleY();
			Ball myBall = Board_backend.getBalls().get(0);
			Double lastY = myBall.getCenterY();
			Double lastX = myBall.getCenterX();
			// Double ball_y = myBall.getCenterY();
			new_paddlePos = Board_UI.getPaddleX();
			new_paddlePos_Y= Board_UI.getPaddleY();
			int click_pos = Board_UI.getPClickX();
			int click_pos_y=Board_UI.getPClickY();

			// IPAddress = InetAddress.getByName("10.192.62.5");//// HAS TO BE THE ONE OF THE OTHER MACHINE
			// String resendPLZ = Collide_paddle+","+myPaddle.getPaddleX()+","+myPaddle.getPaddleY()+","+myBall.getVelX()+","+myBall.getVelY()+","+myPaddle.getBallMissed()+" ";
			// // We nedd to set it according to our variables 
			// sendData=new byte[1024];
			// sendData = resendPLZ.getBytes();
			// int port=190;// handle it
			// sendPacket = new DatagramPacket(sendData, resendPLZ.length(), IPAddress, port);
			// clientSocket.send(sendPacket);

						
			// 			 System.out.println("The client socket in made");
			// 					receivePacket = new DatagramPacket(receiveData, receiveData.length);     
			// 					System.out.println("The packet is made");       
			// 					serverSocket.receive(receivePacket);//
			// 					System.out.println("The packet is received");  
			// 				 //  System.out.println("The connected address is-");
			// 				   String temp = new String(receivePacket.getData());
			// 				   //System.out.println(receivePacket.getAddress().getHostAddress() + " : " + receivePacket.getPort() +" length is"+ temp.length());
   //            	double yourpaddle_x,yourpaddle_y,ball_vel_cx,ball_vel_cy; 
   //           	boolean collision_happened;

   //               String [] temp2 = 	temp.split(" ");
   //               String [] tokens=temp2[0].split(",");
   //               yourpaddle_x=Double.parseDouble(tokens[1]);
   //               yourpaddle_y=Double.parseDouble(tokens[2]);
   //               ball_vel_cx=Double.parseDouble(tokens[3]);
   //               ball_vel_cy=Double.parseDouble(tokens[4]);
   //               ball_missed =Integer.parseInt(tokens[5]);
   //               collision_happened=(Double.parseDouble(tokens[0])==1.0);
   //               			   System.out.println("The value received is "+ new String(receivePacket.getData()));

			if(LastClick != click_pos)
			{
				ClickDiff = ((Double)(click_pos - last_x)).intValue();
			}
			if(LastClick_Y!= click_pos_y)
			{
				ClickDiff_Y = ((Double)(click_pos_y - last_y)).intValue();
			}
			// Double x = 
			LastClick = click_pos;
			LastClick_Y= click_pos_y;
			// TO ASK: What was all this?
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
			double final_posn_y = new_paddlePos_Y - ClickDiff_Y;
			if(player_no==0 || player_no==2)
			{if (final_posn_x < (myPaddle.getPaddleLength()/2.0) || final_posn_x > (600.0 - (myPaddle.getPaddleLength()/2.0) ))
			{
				// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
			}

			}
			else 
			{
			if (final_posn_y < (myPaddle.getPaddleLength()/2.0) || final_posn_y > (600.0 - (myPaddle.getPaddleLength()/2.0) ))
			{
				// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
			}

			}


			// if(false);
			// else// TODO:We need to change depending on player no. we are
			// {
				if(new_paddlePos - ClickDiff > 490 && player_no==0 && player_d0==1)
				Board_backend.movePaddle(0,490,0, 100.0, myPaddle.getBallMissed(), true);
				else if(new_paddlePos - ClickDiff <110 && player_no==0 &&player_d0==1)
				Board_backend.movePaddle(0,110,0, 100.0, myPaddle.getBallMissed(), true);
				else if( player_no==0&&player_d0==1)
				Board_backend.movePaddle(0,new_paddlePos - ClickDiff,0, 100.0, myPaddle.getBallMissed(), true);
				else if(player_d0==3&&myBall.getCenterX() < 110)
				{//Board_backend.movePaddle(0,new_paddlePos - ClickDiff,0, 100.0, myPaddle.getBallMissed(), true);
				// Here I will set my paddle accordingly depending on what i receive
					Board_backend.movePaddle(0,110,0, 100.0, ball_missed, true);// when the other one is player 0;
				}
				else if (player_d0==3&&myBall.getCenterX() > 490) 
				{Board_backend.movePaddle(0,490,0, 100.0, ball_missed, true);		
				}
				else if(player_d0==3)
				{Board_backend.movePaddle(0,myBall.getCenterX(),0, 100.0, myPaddle.getBallMissed(), true);
				}
				else
				{
					//TODO: It receives from other player and updates its own board
				}


				// For second player on left wall
				if(new_paddlePos_Y - ClickDiff_Y > 490 && player_d1==1)
				{
					Board_backend.movePaddle(1,0,490, 100.0, myPaddle.getBallMissed(), true);
				}
				else if(new_paddlePos_Y - ClickDiff_Y <110&& player_d1==1)
				{
					Board_backend.movePaddle(1,0,110, 100.0, myPaddle.getBallMissed(), true);		
				}
				else if(player_d1==1)
				{
					Board_backend.movePaddle(1,0,new_paddlePos_Y - ClickDiff_Y, 100.0, myPaddle.getBallMissed(), true);

				}
				else if(myBall.getCenterY() > 490 && player_d1==3)
					Board_backend.movePaddle(1,0,490, 100.0, myPaddle2.getBallMissed(), true);
				else if(myBall.getCenterY() <110 && player_d1==3 )
					Board_backend.movePaddle(1,0,110, 100.0, myPaddle2.getBallMissed(), true);
				else if( player_d1==3)
					Board_backend.movePaddle(1,0,myBall.getCenterY(), 100.0, myPaddle2.getBallMissed(), true);
				else
				{
					// Depends on what it receives from other players
				}

				// For third player on the bottom part
				if(new_paddlePos - ClickDiff > 490 &&  player_d2==1)
					Board_backend.movePaddle(2,490,600, 100.0, myPaddle.getBallMissed(), true);
				else if(new_paddlePos - ClickDiff <110 && player_d2==1)
					Board_backend.movePaddle(2,110,600, 100.0, myPaddle.getBallMissed(), true);
				else if( player_d2==1)
					Board_backend.movePaddle(2,new_paddlePos - ClickDiff,600, 100.0, myPaddle.getBallMissed(), true);
				else if(player_d2==3&&myBall.getCenterX() < 110)
				{//Board_backend.movePaddle(0,new_paddlePos - ClickDiff,0, 100.0, myPaddle.getBallMissed(), true);
				// Here I will set my paddle accordingly depending on what i receive
					Board_backend.movePaddle(2,110,600, 100.0, ball_missed, true);// when the other one is player 0;
				}
				else if (player_d2==3&&myBall.getCenterX() > 490) 
				{
					Board_backend.movePaddle(2,490,600, 100.0, ball_missed, true);		
				}
				else if(player_d2==3)
				{
					Board_backend.movePaddle(2,myBall.getCenterX(),600, 100.0, myPaddle.getBallMissed(), true);
				}
				else
				{
					//TODO: It receives from other player and updates its own board
				}

				// for the fourth player
				if(new_paddlePos_Y - ClickDiff_Y > 490&& player_d3==1)
				{
					Board_backend.movePaddle(3,600,490, 100.0, myPaddle.getBallMissed(), true);
				}
				else if(new_paddlePos_Y - ClickDiff_Y <110&& player_d3==1)
				{
					Board_backend.movePaddle(3,600,110, 100.0, myPaddle.getBallMissed(), true);		
				}
				else if(player_d3==1)
				{ System.out.println("dsd");
					Board_backend.movePaddle(3,600,new_paddlePos_Y - ClickDiff_Y, 100.0, myPaddle.getBallMissed(), true);
				}
				else if(myBall.getCenterY() > 490 && player_d3==3)
					Board_backend.movePaddle(3,600,490, 100.0, myPaddle2.getBallMissed(), true);
				else if(myBall.getCenterY() <110 && player_d3==3 )
					Board_backend.movePaddle(3,600,110, 100.0, myPaddle2.getBallMissed(), true);
				else if( player_d3==3)
					Board_backend.movePaddle(3,600,myBall.getCenterY(), 100.0, myPaddle2.getBallMissed(), true);
				else
				{
					// Depends on what it receives from other players
				}

				/////////////
				// if(myBall.getCenterX() > 490 )
				// Board_backend.movePaddle(2,490,600, 100.0, myPaddle3.getBallMissed(), true);
				// else if(myBall.getCenterX() <110 && player_no==2)
				// Board_backend.movePaddle(2,110,600, 100.0, myPaddle3.getBallMissed(), true);
				// else if( player_no==2)
				// Board_backend.movePaddle(2,new_paddlePos - ClickDiff,0, 100.0, myPaddle.getBallMissed(), true);
				// // NEED NIKHIL: working of ball missed
				// else
				// Board_backend.movePaddle(2,600-yourpaddle_x,600, 100.0, ball_missed, true);

				// if(myBall.getCenterY() > 490)
				// Board_backend.movePaddle(3,600,490, 100.0, myPaddle4.getBallMissed(), true);
				// else if(myBall.getCenterY() <110)
				// Board_backend.movePaddle(3,600,110, 100.0, myPaddle3.getBallMissed(), true);
				// else
				// Board_backend.movePaddle(3,600,myBall.getCenterY(), 100.0, myPaddle3.getBallMissed(), true);
				
			 
			M += 1;
			// System.out.println("Ball Position : " + ball_y);

// TODO:We need to change depending on player no. we are
			Board_backend.moveBall(0,myBall.getVelX(), myBall.getVelY(), myBall.getVelX() + lastX, myBall.getVelY() + lastY, 10);
			// if(!collision_happened) Board_backend.moveBall(0,myBall.getVelX(), myBall.getVelY(), myBall.getVelX() + lastX, myBall.getVelY() + lastY, 10);
			// else Board_backend.moveBall(0,-ball_vel_cx, -ball_vel_cy, -ball_vel_cx + lastX, -ball_vel_cy + lastY, 10);
			///Send changes
			
}catch(Exception e)
		{
			e.printStackTrace();
		}

		};
	};

	public static void update_Phy()  
	{   Collide_paddle=0;
		ball_missed=0;
		Paddle_No=player_no;
		Paddle_No=3;
		//System.out.println("My paddle no-"+Paddle_No);
		// TODO: check info from other clients first!
		// check B2B, B2W, B2MyPaddle
		ArrayList<Ball> curr_Balls = Board_backend.getBalls();
		ArrayList<Paddle> curr_Paddles = Board_backend.getPaddles();
		int no_balls = curr_Balls.size();
		Paddle myPaddle = Board_backend.getPaddles().get(Paddle_No);
		double myX = myPaddle.getPaddleX();
		Ball myBall = Board_backend.getBalls().get(0);
		double myY = myPaddle.getPaddleY();
		double myLen = myPaddle.getPaddleLength();
		//double pos_pad_x=myPaddle.
		//System.out.println("pos of my paddle--"+myX+","+myY);
		for (int i = 0; i < no_balls ; i ++)
		{
			Ball ith = curr_Balls.get(i);
			double radius = ith.getRadius();
			double center_x = ith.getCenterX();
			double center_y = ith.getCenterY();
			double vel_cx = ith.getVelX();
			double vel_cy = ith.getVelY();
			Paddle myPaddle1 = curr_Paddles.get(0);
			Paddle myPaddle2 = curr_Paddles.get(1);
			Paddle myPaddle3 = curr_Paddles.get(2);
			Paddle myPaddle4 = curr_Paddles.get(3);

			// check B2MyPaddle
			// TODO:We need to change depending on player no. we are: just set the player no and its quite generic

			boolean b2paddle = PEngine.collision_paddle(center_x, center_y, myLen+40, 600, radius, myX, myY,20,Paddle_No + 1);
			boolean check_wall_paddle=(lastBwall+4 == Paddle_No + 5);
			// b2paddle=false;
			if (b2paddle && lastpaddle!=Paddle_No+5&& !check_wall_paddle)
			{   lastpaddle=Paddle_No+ 5;
				lastBwall=0; lastBcorner=0;

				Collide_paddle=1;
				System.out.println("collision with myPaddle.");
				if(Paddle_No==0)
				Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
				else if(Paddle_No==1)
				Board_backend.moveBall(0,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
				else if(Paddle_No==2)
				Board_backend.moveBall(0,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
				else 
				Board_backend.moveBall(0,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
							
			}
			else
			{   double k=1.01;
				// detect B2Wall or b2Corner?
				//System.out.println("Radius is \n\n\n\n"+radius+"\n\n\n");
				int b2wall = PEngine.collision_wall(center_x, center_y, radius,600.0);
				boolean check_paddle_wall=(b2wall+4== lastpaddle) ;
				if(b2wall > 0 && b2wall != lastBwall && !check_paddle_wall)
				{   lastpaddle=0;
				    lastBcorner=0;
					System.out.println("collision of this ball with wall " + b2wall + "\t" + b2wall);
					lastBwall = b2wall;

					//TODO: Change the get missed ball parameter
					if(b2wall == 1)
					{Board_backend.moveBall(0,k*myBall.getVelX(), -k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					Board_backend.movePaddle(0,myPaddle1.getPaddleX(),myPaddle1.getPaddleY(), 100.0, myPaddle1.getBallMissed() + 1, true);
					}
					else if(b2wall == 2)
					{Board_backend.moveBall(0,-k*myBall.getVelX(), k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					Board_backend.movePaddle(1,myPaddle2.getPaddleX(),myPaddle2.getPaddleY(), 100.0, myPaddle2.getBallMissed() + 1, true);
					}
					else if(b2wall == 3)
					{Board_backend.moveBall(0,k*myBall.getVelX(), -k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					Board_backend.movePaddle(2,myPaddle3.getPaddleX(),myPaddle3.getPaddleY(), 100.0, myPaddle3.getBallMissed() + 1, true);
					}
					else if(b2wall == 4)
					{Board_backend.moveBall(0,-k*myBall.getVelX(), k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					Board_backend.movePaddle(3,myPaddle4.getPaddleX(),myPaddle4.getPaddleY(), 100.0, myPaddle4.getBallMissed() + 1, true);
					}
					
				}
				
				else
				{
					double l = 0.0; // TODO : whats this?
					int b2corner = PEngine.collision_corner(center_x, center_y, radius, 600.0, 60.0);
					//b2corner=0
					//System.out.println(l);
					boolean c1=true,c2=true,c3=true,c4=true,check=true;
					if(b2corner==1&&(lastBwall==1||lastBwall==4)) c1=false;
					if(b2corner==2&&(lastBwall==1||lastBwall==2)) c2=false;
					if(b2corner==3&&(lastBwall==2||lastBwall==3)) c3=false;
					if(b2corner==4&&(lastBwall==3||lastBwall==4)) c4=false;
					check=c1&&c2&&c3&&c4; 
					if (b2corner > 0 && b2corner!=lastBcorner && check)
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