package Main;

import Globals.Control;

public class MainControl implements Control{

	private MainWindow window;
	
	public void openWindow()
	{
		window = new MainWindow(this);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
}
