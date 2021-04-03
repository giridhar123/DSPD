package MedicMode.VisiteAmbulatorio.Causal;

import Globals.Control;
import Globals.Book.Calendar.CalendarControl;
import Globals.Entities.Prenotazione;

public class CausalControl implements Control{

	private Control previousControl;
	private CausalWindow window;
	private Prenotazione prenotazione;
	
	public CausalControl(Control previousControl, Prenotazione prenotazione)
	{
		this.previousControl = previousControl;
		this.prenotazione = prenotazione;
	}
	
	public void openWindow()
	{
		window = new CausalWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void onConfirmButtonPressed()
	{
		String causale = window.getCausalFieldText();
		CalendarControl calendarControl = new CalendarControl(previousControl, causale, prenotazione, prenotazione.getCfPaziente(), prenotazione.getCodAmbulatorio());
		calendarControl.openWindow();
		closeWindow();
	}
}
