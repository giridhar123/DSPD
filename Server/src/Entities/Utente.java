package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import Globals.Globals;

public class Utente {
	private String cf;
	private String password;
	private String nome;
	private String cognome;
	private String dataNascita;
	private String email;
	private String telefono;
	private String sesso;
	private String indirizzo;
	private String citta;
	private int permesso;
	private int reparto;
	private String ambulatorio;
	private boolean puoGestireReparto;
	private boolean puoGestireAmbulatorio;

	public Utente(String cf)
	{
		this.cf = cf;
		reparto = 0;
		ambulatorio = "";
		init();
	}
	
	private void init()
	{
		String query = "SELECT Password, nome, cognome, data_Nascita, sesso, indirizzo_residenza, citta, telefono, email, refGruppoPermessi "
					+ " FROM Utente, Persona "
					+ " WHERE RefCf = CF "
					+ " AND CF = '" + cf + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				password = rs.getString(1);
				nome = rs.getString(2);
				cognome = rs.getString(3);
				dataNascita = rs.getString(4);
				sesso = rs.getString(5);
				indirizzo = rs.getString(6);
				citta = rs.getString(7);
				telefono = rs.getString(8);
				email = rs.getString(9);
				permesso = rs.getInt(10);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		if (permesso == Globals.getPermessoMedico())
		{
			setReparto();
			setAmbulatorio();
		}
	}
	
	private void setReparto()
	{
		String query = "SELECT RefReparto FROM TurnoReparto WHERE RefCfDottore = '" + cf + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				reparto = rs.getInt(1);
				puoGestireReparto = true;
			}
			else //Il medico non lavora in nessun reparto ma forse in un ambulatorio?
			{
				query = "SELECT DISTINCT RefReparto "
					+ " FROM TurnoAmbulatorio, Ambulatorio "
					+ " WHERE RefDottore = '" + cf + "' "
					+ " AND RefAmbulatorio = CodAmbulatorio";
				rs = Globals.getDbmsConnection().selectQuery(query);
				if (rs == null)
					return;
				
				if (rs.next())
					reparto = rs.getInt(1);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	private void setAmbulatorio()
	{
		String query = "SELECT RefAmbulatorio FROM TurnoAmbulatorio WHERE RefDottore = '" + cf + "'";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				ambulatorio = rs.getString(1);
				puoGestireAmbulatorio = true;
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}

	//update
	
	public boolean updatePassword(String password)
	{
		this.password = password;
		String query = "UPDATE Utente SET Password = '" + password + "' WHERE RefCf = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateNome(String nome)
	{
		this.nome = nome;
		String query = "UPDATE Persona SET Nome = '" + nome + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateCognome(String cognome)
	{
		this.cognome = cognome;
		String query = "UPDATE Persona SET Cognome = '" + cognome + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateDataNascita(String data)
	{
		this.dataNascita = data;
		String query = "UPDATE Persona SET data_nascita = '" + data + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateSesso(String sesso)
	{
		this.sesso = sesso;
		String query = "UPDATE Persona SET Sesso = '" + sesso + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateCitta(String citta)
	{
		this.citta = citta;
		String query = "UPDATE Persona SET Citta = '" + citta + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
		String query = "UPDATE Persona SET indirizzo_residenza = '" + indirizzo + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateEmail(String email)
	{
		this.email=email;
		String query = "UPDATE Persona SET email = '" + email + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateTelefono(String telefono) 
	{
		this.telefono=telefono;
		String query = "UPDATE Persona SET Telefono = '" + telefono + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updatePermesso(int permesso)
	{
		this.permesso = permesso;
		String query = "UPDATE Utente SET RefGruppoPermessi = '" + permesso + "' WHERE RefCF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	/*
	public boolean updateReparto(int reparto, boolean lunedì, boolean martedì, boolean mercoledì, boolean giovedì, boolean venerdì, boolean sabato, boolean domenica)
	{
		this.reparto = reparto;
		String query = "UPDATE Persona SET Cognome = '" + cognome + "' WHERE CF = '" + cf + "'";
		return Globals.getDbmsConnection().updateQuery(query);
	}
	
	public boolean updateAmbulatorio(String ambulatorio, boolean lunedì, boolean martedì, boolean mercoledì, boolean giovedì, boolean venerdì, boolean sabato)
	{
		this.ambulatorio = ambulatorio;
		return true;
	}
	*/
	
// get	
	
	public String getCf(){
		return cf;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getDataNascita(){
		return dataNascita;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getSesso() {
		return sesso;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public int getPermesso() {
		return permesso;
	}
	
	public int getReparto()
	{
		return reparto;
	}
	
	public String getAmbulatorio()
	{
		return ambulatorio;
	}
	
	public boolean puoGestireReparto()
	{
		return puoGestireReparto;
	}
	
	public boolean puoGestireAmbulatorio()
	{
		return puoGestireAmbulatorio;
	}
}
