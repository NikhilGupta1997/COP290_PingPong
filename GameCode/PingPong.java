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

public class PingPong
{
	/** Main Class controlling GameFlow.
	 **/
	private static String PName;
	private static int Plevel;
	private static MainScreen m;
	private static JLabel main_hd;
	private static JPanel main_panel;
	private static boolean bool1 = false;
	private static boolean bool2 = false;
	private static boolean bool3 = false;
	private static String name1;
	private static String name2;
	private static String name3;
	private static ArrayList<String> IPs;
	private static ArrayList<Integer> Ports;
	private static boolean Join_Coonected = false;
	public PingPong()
	{

	}


	public static void main(String[] args)
	{
   		m = new MainScreen();
   		main_hd = m.GetMain_Head();
   		main_panel = m.GetMain_Panel();

   		JButton createGame = new JButton("Create New Game");
   		JButton joinGame = new JButton("Join Another Game");
		JPanel main_bttn = new JPanel();
		main_bttn.setLayout(new FlowLayout());
		main_bttn.add(createGame);
		main_bttn.add(joinGame);
   		m.getContentPane().add(main_bttn);
   		m.revalidate();

       	m.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    


// JPanel:
		CreateGameScreen create = new CreateGameScreen(); // JPanel
		JButton create_final = new JButton("Start Game!");
		JButton backfromCr = new JButton("Back to Main Menu");
		JPanel cr_bttn = new JPanel();
		cr_bttn.setLayout(new FlowLayout());
		cr_bttn.add(create_final);
		cr_bttn.add(backfromCr);

// JPanel:
		JoinGameScreen join = new JoinGameScreen();
		JButton join_final = new JButton("Request to join!");
		JButton backfromJo = new JButton("Back to Main Menu");
		JPanel jo_bttn = new JPanel();
		jo_bttn.setLayout(new FlowLayout());
		jo_bttn.add(join_final);
		jo_bttn.add(backfromJo);
   		// TODO: keep IP1,IP2, IP3, p1, p2, p3

   		createGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PName = m.userText.getText();
				Plevel = (int) m.spinner.getValue();
				System.out.println(PName + " Level :" + Plevel + "\n");
				// remove old content, add 2 panels.
				clearMainScreen();
				m.getContentPane().remove(main_bttn);

				System.out.println("Components removed");
   				// create.add(cr_bttn);

   				m.setLayout(new GridLayout(2,1,0,50));
   				m.getContentPane().add(create);
   				m.getContentPane().add(cr_bttn);
				// create.setVisible(true);
				m.revalidate();
				m.repaint();
				// m.pack();
				// m.validate();
			}
		});

		joinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PName = m.userText.getText();
				Plevel = (int) m.spinner.getValue();
				clearMainScreen();
				m.getContentPane().remove(main_bttn);
				
				m.setLayout(new GridLayout(2,1,0,50));
				m.getContentPane().add(join);
   				m.getContentPane().add(jo_bttn);
				m.revalidate();
				m.repaint();
			}
		});

		create_final.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// CHECK THE 3 IP, PORTs ENTERED.
				ArrayList<JTextField> ips = create.GetIPs();
				ArrayList<JSpinner> ports = create.GetPorts();
				// DISABLE THIS BUTTON!

				IPs = new ArrayList<String>();
				Ports = new ArrayList<Integer>();

				int no_players = ips.size();

				for (int i = 0; i < no_players; i ++)
				{
					IPs.add((ips.get(i)).getText());
					Ports.add((int)(ports.get(i)).getValue());
				}
				System.out.println(IPs);
				if (no_players > 0)
				{
					if (no_players == 1)
					{
						System.out.println(IPs.get(0));
						CreateGameThread cg1 = new CreateGameThread(1,IPs.get(0), Ports.get(0));
						cg1.start();

						bool2 = true;
						name2 = "";
						bool3 = true;
					}
					else if (no_players == 2)
					{
						CreateGameThread cg1 = new CreateGameThread(1, IPs.get(0), Ports.get(0));
						cg1.start();
						
						CreateGameThread cg2 = new CreateGameThread(2, IPs.get(1), Ports.get(1));
						cg2.start();

						bool3 = true;
					}
					else
					{
						CreateGameThread cg1 = new CreateGameThread(1, IPs.get(0), Ports.get(0));
						cg1.start();
						
						CreateGameThread cg2 = new CreateGameThread(2, IPs.get(1), Ports.get(1));
						cg2.start();

						CreateGameThread cg3 = new CreateGameThread(3, IPs.get(2), Ports.get(2));
						cg3.start();
					}
				}
				else
				{
					bool1 = true;
					bool2 = true;
					bool3 = true;
				}

				StartGameThread start_final = new StartGameThread();
				start_final.start();
				// this one sends final msg to all others.
			}
		});

		join_final.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				// SEND 1 PACKET TO IP, port, say 'Join'S Thread!
				JoinGameThread jg1 = new JoinGameThread((int) join.spinner.getValue(),join.userIP.getText());
				jg1.start();

				JoinGameThread2 jg2 = new JoinGameThread2((int) join.spinner.getValue(),join.userIP.getText());
				jg2.start();

				// check if player can join the given IP's address.
				// Receiver thread
			}
		});

		backfromCr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				m.getContentPane().remove(create);
				m.getContentPane().remove(cr_bttn);

				System.out.println("Components removed again");

				addMainScreen();
				m.getContentPane().add(main_bttn);
				m.revalidate();
				m.repaint();
				// m.pack();
				// m.validate();
				// create.setVisible(false);
				// m.setVisible(true);
			}
		});

		backfromJo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				m.getContentPane().remove(join);
				m.getContentPane().remove(jo_bttn);

				addMainScreen();
				m.getContentPane().add(main_bttn);
				m.revalidate();
				m.repaint();

			}
		});
		// Player p1 = new Player(); : do this when a game has started.
	}

	public static void clearMainScreen()
	{
		m.getContentPane().remove(main_hd);
		m.getContentPane().remove(main_panel);
	}

	public static void addMainScreen()
	{
		m.setLayout(new GridLayout(3,1,0,50));
		m.getContentPane().add(main_hd);
		m.getContentPane().add(main_panel);
	}

	public static class JoinGameThread extends Thread
	{
		public boolean done;

		private static DatagramSocket clientSocket;
		private static String sendTo_IP;
		private static int sendTo_Port;

		/** The thread that sends Join signal, and waits
		* till the main IP says that all players have joined. **/


		public JoinGameThread(int port, String IP)
		{
			done = false;
			sendTo_IP = IP;
			sendTo_Port = port;
			try
			{
				// serverSocket = new DatagramSocket(1900);
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
			* waits for the creator IP to send the signal of AllJoined + all other IPs & Ports.**/
			while (!done)
			{
				try
				{
					// SEND THEN RECEIVE.
					InetAddress IP_game = InetAddress.getByName(sendTo_IP);
					// int send_to_port = sendTo_Port;
					String send_this = "Join," + PName;
					byte[] sendData = new byte[1024];
					sendData = send_this.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData,send_this.length(), IP_game, 1800);
					clientSocket.send(sendPacket);

					done = true;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}			
			}
		}

	}

	public static class JoinGameThread2 extends Thread
	{
		private static DatagramSocket serverSocket;
		private static byte[] receiveData = new byte[1024];
		public static String Received_Str;
		public static boolean done2;
		private static String sendTo_IP;
		private static int sendTo_Port;

		public JoinGameThread2(int port, String IP)
		{
			done2 = false;
			sendTo_IP = IP;
			sendTo_Port = port;
			try
			{
				serverSocket = new DatagramSocket(1901);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		public void run()
		{
			while (!done2)
			{
				try
				{
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					Received_Str =  new String(receivePacket.getData());
					System.out.println("Received : " + Received_Str);
					String [] temp2 = Received_Str.split(" ");
					String [] tokens=temp2[0].split(",");
					// All_Joined, apna_naam, IP1, port1, name1, IP2, Port2, name2
					if (tokens[0].equals("All_Joined") && receivePacket.getAddress().equals(sendTo_IP))
					{
						// being received from the Creator IP.
						done2 = true;
						ArrayList<String> joinIPs = new ArrayList<String>();
						ArrayList<Integer> joinPorts = new ArrayList<Integer>();
						ArrayList<String> joinNames = new ArrayList<String>();

// 1. creator of game IP at 0
						joinIPs.add(sendTo_IP);
						joinPorts.add(sendTo_Port);
						joinNames.add(tokens[1]);

						int no_players = (tokens.length - 2)/3;

						for (int i = 0; i < no_players; i ++)
						{
							joinIPs.add(tokens[3*i + 2]);
							joinPorts.add(Integer.parseInt(tokens[3*i + 3]));
							joinNames.add(tokens[3*i + 4]);
						}

						// clientSocket.close();
// has other 3.
						System.out.println("Starting Player at Join");
						Player p_join = new Player(PName, Plevel,joinIPs, joinPorts, joinNames);
					}

				}
				catch(Exception e)
				{

				}
			}
		}

	}

	public static class CreateGameThread extends Thread
	{
		// 1 serverSocket for receiving.
		// list of clientSocket to send.
		public boolean ith_Joined;
		public boolean Game_Started;
		private static String entered_IP;
		private static int otherPort;
		private static int i;

		private static DatagramSocket serverSocket; // receiving.
		private static byte[] receiveData = new byte[1024];
		public static String Received_Str;


		public CreateGameThread(int ith, String otherIP, int otherPort)
		{
			// sets bool I, name I.
			ith_Joined = false;
			entered_IP = otherIP;
			System.out.println(entered_IP);
			otherPort = otherPort;
			i = ith;
			try
			{
				serverSocket = new DatagramSocket(1800); // receive

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		public void run()
		{
			while (!ith_Joined)
			{
				try
				{
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("Receive packet 1: ");
					serverSocket.receive(receivePacket);
					System.out.println("Received Packet 2: ");
					Received_Str =  new String(receivePacket.getData());
					System.out.println("Received : " + Received_Str);
					String [] temp2 = Received_Str.split(" ");
					String [] tokens=temp2[0].split(",");
					System.out.println(tokens[0] + " " + receivePacket.getAddress());
					String getAdd = (receivePacket.getAddress()).toString();
					System.out.println(getAdd + " : Converted string");
					String s = "/" + entered_IP;
					System.out.println(s);
					if (tokens[0].equals("Join") && getAdd.equals(s))
					{
						System.out.println("Kewl " + i);
						if (i == 1)
						{
							System.out.println("Received player 1 details");
							bool1 = true;
							name1 = tokens[1];
							if (bool1 && bool2 && bool3)
								System.out.println("All true now, in create game THREAD");
						}
						else if (i == 2)
						{
							bool2 = true;
							name2 = tokens[1];
						}
						else if (i == 3)
						{
							bool3 = true;
							name3 = tokens[1];
						}
						ith_Joined = true;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		}

	}




	public static class StartGameThread extends Thread
	{
		private static boolean Game_Started = false;
		private static DatagramSocket clientSocket;
		public StartGameThread()
		{
			try
			{
				clientSocket = new DatagramSocket();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		public void run()
		{
			while (!Game_Started)
			{
				// System.out.println("Game not started");
				if (bool1 && bool2 && bool3)
				{
					System.out.println("All Joined");
					// send to all others : All_Joined TODO
					Game_Started = true;
					ArrayList<String> Names = new ArrayList<String>();
					int no_p = IPs.size();
					if (no_p >= 1)
					{
						Names.add(name1);
					}
					if (no_p >= 2)
					{
						Names.add(name2);
					}
					if (no_p == 3)
					{
						Names.add(name3);
					}
					try
					{
						for (int i = 0; i < no_p; i ++)
						{
							// prepare string to be sent.
							InetAddress ip = InetAddress.getByName(IPs.get(i));
							String sendThis = "All_Joined," + PName + ",";
							byte[] sendData = new byte[1024];
							sendData = sendThis.getBytes();
							System.out.println("Sending All Joined");

							DatagramPacket sendPacket = new DatagramPacket(sendData,sendThis.length(),ip , 1901);
							clientSocket.send(sendPacket);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}

					Player p1 = new Player(PName, Plevel, IPs, Ports, Names);
				}
			}
		}
	}
}