package MedicMode.VisiteAmbulatorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import Globals.Control;
import MedicMode.VisiteAmbulatorio.NewReport.NewReportControl;
import Globals.Globals;
import Globals.Book.ModifyBook.ModifyBookControl;
import Globals.Entities.Prenotazione;
import Globals.Notification.NotificationControl;

public class VisiteAmbulatorioControl implements Control {

	private Control previousControl;
	private VisiteAmbulatorioWindow window;
	
	public VisiteAmbulatorioControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new VisiteAmbulatorioWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void fillTable()
	{
		//Cerco le prenotazioni dell'ambulatorio in cui lavora l'utente ma che non sono state completate
		String query = "SELECT RefNre "
				+ " FROM Visita, Ambulatorio "
				+ " WHERE RefAmbulatorio = CodAmbulatorio "
				+ " AND RefReparto = " + Globals.utente.getReparto() + " "
				+ " AND RefCfDottore IS NULL";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				Prenotazione prenotazione = new Prenotazione(rs.getString(1));				
				window.addRowToTable(prenotazione);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public void onRowClicked()
	{
		int riga = window.getSelectedRow();
		if (riga == -1)
			return;
		
		String nre = window.getValueFromTable(riga, 0);
		Prenotazione prenotazione = new Prenotazione(nre);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		
		Date dataVisita = null;
		Date dataOggi = null;
		try {
			dataVisita = sdf.parse(prenotazione.getData());
			dataOggi = sdf.parse(gc.get(GregorianCalendar.YEAR) + "-" + (gc.get(GregorianCalendar.MONTH) + 1) + "-" + gc.get(GregorianCalendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
			
			return;
		}
		
		//Se il giorno della prenotazione è prima di oggi
		if (dataVisita.compareTo(dataOggi) <= 0)
		{
			//Apro la schermata per inserire il referto
			NewReportControl newReportControl = new NewReportControl(this, prenotazione);
			newReportControl.openWindow();
			closeWindow();
		}
		else
		{
			if (prenotazione.hasRemindSent())
			{
				NotificationControl notificationControl = new NotificationControl("Visita non modificabile perchè confermata.");
				notificationControl.openWindow();
			}
			else
			{
				ModifyBookControl modifyBookControl = new ModifyBookControl(this, prenotazione);
				modifyBookControl.openWindow();
				closeWindow();
			}
		}
	}
}
