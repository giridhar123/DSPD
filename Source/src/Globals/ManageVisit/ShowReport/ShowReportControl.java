package Globals.ManageVisit.ShowReport;

import Globals.Control;
import Globals.Entities.Prenotazione;

public class ShowReportControl implements Control{
	
	private ShowReportWindow window;
	private Control previousControl;
	private Prenotazione prenotazione;
	
	public ShowReportControl(Control previousControl, Prenotazione prenotazione) {
		this.previousControl = previousControl;
		this.prenotazione = prenotazione;
	}
	
	public void openWindow()
	{
		window = new ShowReportWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void fillContents()
	{
		window.setCfFieldText(prenotazione.getCfPaziente());
		window.setPazienteFieldText(prenotazione.getNomePaziente() + " " + prenotazione.getCognomePaziente());
		window.setNreFieldText(prenotazione.getNre());
		window.setDataFieldText(prenotazione.getData());
		window.setAmbulatorioFieldText(prenotazione.getNomeAmbulatorio());
		window.setTipoFieldText(prenotazione.getNomeTipo());
		window.setOraInizioFieldText(prenotazione.getOraInizio());
		window.setOraFineFieldText(prenotazione.getOraFine());
		window.setReportAreaText(prenotazione.getReferto());
	}
}
