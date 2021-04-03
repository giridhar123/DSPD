package Globals.About;


import Globals.Control;

public class AboutControl implements Control {
	
	private AboutWindow window;
	
	public AboutControl()
	{
		window = new AboutWindow(this);
	}
	
	public void openWindow()
	{
		window.open(window);
	}
	
	public void closeWindow()
	{	
		window.closeFrame();
	}

}
