import javax.swing.*;

public class GameBoard extends JFrame
{
	/** This class is a JFrame, that will contain all other JPanels.	**/
	private MainPanel GamePanel;

	public GameBoard()
	{
		startBoard();
	}

	public void startBoard()
	{
		setVisible(true);
		addGamePanel();
	}

	public void addGamePanel()
	{
		this.add(GamePanel);
	}

}