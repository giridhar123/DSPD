package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Entities.Prenotazione;
import Entities.Utente;
import Globals.Globals;
import Mail.MailControl;
import Sms.SmsControl;

public class MainControl {
	
	MainBoundary mainBoundary;
	MailControl mailControl;
	SmsControl smsControl;
	
	public MainControl()
	{
		mainBoundary = new MainBoundary();
		mailControl = new MailControl();
		smsControl = new SmsControl();
	}

	public void start()
	{
		while (true)
		{
			try {
				sendRegistrationNotification();
				sendVisitConfirm();
				sendRemindNotification();
				sendAdvise();
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendRegistrationNotification()
	{
		ResultSet rs = null;
		String query = "SELECT RefCF "
					+ "FROM Utente "
					+ "WHERE ConfermaSpedita = 0";
		
		//Cerco gli utenti a cui non ho inviato la notifica di registrazione
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next()) //finchè ci sono utenti a cui non ho inviato la notifica di registrazione
			{
				Utente utente = new Utente(rs.getString(1));
				
				if (utente.getEmail() != null)
				{
					mailControl.sendRegistration(utente);
					mainBoundary.printRegistrationEmailSent(utente.getEmail());
				}
			
				if (utente.getTelefono() != null)
				{
					smsControl.sendRegistration(utente);
					mainBoundary.printRegistrationSmsSent(utente.getTelefono());
				}
				
				query = "UPDATE Utente SET ConfermaSpedita = 1 WHERE RefCF = '" + utente.getCf() + "'";
				Globals.getDbmsConnection().updateQuery(query);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void sendVisitConfirm()
	{
		//Seleziono le visite a cui non ho mandato la notifica
		String query = "SELECT DISTINCT RefNRE "
					+ "FROM Visita "
					+ "WHERE NotificationSent = 0";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				Prenotazione prenotazione = new Prenotazione(rs.getString(1));
				
				if (prenotazione.getPaziente().getEmail() != null)
				{
					mailControl.sendVisitConfirm(prenotazione);
					mainBoundary.sendEmailVisitConfirm(prenotazione.getNre(), prenotazione.getPaziente().getEmail());
				}
			
				if (prenotazione.getPaziente().getTelefono() != null)
				{
					smsControl.sendVisitConfirm(prenotazione);
					mainBoundary.sendSmsVisitConfirm(prenotazione.getNre(), prenotazione.getPaziente().getTelefono());
				}
				
				query = "UPDATE Visita SET NotificationSent = 1 WHERE RefNre = '" + prenotazione.getNre() + "'";
				Globals.getDbmsConnection().updateQuery(query);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void sendRemindNotification()
	{
		String oggi = Globals.getTodayDate();
		String ora = Globals.getCurrentHoursAndMinutesAndSeconds();
		String dataOggi = oggi + " " + ora;
		ResultSet rs = null;
		
		//Cerco le visite a cui non ho inviato la conferma
		String query = "SELECT RefNRE, Data, OraInizio "
					 + "FROM Visita "
					 + "WHERE RemindSent = 0";
		
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next()) //Finchè ci sono visite a cui non ho inviato la conferma
			{
				String nre = rs.getString(1);
				String dataVisita = rs.getString(2);
				String oraVisita = rs.getString(3);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1, d2;
				long diffInMilliseconds = 0;

				d1 = df.parse(dataOggi);
				d2 = df.parse(dataVisita + " " + oraVisita);
			    diffInMilliseconds = Math.abs(d1.getTime() - d2.getTime());
				long diffInHours = diffInMilliseconds/1000/60/60;
				
				if (diffInHours < 24)
				{
					Prenotazione prenotazione = new Prenotazione(nre);
					if (prenotazione.getPaziente().getEmail() != null)
					{
						mailControl.sendRemind(prenotazione);
						mainBoundary.printRemindEmailSent(prenotazione.getNre(), prenotazione.getPaziente().getEmail());
					}
				
					if (prenotazione.getPaziente().getTelefono() != null)
					{
						smsControl.sendRemind(prenotazione);
						mainBoundary.printRemindSmsSent(prenotazione.getNre(), prenotazione.getPaziente().getEmail());
					}
					
					query = "UPDATE Visita SET RemindSent = 1 " + " WHERE RefNre = '" + prenotazione.getNre() + "' ";
					Globals.getDbmsConnection().updateQuery(query);
				}
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		catch (ParseException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void sendAdvise()
	{
		String query = "SELECT refNre"
					+ " FROM avvisovisita"
					+ " WHERE Sent = 0 ";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				Prenotazione prenotazione = new Prenotazione(rs.getString(1));
				
				if (prenotazione.getPaziente().getEmail() != null)
				{
					mailControl.sendAdvise(prenotazione);
					mainBoundary.printAdviseEmailSent(prenotazione.getAvviso(), prenotazione.getPaziente().getEmail());
				}
			
				if (prenotazione.getPaziente().getTelefono() != null)
				{
					smsControl.sendAdvise(prenotazione);
					mainBoundary.printAdviseSmsSent(prenotazione.getAvviso(), prenotazione.getPaziente().getEmail());
				}
				
				query = "UPDATE avvisoVisita SET Sent = 1 "
						+ " WHERE RefNre = '" + prenotazione.getNre() + "' "
						+ " AND Avviso = '" + prenotazione.getAvviso() + "' ";
				Globals.getDbmsConnection().updateQuery(query);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
}
