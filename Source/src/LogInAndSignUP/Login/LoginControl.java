package LogInAndSignUP.Login;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import AdministratorMode.AdministratorModeControl;
import Globals.Control;
import Globals.Globals;
import Globals.Entities.Utente;
import Globals.Notification.NotificationControl;
import MedicMode.MedicModeControl;
import UserMode.UserModeControl;

public class LoginControl implements Control{
	
	private LoginWindow window;
	private Control previousControl;
	
	public LoginControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new LoginWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	public void loginButtonPressed()
	{
		StringBuilder message = new StringBuilder("<html>");
		String cf = window.getCodiceFiscale().toLowerCase();
		String password = window.getPassword();
		NotificationControl notificationControl;
		//controllo numeri di caratteri del codice fiscale
		if(cf.length()!=16) {
			window.setCFFieldBackgroundColor(Color.red);
			message.append("Il codice fiscale deve avere 16 caratteri");
			message.append("</html>");
			notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
			return;
		}

		window.setCFFieldBackgroundColor(Color.white);
		//query di verifica del codice fiscale
		String query = "SELECT RefCF FROM Utente WHERE RefCF ='"+cf+"'";
		ResultSet rs=Globals.getDbmsConnection().selectQuery(query);
	
		if (rs == null)
			return;
		
			try {
				// se il codice fiscale non esiste nel database(query non restituisce valori)
				if(!rs.next()) {
					window.setCFFieldBackgroundColor(Color.red);
					message.append("Codice fiscale sbagliato o non registrato");
					message.append("</html>");
				 	notificationControl = new NotificationControl(message.toString());
					notificationControl.openWindow();
					return;
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
			
		//controllo campo password vuoto
		if(password.isEmpty()) {
			window.setPasswordBackgroundColor(Color.red);
			message.append("Campo della password vuoto,inserire password");
			message.append("</html>");
			notificationControl = new NotificationControl(message.toString());
			notificationControl.openWindow();
			return;
			}
		
		//query di verifica della password
		query = "SELECT Password FROM Utente WHERE BINARY Password= '" + password + "' AND RefCF = '" + cf + "'";
		rs=Globals.getDbmsConnection().selectQuery(query);
		
		if (rs == null)
			return;
		
			try {
				//se la password non coincide con quella presente nel db(la query non ritorna valori)
				if(!rs.next()){
					window.setPasswordBackgroundColor(Color.red);
					message.append("Password errata,riprovare");
					message.append("</html>");
					notificationControl = new NotificationControl(message.toString());
					notificationControl.openWindow();
					
					return;
				}
			} catch (SQLException e) {
				if(Globals.DEBUG)
					e.printStackTrace();
			}
			
		Globals.utente = new Utente(cf);
		int permesso = Globals.utente.getPermesso();
					
		if(permesso == Globals.getPermessoPaziente())
		{
			UserModeControl userModeControl = new UserModeControl(this);
			userModeControl.openWindow();
		}
		else if (permesso == Globals.getPermessoMedico())
		{
			MedicModeControl medicModeControl= new MedicModeControl(this);
			medicModeControl.openWindow();
		}
		else if(permesso == Globals.getPermessoAmministratore())
		{
			AdministratorModeControl administratorModeControl=new AdministratorModeControl(this);
			administratorModeControl.openWindow();
		}
		else
			return;
					
		closeWindow();
	}
}

