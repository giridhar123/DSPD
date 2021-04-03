package Sms;

import Globals.Globals;

import java.util.ArrayList;
import java.util.Iterator;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import Entities.Prenotazione;
import Entities.Utente;

public class SmsControl {
	//Thanks TWilio
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "wawawawa";
    public static final String AUTH_TOKEN = "wowowow";

    private void send(String to, String text)
    {
    	if (!Globals.SMS)
    	{
    		System.out.println("Invio SMS disattivato");
    		return;
    	}
    	
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+39" + to),
                new com.twilio.type.PhoneNumber("+17042705645"),
                text)
            .create();

       	System.out.println("SMS inviato a: " + to + "\n" + message.getSid());
    }
    
    public void sendRegistration(Utente utente)
    {
    	String testo = "Gentile " + utente.getNome() + " " + utente.getCognome() + ". Ti avvisiamo che la tua registrazione presso Ospedale D.S.P.D. è avvenuta con successo. Le ricordiamo che i suoi dati di accesso sono: CF: " + utente.getCf() + " - password: " + utente.getPassword() + ". In caso di smarrimento delle credenziali non sarà possibile recuperarli, si prega di non cancellare questo messaggio.";
    	send(utente.getTelefono(), testo);
    }
    
    public void sendRemind(Prenotazione prenotazione)
	{
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
		
		send(prenotazione.getPaziente().getTelefono(), testo.toString());
	}
    
    public void sendVisitConfirm(Prenotazione prenotazione)
	{
		String testo = "Gentile " + prenotazione.getPaziente().getNome() + " " + prenotazione.getPaziente().getCognome() + ", le notifichiamo che la sua visita " + prenotazione.getNomeTipo() + " con codice di ricetta: " + prenotazione.getNre() + " "
					+ "è stata prenotata correttamente presso l'ambulatorio " + prenotazione.getNomeAmbulatorio() + " nel reparto di " + prenotazione.getNomeReparto() + " alle ore " 
					+ prenotazione.getOraInizio() + " nel giorno " + prenotazione.getData() + ". La avviseremo 24 ore prima della visita per confermarle la "
					+ "prenotazione. Ospedale D.S.P.D.";
		
		send(prenotazione.getPaziente().getTelefono(), testo);
	}
    
    public void sendAdvise(Prenotazione prenotazione)
	{
		String testo = "Gentile " + prenotazione.getPaziente().getNome() + " " + prenotazione.getPaziente().getCognome() + ", le notifichiamo che la sua visita " + prenotazione.getNomeTipo() + " con codice di ricetta: " + prenotazione.getNre()
			+ " è stata spostata alla data " + prenotazione.getData() + " alle ore " + prenotazione.getOraInizio() + " per il seguente motivo: " + prenotazione.getAvviso() + ". Ci scusiamo "
			+ "per il disagio. Ospedale D.S.P.D.";
		
		send(prenotazione.getPaziente().getTelefono(), testo);
	}
}
