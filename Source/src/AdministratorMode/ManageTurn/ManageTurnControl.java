package AdministratorMode.ManageTurn;

import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Globals;
import Globals.Notification.NotificationControl;
import Globals.Control;

public class ManageTurnControl implements Control {
	
	private ManageTurnWindow window;
	private Control previousControl;
	
	public ManageTurnControl(Control previousControl)
	{
		this.previousControl = previousControl;
	}
	
	public void openWindow()
	{
		window = new ManageTurnWindow(this, previousControl);
		window.open(window);
	}
	
	public void closeWindow()
	{
		window.closeFrame();
	}

	public void aggiungiRepartiAmbulatori()
	{
		String query = null;
		ResultSet rs = null;
		
		window.addItemToRepartoAmbulatorioComboBox("-- Reparti --");
		window.addItemToRepartoAmbulatorioComboBox("--------------");
		
		query = "SELECT NomeReparto FROM Reparto";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
				window.addItemToRepartoAmbulatorioComboBox(rs.getString(1));
		} catch (SQLException e1) {
			if (Globals.DEBUG)
				e1.printStackTrace();
		}
		
		window.addItemToRepartoAmbulatorioComboBox("----------------");
		window.addItemToRepartoAmbulatorioComboBox("-- Ambulatori --");
		window.addItemToRepartoAmbulatorioComboBox("----------------");
		
		query = "SELECT NomeAmbulatorio FROM Ambulatorio";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
				window.addItemToRepartoAmbulatorioComboBox(rs.getString(1));
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public void onComboBoxSelectionChanged()
	{
		String selection = window.getRepartoAmbulatorioComboBoxSelected();
		
		if (selection.equals("-- Reparti --") || selection.equals("--------------") || selection.equals("-- Ambulatori --") || selection.equals("----------------"))
		{
			window.clearLunediComboBox();
			window.clearMartediComboBox();
			window.clearMercolediComboBox();
			window.clearGiovediComboBox();
			window.clearVenerdiComboBox();
			window.clearSabatoComboBox();
			window.clearDomenicaComboBox();
			window.setConfirmButtonEnable(false);
			return;
		}
		
		String query = "SELECT * "
					+ "FROM Reparto "
					+ "WHERE NomeReparto = '" + selection + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				updateTurniRepartoComboBox(selection);
			else updateTurniAmbulatorioComboBox(selection);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void updateTurniRepartoComboBox(String reparto)
	{
		String query = null;
		ResultSet rs = null;
		String[] giorno = {"lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato", "domenica"};

		window.clearLunediComboBox();
		window.clearMartediComboBox();
		window.clearMercolediComboBox();
		window.clearGiovediComboBox();
		window.clearVenerdiComboBox();
		window.clearSabatoComboBox();
		window.clearDomenicaComboBox();
		window.setDomenicaComboBoxEnable(true);
		window.setConfirmButtonEnable(true);
		
		for (int i = 0; i < giorno.length; ++i)
		{
			//Cerco i dottore che lavorano in quel reparto per ogni giorno
			query = "SELECT distinct Nome, Cognome "
					+ "FROM TurnoReparto, Reparto, Persona "
					+ "WHERE RefReparto = CodReparto "
					+ "AND NomeReparto = '" + reparto + "' "
					+ "AND RefCfDottore = CF "
					+ "AND GiornoSettimana = '" + giorno[i] + "'";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
		
			try {
				if (rs.next())
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 1:
						window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 2:
						window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 3:
						window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 4:
						window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 5:
						window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 6:
						window.addItemToDomenicaComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					}
				}
				else //Se non c'è un dottore che non lavora in quel giorno in quel reparto...
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox("Nessuno");
						break;
					case 1:
						window.addItemToMartediComboBox("Nessuno");
						break;
					case 2:
						window.addItemToMercolediComboBox("Nessuno");
						break;
					case 3:
						window.addItemToGiovediComboBox("Nessuno");
						break;
					case 4:
						window.addItemToVenerdiComboBox("Nessuno");
						break;
					case 5:
						window.addItemToSabatoComboBox("Nessuno");
						break;
					case 6:
						window.addItemToDomenicaComboBox("Nessuno");
						break;
					}
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		
		for (int i = 0; i < giorno.length; ++i)
		{
			//Cerco i dottori che lavorano in quel reparto in un giorno diverso...
			query = "SELECT distinct Nome, Cognome "
					+ "FROM TurnoReparto, Reparto, Persona "
					+ "WHERE RefReparto = CodReparto "
					+ "AND NomeReparto = '" + reparto + "' "
					+ "AND RefCfDottore = CF "
					+ "AND GiornoSettimana <> '" + giorno[i] + "'";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
		
			try {
				while (rs.next())
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 1:
						window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 2:
						window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 3:
						window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 4:
						window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 5:
						window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 6:
						window.addItemToDomenicaComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					}
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		
		//Cerco i dottori che non lavorano ne in reparto e ne in ambulatorio (ancora non assegnati)
		query = "SELECT distinct Nome, Cognome "
				+ "FROM Dottore d, Persona "
				+ "WHERE d.RefCfDottore = CF "
				+ "AND NOT EXISTS (SELECT * "
								+ "FROM turnoReparto WHERE RefCFDottore = d.RefCfDottore)"
				+ "AND NOT EXISTS (SELECT * "
								+ "FROM turnoAmbulatorio WHERE RefDottore = d.RefCfDottore)";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
	
		try {
			while (rs.next())
			{
				window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToDomenicaComboBox(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		

		window.addItemToLunediComboBox("Nessuno");
		window.addItemToMartediComboBox("Nessuno");
		window.addItemToMercolediComboBox("Nessuno");
		window.addItemToGiovediComboBox("Nessuno");
		window.addItemToVenerdiComboBox("Nessuno");
		window.addItemToSabatoComboBox("Nessuno");
		window.addItemToDomenicaComboBox("Nessuno");
	}
	
	private void updateTurniAmbulatorioComboBox(String ambulatorio)
	{
		String query = null;
		ResultSet rs = null;
		String[] giorno = {"lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato"};

		window.clearLunediComboBox();
		window.clearMartediComboBox();
		window.clearMercolediComboBox();
		window.clearGiovediComboBox();
		window.clearVenerdiComboBox();
		window.clearSabatoComboBox();
		window.clearDomenicaComboBox();
		window.setDomenicaComboBoxEnable(false);
		window.setConfirmButtonEnable(true);
		
		for (int i = 0; i < giorno.length; ++i)
		{
			//Cerco i dottore che lavorano in quell'ambulatorio per ogni giorno
			query = "SELECT distinct Nome, Cognome "
					+ "FROM TurnoAmbulatorio, Ambulatorio, Persona "
					+ "WHERE RefAmbulatorio = CodAmbulatorio "
					+ "AND NomeAmbulatorio = '" + ambulatorio + "' "
					+ "AND RefDottore = CF "
					+ "AND GiornoSettimana = '" + giorno[i] + "'";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
		
			try {
				if (rs.next())
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 1:
						window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 2:
						window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 3:
						window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 4:
						window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 5:
						window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					}
				}
				else //Se non c'è un dottore che non lavora in quel giorno in quel reparto...
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox("Nessuno");
						break;
					case 1:
						window.addItemToMartediComboBox("Nessuno");
						break;
					case 2:
						window.addItemToMercolediComboBox("Nessuno");
						break;
					case 3:
						window.addItemToGiovediComboBox("Nessuno");
						break;
					case 4:
						window.addItemToVenerdiComboBox("Nessuno");
						break;
					case 5:
						window.addItemToSabatoComboBox("Nessuno");
						break;
					}
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		
		for (int i = 0; i < giorno.length; ++i)
		{
			//Cerco i dottori che lavorano in quell'ambulatorio in un giorno diverso...
			query = "SELECT distinct p.Nome, p.Cognome "
					+ "FROM TurnoAmbulatorio, Ambulatorio, Persona p "
					+ "WHERE RefAmbulatorio = CodAmbulatorio "
					+ "AND NomeAmbulatorio = '" + ambulatorio + "' "
					+ "AND RefDottore = p.CF "
					+ "AND GiornoSettimana <> '" + giorno[i] + "' "
					+ "AND NOT EXISTS (SELECT * "
									+ "FROM TurnoAmbulatorio t2 "
									+ "WHERE t2.RefDottore = p.CF "
									+ "AND t2.GiornoSettimana = '" + giorno[i] + "')";
			rs = Globals.getDbmsConnection().selectQuery(query);
			if (rs == null)
				return;
		
			try {
				while (rs.next())
				{
					switch (i)
					{
					case 0:
						window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 1:
						window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 2:
						window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 3:
						window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 4:
						window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					case 5:
						window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
						break;
					}
				}
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
		}
		
		//Cerco i dottori che non lavorano ne in reparto e ne in ambulatorio (ancora non assegnati)
		query = "SELECT distinct Nome, Cognome "
				+ "FROM Dottore d, Persona "
				+ "WHERE d.RefCfDottore = CF "
				+ "AND NOT EXISTS (SELECT * "
								+ "FROM turnoReparto WHERE RefCFDottore = d.RefCfDottore)"
				+ "AND NOT EXISTS (SELECT * "
								+ "FROM turnoAmbulatorio WHERE RefDottore = d.RefCfDottore)";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
	
		try {
			while (rs.next())
			{
				window.addItemToLunediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToMartediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToMercolediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToGiovediComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToVenerdiComboBox(rs.getString(1) + " " + rs.getString(2));
				window.addItemToSabatoComboBox(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		window.addItemToLunediComboBox("Nessuno");
		window.addItemToMartediComboBox("Nessuno");
		window.addItemToMercolediComboBox("Nessuno");
		window.addItemToGiovediComboBox("Nessuno");
		window.addItemToVenerdiComboBox("Nessuno");
		window.addItemToSabatoComboBox("Nessuno");
	}
	
	public void confirmButtonPressed()
	{
		String selection = window.getRepartoAmbulatorioComboBoxSelected();
		
		//Vedo se quello selezionato è un reparto oppure un ambulatoprio
		String query = "SELECT * "
					+ "FROM Reparto "
					+ "WHERE NomeReparto = '" + selection + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
	
		try {
			if (rs.next())
				updateTurniReparto(selection);
			else updateTurniAmbulatorio(selection);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		NotificationControl notificationControl = new NotificationControl("Modifica del turno avvenuta con successo");
		notificationControl.openWindow();
	}
	
	private void updateTurniReparto(String reparto)
	{
		String query = null;
		ResultSet rs = null;
		String codReparto = null;
		String cfDottore = null;
		String[] nomeCognomeDottore = null;
		String[] giorno = {"lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato", "domenica"};
		
		query = "SELECT CodReparto FROM Reparto WHERE NomeReparto = '" + reparto + "'";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				codReparto = rs.getString(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "DELETE FROM TurnoReparto WHERE RefReparto = '" + codReparto + "'";
		if (!Globals.getDbmsConnection().deleteQuery(query))
			return;

		for (int i = 0; i < giorno.length; ++i)
		{
			switch (i)
			{
			case 0:
				nomeCognomeDottore = window.getLunediComboBoxSelected().split(" ", 2);
				break;
			case 1:
				nomeCognomeDottore = window.getMartediComboBoxSelected().split(" ", 2);
				break;
			case 2:
				nomeCognomeDottore = window.getMercolediComboBoxSelected().split(" ", 2);
				break;
			case 3:
				nomeCognomeDottore = window.getGiovediComboBoxSelected().split(" ", 2);
				break;
			case 4:
				nomeCognomeDottore = window.getVenerdiComboBoxSelected().split(" ", 2);
				break;
			case 5:
				nomeCognomeDottore = window.getSabatoComboBoxSelected().split(" ", 2);
				break;
			case 6:
				nomeCognomeDottore = window.getDomenicaComboBoxSelected().split(" ", 2);
				break;
			}
			
			if (nomeCognomeDottore[0].equals("Nessuno"))
				continue;
			
			query = "SELECT CF "
					+ "FROM Persona, Dottore "
					+ "WHERE RefCFDottore = CF "
					+ "AND Nome = '" + nomeCognomeDottore[0] + "' "
					+ "AND Cognome = '" + nomeCognomeDottore[1] + "' ";
			rs = Globals.getDbmsConnection().selectQuery(query);
			
			if (rs == null)
				return;
					
			try {
				if (rs.next())
					cfDottore = rs.getString(1);
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
			
			query = "INSERT INTO TurnoReparto "
					+ "VALUES "
					+ "('" + cfDottore + "', '" + codReparto + "', '" + giorno[i] + "')";
			
			if (!Globals.getDbmsConnection().insertQuery(query))
				return;
		}
	}
	
	private void updateTurniAmbulatorio(String ambulatorio)
	{
		String query = null;
		ResultSet rs = null;
		String codAmbulatorio = null;
		String cfDottore = null;
		String[] nomeCognomeDottore = null;
		String[] giorno = {"lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato"};
		
		query = "SELECT CodAmbulatorio FROM Ambulatorio WHERE NomeAmbulatorio = '" + ambulatorio + "'";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				codAmbulatorio = rs.getString(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "DELETE FROM TurnoAmbulatorio WHERE RefAmbulatorio = '" + codAmbulatorio + "'";
		if (!Globals.getDbmsConnection().deleteQuery(query))
			return;

		for (int i = 0; i < giorno.length; ++i)
		{
			switch (i)
			{
			case 0:
				nomeCognomeDottore = window.getLunediComboBoxSelected().split(" ", 2);
				break;
			case 1:
				nomeCognomeDottore = window.getMartediComboBoxSelected().split(" ", 2);
				break;
			case 2:
				nomeCognomeDottore = window.getMercolediComboBoxSelected().split(" ", 2);
				break;
			case 3:
				nomeCognomeDottore = window.getGiovediComboBoxSelected().split(" ", 2);
				break;
			case 4:
				nomeCognomeDottore = window.getVenerdiComboBoxSelected().split(" ", 2);
				break;
			case 5:
				nomeCognomeDottore = window.getSabatoComboBoxSelected().split(" ", 2);
				break;
			}
			
			if (nomeCognomeDottore[0].equals("Nessuno"))
				continue;
			
			query = "SELECT CF "
					+ "FROM Persona, Dottore "
					+ "WHERE RefCFDottore = CF "
					+ "AND Nome = '" + nomeCognomeDottore[0] + "' "
					+ "AND Cognome = '" + nomeCognomeDottore[1] + "' ";
			rs = Globals.getDbmsConnection().selectQuery(query);
			
			if (rs == null)
				return;
					
			try {
				if (rs.next())
					cfDottore = rs.getString(1);
			} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
			}
			
			query = "INSERT INTO TurnoAmbulatorio "
					+ "VALUES "
					+ "('" + cfDottore + "', '" + codAmbulatorio + "', '" + giorno[i] + "')";
			
			if (!Globals.getDbmsConnection().insertQuery(query))
				return;
		}
	}
}
