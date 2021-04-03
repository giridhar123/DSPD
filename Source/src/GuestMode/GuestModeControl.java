package GuestMode;

import Globals.Control;

public class GuestModeControl implements Control{
	
	private GuestModeWindow window;
	private Control previousControl;
	
	public GuestModeControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new GuestModeWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
}
