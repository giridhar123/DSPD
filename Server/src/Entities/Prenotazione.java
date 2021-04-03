package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Globals.Globals;

public class Prenotazione extends Ricetta
{
	private String data;
	private String oraInizio;
	private String oraFine;
	private String codAmbulatorio;
	private String nomeAmbulatorio;
	private boolean remindSent;
	private Utente paziente;
	private String avviso;
	
	public Prenotazione (String nre)
	{
		super(nre);
		init();
	}
	
	private void init()
	{
		String query = "SELECT RefCFPaziente, data, oraInizio, oraFine, CodAmbulatorio, nomeAmbulatorio, remindSent "
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
				paziente = new Utente(rs.getString(1));
				data = rs.getString(2);
				oraInizio = rs.getString(3);
				oraFine = rs.getString(4);
				codAmbulatorio = rs.getString(5);
				nomeAmbulatorio = rs.getString(6);
				if (rs.getInt(7) == 0)
					remindSent = false;
				else remindSent = true;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "SELECT Avviso "
				+ " FROM AvvisoVisita "
				+ " WHERE RefNre = '" + super.getNre() + "'" 
				+ " AND Sent = 0";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				avviso = rs.getString(1);
		} catch (SQLException e) {
				if (Globals.DEBUG)
					e.printStackTrace();
		}
	}
	
	public Utente getPaziente()
	{
		return paziente;
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
	
	public boolean hasRemindSent()
	{
		return remindSent;
	}
	
	public String getAvviso()
	{
		return avviso;
	}
	
	@Override
	public String toString()
	{
		return new String("NRE " + super.getNre() + "\nAmbulatorio " + nomeAmbulatorio + "\nCf " + paziente.getCf() + "\nData " + data + "\nOra Inizio " + oraInizio + "\nOra Fine " + oraFine);
	}
}
