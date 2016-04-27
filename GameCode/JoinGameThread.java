import Physics.*;
import java.net.*;
import java.io.File;

public class JoinGameThread extends Thread
{
	public boolean done;
	private static DatagramSocket serverSocket;//
	private static byte[] receiveData = new byte[1024];
	private int Port_connect;
	public static String Received_Str;

	private static DatagramSocket clientSocket;
	private static String sendTo_IP;
	private static int sendTo_Port;

	/** The thread that sends Join signal, and waits
	* till the main IP says that all players have joined. **/


	public JoinGameThread(int port, String IP)
	{
		done = false;
		try
		{
			serverSocket = new DatagramSocket(port);
			clientSocket = new DatagramSocket();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		/**  This is called when thread.start is called. Sends 'Join' repeatedly, and 
		* waits for the creator IP to send the signal of AllJoined.**/
		while (!done)
		{
			try
			{
				// SEND THEN RECEIVE.
				InetAddress IP_game = InetAddress.getByName(sendTo_IP);
				int send_to_port = sendTo_Port;
				String send_this = "Join";
				byte[] sendData = new byte[1024];
				sendData = send_this.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,send_this.length(), IP_game, send_to_port);


				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				Received_Str =  new String(receivePacket.getData());
				System.out.println("Received : " + Received_Str);
				String [] temp2 = Received_Str.split(" ");
				String [] tokens=temp2[0].split(",");
				if (tokens[0].equals("All_Joined") && receivePacket.getAddress().equals(sendTo_IP) && receivePacket.getPort() == sendTo_Port)
				{
					// being received from the Creator IP.
					done = true;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
		}
	}

}