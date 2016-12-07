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

public class PingPong {
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
	// private static ArrayList<Integer> Ports;
	private static long Wait_for_Join_Start;
	private static long Wait_for_JoinGameThread;
	private static JoinGameThread jg1;
	private static JoinGameThread2 jg2;
	private static StartGameThread start_final;
	private static CreateGameThread cg1;
	private static JLabel waiting;
	private static JoinGameScreen join;
	private static JPanel jo_bttn_ring;
	private static JPanel main_bttn;
	private static boolean cr_Added;
	private static boolean jo_Added;
	
	public PingPong() {
	}

	public static void main(String[] args) {
   		m = new MainScreen();
   		main_hd = m.GetMain_Head();
   		main_panel = m.GetMain_Panel();

   		JButton createGame = new JButton("Create New Game");
   		JButton joinGame = new JButton("Join Another Game");
		main_bttn = new JPanel();
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
		final CreateGameScreen create = new CreateGameScreen(); // JPanel
		JButton create_final = new JButton("Start Game!");
		JButton backfromCr = new JButton("Back to Main Menu");
		final JPanel cr_bttn = new JPanel();
		final JPanel cr_bttn_ring = new JPanel();
		cr_bttn_ring.setLayout(new BoxLayout(cr_bttn_ring, BoxLayout.Y_AXIS));
		cr_bttn.setLayout(new FlowLayout());
		cr_bttn.add(create_final);
		cr_bttn.add(backfromCr);
		cr_bttn_ring.add(cr_bttn);

		// JPanel:
		join = new JoinGameScreen();
		final JButton join_final = new JButton("Request to join!");
		JButton backfromJo = new JButton("Back to Main Menu");
		final JPanel jo_bttn = new JPanel();
		jo_bttn_ring = new JPanel();
		jo_bttn_ring.setLayout(new BoxLayout(jo_bttn_ring, BoxLayout.Y_AXIS));
		jo_bttn.setLayout(new FlowLayout());
		jo_bttn.add(join_final);
		jo_bttn.add(backfromJo);
		jo_bttn_ring.add(jo_bttn);


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
   				m.getContentPane().add(cr_bttn_ring);
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
   				m.getContentPane().add(jo_bttn_ring);
				m.revalidate();
				m.repaint();
			}
		});

		create_final.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// CHECK THE 3 IP, PORTs ENTERED.
				ArrayList<JTextField> ips = create.GetIPs();
				// ArrayList<JSpinner> ports = create.GetPorts();
				// DISABLE THIS BUTTON!

				IPs = new ArrayList<String>();
				// Ports = new ArrayList<Integer>();

				int no_players = ips.size();

				for (int i = 0; i < no_players; i ++) {
					IPs.add((ips.get(i)).getText());
					// Ports.add((int)(ports.get(i)).getValue());
				}
				System.out.println(IPs);
				Wait_for_Join_Start = System.currentTimeMillis();
				System.out.println(Wait_for_Join_Start + " Time" + no_players);

				ImageIcon image = new ImageIcon("loading.gif");
				waiting = new JLabel(image, SwingConstants.CENTER); //TODO
				// waiting.setAlignmentX(Component.CENTER_ALIGNMENT);
				// create.add(Box.createVerticalGlue());
				cr_bttn_ring.add(waiting);
				cr_Added = true;
				// create.add(Box.createVerticalGlue());
				m.revalidate();
				m.repaint();

				if (no_players > 0) {
					cg1 = new CreateGameThread(IPs);
					cg1.start();
					if (no_players == 1) {
						bool2 = true;
						name2 = "";
						bool3 = true;
						name3 = "";
					}
					else if (no_players == 2) {
						bool3 = true;
					}
				}
				else {
					System.out.println("No players : " + no_players);
					bool1 = true;
					bool2 = true;
					bool3 = true;
				}

				if(start_final != null)
					start_final.stop();

				start_final = new StartGameThread();
				start_final.start();
						// this one sends final msg to all others.
			}
		});

		join_final.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				join_final.setEnabled(false);
				// SEND 1 PACKET TO IP, port, say 'Join'S Thread!
				ImageIcon image = new ImageIcon("loading.gif");
				waiting = new JLabel(image, SwingConstants.CENTER); //TODO
				jo_bttn_ring.add(waiting);
				jo_Added = true;
				m.revalidate();
				m.repaint();

				jg1 = new JoinGameThread((int) join.spinner.getValue(),join.userIP.getText());
				jg1.start();

				jg2 = new JoinGameThread2((int) join.spinner.getValue(),join.userIP.getText());
				jg2.start();
				Wait_for_JoinGameThread = System.currentTimeMillis();

				// check if player can join the given IP's address.
				// Receiver thread TODO : gif
			}
		});

		backfromCr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				m.getContentPane().remove(create);
				m.getContentPane().remove(cr_bttn_ring);
				if (cr_Added) {
					cr_bttn_ring.remove(waiting);
					cr_Added = false;					
				}

				System.out.println("PINGPONG: Components removed again");

				addMainScreen();
				if (cg1 != null)
					cg1.stop();

				if (start_final != null)
					start_final.stop();

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
			public void actionPerformed(ActionEvent ae) {
				m.getContentPane().remove(join);
				m.getContentPane().remove(jo_bttn_ring);
				if (jo_Added) {
					jo_bttn_ring.remove(waiting);
					jo_Added = false;					
				}
				join_final.setEnabled(true);
				if (jg1 != null)
					jg1.stop();
				if (jg2 != null)
					jg2.stop();
				addMainScreen();
				m.getContentPane().add(main_bttn);
				m.revalidate();
				m.repaint();

			}
		});
		// Player p1 = new Player(); : do this when a game has started.
	}

	public static void clearMainScreen() {
		m.getContentPane().remove(main_hd);
		m.getContentPane().remove(main_panel);
	}

	public static void addMainScreen() {
		m.setLayout(new GridLayout(3,1,0,50));
		m.getContentPane().add(main_hd);
		m.getContentPane().add(main_panel);
	}

	public static class JoinGameThread extends Thread {
		public boolean done;

		private static DatagramSocket clientSocket;
		private static String sendTo_IP;
		private static int sendTo_Port;

		/** The thread that sends Join signal, and waits
		* till the main IP says that all players have joined. **/

		public JoinGameThread(int port, String IP) {
			done = false;
			sendTo_IP = IP;
			sendTo_Port = port;
			try {
				// serverSocket = new DatagramSocket(1900);
				clientSocket = new DatagramSocket();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			/**  This is called when thread.start is called. Sends 'Join' repeatedly, and 
			* waits for the creator IP to send the signal of AllJoined + all other IPs & Ports.**/
			while (!done) {
				try {
					// SEND THEN RECEIVE.
					InetAddress IP_game = InetAddress.getByName(sendTo_IP);
					// int send_to_port = sendTo_Port;
					String send_this = "Join," + PName;
					System.out.println("PINGPONG:" + send_this);
					byte[] sendData = (send_this).getBytes();
					// sendData = send_this.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData,send_this.length(), IP_game, 1800);
					clientSocket.send(sendPacket);

					done = true;
				}
				catch(Exception e) {
					e.printStackTrace();
				}			
			}
		}
	}

	public static class JoinGameThread2 extends Thread {
		private static DatagramSocket serverSocket;
		private static byte[] receiveData = new byte[1024];
		public static String Received_Str;
		public static boolean done2;
		private static String sendTo_IP;
		private static int sendTo_Port;

		public JoinGameThread2(int port, String IP) {
			done2 = false;
			sendTo_IP = IP;
			sendTo_Port = port;
			try {
				serverSocket = new DatagramSocket(1901);
				serverSocket.setSoTimeout(6000);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (!done2) {
				try {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					Received_Str =  new String(receivePacket.getData());
					System.out.println("PINGPONG:" +"Length : " + Received_Str.length() + "Received : " + Received_Str);

					String [] temp2 = Received_Str.split("\\s+");
					String [] tokens=temp2[0].split(",");
					System.out.println("PINGPONG:" +tokens[0] + " " + receivePacket.getAddress());
					String getAddSend = (receivePacket.getAddress()).toString();
					System.out.println("PINGPONG:" +getAddSend + " : Converted string");
					String s2 = "/" + sendTo_IP;
					System.out.println("PINGPONG:" +s2);
					// All_Joined, apna_naam, IP1, port1, name1, IP2, Port2, name2
					if (tokens[0].equals("All_Joined") && getAddSend.equals(s2)) {
						// being received from the Creator IP.
						done2 = true;
						ArrayList<String> joinIPs = new ArrayList<String>();
						ArrayList<String> joinNames = new ArrayList<String>();

						// 1. creator of game IP at 0
						int no_tokens = tokens.length;
						int no_players;
						if (no_tokens == 27)
							no_players = 4;
						else if (no_tokens == 18)
							no_players = 3;
						else if (no_tokens == 11)
							no_players = 2;
						else
							no_players = 1;

						System.out.println("PINGPONG:" +"No players: " + no_players);

						for (int i = 0; i < no_players; i ++) {
							joinIPs.add(tokens[2*i + 1]);
							// joinPorts.add(Integer.parseInt((tokens[3*i + 2]).trim()));
							joinNames.add(tokens[2*i + 2]);
						}

						// clientSocket.clfplayeose();
						// has other 3.
						System.out.println("PINGPONG:" +"Starting Player at Join");
						// LEVEL MUST BE THAT OF CREATOR!
						System.out.println("PINGPONG:" +"IPs at joiner : " + joinIPs);
						m.setVisible(false);
						int[][] ports = new int[no_players][no_players];
						for (int i = 0; i < no_players; i ++)
							for (int j = 0; j < no_players; j ++)
								ports[i][j] = Integer.parseInt(tokens[no_players*i + j + 1 + 2*no_players]);

						Player p_join = new Player(PName, Integer.parseInt((tokens[tokens.length - 1]).trim()),joinIPs,ports, joinNames,Integer.parseInt((tokens[tokens.length - 2]).trim()));
					}
				}
				catch(SocketTimeoutException e) {
					// Time out!
					JOptionPane.showMessageDialog(null,"Could not connect to the IP provided! \n Press OK to go back to Main Menu");
					m.getContentPane().remove(join);
					m.getContentPane().remove(jo_bttn_ring);
					jo_bttn_ring.remove(waiting);

					System.out.println("Components removed again");

					addMainScreen();
					if (cg1 != null)
						cg1.stop();

					if (start_final != null)
						start_final.stop();

					if (jg1 != null)
						jg1.stop();
					m.getContentPane().add(main_bttn);
					m.revalidate();
					m.repaint();
					this.stop();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static class CreateGameThread extends Thread {
		// 1 serverSocket for receiving.
		// list of clientSocket to send.
		public static boolean[] ith_Joined;
		public static boolean Game_Started;
		private static String[] entered_IP;

		private static DatagramSocket serverSocket; // receiving.
		private static byte[] receiveData = new byte[1024];
		public static String Received_Str;


		public CreateGameThread(ArrayList<String> otherIP) {
			// sets bool I, name I.
			int x = otherIP.size();
			ith_Joined = new boolean[x];
			for (int i = 0; i < x; i ++)
				ith_Joined[i] = false;
			entered_IP = new String[x];
			for (int i = 0; i < x; i ++)
				entered_IP[i] = "/" + otherIP.get(i);
			System.out.println("PINGPONG:" +entered_IP);
			try {
				serverSocket = new DatagramSocket(1800); // receive

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		public static int matchAny(String ip) {
			boolean rthis = false;
			for (int i = 0; i < entered_IP.length && !rthis ; i ++) {
				if (entered_IP[i].equals(ip)) {
					rthis = true;
					return i;
				}
			}
			return -1;
		}

		public static void updateGameStart() {
			for (int i = 0; i < ith_Joined.length; i ++)
				Game_Started = Game_Started && ith_Joined[i];
		}

		public void run() {
			while (!(this.Game_Started)) {
				try {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("PINGPONG:" +"Receive packet 1: ");
					serverSocket.receive(receivePacket);
					// byte[] data = new byte[receivePacket.getLength()];
					System.out.println("PINGPONG:" +"Received Packet 2: ");
					Received_Str =  new String(receivePacket.getData());

					System.out.println("PINGPONG:" +"Received : " + Received_Str);

					// char[] char_in = Received_Str.toCharArray();
					Received_Str = Received_Str.replace(" ","");
					Received_Str = Received_Str.replace("\t", "");
					System.out.println("PINGPONG:" +Received_Str + " : replaced");
					String [] temp2 = (Received_Str).split("\\s+");

					char[] annoyingchar = new char[1];
        			char[] charresult = Received_Str.toCharArray();
        			String result = "";
			        for(int i=0;i<charresult.length;i++) {
            			if(charresult[i]==annoyingchar[0])
                			break;
			            result+=charresult[i];
        			}

        			System.out.println("PINGPONG:" +"Final : " + result);

					String [] tokens=result.split(",");
					if(tokens[1] != null)
						System.out.println(tokens[1] + " " + receivePacket.getAddress());
					String getAdd = (receivePacket.getAddress()).toString();
					System.out.println(getAdd + " : Converted string");
					int i = matchAny(getAdd);

					if (tokens[0].equals("Join") && i > -1) {
						System.out.println("PINGPONG:" +"Kewl " + i);
						if (i == 0) {
							System.out.println("PINGPONG:" +"Received player 1 details");
							bool1 = true;
							name1 = tokens[1];
							if (bool1 && bool2 && bool3)
								System.out.println("PINGPONG:" +"All true now, in create game THREAD");
						}
						else if (i == 1) {
							bool2 = true;
							name2 = tokens[1];
						}
						else if (i == 2) {
							bool3 = true;
							name3 = tokens[1];
						}
						ith_Joined[i] = true;
						updateGameStart();
						// this.sleep(120000);
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class StartGameThread extends Thread {
		private static boolean Game_Started = false;
		private static DatagramSocket clientSocket;
		private static int[][] player4;
		private static int[][] player3;
		private static int[][] player2;
		private static int[][] player1;
		public StartGameThread() {
			Game_Started = false;
			player4 = new int[4][4];
			player3 = new int[3][3];
			player2 = new int[2][2];
			for (int i = 0; i < 4; i ++)
				for (int j = 0; j < 4; j ++)
					player4[i][j] = 1000+4*i+j;

			for (int i = 0; i < 3; i ++)
				for (int j = 0; j < 3; j ++)
					player3[i][j] = 1000+3*i+j;

			for (int i = 0; i < 2; i ++)
				for (int j = 0; j < 2; j ++)
					player2[i][j] = 1000+2*i+j;

			player1 = new int[1][1];
			player1[0][0] = -1;

			try {
				clientSocket = new DatagramSocket();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (!Game_Started) {
				// System.out.println("Game not started");
				// update wait_for_join.
				System.out.println(System.currentTimeMillis());
				if ((System.currentTimeMillis() - Wait_for_Join_Start) > 120000) {
					System.out.println("PINGPONG:" +"Time exceeded. Starting game.");
					int no_pl = IPs.size();
					ArrayList<String> PNames = new ArrayList<String>();
					ArrayList<String> finalIPs = new ArrayList<String>();
					// ArrayList<Integer> finalPorts = new ArrayList<Integer>();
					PNames.add(PName);
					try {
						finalIPs.add(0, InetAddress.getLocalHost().getHostAddress());
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					// finalPorts.add(0, 1001);
					if (no_pl == 0) {
						m.setVisible(false);
						Player p1 = new Player(PName, Plevel, finalIPs, player1, PNames,0);
					}
					else if (no_pl >= 1) {
						if (bool1) {
							PNames.add(name1);
							finalIPs.add(IPs.get(0));
							// finalPorts.add(Ports.get(0));
						}
					}
					else if (no_pl >= 2) {
						if (bool2) {
							PNames.add(name2);
							finalIPs.add(IPs.get(1));
							// finalPorts.add(Ports.get(1));
						}
					}
					else { // 3 more players.
						if (bool3) {
							PNames.add(name3);
							finalIPs.add(IPs.get(2));
							// finalPorts.add(Ports.get(2));
						}
					}

					// send to those active.
					int no = finalIPs.size();
					try {
						String sendThis2 = "All_Joined,";
						// I AM Player 0. IPs[0] is player 1 IPs[1] is Player 2 IPs[2] is Player 3.
						for (int i = 0; i < finalIPs.size() ; i ++) {
							sendThis2 += finalIPs.get(i) + ",";
							// sendThis2 += finalPorts.get(i) + ",";
							sendThis2 += PNames.get(i) + ",";
						}

						// append ARRAY STRING.
						if (no == 4)
							sendThis2 += ArrToString(player4, 4);
						else if (no == 3)
							sendThis2 += ArrToString(player3, 3);
						else if (no == 2)
							sendThis2 += ArrToString(player2, 2);
						else
							sendThis2 += "-1,";


						for (int i = 1; i < no ; i ++)
						{
							// prepare string to be sent.
							InetAddress ip = InetAddress.getByName(finalIPs.get(i));
							byte[] sendData = new byte[1024];
							String sData = sendThis2;
							sData += i;
							sData += "," + Plevel;
							sendData = sData.getBytes();
							System.out.println("PINGPONG:" +"Sending All Joined\n" + sData);

							DatagramPacket sendPacket = new DatagramPacket(sendData,sData.length(),ip , 1901);
							clientSocket.send(sendPacket);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
					m.setVisible(false);
					cg1.stop();
					Player p;
					if (no == 4)
						p = new Player(PName, Plevel, finalIPs, player4, PNames,0);
					else if (no == 3)
						p = new Player(PName, Plevel, finalIPs, player3, PNames,0);
					else if (no == 2)
						p = new Player(PName, Plevel, finalIPs, player2, PNames,0);
					else
						p = new Player(PName, Plevel, finalIPs, player1, PNames,0);
					Game_Started = true;
					this.stop();
				}
				else {
					// System.out.println("bool1 :" + Boolean.toString(bool1) + " bool2 :" + Boolean.toString(bool2) + "bool3 :" + Boolean.toString(bool3));

					if (bool1 && bool2 && bool3) {
						System.out.println("PINGPONG:" +"All Joined");
						Game_Started = true;

						ArrayList<String> Names = new ArrayList<String>();
						Names.add(PName);
						try {
							IPs.add(0, InetAddress.getLocalHost().getHostAddress());
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						// Ports.add(0, 1001);

						int no_p = IPs.size();
						if (no_p >= 2) {
							Names.add(name1);
						}
						if (no_p >= 3) {
							Names.add(name2);
						}
						if (no_p == 4) { // as own IP also included, at 0th posn.
							Names.add(name3);
						}
						try {
							String sendThis = "All_Joined,";
							System.out.println("PINGPONG:" +no_p+"Shift is bad "+ sendThis);
							// I AM Player 0. IPs[0] is player 1 IPs[1] is Player 2 IPs[2] is Player 3.
							for (int i = 0; i < no_p ; i ++) {
								sendThis += IPs.get(i) + ",";
								// sendThis += Ports.get(i) + ",";
								sendThis += Names.get(i) + ",";
							}

							if (no_p == 4)
								sendThis += ArrToString(player4, 4);
							else if (no_p == 3)
								sendThis += ArrToString(player3, 3);
							else if (no_p == 2)
								sendThis += ArrToString(player2, 2);
							else
								sendThis += "-1,";

							System.out.println("PINGPONG:" + sendThis);

							for (int i = 1; i < no_p; i ++) {
								// prepare string to be sent.
								InetAddress ip = InetAddress.getByName(IPs.get(i));
								byte[] sendData = new byte[1024];
								String s2 = "" + sendThis;
								s2 += i;
								s2 += "," + Plevel;
								sendData = s2.getBytes();
								System.out.println("PINGPONG:" +"Sending All Joined\n" + s2);

								DatagramPacket sendPacket = new DatagramPacket(sendData,s2.length(),ip , 1901);
								clientSocket.send(sendPacket);
							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						// IPs has all IPs, including my own.
						m.setVisible(false);
						if (cg1 != null)
							cg1.stop();
						Player p1;
						if (no_p == 4)
							p1 = new Player(PName, Plevel, IPs, player4, Names,0);
						else if (no_p == 3)
							p1 = new Player(PName, Plevel, IPs, player3, Names,0);
						else if (no_p == 2)
							p1 = new Player(PName, Plevel, IPs,player2 , Names,0);
						else
							p1 = new Player(PName, Plevel, IPs,player1 , Names,0);
						this.stop();
					}
				}
			}
		}
	}

	public static String ArrToString(int[][] arr, int size) {
		String s = "";
		for (int i = 0; i < size; i ++) {
			for (int j = 0; j < size; j ++)
				s += arr[i][j] + ",";
		}
		return s;
	}
}