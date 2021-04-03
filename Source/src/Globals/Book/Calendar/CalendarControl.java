package Globals.Book.Calendar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import Globals.Control;
import Globals.Globals;
import Globals.Entities.Prenotazione;
import Globals.Entities.Ricetta;
import Globals.Notification.NotificationControl;

public class CalendarControl implements Control{
	
	private boolean isWindowOpened;
	private CalendarWindow window;
	private Control previousControl;
	private String causale;
	private Ricetta ricetta;
	private String cfPaziente;
	private String ambulatorio;
	private String query;
	private int count;
	private ArrayList<Prenotazione> visite;
	
	public CalendarControl(Control previousControl, String causale, Ricetta ricetta, String cfPaziente, String ambulatorio)
	{
		this.visite = new ArrayList<>();
		this.previousControl = previousControl;
		this.causale = causale;
		this.ricetta = ricetta;
		this.cfPaziente = cfPaziente;
		this.ambulatorio = ambulatorio;
		fillArrayList();
		isWindowOpened = false;
	}
	
	public void openWindow() {
		this.window = new CalendarWindow(this, previousControl);
		window.open(window);
		isWindowOpened = true;
	}
	
	public void closeWindow() {
		window.closeFrame();
		isWindowOpened = false;
	}
	
	//funzione che scrive i numeri 1,2... come 01, 02...
	public String format(int val){
		String temp;
		if(val < 10){
			 temp = "0" + val;
			return temp;
		}
		else 
			return Integer.toString(val);
	}
	
	
	//disabilito i bottoni degli orari prenotati per ogni data
	public void disableButtons(ButtonGroup buttonGroup){
		String year = format(window.year);
		String month = format(window.month+1);
		String day = format(window.day);
		String date = year+"-"+month+"-"+day;
		
		
		Iterator<Prenotazione> iterator = visite.iterator();
		while(iterator.hasNext()){
			Prenotazione prenotazione = iterator.next();
			String dataPrenotazione = prenotazione.getData();
			
			int compare = prenotazione.comparePriority(ricetta.getPriorita());
			
			/*
			 * Disabilito i tasti solo se:
			 * la prenotazione che sto per fare ha la stessa data della prenotazione dell'array list
			 * la prenotazione nell'array list deve avere remindSent = false
			 * Oppure se la prenotazione dell'array list ha priorit� maggiore (ma solo se il giorno selezionato � verde)
			 */
			
			if(!dataPrenotazione.equals(date) || prenotazione.hasRemindSent() || (compare < 0 && isGreen(Integer.parseInt(day))))
				continue;
			
			String[] oraInizio = prenotazione.getOraInizio().split(":");
			String[] oraFine = prenotazione.getOraFine().split(":");
			String oraInizioEOraFine = oraInizio[0] + ":" + oraInizio[1] + "/" + oraFine[0] + ":" + oraFine[1] ;
			for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (oraInizioEOraFine.equals(button.getText()))
					button.setEnabled(false);
			}
		}		
	}
	
	/*
	 * nuova funzione per ottenere le visite e disabilitare i bottoni
	 */
	private void fillArrayList(){
		String query = "SELECT RefNRE FROM Visita WHERE Visita.RefCfDottore IS NULL AND Visita.RefAmbulatorio = '" + ambulatorio + "' AND Visita.RemindSent = 0";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if(rs == null)
			return;
		try {
			while(rs.next()) {
				Prenotazione p = new Prenotazione(rs.getString(1));
				visite.add(p);
			}
		} catch (SQLException e) {
			if(Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	
	public void saveInDB(String ora)
	{
		String year = format(window.year);
		String month = format(window.month + 1);
		String day = format(window.day);
		String date = year + "-" + month + "-" + day;
		String[] orario = ora.split("/");
		String oraInizio = orario[0];
		String oraFine = orario[1];
		boolean slitta = false;
		String nreDaSlittare = null;
		String dataDaSlittare = null;
		String oraInizioDaSlittare = null;
		
		//Controllo se in quel giorno c'è già una visita prenotata
		query = "SELECT RefNRE, data, oraInizio "
				+ " FROM visita "
				+ " WHERE Data = '" + date + "' "
				+ " AND OraInizio = '" + oraInizio + "' "
				+ " AND OraFine = '" + oraFine + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next()) //Se esiste un'altra visita per quella data/ora... la devo slittare al primo orario disponibile...
			{
				nreDaSlittare = rs.getString(1);
				dataDaSlittare = rs.getString(2);
				oraInizioDaSlittare = rs.getString(3);
				slitta = true;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		if (slitta)
		{
			String[] orari = {"09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00"};
			boolean orarioFound = false;
			int i;
			
			//For per inizializzare i per fare in modo di poter slittare la visita solamente in avanti e non indietro
			for (i = 0; i < orari.length - 1; ++i)
				if (oraInizioDaSlittare.equals(orari[i]))
					break;
			
			//Fino a quando non trovo un orario libero
			while (!orarioFound)
			{
				for (; i < orari.length - 1 ; ++i)
				{
					query = "SELECT RefNre "
							+ " FROM Visita "
							+ " WHERE data = '" + dataDaSlittare + "' "
							+ " AND oraInizio = '" + orari[i] + "'";
					rs = Globals.getDbmsConnection().selectQuery(query);
					if (rs == null)
						return;
				
					try {
						if (!rs.next()) //C'è un buco in quella data e ora
							orarioFound = true;
					} catch (SQLException e) {
						if (Globals.DEBUG)
							e.printStackTrace();
					}
				}
				//Fine for - Tutti gli orari in quella data sono occupati
				dataDaSlittare = aumentaDiUnGiorno(dataDaSlittare.split("/"));
				i = 0;
			}

			query = "UPDATE Visita SET Data = '" + dataDaSlittare + "', oraInizio = '" + orari[i] + "', oraFine = '" + orari[i + 1] + "' WHERE RefNre = '" + nreDaSlittare + "'";
			if (!Globals.getDbmsConnection().updateQuery(query))
				return;
			
			query = "INSERT INTO avvisovisita VALUES "
					+ " ('" + nreDaSlittare + "', 'Prenotazione spostata per dare spazio ad una prenotazione con urgenza maggiore.', 0)";
			if (!Globals.getDbmsConnection().insertQuery(query))
				return;
		}
		
		//Verifico se la visita con lo stesso nre è già prenotata
		query = "SELECT RefNRE From visita Where refNRE = '" + ricetta.getNre() + "'";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next()) //Se è gia prenotata allora aggiorno la nuova data e ora
			{
				query = "UPDATE Visita SET Data = '" + date + "', oraInizio = '" + oraInizio + "', orafine = '" + oraFine + "' WHERE RefNre = '" + ricetta.getNre() + "'";
				if (!Globals.getDbmsConnection().updateQuery(query))
					return;
				
				if (causale != null) //Aggiungo la causale nel DB che verrà inviata via mail dal server
				{
					query = "INSERT INTO AvvisoVisita VALUES ('" + ricetta.getNre() + "', '" + causale + "', 0)";
					if (!Globals.getDbmsConnection().insertQuery(query))
						return;
				}
			}
			else //Inserisco la prenotazione da zero
			{
				//inserisco visita nel DB
				query = "insert into visita values('" + ricetta.getNre() + "', '" + cfPaziente + "', '" + date + "', '" + oraInizio + "', '" + oraFine + "', '" + ambulatorio + "', null, 0, 0)";
				if (!Globals.getDbmsConnection().insertQuery(query))
					return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
    	NotificationControl notificationControl = new NotificationControl("<html> Visita prenotata </html>");;
    	notificationControl.openWindow();
    	previousControl.openWindow();
    	closeWindow();
	}
	
	private String aumentaDiUnGiorno(String[] data)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(data[0] + "-" + data[1] + "-" + data[2]));
		} catch (ParseException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		c.add(Calendar.DATE, 1);  // numero di giorni da aggiungere
		return sdf.format(c.getTime());  // ritorna la data incrementata		
	}
	
	private boolean isGreen(int aDay)
	{		
		for (int i = 0; i < CalendarData.CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CalendarData.CALENDAR_WIDTH; j++) {
				int giornoBottone = window.calDates[i][j];
				if (aDay != giornoBottone)
					continue;
				
				String testoBottone = window.dateButs[i][j].getText();
				if (testoBottone.equals("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>"))
					return true;

				break;
			}
		}
		return false;
	}
	
	public void updateButtonsForPriority()
	{
		String priorita = ricetta.getPriorita();
		
		if(priorita.equals("B")) {
			setPriorityB();	
		}		
		if(priorita.equals("U")) {
			setPriorityU();
		}		
		if(priorita.equals("P")) {
			setPriorityP();
		}
		if(priorita.equals("D")) {
			setPriorityD();
		}
	}
	
	//imposto di colore verde i 10 giorni successivi alla data di emissione della ricetta
	public void setPriorityB() {
		int mese = window.month;
		String[] dataEmissione = ricetta.getDataEmissione().split("-");
		int meseEmissione = Integer.parseInt(dataEmissione[1]) - 1;
		int giornoEmissione = Integer.parseInt(dataEmissione[2]);
		
		for (int i = 0; i < CalendarData.CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CalendarData.CALENDAR_WIDTH; j++) {
		
				int giorno = window.calDates[i][j];		
				
				if(mese == meseEmissione) {
					if(giorno > giornoEmissione && count < 10 && window.calDates[i][j] != 0 && window.dateButs[i][j].isEnabled()) {
						window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
						count++;
					}
				}
				
				else if(mese == meseEmissione + 1 && count < 10 && giorno < giornoEmissione && window.calDates[i][j] != 0 && window.dateButs[i][j].isEnabled()) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
					count++;
				}
			}
		}
	}
	
	//imposto di colore verde i 2 giorni successivi  alla data di emissione della ricetta
	public void setPriorityU() {
		int mese = window.month;
		String[] dataEmissione = ricetta.getDataEmissione().split("-");
		int meseEmissione = Integer.parseInt(dataEmissione[1]) - 1;
		int giornoEmissione = Integer.parseInt(dataEmissione[2]);
		
		for (int i = 0; i < CalendarData.CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CalendarData.CALENDAR_WIDTH; j++) {
		
				int giorno = window.calDates[i][j];		
				
				if(mese == meseEmissione) {
					if(giorno > giornoEmissione && count < 2 && window.calDates[i][j] != 0 && window.dateButs[i][j].isEnabled()) {
						window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
						count++;
					}
				}
				
				else if(mese == meseEmissione + 1 && count < 2 && giorno < giornoEmissione && window.calDates[i][j] != 0 && window.dateButs[i][j].isEnabled()) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
					count++;
				}
			}	
		}
	}
	
	//imposto di colore verde i 2 mesi successivi alla data di emissione della ricetta 
	public void setPriorityD() {
		int mese = window.month;
		String[] dataEmissione = ricetta.getDataEmissione().split("-");
		int meseEmissione = Integer.parseInt(dataEmissione[1]) - 1;
		int giornoEmissione = Integer.parseInt(dataEmissione[2]);
		
		for (int i = 0; i < CalendarData.CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CalendarData.CALENDAR_WIDTH; j++) {
		
				int giorno = window.calDates[i][j];		
				
				if(mese == meseEmissione ) {
					if(giorno > giornoEmissione)
						window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
				
				else if(mese > meseEmissione && mese < meseEmissione+2) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
				
				else if(mese == meseEmissione+2 && giorno < giornoEmissione) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
			}		
		}
	}
	
	//imposto di colore verde i 3 mesi successivi  alla data di emissione della ricetta
	public void setPriorityP() {
		int mese = window.month;
		String[] dataEmissione = ricetta.getDataEmissione().split("-");
		int meseEmissione = Integer.parseInt(dataEmissione[1]) - 1;
		int giornoEmissione = Integer.parseInt(dataEmissione[2]);
		
		for (int i = 0; i < CalendarData.CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CalendarData.CALENDAR_WIDTH; j++) {
		
				int giorno = window.calDates[i][j];		
				
				if(mese == meseEmissione) {
					if(giorno > giornoEmissione)
						window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
				
				else if(mese > meseEmissione && mese < meseEmissione+3) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
				
				else if(mese == meseEmissione+3 && giorno < giornoEmissione) {
					window.dateButs[i][j].setText("<html><font color=#00d418>" + window.calDates[i][j] + "</font></html>");
				}
			}	
		}
	}
	
	public boolean isWindowOpened()
	{
		return isWindowOpened;
	}
}