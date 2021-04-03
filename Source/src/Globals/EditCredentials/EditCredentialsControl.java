package Globals.EditCredentials;

import java.awt.Color;
import Globals.Control;
import Globals.Globals;
import Globals.Notification.NotificationControl;

public class EditCredentialsControl implements Control {

	private EditCredentialsWindow window;
	private Control previousControl;
	
	public EditCredentialsControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void fillContent()
	{	
			window.setNomeFieldText(Globals.utente.getNome());
			window.setCognomeFieldText(Globals.utente.getCognome());
			window.setIndirizzoFieldText(Globals.utente.getIndirizzo());
			window.setCittaFieldText(Globals.utente.getCitta());
			window.setCfFieldText(Globals.utente.getCf());
			window.setMailFieldText(Globals.utente.getEmail());
			window.setNumberFieldText(Globals.utente.getTelefono());
			if(Globals.utente.getSesso().equals("M")) 
				window.setSessoComboBoxSelection(0);				
			else
				window.setSessoComboBoxSelection(1);
			
			String[] nascita = Globals.utente.getDataNascita().split("-");
			window.setDate(nascita);	
	}
	
	public void saveButtonPressed()
	{
		NotificationControl notificationControl = null;
		StringBuilder message = new StringBuilder("<html>");
		boolean error = false;
		
		String nome = window.getNomeFieldText();
		String cognome = window.getCognomeFieldText();
		String citta = window.getCittaFieldText();
		String indirizzo = window.getIndirizzoFieldText();
		String email = window.getMailFieldText();
		String telefono = window.getNumberFieldText();
		
		if (nome.isEmpty())
		{
			message.append("Nome non valido.<br/>");
			error = true;
			window.setNomeFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setNomeFieldTextBackgroundColor(Color.WHITE);
			
		if (cognome.isEmpty())
		{
			message.append("Cognome non valido.<br/>");
			error = true;
			window.setCognomeFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setCognomeFieldTextBackgroundColor(Color.WHITE);
			
		if (citta.isEmpty())
		{
			message.append("Città non valida.<br/>");
			error = true;
			window.setCittaFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setCittaFieldTextBackgroundColor(Color.WHITE);
			
		if (indirizzo.isEmpty())
		{
			message.append("Indirizzo non valido.<br/>");
			error = true;
			window.setIndirizzoFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setIndirizzoFieldTextBackgroundColor(Color.WHITE);
			
		if (email.isEmpty())
		{
			message.append("Email non valida.<br/>");
			error = true;
			window.setMailFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setMailFieldTextBackgroundColor(Color.WHITE);
		
		if (telefono.isEmpty())
		{
			message.append("Telefono non valido.<br/>");
			error = true;
			window.setNumberFieldTextBackgroundColor(Color.RED);
		}
		else
			window.setNumberFieldTextBackgroundColor(Color.WHITE);
			
		if (error)
		{
			notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
			return;
		}
		
		String data = window.getDate();
		String sesso = window.getSessoComboBoxText();
		
		//Se almeno un aggiornamento fallisce...
		if (!(Globals.utente.updateNome(nome) &&
			Globals.utente.updateCognome(cognome) &&
			Globals.utente.updateCitta(citta) &&
			Globals.utente.updateIndirizzo(indirizzo) &&
			Globals.utente.updateEmail(email) &&
			Globals.utente.updateTelefono(telefono)))
			return;
		
		String currentPassword = Globals.utente.getPassword();
		
		String vecchiaPassword = window.getVecchiaPassword();
		String nuovaPassword = window.getNuovaPassword();
		String ripetiPassword = window.getRipetiPassword();
		if (!vecchiaPassword.isEmpty() && !nuovaPassword.isEmpty() && !ripetiPassword.isEmpty())
		{
			if(vecchiaPassword.equals(currentPassword))
			{
				if(nuovaPassword.equals(ripetiPassword))
				{
					if (!Globals.utente.updatePassword(nuovaPassword))
						return;
				}
				else
				{
					window.setNuovaPasswordFieldBackgroundColor(Color.RED);
					window.setRipetiPasswordFieldBackgroundColor(Color.RED);
					
					notificationControl = new NotificationControl("Le password non coincidono");
					notificationControl.openWindow();
					return;
				}
			}
			else
			{
				window.setVecchiaPasswordFieldBackgroundColor(Color.RED);
				notificationControl = new NotificationControl("Password errata.");
				notificationControl.openWindow();
				return;
			}
		}
		
		notificationControl = new NotificationControl("Dati cambiati correttamente.");
		notificationControl.openWindow();
	}

	public void openWindow()
	{
		window = new EditCredentialsWindow(this , previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
}
