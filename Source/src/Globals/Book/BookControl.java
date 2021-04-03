package Globals.Book;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import Globals.Control;
import Globals.Globals;
import Globals.Book.Calendar.CalendarControl;
import Globals.Book.Document.DocumentControl;
import Globals.Entities.Ricetta;
import Globals.Notification.NotificationControl;

public class BookControl implements Control{
	
	private BookWindow window;
	private Control previousControl;
	private boolean autoFill;
	private String query;
	private ResultSet rs;
	private NotificationControl notificationControl;
	private Ricetta ricetta;
	
	public BookControl(Control previousControl, boolean autoFill)
	{
		this.previousControl = previousControl;
		this.autoFill = autoFill;
	}
	
	public void openWindow()
	{
		window = new BookWindow(this, previousControl);
		if (autoFill)
			checkUserFields();
		
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void checkUserFields()
	{
		//Se l'utente non è loggato
		if (Globals.utente == null)
			return;
		
		window.setCfFieldText(Globals.utente.getCf());
		window.setCfFieldEnabled(false);
		
		String[] nascita;
		nascita = Globals.utente.getDataNascita().split("-");
		window.setDate(nascita);
		window.setDateEnabled(false);
		
		window.setNomeFieldText(Globals.utente.getNome());
		window.setNomeFieldEnabled(false);
		
		window.setCognomeFieldText(Globals.utente.getCognome());
		window.setCognomeFieldEnabled(false);
		
		window.setIndirizzoFieldText(Globals.utente.getIndirizzo());
		window.setIndirizzoFieldEnabled(false);
		
		window.setCittaFieldText(Globals.utente.getCitta());
		window.setCittaFieldEnabled(false);
	
		window.setTelefonoFieldText(Globals.utente.getTelefono());
		window.setTelefonoFieldEnabled(false);
		
		if (Globals.utente.getSesso().equals("M"))
			window.setSessoComboBoxIndex(0);
		else window.setSessoComboBoxIndex(1);
		
		window.setSessoComboBoxEnabled(false);

		window.setEmailFieldText(Globals.utente.getEmail());
		window.setEmailFieldEnabled(false);
	}
	
	//verifico la validit� del campo NRE(codiceRicetta)
	public void confirmPressed() {
		StringBuilder message = new StringBuilder("<html>");
		boolean error = false;
		
		String cfPaziente = window.getCfFieldText();
		String dataNascita = window.getDate();
		String nomePaziente = window.getNomeFieldText();
		String cognomePaziente = window.getCognomeFieldText();
		String sesso = window.getSessoComboBoxText();
		String residenza = window.getResidenzaFieldText();
		String telefono = window.getTelefonoFieldText();
		String codRicetta = window.getCodRicettaFieldText();
		String citta = window.getCittaFieldText();
		String email = window.getEmailFieldText();
		String nre = window.getCodRicettaFieldText();
		String giorno = window.getGiorno();
		String mese = window.getMese();
		String anno = window.getAnno();
		
		if(cfPaziente.length() != 16) {
			window.setCfFieldBackgroudColor(Color.RED);
			message.append("Codice fiscale non valido.<br/>");
			error = true;
		}
		else
			window.setCfFieldBackgroudColor(Color.WHITE);
		
		if(telefono.length() != 10) {
			window.setTelefonoFieldBackgroudColor(Color.RED);
			message.append("Numero di telefono non valido.<br/>");
			error = true;
		}
		else
			window.setTelefonoFieldBackgroudColor(Color.WHITE);
		
		if(nomePaziente.isEmpty()) {
			window.setNomeFieldBackgroundColor(Color.RED);
			message.append("Campo nome vuoto.<br/>");
			error = true;
		}
		else
			window.setCognomeFieldBackgroundColor(Color.WHITE);
		
		if(cognomePaziente.isEmpty()) {
			window.setCognomeFieldBackgroundColor(Color.RED);
			message.append("Campo cognome vuoto.<br/>");
			error = true;
		}
		else
			window.setNomeFieldBackgroundColor(Color.WHITE);
		
		if(citta.isEmpty()) {
			window.setCittaFieldBackgroundColor(Color.RED);
			message.append("Campo citta vuoto.<br/>");
			error = true;
		}
		else
			window.setCittaFieldBackgroundColor(Color.WHITE);
		
		if(residenza.isEmpty()) {
			window.setResidenzaFieldBackgroundColor(Color.RED);
			message.append("Campo residenza vuoto.<br/>");
			error = true;
		}
		else
			window.setResidenzaFieldBackgroundColor(Color.WHITE);
		
		if (email.isEmpty())
		{
			window.setEmailFieldBackgroundColor(Color.RED);
			message.append("Email non valida.<br/>");
			error = true;
		}
		else
			window.setEmailFieldBackgroundColor(Color.WHITE);		
			
		if(nre.length() != 15) {
			window.setCodRicettaFieldBackgroudColor(Color.RED);
			message.append("Codice NRE non valido\n");
			error = true;
		}
		else
			window.setCodRicettaFieldBackgroudColor(Color.WHITE);
		
		//La data non deve essere vuota
		if(giorno.equals("Giorno"))
		{
			error=true;
			message.append("Giorno non valido<br/>");
			window.setGiornoFieldBackgroundColor(Color.RED);
		}
		else window.setGiornoFieldBackgroundColor(Color.WHITE);
				
		if(mese.equals("Mese"))
		{
			error=true;
			message.append("Mese non valido<br/>");
			window.setMeseFieldBackgroundColor(Color.RED);
		}
		else window.setMeseFieldBackgroundColor(Color.WHITE);
				
		if(anno.equals("Anno"))
		{
			error=true;
			message.append("Anno non valido<br/>");
			window.setAnnoFieldBackgroundColor(Color.RED);
		}
		else window.setAnnoFieldBackgroundColor(Color.WHITE);
		
		//la data deve esistere
		if(!checkData(dataNascita)) {
			error=true;
			message.append("Data non valida<br/>");
			window.setAnnoFieldBackgroundColor(Color.RED);
			window.setMeseFieldBackgroundColor(Color.RED);
			window.setGiornoFieldBackgroundColor(Color.RED);
		}
		else {
			window.setAnnoFieldBackgroundColor(Color.WHITE);
			window.setMeseFieldBackgroundColor(Color.WHITE);
			window.setGiornoFieldBackgroundColor(Color.WHITE);
		}

		
		if(!error) {
			//vedo se la persona che sta prenotando è già nel DB
			if(	!Globals.isPersonInDB(cfPaziente))
				Globals.addPersonaToDB(cfPaziente, nomePaziente, cognomePaziente, dataNascita, sesso, residenza, telefono, citta, email);
		}
		else //Si sono verificati errori nei campi
		{
			notificationControl = new NotificationControl(message.toString() + "</html>");
			notificationControl.openWindow();
			return;
		}
				
		//verifico che la ricetta sia nel DB
		query = "SELECT CodNRE "
				+ "FROM Ricetta "
				+ "WHERE CodNRE = '" + nre + "'";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if(rs == null)
			return;
					
		try {
			if(rs.next())
			{
				//Verifico che non ci sia già una prenotazione con lo stesso nre
				query = "SELECT Refnre "
					+ " FROM Visita "
					+ " WHERE Refnre = '" + nre + "'";
				rs = Globals.getDbmsConnection().selectQuery(query);
				if (rs == null)
					return;
				
				if (rs.next())//Se c'è già una visita prenotata con lo stesso nre
				{
					window.setCodRicettaFieldBackgroudColor(Color.RED);
					message.append("Visita già prenotata\n");
					notificationControl = new NotificationControl(message.toString() + "</html>");
					notificationControl.openWindow();
					return;
				}
				
				ricetta = new Ricetta(nre);
				window.setCodRicettaFieldBackgroudColor(Color.white);
				updateRepartoComboBox();
				updateAmbulatoriolist(); //riempi comboBox con gli ambulatori
				window.setCodRicettaFieldTextEnabled(false);
				window.setBookButtonEnabled(true);
				
				window.setCfFieldEnabled(false);
				window.setDateEnabled(false);
				window.setNomeFieldEnabled(false);
				window.setCognomeFieldEnabled(false);
				window.setSessoComboBoxEnabled(false);
				window.setCittaFieldEnabled(false);
				window.setIndirizzoFieldEnabled(false);
				window.setTelefonoFieldEnabled(false);
				window.setEmailFieldEnabled(false);
			}		
			else
			{
				window.setCodRicettaFieldBackgroudColor(Color.RED);
				message.append("Codice NRE non valido\n");
				notificationControl = new NotificationControl(message.toString() + "</html>");
				notificationControl.openWindow();
				return;
			}		
		} catch (SQLException e) {
			if(Globals.DEBUG)
				e.printStackTrace();
		}
	}
		
	//recupera Reparto dal tipo di visita
	private void updateRepartoComboBox() {
		window.setRepartoFieldText(ricetta.getNomeReparto());
	}
		
	//riempi comboBox amubulatorio
	private void updateAmbulatoriolist() {
		window.clearAmbulatorioComboBox();
		int codReparto = ricetta.getCodReparto();
		String query = "SELECT CodAmbulatorio "
					+ " FROM Reparto, Ambulatorio "
					+ " WHERE Reparto.CodReparto = Ambulatorio.RefReparto "
					+ " AND Reparto.CodReparto = '" + codReparto + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
			
		if(rs == null)
			return;
			
		int i = 0;
		try {
			while(rs.next()) {
				window.insertItemInAmbulatorioComboBoxAt(rs.getString(1), i);
				i++;
			}
		} catch (SQLException e) {
			if(Globals.DEBUG)
			e.printStackTrace();
		}
	}
	
	//verifico la validità dei dati anagrafici e apro il calendario
	public void bookButtonPressed() {
		if (ricetta.needDocuments())
		{
			//apro una finestra con l'elenco dei documenti
			DocumentControl documentControl = new DocumentControl(this, ricetta);
			documentControl.openWindow();
			return;
		}
		else { //Non sono necessari documenti, apro il calendario
			if (openCalendar());
			closeWindow();
		}
	}
	
	//funzione che mi permette di aprire il calendario dall dialog dei documenti
	public boolean openCalendar() {
		if (ricetta == null)
			return false;
		
		CalendarControl calendarControl = new CalendarControl(this, null, ricetta, window.getCfFieldText(), window.getAmbulatorioFieldText());
		calendarControl.openWindow();
		
		return calendarControl.isWindowOpened();
	}
	
	//verifica l'esistenza della data
	public boolean checkData(String data){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    dateFormat.setLenient(false);
	    try {
			dateFormat.parse(data.trim());
		} catch (java.text.ParseException e) {
			return false;
		}
	    return true;
	}
}
