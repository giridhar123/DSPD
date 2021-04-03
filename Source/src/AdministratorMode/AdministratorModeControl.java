package AdministratorMode;

import Globals.Control;

public class AdministratorModeControl implements Control
{
	private AdministratorModeWindow window;
	private Control previousControl;
	
	public AdministratorModeControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new AdministratorModeWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	

}
