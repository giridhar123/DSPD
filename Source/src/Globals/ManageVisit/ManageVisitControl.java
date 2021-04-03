package Globals.ManageVisit;

import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Control;
import Globals.Globals;
import Globals.Book.ModifyBook.ModifyBookControl;
import Globals.Entities.Prenotazione;
import Globals.ManageVisit.ShowReport.ShowReportControl;
import Globals.Notification.NotificationControl;

public class ManageVisitControl  implements Control{

	private ManageVisitWindow window;
	private Control previousControl;
	
	public ManageVisitControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManageVisitWindow(this, previousControl);
		window.open(window);
	}
	
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	/*
	 * Aggiungiamo le righe alla tabella della gestione visita con le visite prenotate dal paziente
	 */
	public void fillTable()
	{
		//recupero i dati da inserire nella tabella delle visite
		String query = "SELECT RefNre "
					+ " FROM visita "
					+ " WHERE RefCfPaziente = '" + Globals.utente.getCf() + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while(rs.next()) {
				Prenotazione prenotazione = new Prenotazione(rs.getString(1));
				window.addRowToTable(prenotazione);
			}
		} catch (SQLException e) {
			if(Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public void onVisitClicked()
	{
		int riga = window.getSelectedRow();
		
		if (riga != -1)
		{
			String nre = window.getValueOfTableAt(riga, 0);
			Prenotazione prenotazione = new Prenotazione(nre);			
		
			if (prenotazione.hasReferto())
			{
				ShowReportControl reportControl = new ShowReportControl(this, prenotazione);
				reportControl.openWindow();
				closeWindow();
			}
			else
			{				
				if (prenotazione.hasRemindSent())
				{
					NotificationControl notificationControl = new NotificationControl("Impossibile modificare la prenotazione in quanto è avennuta la conferma.");
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
}
