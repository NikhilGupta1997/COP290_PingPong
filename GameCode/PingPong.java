import java.awt.*;
import Model.*;
import View.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.*;
import Physics.*;


public class PingPong
{
	private static String PName;
	private static int Plevel;
	private static MainScreen m;
	private static JLabel main_hd;
	private static JPanel main_panel;
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

 
   		m.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

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
				Player p1 = new Player(PName, Plevel);
			}
		});

		join_final.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				// check if player can join the given IP's address.
			}
		});

		backfromCr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				m.getContentPane().remove(create);
				m.getContentPane().remove(cr_bttn);

				System.out.println("Components removed again");

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
}