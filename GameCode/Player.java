import java.awt.*;
import Model.*;
import View.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import Physics.*;
import java.net.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

public class Player {
	/** This class acts as view model, linking model and view.
	* No of receiver threads = no of other players playing with the current one. **/
	private static ArrayList<ReceiverThread> RecieveThreads;
	private static ArrayList<String> gl_other_ips=new ArrayList<String>();
	private static int[][] gl_other_ports;
	private static ArrayList<String> gl_names;
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
	private static double vel_compu=3.0;
	private static int no_players;
	private static int gl_level;
	private static double [] length_paddle=new double[4];
	private static int lostl;
	private static int server;
	private static int LastClick = 0;
	private static int LastClick_Y = 0;
	private static physics PEngine;
	private static int [] lastBwall; // array for multiple balls
	private static int lastRWall;
	private static int [] lastpaddle;
	private static int lastRpaddle;
	private static int [] lastBcorner;
	private static int lastRcorner;
	private static int [] ball_missed = new int[5];
	private static int [] player_desc = new int[4];//Player_desc for all players 
	private static int [] Collide_paddle ;// we need to make array when no of balls grow
	//1 for above wall,2 for right wall and so on...... 
	// 5 for top paddle and so on 
	private static String sentence1;
	private static byte[] sendData = new byte[1024];    
	private static DatagramSocket clientSocket;//
	private static InetAddress IPAddress;
	private static DatagramPacket sendPacket;  
	private static String destPort;
	private static boolean[] ball_colln;
	private static boolean[] Connection;
			//clientSocket = new DatagramSocket();

				   // wE NEED TO BIND IT TO ANOTHER MACHINE FROM HERE
	private static int port1=180;

	 // private static DatagramSocket serverSocket;//
		//    private static byte[] receiveData = new byte[1024]; 
		//    private static DatagramPacket receivePacket;
    private static String sentence;
    private static String servPort;
    private static boolean packetStatus;
    private static int[] lastBBCollision;
    private static boolean colln_random;
    private static int random_paddle_no;
    private static int random_type;

    // Just returns the average of the input
	public static double average(double a, double b, double c, double d) {
		return (a+b+c+d)/4.0;
	}			
				   
	// Returns the closest ball to the Player paddle
	public static void closest_ball(ArrayList<Ball> array_balls, Ball[] Close_ball) {	
		Ball zero = array_balls.get(0);
		int[] closest = {0,0,0,0};
		double[] smallest_dis = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};
		for (int i = 0; i < array_balls.size(); i++) {
			Ball xball = array_balls.get(i);
			double center_x1 = xball.getCenterX();
			double center_y1 = xball.getCenterY();
			double vel_cx1 = xball.getVelX();
			double vel_cy1 = xball.getVelY();
			// Update the closest ball for each paddle
			if(Math.abs((center_y1 - 10)/vel_cy1) < smallest_dis[0] && vel_cy1 < 0) {
				smallest_dis[0] = Math.abs((center_y1 - 10)/vel_cy1);
				closest[0] = i;
			}
			if(Math.abs((center_x1 - 10)/(vel_cx1)) < smallest_dis[1] && vel_cx1 < 0) {
				smallest_dis[1] = Math.abs((center_x1 - 10)/(vel_cx1));
				closest[1] = i;
			}
			if(Math.abs((570 - center_y1)/(vel_cy1)) < smallest_dis[2] && vel_cy1 > 0) {
				smallest_dis[2] = Math.abs((570 - center_y1)/(vel_cy1));
				closest[2] = i;
			}
			if(Math.abs((570 - center_x1)/(vel_cx1)) < smallest_dis[3] && vel_cx1 > 0) {
				smallest_dis[3] = Math.abs((570 - center_x1)/(vel_cx1));
				closest[3] = i;
			}
		}
		// Construct Ball List
		for(int i = 0; i < 4; i++)
			Close_ball[i] = array_balls.get(closest[i]);
	}	

	public Player(String pname, int plevel, ArrayList<String> other_ips, int [] [] other_ports, ArrayList<String> names, int p_no) {	
		// a Board object
		System.out.println("IPS="+other_ips+",,,,"+"Ports="+other_ports+"....Names"+names) ;
		no_players=names.size();
		//gl_other_ports=new int[no_players][no_players];
		for(int i=0;i<no_players;i++) {
			for(int i1=0;i1<no_players;i1++)
				System.out.print(other_ports[i][i1]);
			System.out.println("");
		}
		
		Collide_paddle = new int[plevel];
		gl_other_ips = other_ips;
		gl_other_ports = other_ports;
		gl_names = names;
		gl_level = plevel;

		// This function controls the AI paddle speed for each level
		vel_compu = 3.5 + (plevel - 0.9)*4.3;

		Connection = new boolean[(no_players - 1)];
		System.out.println("Received level :" + plevel);
		for(int i=0;i<4;i++) {
			if(i>=(no_players)) 
				player_desc[i]=3;
			else if(i==p_no) 
				player_desc[i]=1;
			else 
				player_desc[i]=2;	
		}
		// Player Description done
		Board_backend = new Board();
		Ball [] b=new Ball[5];
		b[0] = new Ball(-4.5*1.4, 3.5*1.4, 250.0, 280.0, 10);
		b[1] = new Ball(4.5*1.4, 3.5*1.4, 150.0, 280.0, 10);
		b[2] = new Ball(2.5*1.4, 5.5*1.2, 350.0, 180.0, 10);
		b[3] = new Ball(4.5*1.2, 2.5*1.4, 150.0, 180.0, 10);
		b[4] = new Ball(4.0*1.1, 2.5*1.4, 50.0, 200.0, 10);
		 
		int c = 0;
		//if(player_no==0)
		for (int i = 0; i < plevel; i ++) {
			// Put code of random ball here. TODO
			// speed prop to level.
			Board_backend.addBall(b[i]);
			c++;		
		}

		ball_colln = new boolean[plevel];

		lastBBCollision = new int[plevel];
		for (int i = 0; i < plevel; i ++)
			lastBBCollision[i] = -1;
		Paddle p = new Paddle(100.0, 400.0, 0.0, 0,true);
		Board_backend.addPaddle(p);
		p = new Paddle(150.0, 0.0, 400.0, 0,true);
		Board_backend.addPaddle(p);
		p = new Paddle(100.0, 400.0, 600.0, 0,true);
		Board_backend.addPaddle(p);
		p = new Paddle(100.0, 600.0, 400.0, 0,true);
		Board_backend.addPaddle(p);

		// Board_backend.addObject(r1);
		// Board_UI = new GameBoard();
		RandomObj r1 = new RandomObj(5.5, 4.5, 250, 150, 0);
		Board_backend.addObject(r1);
		Board_UI = new GameBoard(p_no);
		lastBwall =new int[c]; // array for multiple balls
		lastpaddle=new int[c];
	 	lastBcorner=new int[c];

	 	lastRWall = 0;
	 	lastRpaddle = 0;
	 	lastRcorner = 0;
	 	//ball_missed=new int[c];
	 	for (int i=0;i<c;i++) {
	 		lastBwall[i]=0;lastpaddle[i]=0;lastBcorner[i]=0;
	 	}
		timerDelay = 25;
		gameTimer = new Timer(timerDelay, timerAction);
		gameTimer.start();
		M =0;
		PEngine = new physics();
		PlayerName = pname;
		GameLevel = plevel;
		MyGame = new Game();
		Paddle_No = p_no;
		// need to put IPs, Ports in Game object.
		try {
			clientSocket=new DatagramSocket();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("safd");
		RecieveThreads = new ArrayList<ReceiverThread>();
		int f1=0;
		for(int i=0;i<no_players;i++) {
			if(i==p_no) 
				continue;
			RecieveThreads.add(new ReceiverThread(other_ports[i][p_no],plevel));// listens on this port
			//System.out.println("Port: "+other_ports.get(i));
			Connection[f1]=true;
			System.out.println("connection is true for ");
			RecieveThreads.get(f1).start();
			f1++;
		}
		player_no=p_no;
		//System.out.println("End Received "+no_players);
		//working
	}
	
	static ActionListener timerAction = new ActionListener() { 
		/** Describes the action performed by timer after every fixed amount of time. **/
		@Override
		public void actionPerformed(ActionEvent event) { 
			try {
				// call Board_UI ka update function.
				ArrayList<Ball> curr_Balls = Board_backend.getBalls();
				int no_balls=curr_Balls.size();
				int f1 = 0;
				for(int i=0;i<no_players;i++) {
					if(i == player_no) 
						continue;
					Connection[f1]=(RecieveThreads.get(f1)).isConnected;
					if(!Connection[f1]) {	
						System.out.println("dsadsa");	
						gl_names.set(i,"DISCONNECTED");
						player_desc[i]=3;
					}
					f1++;
				}
				int player_d0=player_desc[0],player_d1=player_desc[1],player_d2=player_desc[2],player_d3=player_desc[3];
				// System.out.println(player_d0+","+player_d1+","+player_d2+","+player_d3);
				// player_desc is 1 for current player,2 for other and 3 for computer
				server=-1;// change 1
				for (int i=0;i<4;i++) {
					if(player_desc[i]==1|| player_desc[i]==2) {
						server=i; // Server decides which one controls the game
						break;
					}
				}
				// System.out.println("Player and server are="+player_no+","+server);
				if(Board_UI.getWinner() == 1) {
					JOptionPane.showMessageDialog(null,"Click ok to start the server 1" );
					Board_UI.setVisible(false);
					Board_UI.setReset();
					Board_UI.setDefaultCloseOperation(Board_UI.EXIT_ON_CLOSE);
					PingPong.main(null);
					gameTimer.stop();
				}
				else if(Board_UI.getWinner() == 2) {
					JOptionPane.showMessageDialog(null,"Click ok to start the server 2" );
					Board_UI.setVisible(false);
					Board_UI.setReset();
					Board_UI.setDefaultCloseOperation(Board_UI.EXIT_ON_CLOSE);
					PingPong.main(null);
					gameTimer.stop();
				}
				else if(Board_UI.getWinner() == 3) {
					JOptionPane.showMessageDialog(null,"Click ok to start the server 3" );
					Board_UI.setVisible(false);
					Board_UI.setReset();
					Board_UI.setDefaultCloseOperation(Board_UI.EXIT_ON_CLOSE);
					PingPong.main(null);
					gameTimer.stop();
				}
				else if(Board_UI.getWinner() == 4) {
					JOptionPane.showMessageDialog(null,"Click ok to start the server 4" );
					Board_UI.setVisible(false);
					Board_UI.setReset();
					Board_UI.setDefaultCloseOperation(Board_UI.EXIT_ON_CLOSE);
					PingPong.main(null);
					gameTimer.stop();
				}
				else 
				{}

				update_Phy();// parameters: Collision with paddle ,we need to ignore other collisions of the same wall
				update_Random();
				ArrayList<Ball> updatedBalls = Board_backend.getBalls();
				ArrayList<Paddle> updatedPaddles = Board_backend.getPaddles();
				RandomObj updatedObjects = Board_backend.getObjects();
				//System.out.println("lost=="+lostl+"\n\n\n\n\n");
				Board_UI.reDraw(updatedBalls, updatedPaddles, updatedObjects, gl_names);
				updatedPaddles = Board_backend.getPaddles();//Might Have to Remove it
				Paddle myPaddle2 = updatedPaddles.get(1);
				Paddle myPaddle3 = updatedPaddles.get(2);
				Paddle myPaddle4 = updatedPaddles.get(3);
				Paddle myPaddle1 = updatedPaddles.get(0);
				double [] length_paddle1={myPaddle1.getPaddleLength(),myPaddle2.getPaddleLength(),myPaddle3.getPaddleLength(),myPaddle4.getPaddleLength()};
				length_paddle=length_paddle1;
				Paddle myPaddle = updatedPaddles.get(player_no);
				//System.out.println(length_paddle[0]+","+length_paddle[1]+","+length_paddle[2]+","+length_paddle[3]);
				// TODO: Important to change the paddle no 
				// TODO : Handle collisions of Random Objs.

				Double last_x = myPaddle.getPaddleX();
				Double last_y = myPaddle.getPaddleY();
				Ball myBall = Board_backend.getBalls().get(0);// Brings redundancy
				Double lastY = myBall.getCenterY();
				Double lastX = myBall.getCenterX();
				// Double ball_y = myBall.getCenterY();
				new_paddlePos = Board_UI.getPaddleX();
				new_paddlePos_Y= Board_UI.getPaddleY();
				int click_pos = Board_UI.getPClickX();
				int click_pos_y=Board_UI.getPClickY();
				String resendPLZ="";
				for(int i=0;i<gl_level-1;i++)
					resendPLZ=resendPLZ+Collide_paddle[i]+"#";
				resendPLZ+=Collide_paddle[gl_level-1]+",";
				resendPLZ=resendPLZ+myPaddle.getPaddleX()+","+myPaddle.getPaddleY()+",";
				// vel of ball
				for(int i=0;i<gl_level-1;i++)
					resendPLZ=resendPLZ+Board_backend.getBalls().get(i).getVelX()+"#";
				resendPLZ=resendPLZ+Board_backend.getBalls().get(gl_level-1).getVelX()+",";
				for(int i=0;i<gl_level-1;i++)
					resendPLZ=resendPLZ+Board_backend.getBalls().get(i).getVelY()+"#";
				resendPLZ=resendPLZ+Board_backend.getBalls().get(gl_level-1).getVelY()+",";

				///Pos of ball
				for(int i=0;i<gl_level-1;i++)
					resendPLZ=resendPLZ+Board_backend.getBalls().get(i).getCenterX()+"#";
				resendPLZ=resendPLZ+Board_backend.getBalls().get(gl_level-1).getCenterX()+",";
				for(int i=0;i<gl_level-1;i++)
					resendPLZ=resendPLZ+Board_backend.getBalls().get(i).getCenterY()+"#";
				resendPLZ=resendPLZ+Board_backend.getBalls().get(gl_level-1).getCenterY()+",";
				//Now coming on to the lost life part
				resendPLZ=resendPLZ+lostl+",";
				///Random Object
				resendPLZ=resendPLZ+"0"+" ";
				//Note: For multiple balls something is wrong
				//System.out.println("Working "+ resendPLZ);
				//String resendPLZ = Collide_paddle+","+myPaddle.getPaddleX()+","+myPaddle.getPaddleY()+","+myBall.getVelX()+","+myBall.getVelY()+","+myPaddle.getBallMissed()+" ";
				// We nedd to set it according to our variables 
				// send consist of collide paddle array,followed by its paddle,then pos+x_ball array,ball_y ball array,ball_velx,ball_vely,lost life ya not,random objects
				sendData=new byte[1024];
				sendData = resendPLZ.getBytes();
				//int f2=0;
				for(int k=0;k<no_players;k++) {// Sending to other ports in sequential manner
					if(k==player_no) 
						continue;
					IPAddress = InetAddress.getByName(gl_other_ips.get(k));

					int port=gl_other_ports[player_no][k];// handle it
					// System.out.println("Sending to "+IPAddress+";;"+port);
					sendPacket = new DatagramPacket(sendData, resendPLZ.length(), IPAddress, port);
					clientSocket.send(sendPacket);
					//f2++;
				}
				double [] yourpaddle_x=new double[4];
	          	double [] yourpaddle_y=new double[4];
	          	double [] [] ball_vel_cx=new double[4][no_balls];
	          	double [] [] ball_vel_cy=new double[4][no_balls];
	          	double [] [] ball_pos_cx=new double[4][no_balls];
	          	double [] [] ball_pos_cy=new double[4][no_balls];
	              	
	         	boolean [][] collision_happened=new boolean[4][no_balls];
	         	int k=0;
	         	for(int i=0;i<no_players;i++) {
	         	 	if(i==player_no)
	             		continue;
	             	ReceiverThread receive=RecieveThreads.get(k);

	             	yourpaddle_x[i] = receive.rec_paddleX;
	             	yourpaddle_y[i] = receive.rec_paddleY;
	             	for(int k1=0;k1<no_balls;k1++) {
		             	// i is the player no
		             	collision_happened[i][k1]=receive.rec_collision_occur[k1];
		             	ball_vel_cx[i][k1] = receive.rec_ball_velX[k1];
		             	ball_vel_cy[i][k1] = receive.rec_ball_velY[k1]; // pick ith ball.
		             	ball_pos_cx[i][k1] = receive.balls_cx[k1]; // pick ith TODO
		             	ball_pos_cy[i][k1] = receive.balls_cy[k1];
	             	}
	             	// pick ith ball.
	             	
	             //	collision_happened= receive.rec_collision_occur;
	                k++;
	             	}
	             	int [] collision_paddle=new int[curr_Balls.size()];
	             	for(int i=0;i<curr_Balls.size();i++)
	             		{// ball no.
	             			collision_paddle[i]=-1;
	             			for(int k1=0;k<4;k++)
	             			{if(k1==player_no) continue;
	             			if(collision_happened[k1][i])
	             			{
	             				collision_paddle[i]=k1;
	             			}	
	             			}
	             		}

	            //Setting final values after receiving 
			    // System.out.println("The value received is "+ new String(receivePacket.getData()));

				if(LastClick != click_pos)
					ClickDiff = ((Double)(click_pos - last_x)).intValue();
				if(LastClick_Y!= click_pos_y)
					ClickDiff_Y = ((Double)(click_pos_y - last_y)).intValue();
				// Double x = 
				LastClick = click_pos;
				LastClick_Y = click_pos_y;
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
				if(player_no==0 || player_no==2) {
					if (final_posn_x < (myPaddle.getPaddleLength()/2.0) || final_posn_x > (600.0 - (myPaddle.getPaddleLength()/2.0) )) {
						// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
					}
				}
				else {
					if (final_posn_y < (myPaddle.getPaddleLength()/2.0) || final_posn_y > (600.0 - (myPaddle.getPaddleLength()/2.0) )) {
						// YOU DONT MOVE THE PADDLE. END OF SCREEN(X)
					}
				}

				///////////////////////////////////////////////Computer AI////////////////////////////////////////////////////////////////////////////////////////////
				Random randomo=new Random();
				
				Ball [] myBall_array = new Ball[4];// sets which ball is closest
				closest_ball(curr_Balls,myBall_array);
				// int random=randomo.nextInt(2500);
				double next_vel_1=Math.abs(myBall_array[0].getCenterX()-myPaddle1.getPaddleX())/(myBall_array[0].getCenterX()-myPaddle1.getPaddleX()) * vel_compu;
				//if(random==3)  next_vel_1*=-1;

				//random=randomo.nextInt(2500);
				double next_vel_3=Math.abs(myBall_array[2].getCenterX()-myPaddle3.getPaddleX())/(myBall_array[2].getCenterX()-myPaddle3.getPaddleX()) * vel_compu;
				//if(random==3)  next_vel_3*=-1;

				//random=randomo.nextInt(2500);
				double next_vel_2=Math.abs(myBall_array[1].getCenterY()-myPaddle2.getPaddleY())/(myBall_array[1].getCenterY()-myPaddle2.getPaddleY()) * vel_compu;
				//if(random==3)  next_vel_2*=-1;

				//random=randomo.nextInt(2500);
				double next_vel_4=Math.abs(myBall_array[3].getCenterY()-myPaddle4.getPaddleY())/(myBall_array[3].getCenterY()-myPaddle4.getPaddleY()) * vel_compu;
				//if(random==3)  next_vel_4*=-1;
				
				double next_pos_1;
				if(Math.abs(myBall_array[0].getCenterX()-myPaddle1.getPaddleX()) > length_paddle[0]/3)
					next_pos_1=myPaddle1.getPaddleX()+ next_vel_1;
				else
					next_pos_1=myPaddle1.getPaddleX();
				if(next_pos_1>(540-length_paddle[0]/2)) 
					next_pos_1=(540-length_paddle[0]/2);
				else if(next_pos_1<(60+length_paddle[0]/2)) 
					next_pos_1=(60+length_paddle[0]/2); 

				double next_pos_2;
				if(Math.abs(myBall_array[1].getCenterY()-myPaddle2.getPaddleY()) > length_paddle[1]/3)
					next_pos_2=myPaddle2.getPaddleY()+ next_vel_2;
				else
					next_pos_2=myPaddle2.getPaddleY();
				if(next_pos_2>(540-length_paddle[1]/2)) 
					next_pos_2=(540-length_paddle[1]/2);
				else if(next_pos_2<(60+length_paddle[1]/2)) 
					next_pos_2=(60+length_paddle[1]/2); 

				//System.out.println("Next Position "+next_pos_2+","+next_vel_2);
				double next_pos_3;
				if(Math.abs(myBall_array[2].getCenterX()-myPaddle3.getPaddleX()) > length_paddle[2]/3)
					next_pos_3=myPaddle3.getPaddleX()+ next_vel_3;
				else
					next_pos_3=myPaddle3.getPaddleX();
				if(next_pos_3>(540-length_paddle[2]/2)) 
					next_pos_3=(540-length_paddle[2]/2);
				else if(next_pos_3<(60+length_paddle[2]/2)) 
					next_pos_3=(60+length_paddle[2]/2); 
				
				double next_pos_4;
				if(Math.abs(myBall_array[3].getCenterY()-myPaddle4.getPaddleY()) > length_paddle[3]/3)
					next_pos_4=myPaddle4.getPaddleY()+ next_vel_4;
				else
					next_pos_4=myPaddle4.getPaddleY();
				if(next_pos_4>(540-length_paddle[3]/2)) 
					next_pos_4=(540-length_paddle[3]/2);
				else if(next_pos_4<(60+length_paddle[3]/2)) 
					next_pos_4=(60+length_paddle[3]/2); 
				
				// if(false);
				// else// TODO:We need to change depending on player no. we are
				// {
				if(new_paddlePos - ClickDiff > (540-length_paddle[0]/2) && player_no==0 && player_d0==1)
					Board_backend.movePaddle(0,(540-length_paddle[0]/2),0, length_paddle[0], myPaddle1.getBallMissed(), true);
				else if(new_paddlePos - ClickDiff <(60+length_paddle[0]/2) && player_no==0 &&player_d0==1)
					Board_backend.movePaddle(0,(60+length_paddle[0]/2),0, length_paddle[0], myPaddle1.getBallMissed(), true);
				else if( player_no==0&&player_d0==1)
					Board_backend.movePaddle(0,new_paddlePos - ClickDiff,0,length_paddle[0], myPaddle1.getBallMissed(), true);
				else if(player_d0==3&&myBall.getCenterX() < (60+length_paddle[0]/2)&&false)
					Board_backend.movePaddle(0,(60+length_paddle[0]/2),0, length_paddle[0], ball_missed[0], true);// when the other one is player 0;
				else if (player_d0==3&&myBall.getCenterX() > (540-length_paddle[0]/2)&&false) 
					Board_backend.movePaddle(0,(540-length_paddle[0]/2),0, length_paddle[0], ball_missed[0], true);		
				else if(player_d0==3)
					Board_backend.movePaddle(0,next_pos_1,0, length_paddle[0], myPaddle1.getBallMissed(), true);
				else
					Board_backend.movePaddle(0,yourpaddle_x[0],yourpaddle_y[0], length_paddle[0], myPaddle1.getBallMissed(), true);

				// For second player on left wall
				if(new_paddlePos_Y - ClickDiff_Y > (540-length_paddle[1]/2) && player_d1==1)
					Board_backend.movePaddle(1,0,(540-length_paddle[1]/2), length_paddle[1], myPaddle2.getBallMissed(), true);
				else if(new_paddlePos_Y - ClickDiff_Y <(60+length_paddle[1]/2)&& player_d1==1)
					Board_backend.movePaddle(1,0,(60+length_paddle[1]/2), length_paddle[1], myPaddle2.getBallMissed(), true);		
				else if(player_d1==1)
					Board_backend.movePaddle(1,0,new_paddlePos_Y - ClickDiff_Y, length_paddle[1], myPaddle2.getBallMissed(), true);
				else if(myBall.getCenterY() > (540-length_paddle[1]/2) && player_d1==3&&false)
					Board_backend.movePaddle(1,0,(540-length_paddle[1]/2),length_paddle[1], myPaddle2.getBallMissed(), true);
				else if(myBall.getCenterY() <(60+length_paddle[1]/2) && player_d1==3 &&false)
					Board_backend.movePaddle(1,0,(60+length_paddle[1]/2),length_paddle[1], myPaddle2.getBallMissed(), true);
				else if( player_d1==3)
					Board_backend.movePaddle(1,0,next_pos_2, length_paddle[1], myPaddle2.getBallMissed(), true);
				else
					Board_backend.movePaddle(1,yourpaddle_x[1],yourpaddle_y[1], length_paddle[1], myPaddle2.getBallMissed(), true);

				// For third player on the bottom part
				if(new_paddlePos - ClickDiff > (540-length_paddle[2]/2) &&  player_d2==1)
					Board_backend.movePaddle(2,(540-length_paddle[2]/2),600,length_paddle[2], myPaddle3.getBallMissed(), true);
				else if(new_paddlePos - ClickDiff <(60+length_paddle[2]/2) && player_d2==1)
					Board_backend.movePaddle(2,(60+length_paddle[2]/2),600, length_paddle[2], myPaddle3.getBallMissed(), true);
				else if( player_d2==1)
					Board_backend.movePaddle(2,new_paddlePos - ClickDiff,600, length_paddle[2], myPaddle3.getBallMissed(), true);
				else if(player_d2==3&&myBall.getCenterX() < (60+length_paddle[2]/2)&&false)
					Board_backend.movePaddle(2,(60+length_paddle[2]/2),600,length_paddle[2] , myPaddle3.getBallMissed(), true);// when the other one is player 0;
				else if (player_d2==3&&myBall.getCenterX() > (540-length_paddle[2]/2)&&false) 
					Board_backend.movePaddle(2,(540-length_paddle[2]/2),600, length_paddle[2], myPaddle3.getBallMissed(), true);		
				else if(player_d2==3)
					Board_backend.movePaddle(2,next_pos_3,600, length_paddle[2], myPaddle3.getBallMissed(), true);
				else
					Board_backend.movePaddle(2,yourpaddle_x[2],yourpaddle_y[2], length_paddle[2], myPaddle3.getBallMissed(), true);

				// for the fourth player
				if(new_paddlePos_Y - ClickDiff_Y > (540-length_paddle[3]/2)&& player_d3==1)
					Board_backend.movePaddle(3,600,(540-length_paddle[3]/2), length_paddle[3], myPaddle4.getBallMissed(), true);
				else if(new_paddlePos_Y - ClickDiff_Y <(60+length_paddle[3]/2)&& player_d3==1)
					Board_backend.movePaddle(3,600,(60+length_paddle[3]/2), length_paddle[3], myPaddle4.getBallMissed(), true);		
				else if(player_d3==1)
					Board_backend.movePaddle(3,600,new_paddlePos_Y - ClickDiff_Y, length_paddle[3], myPaddle4.getBallMissed(), true);
				else if(myBall.getCenterY() > (540-length_paddle[3]/2) && player_d3==3 &&false)
					Board_backend.movePaddle(3,600,(540-length_paddle[3]/2), length_paddle[3], myPaddle4.getBallMissed(), true);
				else if(myBall.getCenterY() <(60+length_paddle[3]/2) && player_d3==3 &&false)
					Board_backend.movePaddle(3,600,(60+length_paddle[3]/2), length_paddle[3], myPaddle4.getBallMissed(), true);
				else if( player_d3==3)
					Board_backend.movePaddle(3,600,next_pos_4, length_paddle[3], myPaddle4.getBallMissed(), true);
				else
					Board_backend.movePaddle(3,yourpaddle_x[3],yourpaddle_y[3], length_paddle[3], myPaddle4.getBallMissed(), true);

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

				///////////////////////////NOT GENERIC /////////////////////////////////////// Write for other balls as well
				for(int i=0;i<curr_Balls.size();i++) {
			 		Ball myBall1=updatedBalls.get(i);
					Double lastY1 = myBall1.getCenterY();
					Double lastX1 = myBall1.getCenterX();
					Double lastY2 = ball_pos_cy[server][i];
					Double lastX2 = ball_pos_cx[server][i];
			
			 		if(collision_paddle[i]!=-1)
			 			Board_backend.moveBall(i,ball_vel_cx[(collision_paddle[i])][i], ball_vel_cy[collision_paddle[i]][i], ball_vel_cx[collision_paddle[i]][i] + lastX1,ball_vel_cy[collision_paddle[i]][i] + lastY1, 10);	
					else if(server==player_no)		
					 	Board_backend.moveBall(i,myBall1.getVelX(), myBall1.getVelY(), myBall1.getVelX() + lastX1, myBall1.getVelY() + lastY1, 10);	
			 		else
			 			Board_backend.moveBall(i,ball_vel_cx[server][i], ball_vel_cy[server][i], lastX2, lastY2, 10);	
			 	}
			
				//if(!collision_happened[i]) Board_backend.moveBall(i,myBall.getVelX(), myBall.getVelY(), myBall.getVelX() + lastX, myBall.getVelY() + lastY, 10);
				//else Board_backend.moveBall(0,ball_vel_cx, ball_vel_cy, -ball_vel_cx + lastX, -ball_vel_cy + lastY, 10);
				///Send changes
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		};
	};

	public static void update_Random() {
		if (Board_backend.getObjects() != null) {
			colln_random = false;
			random_paddle_no = -1;

			double radius = 10.0;
			RandomObj r = Board_backend.getObjects();

			random_type = r.getEffect();

			ArrayList<Paddle> curr_Paddles = Board_backend.getPaddles();
			Paddle myPaddle = Board_backend.getPaddles().get(Paddle_No);
			double myX = myPaddle.getPaddleX();
			//Ball myBall = Board_backend.getBalls().get(0);
			double myY = myPaddle.getPaddleY();
			double myLen = myPaddle.getPaddleLength();

			double rX = r.getCenterX();
			double rY = r.getCenterY();

			double r_vx = r.getVelX();
			double r_vy = r.getVelY();

			int r_eff = r.getEffect();

			Paddle myPaddle1 = curr_Paddles.get(0);
			Paddle myPaddle2 = curr_Paddles.get(1);
			Paddle myPaddle3 = curr_Paddles.get(2);
			Paddle myPaddle4 = curr_Paddles.get(3);

			boolean [] r2paddlea=new boolean[4];
			int r2paddle=0;
			r2paddlea[0] = PEngine.collision_paddle(rX, rY, length_paddle[0]+40, 600, radius, myPaddle1.getPaddleX(),  myPaddle1.getPaddleY(),20,1);
			r2paddlea[1] = PEngine.collision_paddle(rX, rY, length_paddle[1]+40, 600, radius, myPaddle2.getPaddleX(),  myPaddle2.getPaddleY(),20,2);
			r2paddlea[2] = PEngine.collision_paddle(rX, rY, length_paddle[2]+40, 600, radius, myPaddle3.getPaddleX(),  myPaddle3.getPaddleY(),20,3);
			r2paddlea[3] = PEngine.collision_paddle(rX, rY, length_paddle[3]+40, 600, radius, myPaddle4.getPaddleX(),  myPaddle4.getPaddleY(),20,4);

			for(int it=0;it<4;it++) {
				if(r2paddlea[it])  r2paddle=it+1;
			}
			boolean check_wall_paddle=(lastRWall+4 == r2paddle + 4);

			if (r2paddle!=0 && lastRpaddle!=r2paddle+4 && !check_wall_paddle&&curr_Paddles.get(r2paddle-1).getBallMissed()<5) {
				System.out.println("With paddle " + r2paddle);
				lastRpaddle=r2paddle+ 4;
				// if(r2paddle==3) 
				lastRWall=0; lastRcorner=0;
				// if(r2paddle==3) System.out.println("Print2 "+b2paddle);
				Board_backend.removeObj();
				colln_random = true;
				random_paddle_no = r2paddle - 1;
				// if((r2paddle-1)==0)
				// {
				// 	Board_backend.moveObj(r_vx, -r_vy, rX, rY, r_eff);
				// }
				// else if ((r2paddle-1)==1)
				// {
				// 	Board_backend.moveObj(-r_vx, r_vy, rX, rY, r_eff);
				// }
				// else if ((r2paddle-1)==2)
				// {
				// 	Board_backend.moveObj(r_vx, -r_vy, rX, rY, r_eff);
				// }
				// else if ((r2paddle-1)==3)
				// {
				// 	Board_backend.moveObj(-r_vx, r_vy, rX, rY, r_eff);
				// }

				int pdl_no = r2paddle - 1;

				if (r_eff == 0)
					Board_backend.movePaddle(pdl_no, curr_Paddles.get(pdl_no).getPaddleX() , curr_Paddles.get(pdl_no).getPaddleY(), length_paddle[pdl_no] + 30, curr_Paddles.get(pdl_no).getBallMissed(), true);
				else if (r_eff == 1)
					Board_backend.movePaddle(pdl_no, curr_Paddles.get(pdl_no).getPaddleX() , curr_Paddles.get(pdl_no).getPaddleY(), length_paddle[pdl_no] - 20, curr_Paddles.get(pdl_no).getBallMissed(), true);
				else if (r_eff == 2)
					Board_backend.movePaddle(pdl_no, curr_Paddles.get(pdl_no).getPaddleX() , curr_Paddles.get(pdl_no).getPaddleY(), length_paddle[pdl_no], curr_Paddles.get(pdl_no).getBallMissed() - 1, true);
			}
			else {
				int r2wall = PEngine.collision_wall(rX, rY, radius,600.0);
				boolean check_paddle_wall=(r2wall+4== lastRpaddle);
				double k = 1.00001;
				if(r2wall > 0 && r2wall != lastRWall && !check_paddle_wall) {
					System.out.println("Wall " + r2wall);
					lastRpaddle = 0;
					lastRcorner = 0;
					lastRWall = r2wall;
					if (r2wall == 1)
						Board_backend.moveObj(k*r_vx, -k*r_vy, rX, rY,  r_eff);
					else if (r2wall == 2)
						Board_backend.moveObj(-k*r_vx, k*r_vy, rX, rY,  r_eff);
					else if (r2wall == 3)
						Board_backend.moveObj(k*r_vx, -k*r_vy, rX, rY,  r_eff);
					else if (r2wall == 4)
						Board_backend.moveObj(-k*r_vx, k*r_vy, rX, rY, r_eff);
				}
				else {
					int r2corner = PEngine.collision_corner(rX, rY, radius, 600.0, 60.0);
					boolean c1=true,c2=true,c3=true,c4=true,check=true;
					if(r2corner==1&&(lastRWall==1||lastRWall==2||lastRpaddle==5||lastRpaddle==6)) c1=false;
					if(r2corner==2&&(lastRWall==1||lastRWall==4||lastRpaddle==5||lastRpaddle==8)) c2=false;
					if(r2corner==3&&(lastRWall==3||lastRWall==4||lastRpaddle==7||lastRpaddle==8)) c3=false;
					if(r2corner==4&&(lastRWall==2||lastRWall==3||lastRpaddle==6||lastRpaddle==7)) c4=false;
					check=c1&&c2&&c3&&c4; 
					if (r2corner > 0 && r2corner!=lastRcorner && check) {
						lastRcorner=r2corner;
						lastRWall=0;
						lastRpaddle=0;
						if (r2corner%2 == 1)
							Board_backend.moveObj(-r_vy, -r_vx,rX, rY, r_eff);
						else
							Board_backend.moveObj(r_vy, r_vx, rX, rY, r_eff);
					}
					else
						Board_backend.moveObj(r_vy, r_vx,rX + r_vx, rY + r_vy, r_eff);
				}
			}
		}
	}

	public static void update_Phy() {   
		lostl=0;
		Paddle_No=player_no;
		Paddle_No=3;

		// TODO ; Paddle Number!
		//System.out.println("My paddle no-"+Paddle_No);
		// TODO: check info from other clients first!
		// check B2B, B2W, B2MyPaddle
		ArrayList<Ball> curr_Balls = Board_backend.getBalls();
		ArrayList<Paddle> curr_Paddles = Board_backend.getPaddles();
		int no_balls = curr_Balls.size();
		Paddle myPaddle = Board_backend.getPaddles().get(Paddle_No);
		double myX = myPaddle.getPaddleX();
		//Ball myBall = Board_backend.getBalls().get(0);
		double myY = myPaddle.getPaddleY();
		double myLen = myPaddle.getPaddleLength();
		//double pos_pad_x=myPaddle.
		//System.out.println("pos of my paddle--"+myX+","+myY);
		for (int i = 0; i < no_balls ; i ++) {
			ball_missed[i]=0;
			Collide_paddle[i]=0;
			Ball ith = curr_Balls.get(i);
			double radius = ith.getRadius();
			double center_x = ith.getCenterX();
			double center_y = ith.getCenterY();
			double vel_cx = ith.getVelX();
			double vel_cy = ith.getVelY();
			Ball myBall = curr_Balls.get(i);
			Paddle myPaddle1 = curr_Paddles.get(0);
			Paddle myPaddle2 = curr_Paddles.get(1);
			Paddle myPaddle3 = curr_Paddles.get(2);
			Paddle myPaddle4 = curr_Paddles.get(3);

			// check B2MyPaddle
			// TODO:We need to change depending on player no. we are: just set the player no and its quite generic
			//System.out.println("fsafa" +length_paddle[0]+","+length_paddle[1]+","+length_paddle[2]+","+length_paddle[3]);
			
			boolean [] b2paddlea=new boolean[4];
			int b2paddle=0;
			b2paddlea[0] = PEngine.collision_paddle(center_x, center_y, length_paddle[0]+40, 600, radius, myPaddle1.getPaddleX(),  myPaddle1.getPaddleY(),20,1);
			b2paddlea[1] = PEngine.collision_paddle(center_x, center_y, length_paddle[1]+40, 600, radius, myPaddle2.getPaddleX(),  myPaddle2.getPaddleY(),20,2);
			b2paddlea[2] = PEngine.collision_paddle(center_x, center_y, length_paddle[2]+40, 600, radius, myPaddle3.getPaddleX(),  myPaddle3.getPaddleY(),20,3);
			b2paddlea[3] = PEngine.collision_paddle(center_x, center_y, length_paddle[3]+40, 600, radius, myPaddle4.getPaddleX(),  myPaddle4.getPaddleY(),20,4);
			for(int it=0;it<4;it++) {
				if(b2paddlea[it])  b2paddle=it+1;
			}
			//if(b2paddle!=0) System.out.println("Print1 "+b2paddle);

			boolean check_wall_paddle=(lastBwall[i]+4 == b2paddle + 4);
			//check_wall_paddle=false;
			// b2paddle=false;
			// if(b2paddle==3 && lastpaddle[i]==b2paddle+4)
			// {
			// 	System.out.println("true is="+check_wall_paddle);
			// }
			// else if(b2paddle==3)
			// { 	System.out.println("true is="+check_wall_paddle);
			// 	System.out.println("False is "+(lastpaddle[i]!=b2paddle+4)+" The ball is "+i);
			// }
			if(b2paddle==3) {
				System.out.println("Print 1.)"+check_wall_paddle+" 2.) "+(lastpaddle[i]!=b2paddle+4));
			}
			if (b2paddle!=0 && lastpaddle[i]!=b2paddle+4&& !check_wall_paddle&&curr_Paddles.get(b2paddle-1).getBallMissed()<5) {   
				lastBBCollision[i] = -1;
				ball_colln[i] = false;
				lastpaddle[i]=b2paddle+ 4;
				if(b2paddle==3) lastBwall[i]=0; lastBcorner[i]=0;
				if(b2paddle==3) System.out.println("Print2 "+b2paddle);
				if(b2paddle-1==Paddle_No) {
					Collide_paddle[i]=1;
				 	//System.out.println("collision with myPaddle.");
				}

				if((b2paddle-1) == 0) {
					Board_backend.moveBall(i,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					try {
			            AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
			            // .getAbsoluteFile()
			            // System.out.println("after l1");
			            Clip clip = AudioSystem.getClip();
			            // System.out.println("after l2");
			            clip.open(audio1);
			            // System.out.println("after l3");
			            clip.start();
			            // System.out.println("after l4");
			        }
			        catch(UnsupportedAudioFileException e1) {
			            System.err.println(e1.getMessage());
			            System.out.println("e1");
			        }
		            catch(LineUnavailableException e2) {
		                System.err.println(e2.getMessage());
		                System.out.println("e2");
		            }
		            catch(IOException e3) {
		                System.err.println(e3.getMessage());
		                System.out.println("e3");
		            }
				}
				else if((b2paddle-1) == 1) {
					Board_backend.moveBall(i,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					try {
			            AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
			            // .getAbsoluteFile()
			            // System.out.println("after l1");
			            Clip clip = AudioSystem.getClip();
			            // System.out.println("after l2");
			            clip.open(audio1);
			            // System.out.println("after l3");
			            clip.start();
			            // System.out.println("after l4");
			        }
		            catch(UnsupportedAudioFileException e1) {
		                System.err.println(e1.getMessage());
		                System.out.println("e1");
		            }
		            catch(LineUnavailableException e2) {
		                System.err.println(e2.getMessage());
		                System.out.println("e2");
		            }
		            catch(IOException e3) {
		                System.err.println(e3.getMessage());
		                System.out.println("e3");
		            }
				}
				else if((b2paddle-1)==2) {	
					System.out.println("Machaya wall was"+lastBwall[i]);
					Board_backend.moveBall(i,myBall.getVelX(), -myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					
					lastBwall[i]=0;

					try {
			            AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
			            // .getAbsoluteFile()
			            // System.out.println("after l1");
			            Clip clip = AudioSystem.getClip();
			            // System.out.println("after l2");
			            clip.open(audio1);
			            // System.out.println("after l3");
			            clip.start();
			            // System.out.println("after l4");
		            }
		            catch(UnsupportedAudioFileException e1) {
		                System.err.println(e1.getMessage());
		                System.out.println("e1");
		            }
		            catch(LineUnavailableException e2) {
		                System.err.println(e2.getMessage());
		                System.out.println("e2");
		            }
		            catch(IOException e3) {
		                System.err.println(e3.getMessage());
		                System.out.println("e3");
		            }
				}
				else {
					Board_backend.moveBall(i,-myBall.getVelX(), myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
					try {
			            AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
			            // .getAbsoluteFile()
			            // System.out.println("after l1");
			            Clip clip = AudioSystem.getClip();
			            // System.out.println("after l2");
			            clip.open(audio1);
			            // System.out.println("after l3");
			            clip.start();
			            // System.out.println("after l4");
			        }
		            catch(UnsupportedAudioFileException e1) {
		                System.err.println(e1.getMessage());
		                System.out.println("e1");
		            }
		            catch(LineUnavailableException e2) {
		                System.err.println(e2.getMessage());
		                System.out.println("e2");
		            }
		            catch(IOException e3) {
		                System.err.println(e3.getMessage());
		                System.out.println("e3");
		            }
				}						
			}
			else {   
				double k=1.000001;
				// detect B2Wall or b2Corner?
				
				int b2wall = PEngine.collision_wall(center_x, center_y, radius,600.0);
				//if(b2wall!=0) System.out.println("length is "+length_paddle[b2wall-1]);
				boolean check_paddle_wall=(b2wall+4== lastpaddle[i]);
				if(b2wall > 0 && b2wall != lastBwall[i] && !check_paddle_wall) {   
					lastBBCollision[i] = -1;
					ball_colln[i] = false;
					lastpaddle[i]=0;
				    lastBcorner[i]=0;
					// System.out.println("collision of this ball with wall " + b2wall + "\t" + i+" length is"+length_paddle[b2wall-1]);
					lastBwall[i] = b2wall;
					if(b2wall==player_no+1) 
						lostl=1;
					//TODO: Change the get missed ball parameter
					if(b2wall == 1) {
						Board_backend.moveBall(i,k*myBall.getVelX(), -k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
						Board_backend.movePaddle(0,myPaddle1.getPaddleX(),myPaddle1.getPaddleY(),  length_paddle[0], myPaddle1.getBallMissed() + 1, true);
						try {
				           	AudioInputStream audio1;
				            if(myPaddle1.getBallMissed()>=5)
				            	audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				            else
				            	audio1 = AudioSystem.getAudioInputStream(new File("baseball_hit.wav"));
				            // .getAbsoluteFile()
				            // System.out.println("after l1");
				            Clip clip = AudioSystem.getClip();
				            // System.out.println("after l2");
				            clip.open(audio1);
				            // System.out.println("after l3");
				            clip.start();
				            // System.out.println("after l4");
				        }
			            catch(UnsupportedAudioFileException e1) {
			                System.err.println(e1.getMessage());
			                System.out.println("e1");
			            }
			            catch(LineUnavailableException e2) {
			                System.err.println(e2.getMessage());
			                System.out.println("e2");
			            }
			            catch(IOException e3) {
			                System.err.println(e3.getMessage());
			                System.out.println("e3");
			            }
					}
					else if(b2wall == 2) {
						Board_backend.moveBall(i,-k*myBall.getVelX(), k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
						Board_backend.movePaddle(1,myPaddle2.getPaddleX(),myPaddle2.getPaddleY(),  length_paddle[1], myPaddle2.getBallMissed() + 1, true);
						try {
				            AudioInputStream audio1;
				            if(myPaddle2.getBallMissed()>=5)
				              	audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				            else
				              	audio1 = AudioSystem.getAudioInputStream(new File("baseball_hit.wav"));
				            // .getAbsoluteFile()
				            // System.out.println("after l1");
				            Clip clip = AudioSystem.getClip();
				            // System.out.println("after l2");
				            clip.open(audio1);
				            // System.out.println("after l3");
				            clip.start();
				            // System.out.println("after l4");
				        }
			            catch(UnsupportedAudioFileException e1) {
			                System.err.println(e1.getMessage());
			                System.out.println("e1");
			            }
			            catch(LineUnavailableException e2) {
			                System.err.println(e2.getMessage());
			                System.out.println("e2");
			            }
			            catch(IOException e3) {
			                System.err.println(e3.getMessage());
			                System.out.println("e3");
			            }
					}
					else if(b2wall == 3) {
						Board_backend.moveBall(i,k*myBall.getVelX(), -k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
						Board_backend.movePaddle(2,myPaddle3.getPaddleX(),myPaddle3.getPaddleY(),  length_paddle[2], myPaddle3.getBallMissed() + 1, true);
						try {
				            AudioInputStream audio1;
				            if(myPaddle3.getBallMissed()>=5)
				              	audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				            else
				              	audio1 = AudioSystem.getAudioInputStream(new File("baseball_hit.wav"));
				            // .getAbsoluteFile()
				            // System.out.println("after l1");
				            Clip clip = AudioSystem.getClip();
				            // System.out.println("after l2");
				            clip.open(audio1);
				            // System.out.println("after l3");
				            clip.start();
				            // System.out.println("after l4");
				        }
			            catch(UnsupportedAudioFileException e1) {
			                System.err.println(e1.getMessage());
			                System.out.println("e1");
			            }
			            catch(LineUnavailableException e2) {
			                System.err.println(e2.getMessage());
			                System.out.println("e2");
			            }
			            catch(IOException e3) {
			                System.err.println(e3.getMessage());
			                System.out.println("e3");
			            }
					}
					else if(b2wall == 4) {
						Board_backend.moveBall(i,-k*myBall.getVelX(), k*myBall.getVelY(), myBall.getCenterX(), myBall.getCenterY(), 10);
						Board_backend.movePaddle(3,myPaddle4.getPaddleX(),myPaddle4.getPaddleY(), length_paddle[3], myPaddle4.getBallMissed() + 1, true);
						try {
				            AudioInputStream audio1;
				            if(myPaddle4.getBallMissed()>=5)
				              	audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				            else
				              	audio1 = AudioSystem.getAudioInputStream(new File("baseball_hit.wav"));
				            // .getAbsoluteFile()
				            // System.out.println("after l1");
				            Clip clip = AudioSystem.getClip();
				            // System.out.println("after l2");
				            clip.open(audio1);
				            // System.out.println("after l3");
				            clip.start();
				            // System.out.println("after l4");
				        }
			            catch(UnsupportedAudioFileException e1) {
			                System.err.println(e1.getMessage());
			                System.out.println("e1");
			            }
			            catch(LineUnavailableException e2) {
			                System.err.println(e2.getMessage());
			                System.out.println("e2");
			            }
			            catch(IOException e3) {
			                System.err.println(e3.getMessage());
			                System.out.println("e3");
			            }
					}				
				}
				else {
					double l = 0.0; // TODO : whats this? check ball to corner here
					int b2corner = PEngine.collision_corner(center_x, center_y, radius, 600.0, 60.0);

					//b2corner=0
					//System.out.println(l);
					boolean c1=true,c2=true,c3=true,c4=true,check=true;
					if(b2corner==1&&(lastBwall[i]==1||lastBwall[i]==2||lastpaddle[i]==5||lastpaddle[i]==6)) c1=false;
					if(b2corner==2&&(lastBwall[i]==1||lastBwall[i]==4||lastpaddle[i]==5||lastpaddle[i]==8)) c2=false;
					if(b2corner==3&&(lastBwall[i]==3||lastBwall[i]==4||lastpaddle[i]==7||lastpaddle[i]==8)) c3=false;
					if(b2corner==4&&(lastBwall[i]==2||lastBwall[i]==3||lastpaddle[i]==6||lastpaddle[i]==7)) c4=false;
					check = c1 && c2 && c3 && c4; 
					if (b2corner > 0 && b2corner!=lastBcorner[i] && check) { 
						lastBBCollision[i] = -1;
						ball_colln[i] = false;
						lastBcorner[i]=b2corner;
						lastBwall[i]=0;
						lastpaddle[i]=0;	
						if(b2corner%2==1) {
							Board_backend.moveBall(i,-vel_cy , -vel_cx, center_x ,center_y ,radius);
							try {
				                AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				                // .getAbsoluteFile()
				                // System.out.println("after l1");
				                Clip clip = AudioSystem.getClip();
				                // System.out.println("after l2");
				                clip.open(audio1);
				                // System.out.println("after l3");
				                clip.start();
				                // System.out.println("after l4");
				            }
				            catch(UnsupportedAudioFileException e1) {
				                System.err.println(e1.getMessage());
				                System.out.println("e1");
				            }
				            catch(LineUnavailableException e2) {
				                System.err.println(e2.getMessage());
				                System.out.println("e2");
				            }
				            catch(IOException e3) {
				                System.err.println(e3.getMessage());
				                System.out.println("e3");
				            }
				        }
						else {
							Board_backend.moveBall(i,vel_cy , vel_cx, center_x ,center_y ,radius);
							try {
				                AudioInputStream audio1 = AudioSystem.getAudioInputStream(new File("blip.wav"));
				                // .getAbsoluteFile()
				                // System.out.println("after l1");
				                Clip clip = AudioSystem.getClip();
				                // System.out.println("after l2");
				                clip.open(audio1);
				                // System.out.println("after l3");
				                clip.start();
				                // System.out.println("after l4");
				            }
				            catch(UnsupportedAudioFileException e1) {
				                System.err.println(e1.getMessage());
				                System.out.println("e1");
				            }
				            catch(LineUnavailableException e2) {
				                System.err.println(e2.getMessage());
				                System.out.println("e2");
				            }
				            catch(IOException e3) {
				                System.err.println(e3.getMessage());
				                System.out.println("e3");
				            }
			        	}
						System.out.println("colln with corner.-- "+ b2corner+", ball="+ i);
					}
					else {
						// now check b2b collision.
						boolean any_colln = false;
						for (int j = 0; j < i && !any_colln; j ++) { 
							// distance b/w centers.
							Ball jth = curr_Balls.get(j);
							double rad2 = jth.getRadius();
							double center_x2 = jth.getCenterX();
							double center_y2 = jth.getCenterY();
							double vel_b2_x = jth.getVelX();
							double vel_b2_y = jth.getVelY();
							double cc_dist = Math.sqrt( (center_x - center_x2)*(center_x - center_x2) + (center_y - center_y2)*(center_y - center_y2) );
							if(cc_dist<50) {
								// System.out.println("dsfafa=="+cc_dist);
								// System.out.println("dsfafa i " + i + " vx: " + vel_cx + " vy: " + vel_cy + " cx: " + center_x + " cy: " + center_y);
								// System.out.println("dsfafa j " + j + " vx: " + vel_b2_x + " vy: " + vel_b2_y + " cx: " + center_x2 + " cy: " + center_y2);
							}
							if (((cc_dist - 20) < 0.001) && ((lastBBCollision[i] != j) || (lastBBCollision[j] != i))) {
								//System.out.println("YO---collision" + i + j);
								// ball 2 ball
								lastBBCollision[i] = j;
								lastBBCollision[j] = i;

								lastBcorner[i] = 0;
								lastBcorner[j] = 0;
								lastBwall[i] = 0;
								lastBwall[j] = 0;
								lastpaddle[i] = 0;
								lastpaddle[j] = 0;

								any_colln = true;
								ball_colln[i] = false;
								Double x1x2 = (center_x2 - center_x);
								Double y1y2 = (center_y2 - center_y);
								Double cc_angle = Math.atan(y1y2/x1x2);
								//System.out.println("Ball i " + i + " vx: " + vel_cx + " vy: " + vel_cy + " cx: " + center_x + " cy: " + center_y);
								//System.out.println("Ball j " + j + " vx: " + vel_b2_x + " vy: " + vel_b2_y + " cx: " + center_x2 + " cy: " + center_y2);
								vel_b2b new_vel_b1_b2 = PEngine.Colision_b2b(vel_cx, vel_cy, vel_b2_x, vel_b2_y,cc_angle);
								velocity finalb1 = new_vel_b1_b2.v1;
								velocity finalb2 = new_vel_b1_b2.v2;

								//System.out.println("Ball i ka FINAL:" + finalb1.vx1 + " " + finalb1.vy1 + " ");
								//System.out.println("Ball j ka FINAL:" + finalb2.vx1 + " " + finalb2.vy1 + " ");

								Board_backend.moveBall(i, finalb1.vx1, finalb1.vy1, center_x + finalb1.vx1, center_y + finalb1.vy1, radius);
								Board_backend.moveBall(j, finalb2.vx1, finalb2.vy1, center_x2 + finalb2.vx1, center_y2 + finalb2.vy1, rad2);
							}
						}
						if (!any_colln) {
							// NO COLLISION.
							//System.out.println("No collision, ball moved fwd.");
							// lastBBCollision[i] = -1;
							//Board_backend.moveBall(i,vel_cx , vel_cy, center_x + vel_cx,center_y + vel_cy,radius);
							ball_colln[i] = true;
						}
					}
				}
			}
		}
	}
};