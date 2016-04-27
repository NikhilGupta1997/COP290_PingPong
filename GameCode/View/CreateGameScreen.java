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
   	private JPanel p1;
      private JPanel p2;
      private JPanel p3;
   	public JTextField IP1;
      public JTextField IP2;
      public JTextField IP3;
   	public JSpinner spinner1;
      public JSpinner spinner2;
      public JSpinner spinner3;

	public CreateGameScreen()
	{
		/** Constructor. Sets visibility true. **/
		// super("PingPongCreate");
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(800,800);
      headerLabel = new JLabel("Enter opponents' details :", JLabel.CENTER);

      // statusLabel.setSize(350,100);
      p1 = new JPanel();
      p1.setLayout(new FlowLayout());
      p2 = new JPanel();
      p2.setLayout(new FlowLayout());
      p3 = new JPanel();
      p3.setLayout(new FlowLayout());

      this.add(headerLabel);
      this.add(p1);
      this.add(p2);
      this.add(p3);
      addFields();
      // this.pack();
      // mainFrame.add(statusLabel);
      // this.setVisible(true);  
  	}

	public void addFields()
	{
		// headerLabel.setText(""); 

		IP1 = new JTextField("IP address 1");
      IP2 = new JTextField("IP address 2");
      IP3 = new JTextField("IP address 3");
		SpinnerModel spinnerModel =
         new SpinnerNumberModel(0, //initial value
            0, //min
            9999, //max
            1);//step
		spinner1 = new JSpinner(spinnerModel);
		spinner1.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });

      spinner2 = new JSpinner(spinnerModel);
      spinner2.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });

      spinner3 = new JSpinner(spinnerModel);
      spinner3.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });

      p1.add(IP1);
      p1.add(spinner1);
      p2.add(IP2);
      p2.add(spinner2);
      p3.add(IP3);
      p3.add(spinner3);

      // this.setVisible(true);
	}

}