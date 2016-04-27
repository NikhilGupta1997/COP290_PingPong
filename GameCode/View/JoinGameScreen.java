package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
// import Model.*;
import java.util.*;
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
		this.setLayout(new GridLayout(3, 1, 0, 50));
		this.setSize(800,800);
      headerLabel = new JLabel("Join Another Game", JLabel.CENTER);

      // statusLabel.setSize(350,100);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      this.add(headerLabel);
      this.add(controlPanel);
      addFields();
      // this.pack();
      // mainFrame.add(statusLabel);
      // this.setVisible(true);  
  	}

	public void addFields()
	{
		// headerLabel.setText(""); 

		userIP = new JTextField("IP");
		SpinnerModel spinnerModel =
         new SpinnerNumberModel(1, //initial value
            1, //min
            5, //max
            1);//step
		spinner = new JSpinner(spinnerModel);
		spinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            // statusLabel.setText("Value : " + ((JSpinner)e.getSource()).getValue());
         }
      });

      controlPanel.add(userIP);
      controlPanel.add(spinner);
      // this.setVisible(true);
	}

}