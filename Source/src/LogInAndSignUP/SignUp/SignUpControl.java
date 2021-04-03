package LogInAndSignUP.SignUp;

import java.awt.Color;
import Globals.Globals;
import Globals.Notification.NotificationControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import Globals.Control;

public class SignUpControl  implements Control{
	
	private SignUpWindow window;
	private Control previousControl;
	
	public SignUpControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new SignUpWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void checkButtonPressed()
	{
		String cf = window.getCfFieldText().toLowerCase();
		StringBuilder message = new StringBuilder();
		NotificationControl notificationControl;
		
		// Il codice fiscale deve essere di 16 caratteri
		if(cf.length() != 16)
		{
			message.append("Codice fiscale non valido");
			window.setCfFieldBackgroundColor(Color.RED);
			notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
			return;
		}
		
		window.setCfFieldBackgroundColor(Color.WHITE);
		
		// Vedo se il codice fiscale è quello di un utente già registrato
		String query = "SELECT RefCF FROM Utente WHERE RefCF = '" + cf + "'";
		ResultSet result = Globals.getDbmsConnection().selectQuery(query);
		if (result == null)
			return;
		try {
			if (result.next())
			{
				window.setConfirmButtonEnable(false);
				window.setPassFieldEnable(false);
				window.setRepeatPassFieldEnable(false);
				window.setNomeFieldEnable(false);
				window.setCognomeFieldEnable(false);
				window.setTelefonoFieldEnable(false);
				window.setSessoComboBoxEnable(false);
				window.setEmailFieldEnable(false);
				window.setDateEnabled(false);
					
				window.setPassFieldText("");
				window.setRepeatPasswordFieldText("");
				window.setNomeFieldText("");
				window.setCognomeFieldText("");
				window.setTelefonoFieldText("");
				window.setIndirizzoFieldText("");
				window.setCittaFieldText("");
				window.setEmailFieldText("");
				message.append("Utente gia'� registrato");
				notificationControl = new NotificationControl(message.toString());
				notificationControl.openWindow();
				return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		// Cerco il codice fiscale nella tabella Persona
		query = "SELECT Nome, Cognome, Data_Nascita, Sesso, Indirizzo_Residenza, Telefono, Citta FROM Persona WHERE CF = '" + cf + "'";
		result = Globals.getDbmsConnection().selectQuery(query);
		if (result == null)
			return;
		
		String nome;
		String cognome;
		String[] nascita;
		String sesso;
		String indirizzo;
		String citta;
		String telefono;
		
		try
		{
			if (result.next())
			{
				nome = result.getString(1);
				cognome = result.getString(2);
				nascita = result.getString(3).split("-");
				sesso = result.getString(4);
				indirizzo = result.getString(5);
				telefono = result.getString(6);
				citta = result.getString(7);
				
				
				window.setNomeFieldText(nome);
				window.setCognomeFieldText(cognome);
				window.setDate(nascita);
				window.setTelefonoFieldText(telefono);
				window.setIndirizzoFieldText(indirizzo);
				window.setCittaFieldText(citta);
				
				if (sesso.equals("M"))
					window.setSessoComboBoxSelection(0);
				else
					window.setSessoComboBoxSelection(1);
				
				window.setConfirmButtonEnable(true);
				window.setPassFieldEnable(true);
				window.setRepeatPassFieldEnable(true);
				window.setNomeFieldEnable(false);
				window.setCognomeFieldEnable(false);
				window.setTelefonoFieldEnable(false);
				window.setSessoComboBoxEnable(false);
				window.setIndirizzoEnable(false);
				window.setCittaEnable(false);
				window.setEmailFieldEnable(true);
				window.setDateEnabled(false);
			}
			else
			{
				window.setNomeFieldText("");
				window.setCognomeFieldText("");
				window.setTelefonoFieldText("");
				window.setIndirizzoFieldText("");
				window.setConfirmButtonEnable(true);
				window.setPassFieldEnable(true);
				window.setRepeatPassFieldEnable(true);
				window.setNomeFieldEnable(true);
				window.setCognomeFieldEnable(true);
				window.setTelefonoFieldEnable(true);
				window.setSessoComboBoxEnable(true);
				window.setIndirizzoEnable(true);
				window.setCittaEnable(true);
				window.setEmailFieldEnable(true);
				window.setDateEnabled(true);
			}
		}
		catch(Exception exc)
		{
			if (Globals.DEBUG)
				exc.printStackTrace();
		}
		
		window.setCfFieldEnable(false);
	}
	
	public void onConfirmButtonPressed()
	{
		NotificationControl notificationControl;
		StringBuilder message = new StringBuilder("<html>");
		boolean error = false;
		String cf = window.getCfFieldText().toLowerCase();
		String password = window.getPasswordFieldText();
		String repeatedPassword = window.getRepeatPasswordFieldText();
		String nome = window.getNomeFieldText();
		String cognome = window.getCognomeFieldText();
		String data = window.getDate();
		String giorno = window.getGiorno();
		String mese = window.getMese();
		String anno = window.getAnno();
		String sesso = window.getSessoComboBoxText();
		String indirizzo = window.getIndirizzoFieldText();
		String citta = window.getCittaFieldText();
		String telefono = window.getTelefonoFieldText();
		String email = window.getEmailFieldText();
		String query;
		
		// Le due password devono coincidere
		if(password.isEmpty() || !password.equals(repeatedPassword))
		{
			error = true;
			
			if (password.isEmpty())
				message.append("Password non valida<br/>");
			else
				message.append("Le password non coincidono<br/>");
			
			window.setPasswordFieldBackgroundColor(Color.RED);
			window.setRepeatPasswordBackgroundColor(Color.RED);
		}
		else
		{
			window.setPasswordFieldBackgroundColor(Color.WHITE);
			window.setRepeatPasswordBackgroundColor(Color.WHITE);
		}
		
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
		
		// L'indirizzo non deve essere vuoto
		if (indirizzo.isEmpty())
		{
			error = true;
			message.append("Indirizzo non valido<br/>");
			window.setIndirizzoFieldBackgroundColor(Color.RED);
		}
		else
			window.setIndirizzoFieldBackgroundColor(Color.WHITE);
		
		//la citta non deve essere vuota
		if (citta.isEmpty())
		{
			error = true;
			message.append("Citta non valida<br/>");
			window.setCittaFieldBackgroundColor(Color.RED);
		}
		else
			window.setCittaFieldBackgroundColor(Color.WHITE);
		
		//L'email non deve essere vuota
		
		if(email.isEmpty())
		{
			error=true;
			message.append("Email non valida<br/>");
			window.setEmailFieldBackgroundColor(Color.RED);
		}
		else
			window.setEmailFieldBackgroundColor(Color.WHITE);
		
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
		if(!checkData(data)) {
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
		
		if (!error)
		{
			query = "SELECT CF from Persona where CF = '"+ cf +"'";
			ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
			if(rs == null)
				return;
			try {
				if(!rs.next()) {
					query = "INSERT into Persona values('"+ cf + "', '" + nome + "', '" + cognome + "', '" + data + "', '" + sesso + "', '" + indirizzo + "', '" + telefono + "', '" + citta + "', '" + email + "')";
					if (!Globals.getDbmsConnection().insertQuery(query))
						return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			query = "INSERT into Utente values('"+ cf + "', '" + password + "', 1, 0)";
			if (!Globals.getDbmsConnection().insertQuery(query))
				return;
				
			message.append("Utente registrato correttamente<br/>");
			previousControl.openWindow();
			closeWindow();
		}

		message.append("</html>");
		notificationControl = new NotificationControl(message.toString());
		notificationControl.openWindow();
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
