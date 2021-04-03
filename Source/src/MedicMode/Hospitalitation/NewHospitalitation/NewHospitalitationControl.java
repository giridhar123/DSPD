package MedicMode.Hospitalitation.NewHospitalitation;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import Globals.Globals;
import Globals.Notification.NotificationControl;
import Globals.Control;

public class NewHospitalitationControl implements Control {
	
	private NewHospitalitationWindow window;
	private Control previousControl;
	
	public NewHospitalitationControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new NewHospitalitationWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void checkButtonPressed()
	{
		StringBuilder message = new StringBuilder("<html>");
		String query = null;
		ResultSet rs = null;
		
		String cfPaziente = window.getCfFieldText().toLowerCase();
		if (cfPaziente.length() != 16)
		{
			window.setCfFieldBackgroundColor(Color.RED);
			message.append("Codice fiscale non valido</html>");
			NotificationControl notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
			return;
		}
		
		window.setCfFieldEnable(false);
		window.setCfFieldBackgroundColor(Color.WHITE);
		
		if (Globals.isPersonInDB(cfPaziente))
		{
			query = "SELECT Nome, Cognome, Data_Nascita, Sesso, Indirizzo_Residenza, Telefono, Citta, Email "
				  + "FROM Persona "
				  + "WHERE CF = '"
				  + cfPaziente + "'";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
		
			try {
				if (rs.next())
				{
					window.setNomeFieldText(rs.getString(1));
					window.setCognomeFieldText(rs.getString(2));
					String[] nascita = rs.getString(3).split("-");
				
					window.setNascitaDate(nascita);
				
					String sesso = rs.getString(4);
					if (sesso.equals("M"))
						window.setSessoComboBoxSelection(0);
					else
						window.setSessoComboBoxSelection(1);
				
					window.setIndirizzoFieldText(rs.getString(5));
					window.setTelefonoFieldText(rs.getString(6));
					window.setCittaFieldText(rs.getString(7));
					window.setEmailFieldText(rs.getString(8));
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		else //L'utente non è nel DB
		{
			if (!Globals.getDbmsConnection().checkConnection())
				return;
			window.setCfFieldEnable(false);
			window.setNomeFieldEnable(true);
			window.setCognomeFieldEnable(true);
			window.setNascitaDateEnabled(true);
			window.setSessoComboBoxEnable(true);
			window.setEmailFieldEnable(true);
			window.setTelefonoFieldEnable(true);
			window.setIndirizzoFieldEnable(true);
			window.setCittaFieldEnable(true);
		}
		
		window.setConfirmDataButtonEnable(true);
	}
	
	public void confirmDataButtonPressed()
	{
		NotificationControl notificationControl;
		StringBuilder message = new StringBuilder("<html>");
		boolean error = false;
		String nome = window.getNomeFieldText();
		String cognome = window.getCognomeFieldText();
		String[] dataNascita = window.getNascitaDate().split("/");
		String indirizzo = window.getIndirizzoFieldText();
		String telefono = window.getTelefonoFieldText();
		String email = window.getEmailFieldText();
		
		// Il nome non deve essere vuoto
		if (nome.isEmpty())
		{
			error = true;
			message.append("Nome non valido<br/>");
			window.setNomeFieldBackgroundColor(Color.RED);
		}
		else
			window.setNomeFieldBackgroundColor(Color.WHITE);
		
		// Il cognome non deve essere vuoto
		if (cognome.isEmpty())
		{
			error = true;
			message.append("Cognome non valido<br/>");
			window.setCognomeFieldBackgroundColor(Color.RED);
		}
		else
			window.setCognomeFieldBackgroundColor(Color.WHITE);
		
		// Il numero di telefono deve essere di 10 caratteri
		if (telefono.length() != 10)
		{
			error = true;
			message.append("Numero di telefono non valido<br/>");
			window.setTelefonoFieldBackgroundColor(Color.RED);
		}
		else
			window.setTelefonoFieldBackgroundColor(Color.WHITE);
		
		//La data deve essere scelta
		if (dataNascita[0].equals("Anno"))
		{
			error = true;
			message.append("Anno di nascita non valido<br/>");
			window.setAnnoNascitaBackgroundColor(Color.RED);
		}
		else
			window.setAnnoNascitaBackgroundColor(Color.WHITE);
		
		if (dataNascita[1].equals("Mese"))
		{
			error = true;
			message.append("Mese di nascita non valido<br/>");
			window.setMeseNascitaBackgroundColor(Color.RED);
		}
		else
			window.setMeseNascitaBackgroundColor(Color.WHITE);
		
		if (dataNascita[2].equals("Giorno"))
		{
			error = true;
			message.append("Giorno di nascita non valido<br/>");
			window.setGiornoNascitaBackgroundColor(Color.RED);
		}
		else
			window.setGiornoNascitaBackgroundColor(Color.WHITE);
		
		
		// L'indirizzo non deve essere vuoto
		if (indirizzo.isEmpty())
		{
			error = true;
			message.append("Indirizzo non valido<br/>");
			window.setIndirizzoFieldBackgroundColor(Color.RED);
		}
		else
			window.setIndirizzoFieldBackgroundColor(Color.WHITE);
		
		//L'email non deve essere vuota
		if (email.isEmpty())
		{
			error = true;
			message.append("Email non valida<br/>");
			window.setEmailFieldBackgroundColor(Color.RED);
		}
		else
			window.setEmailFieldBackgroundColor(Color.WHITE);
		
		if (!error)
		{
			//Disattivo tutto
			window.setNomeFieldEnable(false);
			window.setCognomeFieldEnable(false);
			window.setNascitaDateEnabled(false);
			window.setSessoComboBoxEnable(false);
			window.setEmailFieldEnable(false);
			window.setTelefonoFieldEnable(false);
			window.setIndirizzoFieldEnable(false);
			window.setCittaFieldEnable(false);
			
			checkFreeBads();
		}
		else
		{
			message.append("</html>");
			notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
		}
	}
	
	private void checkFreeBads()
	{
		String query;
		ResultSet rs;
		int postiLetto = 0;
		int postiLettoOccupati = 0;
		int postiLettoDisponibili = 0;
		
		// Cerco il numero di posti letto per il reparto in cui lavora il medico
		query = "SELECT N_PostiLetto "
				+ " FROM Reparto "
				+ " WHERE CodReparto = " + Globals.utente.getReparto();
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				postiLetto =  rs.getInt(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		// Prendo la data corrente
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
		int meseCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int giornoCorrente = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		String data = annoCorrente + "/" + meseCorrente + "/" + giornoCorrente;
		
		// Cerco il numero di posti letto occupati in quel giorno per quel reparto
		query = "SELECT COUNT(*) "
				+ "FROM Ricovero "
				+ "WHERE RefReparto = '" + Globals.utente.getReparto() + "' "
				+ "AND dataInizio <= '" + data + "' "
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
				{
					if (postiLettoDisponibili == 1)
						window.setPostiLettoLabelText("C'è " + postiLettoDisponibili + " posto letto disponibile.");
					else
						window.setPostiLettoLabelText("Ci sono " + postiLettoDisponibili + " posti letto disponibili.");
					
					window.sethospitalizeButtonVisible(true);
				}
				else
				{
					int personeInCoda = 0;
					
					// Cerco il numero di persone in coda
					String query2 = "SELECT COUNT(*) "
							+ " FROM Ricovero "
							+ " WHERE RefReparto = " + Globals.utente.getReparto()
							+ " AND priorita IS NOT NULL";
					ResultSet rs2 = Globals.getDbmsConnection().selectQuery(query2);
					if (rs2 == null)
						return;
					
					try {
						if (rs2.next())
							personeInCoda = rs2.getInt(1);
					} catch (SQLException e) {
						if (Globals.DEBUG)
							e.printStackTrace();
					}
					
					window.setPostiLettoLabelText("<html>Non ci sono posti letto disponibili.<br/> Ci sono " + personeInCoda + " persone in coda.<br/>Si desidera mettere il paziente in coda?</html>");
					window.setMettiInCodaButtonVisible(true);
				}
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public void mettiInCodaButtonPressed()
	{
		String query = null;
		ResultSet rs = null;
		NotificationControl notificationControl;
		String cfPaziente = window.getCfFieldText().toLowerCase();
		int reparto = Globals.utente.getReparto();
		
		// Prendo la data corrente
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
		int meseCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int giornoCorrente = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String oggi = annoCorrente + "/" + meseCorrente + "/" + giornoCorrente;
		
		//Controllo se quel paziente è gia ricoverato
		query = "SELECT * "
			  + " FROM Ricovero "
			  + " WHERE RefCFPaziente = '" + cfPaziente + "' "
		      + " AND dataFine IS NULL "
		      + " AND priorita IS NULL";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next()) //Paziente già ricoverato
			{
				notificationControl = new NotificationControl("Paziente già ricoverato");
				notificationControl.openWindow();
				return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		//Controllo se il paziente è gia messo in coda
		query = "SELECT * FROM Ricovero "
			+ " WHERE RefCFPaziente = '" + cfPaziente + "' "
			+ " AND dataFine IS NULL "
			+ " AND priorita != 0";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
			
		try {
			if (rs.next()) //Paziente già messo in coda
			{
				notificationControl = new NotificationControl("Paziente già in coda");
				notificationControl.openWindow();
				return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		//Controllo se il paziente è presente nel DB
		if (!Globals.isPersonInDB(cfPaziente))
		{
			String nomePaziente = window.getNomeFieldText();
			String cognomePaziente = window.getCognomeFieldText();
			String dataNascita = window.getNascitaDate();
			String sesso = window.getSessoComboBoxText();
			String indirizzoResidenza = window.getIndirizzoFieldText();
			String telefono = window.getTelefonoFieldText();
			String citta = window.getCittaFieldText();
			String email = window.getEmailFieldText();
			if (!Globals.addPersonaToDB(cfPaziente, nomePaziente, cognomePaziente, dataNascita, sesso, indirizzoResidenza, telefono, citta, email))
				return;
		}
		
		int priorita = 0;
		
		// Cerco il numero di persone in coda
		query = "SELECT COUNT(*) "
				+ " FROM Ricovero "
				+ " WHERE RefReparto = " + reparto
				+ " AND priorita != 0";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				priorita = rs.getInt(1) + 1;
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		// Metto in coda il paziente
		query = "INSERT INTO Ricovero (RefCFPaziente, RefReparto, dataInizio, dataFine, priorita) "
				+ "VALUES "
				+ "('" + cfPaziente + "', '" + reparto + "', '" + oggi + "', null, " + priorita + ")";
		
		if (!Globals.getDbmsConnection().insertQuery(query))
			return;
		
		notificationControl = new NotificationControl("Paziente messo in coda correttamente.");
		notificationControl.openWindow();
	}
	
	public void hospitalizeButtonPressed()
	{
		String query = null;
		ResultSet rs = null;
		NotificationControl notificationControl;
		String cfPaziente = window.getCfFieldText().toLowerCase();
		int reparto = Globals.utente.getReparto();
		
		// Prendo la data corrente
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
		int meseCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int giornoCorrente = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String oggi = annoCorrente + "/" + meseCorrente + "/" + giornoCorrente;
		
		//Controllo se quel paziente è gia ricoverato
		query = "SELECT * FROM Ricovero "
			  + "WHERE RefCFPaziente = '" + cfPaziente + "' "
		      + "AND dataFine IS NULL "
		      + "AND priorita IS NULL";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next()) //Paziente già ricoverato
			{
				notificationControl = new NotificationControl("Paziente già ricoverato");
				notificationControl.openWindow();
				return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		//Controllo se il paziente è presente nel DB
		if (!Globals.isPersonInDB(cfPaziente))
		{
			String nomePaziente = window.getNomeFieldText();
			String cognomePaziente = window.getCognomeFieldText();
			String dataNascita = window.getNascitaDate();
			String sesso = window.getSessoComboBoxText();
			String indirizzoResidenza = window.getIndirizzoFieldText();
			String telefono = window.getTelefonoFieldText();
			String citta = window.getCittaFieldText();
			String email = window.getEmailFieldText();
			if (!Globals.addPersonaToDB(cfPaziente, nomePaziente, cognomePaziente, dataNascita, sesso, indirizzoResidenza, telefono, citta, email))
				return;
		}
		
		// Ricovero il paziente
		query = "INSERT INTO Ricovero (RefCFPaziente, RefReparto, dataInizio, dataFine, priorita) "
				+ "VALUES "
				+ "('" + cfPaziente + "', '" + reparto + "', '" + oggi + "', null, null)";
		
		if (!Globals.getDbmsConnection().insertQuery(query))
			return;
		
		notificationControl = new NotificationControl("Paziente ricoverato correttamente.");
		notificationControl.openWindow();
	}
}

