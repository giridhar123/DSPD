package UserMode;

import Globals.Control;

public class UserModeControl implements Control{

	private UserModeWindow window;
	private Control previousControl;
	
	public UserModeControl(Control previousControl) {
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new UserModeWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
}
