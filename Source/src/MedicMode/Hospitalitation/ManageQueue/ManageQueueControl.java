package MedicMode.Hospitalitation.ManageQueue;

import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Control;
import Globals.Globals;
import Globals.Notification.NotificationControl;

public class ManageQueueControl implements Control {

	private ManageQueueWindow window;
	private Control previousControl;
	
	public ManageQueueControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManageQueueWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}

	public void fillTables()
	{	
		fillRicoveratiTable();
		fillInCodaTable();
		checkFreeBads();
	}
	
	private void fillRicoveratiTable()
	{
		window.clearRicoveratiTable();
		int reparto = Globals.utente.getReparto();
		String nome = null;
		String cognome = null;
		
		//Riempio la tabella dei ricoverati
		String query = "SELECT CF, Nome, Cognome, dataInizio "
				+ "FROM Ricovero, Persona "
				+ "WHERE RefCFPaziente = CF"
				+ " AND RefReparto = " + reparto
				+ " AND dataFine IS NULL"
				+ " AND priorita IS NULL";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				nome = rs.getString(2);
				cognome = rs.getString(3);
				window.addRowIntoRicoveratiTable(rs.getString(1), nome + " " + cognome, rs.getString(4));
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void fillInCodaTable()
	{
		window.clearInCodaTable();
		int reparto = Globals.utente.getReparto();
		String nome = null;
		String cognome = null;
		
		//Riempio la tabella delle persone in coda
		String query = "SELECT CF, Nome, Cognome, dataInizio, priorita "
					+ "FROM Ricovero, Persona "
					+ "WHERE RefCFPaziente = CF"
					+ " AND RefReparto = " + reparto
					+ " AND dataFine IS NULL"
					+ " AND priorita != 0"
					+ " ORDER BY (priorita)";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				nome = rs.getString(2);
				cognome = rs.getString(3);
				window.addRowIntoInCodaTable(rs.getString(1), nome + " " + cognome, rs.getString(4), rs.getString(5));
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public void checkFreeBads()
	{
		boolean freeBads = false;
		int postiLettoOccupati = 0;
		int postiLettoDisponibili = 0;
		int postiLetto = 0;
		String oggi = Globals.getTodayDate();
		
		// Cerco il numero di posti letto per il reparto in cui lavora il medico
		String query = "SELECT N_PostiLetto "
						+ "FROM Reparto "
						+ "WHERE CodReparto = " + Globals.utente.getReparto();
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
				
		try {
			if (rs.next())
				postiLetto =  rs.getInt(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		// Cerco il numero di posti letto occupati in quel giorno per quel reparto
		query = "SELECT COUNT(*) "
			+ "FROM Ricovero "
			+ "WHERE RefReparto = '" + Globals.utente.getReparto() + "' "
			+ "AND dataInizio <= '" + oggi + "' "
			+ "AND dataFine IS NULL "
			+ "AND priorita IS NULL";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
					
		try {
			if (rs.next())
			{
				postiLettoOccupati = rs.getInt(1);
				postiLettoDisponibili = postiLetto - postiLettoOccupati;

				//Se ci sono posti letto disponibili
				if (postiLettoDisponibili > 0)
					freeBads = true;
				
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
				
		if (freeBads)
			window.setRicoveraButtonEnable(true);
		else
			window.setRicoveraButtonEnable(false);
	}
	
	public void dismissButtonPressed(String cfPaziente)
	{
		// Prendo la data corrente
		String oggi = Globals.getTodayDate();
				
		String query = "UPDATE Ricovero SET DataFine = '" + oggi + "', Priorita = NULL"
				+ " WHERE RefCFPaziente = '" + cfPaziente + "'";
		
		if (!Globals.getDbmsConnection().updateQuery(query))
		{
			NotificationControl notificationControl = new NotificationControl("Impossible dimettere il paziente");
			notificationControl.openWindow();
			return;
		}
		fillRicoveratiTable();
		window.setRicoveraButtonEnable(true);
	}
	
	public void hospitalitationButtonPressed(String cfPaziente, int oldPriorita)
	{
		// Prendo la data corrente
		String oggi = Globals.getTodayDate();
		String query = null;
						
		//Ricovero il paziente
		query = "UPDATE Ricovero SET DataInizio = '" + oggi + "', Priorita = NULL"
					 + " WHERE RefCFPaziente = '" + cfPaziente + "'"
					 + " AND priorita = " + oldPriorita;
		
		if (!Globals.getDbmsConnection().updateQuery(query))
		{
			NotificationControl notificationControl = new NotificationControl("Impossible ricoverare il paziente");
			notificationControl.openWindow();
			return;
		}
		
		//Cerco quante persone ci sono in coda
		int reparto = Globals.utente.getReparto();
		query = "SELECT COUNT(*) "
				+ "FROM Ricovero "
				+ "WHERE RefReparto = " + reparto
				+ " AND priorita != 0";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		int personeInCoda = 0;
		
		try {
			if (rs.next())
				personeInCoda = rs.getInt(1) + 1;
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		for (int i = oldPriorita + 1; i <= personeInCoda; ++i)
		{
			query = "UPDATE Ricovero SET priorita = " + (i-1) + " WHERE Priorita = " + i + " AND RefReparto = " + reparto;
			if (!Globals.getDbmsConnection().updateQuery(query))
			{
				NotificationControl notificationControl = new NotificationControl("Impossible ricoverare il paziente");
				notificationControl.openWindow();
				return;
			}
		}
		
		if (!Globals.getDbmsConnection().updateQuery(query))
		{
			NotificationControl notificationControl = new NotificationControl("Impossible ricoverare il paziente");
			notificationControl.openWindow();
			return;
		}
		fillRicoveratiTable();
		fillInCodaTable();
		checkFreeBads();
	}
}
