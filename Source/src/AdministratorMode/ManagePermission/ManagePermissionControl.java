package AdministratorMode.ManagePermission;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Globals;
import Globals.Notification.NotificationControl;
import Globals.Control;

public class ManagePermissionControl implements Control
{
	private ManagePermissionWindow window;
	private Control previousControl;
	
	public ManagePermissionControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManagePermissionWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void searchButtonPressed()
	{
		//Disabilito tutto e riattivo dopo ciò che mi serve
		window.setUpdateButtonEnable(false);
		window.setGruppoPermessiBoxEnable(false);
		window.setSpecializzazioneEnable(false);
		
		window.setNomeFieldText("");
		window.setCognomeFieldText("");
		window.setTelefonoFieldText("");
		window.setIndirizzoFieldText("");
		window.setCittaFieldText("");
		window.setEmailFieldText("");
		window.setSpecializzazioneFieldText("");
		
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
		else
			window.setCfFieldBackgroundColor(Color.WHITE);
		
		// Vedo se il codice fiscale e' quello di un utente già registrato
		String query = "SELECT RefCF FROM Utente WHERE RefCF = '" + cf + "'";
		ResultSet result = Globals.getDbmsConnection().selectQuery(query);
		if (result == null)
			return;
		
		try {
			if (!result.next()) //Se l'utente non è registrato
			{
				message.append("Utente non registrato");
				notificationControl = new NotificationControl(message.toString());
				notificationControl.openWindow();
				return;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		// Cerco il codice fiscale nella tabella Persona e utente
		query = "SELECT Nome, Cognome, Data_Nascita, Sesso, Indirizzo_Residenza, Telefono, Citta, Email, RefGruppoPermessi "
				+ "FROM Persona, Utente "
				+ "WHERE RefCf = CF "
				+ "AND CF = '" + cf + "'";
		result = Globals.getDbmsConnection().selectQuery(query);
		if (result == null)
			return;
		
		String nome = null;
		String cognome = null;
		String[] nascita = null;
		String sesso = null;
		String indirizzo = null;
		String citta = null;
		String telefono = null;
		String email = null;
		int gruppoPermesso = 0;
		
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
				email = result.getString(8);
				gruppoPermesso = result.getInt(9);
			}
		}
		catch(Exception exc){
			if (Globals.DEBUG)
				exc.printStackTrace();
			
			return;
		}
		
		window.setNomeFieldText(nome);
		window.setCognomeFieldText(cognome);
		window.setDate(nascita);
		window.setTelefonoFieldText(telefono);
		window.setIndirizzoFieldText(indirizzo);
		window.setCittaFieldText(citta);
		window.setEmailFieldText(email);
		window.setGruppoPermessiBox(gruppoPermesso);
		if (sesso.equals("M"))
			window.setSessoComboBoxSelection(0);
		else
			window.setSessoComboBoxSelection(1);
				
		window.setUpdateButtonEnable(true);
		window.setGruppoPermessiBoxEnable(true);
			
		//controllo della specializzazione etc PEPPE AIUTOOOOOOO
		if (gruppoPermesso == Globals.getPermessoPaziente())
		{
			window.setGruppoPermessiBox(1);
			System.out.println("a");
		}
		else if (gruppoPermesso == Globals.getPermessoMedico())
		{ 
			System.out.println("B");
			window.setGruppoPermessiBox(2);
			String specializzazione = null;
			
			query = "SELECT Specializzazione "
					+ "FROM Dottore "
					+ "WHERE RefCFDottore = '" + cf + "' ";
			result = Globals.getDbmsConnection().selectQuery(query);
			if (result == null)
				return;
						
			try{
				if(result.next()) {
					specializzazione = result.getString(1);
					window.setSpecializzazioneFieldText(specializzazione);
					window.setSpecializzazioneEnable(true);
					//DAVIDE ATTIVO IL TASTO GESTIONE TURNO
				}
			}catch(Exception exc){
				if (Globals.DEBUG)
					exc.printStackTrace();
			}
		}
		else if (gruppoPermesso == Globals.getPermessoAmministratore())
		{
			System.out.println("C");
			window.setGruppoPermessiBox(3);
		}
	}
	
	public void onGruppoPermessiComboBoxSelectionChanged()
	{
		int index = window.getGruppoPermessiBoxIndex();
		
		switch (index)
		{
		case 0:
			window.setSpecializzazioneEnable(false);
			window.setSpecializzazioneFieldText("");
			break;
		case 1:
			window.setSpecializzazioneEnable(true);
			window.setSpecializzazioneFieldText("?");
			break;
		case 2:
			window.setSpecializzazioneEnable(false);
			window.setSpecializzazioneFieldText("");
			break;
		}
	}
	
	public void updateButtonPressed()
	{
		String query = null;
		ResultSet rs = null;
		
		String cf = window.getCfFieldText();
		int gruppoPermessi = window.getGruppoPermessiBoxIndex() + 1;
		String specializzazione = null;
		
		query = "UPDATE Utente SET RefGruppoPermessi = " + gruppoPermessi + " WHERE RefCf = '" + cf + "'";
		if(!Globals.getDbmsConnection().updateQuery(query))
			return;
		
		if (gruppoPermessi == Globals.getPermessoPaziente() || gruppoPermessi == Globals.getPermessoAmministratore())
		{
			query = "DELETE FROM TurnoAmbulatorio WHERE RefDottore = '" + cf + "'";
			if (!Globals.getDbmsConnection().deleteQuery(query))
				return;
			
			query = "DELETE FROM TurnoReparto WHERE RefCfDottore = '" + cf + "'";
			if (!Globals.getDbmsConnection().deleteQuery(query))
				return;
			
			query = "DELETE FROM Dottore WHERE RefCfDottore = '" + cf + "'";
			if (!Globals.getDbmsConnection().deleteQuery(query))
				return;			
		}
		else if (gruppoPermessi == Globals.getPermessoMedico())
		{
			specializzazione = window.getSpecializzazioneFieldText();
			query = "SELECT * FROM Dottore WHERE RefCFDottore = '" + cf + "'";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
			
			try {
				if (rs.next())
				{
					query = "UPDATE Dottore SET specializzazione = '" + specializzazione + "'";
					if(!Globals.getDbmsConnection().updateQuery(query))
						return;
				}
				else
				{
					query = "INSERT INTO Dottore VALUES ('" + cf + "', '" + specializzazione + "')";
					if (!Globals.getDbmsConnection().insertQuery(query))
						return;
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		
		NotificationControl notificationControl = new NotificationControl("Operazione eseguita correttamente");
		notificationControl.openWindow();
	}
}