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
    this.setResizable(false);
		startBoard();
	}

	public void startBoard()
	{
		/** calls function to add the Game JPanel child **/
    
		this.setLayout(new GridLayout(3, 1, 0, 0));
		this.setSize(800,800);

    this.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent windowEvent){
         System.exit(0);
        }        
     	}); 
      headerLabel = new JLabel("Welcome to PingPong Game!", JLabel.CENTER);
      headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
      headerLabel.setForeground(Color.BLUE);  

      // statusLabel.setSize(350,100);
      controlPanel = new JPanel();
      controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

      this.add(headerLabel);
      this.add(controlPanel);
      addFields();
      // this.pack();
      // mainFrame.add(statusLabel);
      this.setVisible(true);
      // this.getContentPane().setBackground(Color.BLUE);

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
    namelabel.setHorizontalAlignment(JLabel.CENTER);
    namelabel.setFont(new Font("Garamond", Font.BOLD, 26));
    namelabel.setForeground(Color.decode("#05b8cc"));


		JLabel levelLabel = new JLabel("Choose Game Level:", JLabel.CENTER);
    levelLabel.setFont(new Font("Garamond", Font.BOLD, 26));
    levelLabel.setForeground(Color.decode("#05b8cc")); 

		userText = new JTextField(10);
    userText.setHorizontalAlignment(JTextField.CENTER);
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

      JPanel main_name = new JPanel();
      main_name.setLayout(new FlowLayout());
      namelabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,40));
      main_name.add(namelabel);

      JPanel userP = new JPanel();
      userP.add(userText);
      userP.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLUE));
      main_name.add(userP);
      controlPanel.add(main_name);

      JPanel main_level = new JPanel();
      main_level.setLayout(new FlowLayout());
      levelLabel.setBorder(BorderFactory.createEmptyBorder(0,40,0,20));
      main_level.add(levelLabel);

      JPanel sp1 = new JPanel();
      sp1.add(spinner);
      sp1.setBorder(BorderFactory.createEmptyBorder(0,10,0,20));     
      main_level.add(sp1);
      controlPanel.add(main_level);
      // controlPanel.setBackground(Color.RED);
      this.setVisible(true);
	}

}