package MedicMode.Hospitalitation.ManageHospitalitation;

import Globals.Control;
import Globals.Globals;

public class ManageHospitalitationControl implements Control{
	
	private ManageHospitalitationWindow window;
	private Control previousControl;
	
	public ManageHospitalitationControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManageHospitalitationWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void updateManageQueueButton()
	{
		if (Globals.utente.puoGestireReparto())
			window.setManageQueueButtonEnable(true);
	}
}
