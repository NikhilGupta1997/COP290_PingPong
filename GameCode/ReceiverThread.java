import Physics.*;
import java.net.*;
import java.io.File;

public class ReceiverThread extends Thread
{
	private static DatagramSocket serverSocket;//
	private static byte[] receiveData = new byte[1024];
	private int Port_connect;
	public static String Received_Str;

/** All the data is received and then parsed here. 
* The Player class can then access the parsed values as they are global fields.
* ArrayList of boolean to handle multiple balls. **/

	public static double rec_paddleX;
	public static double rec_paddleY;
	public static double rec_ball_velX;
	public static double rec_ball_velY;
	public static int rec_balls_missed;
	public static boolean rec_collision_occur;

	public ReceiverThread(int port)
	{
		Port_connect = port;
		try
		{
			serverSocket = new DatagramSocket(port);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		/**  This is called when thread.start is called. **/
		try
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			Received_Str =  new  String(receivePacket.getData());
			String [] temp2 = Received_Str.split(" ");
			String [] tokens=temp2[0].split(",");
			rec_paddleX=Double.parseDouble(tokens[1]);
			rec_paddleY=Double.parseDouble(tokens[2]);
			rec_ball_velX=Double.parseDouble(tokens[3]);
			rec_ball_velY=Double.parseDouble(tokens[4]);
			rec_balls_missed =Integer.parseInt(tokens[5]);
			rec_collision_occur=(Double.parseDouble(tokens[0])==1.0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}