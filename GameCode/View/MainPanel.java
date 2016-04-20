import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
	/** We use a JPanel because it is easy to draw 2D objects 
	* on a JPanel using paintComponent() method. **/
	private static final Default_Height;
	private static final Default_Width;

	public MainPanel()
	{
		this.setPreferredSize(Default_Width, Default_Height);
		this.setVisible(true);
	}
}