package Globals.Book.ModifyBook;

import Globals.Control;
import Globals.Globals;
import Globals.Book.Calendar.CalendarControl;
import Globals.Entities.Prenotazione;
import Globals.Notification.NotificationControl;
import MedicMode.VisiteAmbulatorio.Causal.CausalControl;

public class ModifyBookControl implements Control{

	private ModifyBookWindow window;
	private Control previousControl;
	private Prenotazione prenotazione;
	
	public ModifyBookControl(Control previousControl, Prenotazione prenotazione)
	{
		this.previousControl = previousControl;
		this.prenotazione = prenotazione;
	}
	
	public void openWindow()
	{
		window = new ModifyBookWindow(this, previousControl, prenotazione);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void fillContents()
	{
		window.setNreFieldText(prenotazione.getNre());
		window.setTipoVisitaFieldText(prenotazione.getNomeTipo());
		window.setDataFieldText(prenotazione.getData());
		window.setOraInizioFieldText(prenotazione.getOraInizio());
		window.setOraFineFieldText(prenotazione.getOraFine());
		window.setAmbulatorioFieldText(prenotazione.getNomeAmbulatorio());
		window.setRepartoFieldText(prenotazione.getNomeReparto());
	}
	
	public void modifyBookButtonPressed() {
		String cfUtente = Globals.utente.getCf();
		String cfPaziente = prenotazione.getCfPaziente();
		
		//Se l'utente autenticato sta modificando la prenotazione di un altro utente...
		if (!cfUtente.equals(cfPaziente))
		{
			CausalControl causalControl = new CausalControl(this, prenotazione);
			causalControl.openWindow();
		}
		else
		{
			CalendarControl calendarControl = new CalendarControl(this, null, prenotazione, cfPaziente, prenotazione.getCodAmbulatorio());
			calendarControl.openWindow();
		}
	}
	
	public void deleteBookButtonPressed()
	{		
		if (prenotazione.delete())
		{
			NotificationControl notificationControl = new NotificationControl("Prenotazione cancellata con successo.");
			notificationControl.openWindow();
		}
	}
}
