package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
// import Model.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;


public class CreateGameScreen extends JPanel
{
	/** This class is a JFrame. This appears when a user creates a new game	**/
   	private JLabel headerLabel;
   	private JPanel p;
      // private JPanel p2;
      // private JPanel p3;
   	public ArrayList<JTextField> IPs;
      public ArrayList<JSpinner> Ports;

	public CreateGameScreen()
	{
		/** Constructor. Sets visibility true. **/
		// super("PingPongCreate");
      IPs = new ArrayList<JTextField>();
      Ports = new ArrayList<JSpinner>();
		startBoard();
	}

   public ArrayList<JTextField> GetIPs()
   {
      return IPs;
   }

   public ArrayList<JSpinner> GetPorts()
   {
      return Ports;
   }

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(800,800);
      headerLabel = new JLabel("Enter opponents' details :", JLabel.CENTER);
      headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
      headerLabel.setForeground(Color.BLUE);
      headerLabel.setBorder(BorderFactory.createEmptyBorder(100,0,40,0));

      // statusLabel.setSize(350,100);
      p = new JPanel();
      p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

      // p3 = new JPanel();
      // p3.setLayout(new FlowLayout());

      this.add(headerLabel);


      addSpinner();
      this.add(p);
      // this.pack();
      // mainFrame.add(statusLabel);
      // this.setVisible(true);  
  	}

   public void addSpinner()
   {
      JPanel nop = new JPanel(new FlowLayout());
      JLabel levelLabel = new JLabel("Select number of players:", JLabel.CENTER);
      levelLabel.setFont(new Font("Garamond", Font.BOLD, 26));
      levelLabel.setForeground(Color.decode("#05b8cc")); 

         JPanel labels = new JPanel();
         labels.setLayout(new FlowLayout());
         JLabel MyIP = new JLabel("IP Address      ");
         JLabel MyPort = new JLabel("Port");
         labels.add(MyIP);
         MyIP.setFont(new Font("Garamond", Font.BOLD, 18));
         MyIP.setForeground(Color.decode("#05b8cc")); 
         MyIP.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
         labels.add(MyPort);
         MyPort.setFont(new Font("Garamond", Font.BOLD, 18));
         MyPort.setForeground(Color.decode("#05b8cc")); 
         MyPort.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

            SpinnerModel spinnerModel =
         new SpinnerNumberModel(0, //initial value
            0, //min
            3, //max
            1);//step
      JSpinner spinner = new JSpinner(spinnerModel);
      spinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // CHANGE Number of IP-Port fields.
            int no_play = (int) ((JSpinner)e.getSource()).getValue();
            labelCheck(no_play, labels);
            addFields(no_play);
         }
      });
      nop.add(levelLabel);
      nop.add(spinner);
      this.add(nop);
      

   }

   public void labelCheck(int n, JPanel panel)
   {
      this.remove(panel);
      if(n != 0)
      {
         this.add(panel);
      }
      this.revalidate();
      this.repaint();

   }

	public void addFields(int n)
	{
		// headerLabel.setText("");
      this.remove(p);
      IPs = new ArrayList<JTextField>();
      Ports = new ArrayList<JSpinner>();

  

      p = new JPanel(new GridLayout(n,1,0,6));
      for (int i = 0; i < n; i ++)
      {
      
         JPanel ith = new JPanel();
         ith.setLayout(new FlowLayout());
         JTextField ith_T = new JTextField("IP address " + (i + 1));
         IPs.add(ith_T);
         SpinnerModel spinnerModel =
         new SpinnerNumberModel(0, //initial value
            0, //min
            9999, //max
            1);//step
      JSpinner spinner1 = new JSpinner(spinnerModel);
      spinner1.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });
      Ports.add(spinner1);
         // ith_T.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
         spinner1.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
         ith.add(ith_T);
         ith.add(spinner1);
         p.add(ith);
      }

      this.add(p);
      this.revalidate();
      this.repaint();


      // this.setVisible(true);
	}

}
