package Globals.Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import Globals.Globals;

public class Prenotazione extends Ricetta
{
	private String cfPaziente;
	private String nomePaziente;
	private String cognomePaziente;
	private String data;
	private String oraInizio;
	private String oraFine;
	private String codAmbulatorio;
	private String nomeAmbulatorio;
	private String nomeMedico;
	private String cognomeMedico;
	private String referto;
	private boolean remindSent;
	
	public Prenotazione (String nre)
	{
		super(nre);
		init();
	}
	
	private void init()
	{
		String query = "SELECT RefCFPaziente, Nome, cognome, data, oraInizio, oraFine, CodAmbulatorio, nomeAmbulatorio, remindSent "
				+ " FROM Visita, Persona, Ambulatorio "
				+ " WHERE RefNre = '" + super.getNre() + "'" 
				+ " AND RefCFPaziente = CF "
				+ " AND RefAmbulatorio = CodAmbulatorio ";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				cfPaziente = rs.getString(1);
				nomePaziente = rs.getString(2);
				cognomePaziente = rs.getString(3);
				data = rs.getString(4);
				oraInizio = rs.getString(5);
				oraFine = rs.getString(6);
				codAmbulatorio = rs.getString(7);
				nomeAmbulatorio = rs.getString(8);
				if (rs.getInt(9) == 0)
					remindSent = false;
				else remindSent = true;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "SELECT Nome, cognome "
				+ " FROM Visita, Persona "
				+ " WHERE RefCfDottore = CF "
				+ " AND RefNre = '" + super.getNre() + "'";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				nomeMedico = rs.getString(1);
				cognomeMedico = rs.getString(2);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		setReferto();
	}
	
	public String getCfPaziente()
	{
		return cfPaziente;
	}
	
	public String getNomePaziente()
	{
		return nomePaziente;
	}
	
	public String getCognomePaziente()
	{
		return cognomePaziente;
	}
	
	public String getData()
	{
		return data;
	}
	
	public String getOraInizio()
	{
		return oraInizio;
	}
	
	public String getOraFine()
	{
		return oraFine;
	}
	
	public String getCodAmbulatorio()
	{
		return codAmbulatorio;
	}
	
	public String getNomeAmbulatorio()
	{
		return nomeAmbulatorio;
	}
	
	public String getNomeMedico()
	{
		if (nomeMedico == null)
			return "";
		else
			return nomeMedico;
	}
	
	public String getCognomeMedico()
	{
		if (cognomeMedico == null)
			return "";
		else
			return cognomeMedico;
	}
	
	public boolean hasReferto()
	{
		if (referto != null)
			return true;
		
		return false;
	}
	
	private void setReferto()
	{
		String query = "SELECT descrizione FROM referto WHERE RefVisita = '" + super.getNre() + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				referto = rs.getString(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public String getReferto()
	{
		return referto;
	}
	
	public boolean addReferto(String report)
	{
		String query = "INSERT INTO referto VALUES ('" + super.getNre() + "', '" + report + "')";
		if (!Globals.getDbmsConnection().insertQuery(query))
			return false;
		
		query = "UPDATE Visita SET RefCfDottore = '" + Globals.utente.getCf() + "' WHERE RefNRE = '" + super.getNre() + "'";
		if (!Globals.getDbmsConnection().updateQuery(query))
			return false;
		
		setReferto();
		
		return true;
	}
	
	public boolean hasRemindSent()
	{
		return remindSent;
	}
	
	public boolean delete()
	{
		String query = "DELETE FROM Visita WHERE RefNre = '" + super.getNre() + "'";
		return Globals.getDbmsConnection().deleteQuery(query);
	}
	
	@Override
	public String toString()
	{
		return new String("NRE " + super.getNre() + "\nAmbulatorio " + nomeAmbulatorio + "\nCf " + cfPaziente + "\nData " + data + "\nOra Inizio " + oraInizio + "\nOra Fine " + oraFine);
	}
}
