import Physics.*;
import java.net.*;
import java.io.File;

public class ReceiverThread extends Thread
{
	private static DatagramSocket serverSocket;
	private static byte[] receiveData = new byte[1024];
	private int Port_connect;
	public static String Received_Str;
	// public static int last_msg_time;
	public static boolean isConnected;

/** All the data is received and then parsed here. 
* The Player class can then access the parsed values as they are global fields.
* ArrayList of boolean to handle multiple balls. **/

	public static boolean [] rec_collision_occur;
	public static double rec_paddleX;
	public static double rec_paddleY;
	public static double [] rec_ball_velX;
	public static double [] rec_ball_velY;
	public static double [] ballc_x;
	public static double [] ballc_y;
	public static boolean random_Add;
	public static double random_x;
	public static double random_y;
	public static double random_velX;
	public static double random_velY;
	public static boolean random_effect;

	public ReceiverThread(int port,int level)
	{
		Port_connect = port;
		rec_collision_occur=new boolean[level];
		rec_ball_velX = new double[level];
		rec_ball_velY = new double[level];
		ballc_x = new double[level];
		ballc_y = new double[level];

		try
		{
			serverSocket = new DatagramSocket(port);
			serverSocket.setSoTimeout(2000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
				{
					serverSocket.receive(receivePacket);
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

					String balls_cx = tokens[5];
					String [] cx_Arr = balls_cx.split("#");
					for (int i= 0; i < cx_Arr.length; i ++)
					{
						ballc_x[i] = Double.parseDouble(cx_Arr[i]);						
					}

					String balls_cy = tokens[6];
					String [] cy_Arr = balls_cy.split("#");
					for (int i= 0; i < cy_Arr.length; i ++)
					{
						ballc_y[i] = Double.parseDouble(cy_Arr[i]);			
					}

					random_Add = (Integer.parseInt(tokens[7]) == 1);
					if (random_Add)
					{
						random_x = Double.parseDouble(tokens[8]);
						random_y = Double.parseDouble(tokens[9]);
						random_velX = Double.parseDouble(tokens[10]);
						random_velY = Double.parseDouble(tokens[11]);
						random_effect = (Integer.parseInt(tokens[12]) == 1);
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
				e.printStackTrace();
			}			
		}
	}

}