package Mail;

import Globals.Globals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Entities.Prenotazione;
import Entities.Utente;

public class MailControl {
	
	final static String username = "davide.lavalle.45@gmail.com";
	final static String password = "try it another day";
	
	private void send(String to, String oggetto, String testo)
	{
		if (!Globals.MAIL)
		{
			System.out.println("Invio MAIL disattivato");
			return;
		}
			
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
	  	});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("davide.lavalle.45@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(to));
			message.setSubject(oggetto);
			message.setText(testo);

			Transport.send(message);

			System.out.println("Email inviata a:" + to + "\nOggetto: " + oggetto);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendRegistration(Utente utente)
	{
		String oggetto = "Registrazione presso ospedale D.S.P.D.";
		String testo = "Gentile " + utente.getNome() + " " + utente.getCognome() + ". Ti avvisiamo che la tua registrazione presso Ospedale D.S.P.D. è avvenuta con successo. Le ricordiamo che i suoi dati di accesso sono: CF: " + utente.getCf() + " - password: " + utente.getPassword() + ". In caso di smarrimento delle credenziali non sarà possibile recuperarli, si prega di non cancellare questa e-mail.";
		
		send(utente.getEmail(), oggetto, testo);
	}
	
	public void sendRemind(Prenotazione prenotazione)
	{
		String oggetto = "Conferma prenotazione presso Ospedale D.S.P.D.";
		StringBuilder testo = new StringBuilder();
		testo.append("Gentile " + prenotazione.getPaziente().getNome() + " " + prenotazione.getPaziente().getCognome() + " le ricordiamo che la sua visita " + prenotazione.getNomeTipo() + " con codice di ricetta "
					+ "" + prenotazione.getNre() + " è stata confermata per domani alle ore " + prenotazione.getOraInizio() + " presso l'ambulatorio " + prenotazione.getNomeAmbulatorio() + ".");
		
		ArrayList<String> documenti = prenotazione.getDocumentiNecessari();
		
		if (!documenti.isEmpty())
		{
			testo.append("Le ricordiamo di portare i seguenti documenti: ");
		
			Iterator<String> iterator = documenti.iterator();
			while (iterator.hasNext())
			{
				testo.append(iterator.next());
				if (iterator.hasNext())
					testo.append(", ");
				else
					testo.append(".");
			}
		}
		
		send(prenotazione.getPaziente().getEmail(), oggetto, testo.toString());
	}
	
	public void sendVisitConfirm(Prenotazione prenotazione)
	{
		String oggetto = "Notificia di avvenuta prenotazione.";
		String testo = "Gentile " + prenotazione.getPaziente().getNome() + " " + prenotazione.getPaziente().getCognome() + ", le notifichiamo che la sua visita " + prenotazione.getNomeTipo() + " con codice di ricetta: " + prenotazione.getNre() + " "
					+ "è stata prenotata correttamente presso l'ambulatorio " + prenotazione.getNomeAmbulatorio() + " nel reparto di " + prenotazione.getNomeReparto() + " alle ore " 
					+ prenotazione.getOraInizio() + " nel giorno " + prenotazione.getData() + ". La avviseremo 24 ore prima della visita per confermarle la "
					+ "prenotazione. Ospedale D.S.P.D.";
		
		send(prenotazione.getPaziente().getEmail(), oggetto, testo);
	}
	
	public void sendAdvise(Prenotazione prenotazione)
	{
		String oggetto = "Notifica di cambio data della prenotazione.";
		String testo = "Gentile " + prenotazione.getPaziente().getNome() + " " + prenotazione.getPaziente().getCognome() + ", le notifichiamo che la sua visita " + prenotazione.getNomeTipo() + " con codice di ricetta: " + prenotazione.getNre()
			+ " è stata spostata alla data " + prenotazione.getData() + " alle ore " + prenotazione.getOraInizio() + " per il seguente motivo: " + prenotazione.getAvviso() + ". Ci scusiamo "
			+ "per il disagio. Ospedale D.S.P.D.";
		
		send(prenotazione.getPaziente().getEmail(), oggetto, testo);
	}
}

