package MedicMode.VisiteAmbulatorio.NewReport;

import Globals.Control;
import Globals.Entities.Prenotazione;
import Globals.Notification.NotificationControl;

public class NewReportControl implements Control{

	private Control previousControl;
	private NewReportWindow window;
	private Prenotazione prenotazione;
	
	public NewReportControl(Control previousControl, Prenotazione prenotazione)
	{
		this.previousControl = previousControl;
		this.prenotazione = prenotazione;
	}
	
	public void openWindow()
	{
		window = new NewReportWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}

	public void okButtonPressed()
	{ 
		if (!prenotazione.addReferto(window.getReportFieldText()))
			return;
	
		closeWindow();
		previousControl.openWindow();
		NotificationControl notificationControl = new NotificationControl("Referto inserito con successo.");
		notificationControl.openWindow();
	}
}
