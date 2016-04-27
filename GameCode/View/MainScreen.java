package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
// import Model.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;


public class MainScreen extends JFrame
{
	/** This class is a JFrame, that will contain the first screen that appears.	**/
	private JLabel headerLabel;
   	private JPanel controlPanel;
   	public JTextField userText;
   	public JSpinner spinner;

	public MainScreen()
	{
		/** Constructor. Sets visibility true. **/
		super("PingPongMain");
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
		this.setLayout(new GridLayout(3, 1, 0, 50));
		this.setSize(800,800);
      	this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      	});    
      headerLabel = new JLabel("Welcome to PingPong Game!", JLabel.CENTER);

      // statusLabel.setSize(350,100);
      controlPanel = new JPanel();
      controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

      this.add(headerLabel);
      this.add(controlPanel);
      addFields();
      // this.pack();
      // mainFrame.add(statusLabel);
      this.setVisible(true);  
  	}

  	public JLabel GetMain_Head()
  	{
  		return headerLabel;
  	}

  	public JPanel GetMain_Panel()
  	{
  		return controlPanel;
  	}

	public void addFields()
	{
		// headerLabel.setText(""); 

		JLabel namelabel= new JLabel("Your Name", JLabel.CENTER);
		JLabel levelLabel = new JLabel("Choose Game Level:", JLabel.CENTER);
		userText = new JTextField(2);
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

      controlPanel.add(namelabel);
      controlPanel.add(userText);
      controlPanel.add(levelLabel);       
      controlPanel.add(spinner);
      this.setVisible(true);
	}

}