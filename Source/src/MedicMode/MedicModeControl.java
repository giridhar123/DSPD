package MedicMode;

import Globals.Control;
import Globals.Globals;

public class MedicModeControl implements Control 
{
	private MedicModeWindow window;
	private Control previousControl;
	
	public MedicModeControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new MedicModeWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void updateAmbulatorioButton()
	{
		if (Globals.utente.puoGestireAmbulatorio())
			window.setAmbulatorioButtonEnable(true);
	}
}
