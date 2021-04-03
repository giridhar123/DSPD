package MedicMode.ManageVisitDoctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Control;
import Globals.Globals;
import Globals.Entities.Prenotazione;

public class ManageVisitDoctorControl implements Control{
	
	private ManageVisitDoctorWindow window;
	private Control previousControl;
	
	public ManageVisitDoctorControl(Control previousControl) {
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManageVisitDoctorWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}
	
	//riempio la tabella delle visite
	public void fillTable()
	{
		String query = "SELECT RefNRE  "
				+ " FROM visita "
				+ "	WHERE RefCfDottore = '" + Globals.utente.getCf() + "'";
		
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
			{
				Prenotazione prenotazione = new Prenotazione(rs.getString(1));
				window.addRowToTable(prenotazione);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
}
