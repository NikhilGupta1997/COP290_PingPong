package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
// import Model.*;
import java.util.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.event.*;


public class JoinGameScreen extends JPanel
{
	/** This class is a JFrame, that will contain the first screen that appears.	**/
	private JLabel headerLabel;
   	private JPanel controlPanel;
   	public JTextField userIP;
   	public JSpinner spinner;

	public JoinGameScreen()
	{
		/** Constructor. Sets visibility true. **/
		// super("JoinGame");
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(800,800);
      headerLabel = new JLabel("Join Another Game", JLabel.CENTER);
      headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
      headerLabel.setForeground(Color.BLUE);
      headerLabel.setBorder(BorderFactory.createEmptyBorder(100,0,40,0));

      // statusLabel.setSize(350,100);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      try{
         JPanel theIP = new JPanel();
         theIP.setLayout(new FlowLayout());
         JLabel IPText = new JLabel("My IP Address is :      ");
         JLabel Address = new JLabel(InetAddress.getByName("10.192.32.60").getHostAddress());

         IPText.setFont(new Font("Garamond", Font.BOLD, 18));
         IPText.setForeground(Color.decode("#05b8cc")); 
         IPText.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        
         Address.setFont(new Font("Garamond", Font.BOLD, 18));
         Address.setForeground(Color.RED); 
         Address.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
         theIP.add(IPText); 
         theIP.add(Address);
         theIP.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
         theIP.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

         JPanel labels = new JPanel();
         labels.setLayout(new FlowLayout());
         JLabel MyIP = new JLabel("IP Address      ");
         JLabel MyPort = new JLabel("Port");
         
         MyIP.setFont(new Font("Garamond", Font.BOLD, 18));
         MyIP.setForeground(Color.decode("#05b8cc")); 
         MyIP.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        
         MyPort.setFont(new Font("Garamond", Font.BOLD, 18));
         MyPort.setForeground(Color.decode("#05b8cc")); 
         MyPort.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
         labels.add(MyIP); 
         labels.add(MyPort);
         labels.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));

      this.add(headerLabel);
      this.add(theIP);
      this.add(labels);
      this.add(controlPanel);
      addFields();
   }
   catch(Exception e)
   {
      System.out.println("Still not found");
   }
      // this.pack();
      // mainFrame.add(statusLabel);
      // this.setVisible(true);  
  	}

	public void addFields()
	{
		// headerLabel.setText(""); 

		userIP = new JTextField("0.0.0.0", 10);
      userIP.setHorizontalAlignment(JTextField.RIGHT);
      // userIP.setFont(new Font("Garamond", Font.BOLD, 20));
      // userIP.setForeground(Color.decode("#05b8cc")); 
      // userIP.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		SpinnerModel spinnerModel =
         new SpinnerNumberModel(0, //initial value
            0, //min
            9999, //max
            1);//step
		spinner = new JSpinner(spinnerModel);
		spinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });
      spinner.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
      controlPanel.add(userIP);
      controlPanel.add(spinner);
      // this.setVisible(true);
	}

}