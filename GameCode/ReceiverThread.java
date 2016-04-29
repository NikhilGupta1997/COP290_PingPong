import Physics.*;
import java.net.*;
import java.io.File;

public class ReceiverThread extends Thread
{
	private  DatagramSocket serverSocket;
	private  byte[] receiveData = new byte[1024];
	private int Port_connect;
	public  String Received_Str;
	// public  int last_msg_time;
	public  boolean isConnected;
	///public int port1;

/** All the data is received and then parsed here. 
* The Player class can then access the parsed values as they are global fields.
* ArrayList of boolean to handle multiple balls. **/

	public  boolean [] rec_collision_occur;
	public  double rec_paddleX;
	public  double rec_paddleY;
	public  double [] rec_ball_velX;
	public  double [] rec_ball_velY;
	public  double [] balls_cx;
	public  double [] balls_cy;
	public  boolean random_Add;
	public  double random_x;
	public  double random_y;
	public  double random_velX;
	public  double random_velY;
	public  boolean random_effect;
	private  int lostlife;

	public ReceiverThread(int port,int level)
	{  System.out.println("Port is"+ port+" level is"+level);
		Port_connect = port;
		rec_collision_occur=new boolean[level];
		rec_ball_velX = new double[level];
		rec_ball_velY = new double[level];
		balls_cx = new double[level];
		balls_cy = new double[level];

		try
		{
			serverSocket = new DatagramSocket(port);
			serverSocket.setSoTimeout(20000);
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
	}

	public void run()
	{
		/**  This is called when thread.start is called. **/
		while (true)
		{
			try
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try
				{ 	//System.out.println("Receiver thread is working at "+ Port_connect);
					serverSocket.receive(receivePacket);
					System.out.println("fds");
					isConnected = true;
					Received_Str =  new String(receivePacket.getData());
					System.out.println("Received : " + Received_Str);
					String [] temp2 = Received_Str.split(" ");

					String [] tokens=temp2[0].split(",");

					String colln_arr = tokens[0];
					String [] colln_occur = colln_arr.split("#");
					for (int i = 0; i < colln_occur.length ; i ++)
					{
						rec_collision_occur[i] = (Integer.parseInt(colln_occur[i]) == 1);
					}

					rec_paddleX=Double.parseDouble(tokens[1]);
					rec_paddleY=Double.parseDouble(tokens[2]);

					String balls_vx=tokens[3];
					String [] vx_Arr = balls_vx.split("#");
					for (int i= 0; i < vx_Arr.length; i ++)
					{
						rec_ball_velX[i] = Double.parseDouble(vx_Arr[i]);
					}
					String balls_vy=tokens[4];
					String [] vy_Arr = balls_vy.split("#");
					for (int i= 0; i < vy_Arr.length; i ++)
					{
						rec_ball_velY[i] = Double.parseDouble(vy_Arr[i]);						
					}

//<<<<<<< HEAD
					String balls_cx1 = tokens[5];
					String [] cx_Arr = balls_cx1.split("#");
// =======
// 					String balls_cx = tokens[5];
// 					String [] cx_Arr = balls_cx.split("#");
// >>>>>>> 91f0b38c7dc436414493be036c969d92cab95c4b
					for (int i= 0; i < cx_Arr.length; i ++)
					{
						balls_cx[i] = Double.parseDouble(cx_Arr[i]);						
					}

//<<<<<<< HEAD
					String balls_cy1 = tokens[6];
					String [] cy_Arr = balls_cy1.split("#");
// =======
// 					String balls_cy = tokens[6];
// 					String [] cy_Arr = balls_cy.split("#");
// >>>>>>> 91f0b38c7dc436414493be036c969d92cab95c4b
					for (int i= 0; i < cy_Arr.length; i ++)
					{
						balls_cy[i] = Double.parseDouble(cy_Arr[i]);			
					}
					lostlife=Integer.parseInt(tokens[7]);
					random_Add = (Integer.parseInt(tokens[8]) == 1);
					if (random_Add)
					{
						random_x = Double.parseDouble(tokens[9]);
						random_y = Double.parseDouble(tokens[10]);
						random_velX = Double.parseDouble(tokens[11]);
						random_velY = Double.parseDouble(tokens[12]);
						random_effect = (Integer.parseInt(tokens[13]) == 1);
					}
					Thread.sleep(10); // must be less to prevent lag
				}
				catch(SocketTimeoutException e)
				{
					isConnected = false;
				}

			}
			catch(Exception e)
			{
				// e.printStackTrace();
			}			
		}
	}

}