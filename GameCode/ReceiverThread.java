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

					rec_paddleX=Double.parseDouble(tokens[1]);
					rec_paddleY=Double.parseDouble(tokens[2]);

					String balls_vx=tokens[3];
					String balls_vy=tokens[4];
					String balls_cx = tokens[5];
					String balls_cy = tokens[6];

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