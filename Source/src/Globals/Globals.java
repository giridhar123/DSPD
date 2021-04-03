package Globals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import Globals.Entities.Utente;

public abstract class Globals {
	//True to print debug info
	public final static boolean DEBUG = true;
	
	//Database credentials
	private static String DB_HOSTNAME;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String DB_NAME;
	
	//Utente
	public static Utente utente;

	//dbmsConnection
	private static dbmsConnection dbms;
	
	//Permessi
	private static int PERMESSO_PAZIENTE;
	private static int PERMESSO_MEDICO;
	private static int PERMESSO_AMMINISTRATORE;
	
	public static void init()
	{
		initializeDatabaseValues();
		Globals.dbms = new dbmsConnection();
		Globals.dbms.connect();
		if (Globals.dbms.checkConnection())
			setPermissions();
	}
	
	private static void initializeDatabaseValues()
	{
		String fileName = "database.txt";

	    // This will reference one line at a time
	    String hostname = null;
	    String username = null;
	    String password = null;

	    try {
	        // FileReader reads text files in the default encoding.
	        FileReader fileReader = new FileReader(fileName);

	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        hostname = bufferedReader.readLine();
	        username = bufferedReader.readLine();
	        password = bufferedReader.readLine();
	        

	        // Always close files.
	        bufferedReader.close();         
	    }
	    catch(FileNotFoundException ex) {
	        System.out.println("Unable to open file '" + fileName + "'");                
	    }
	    catch(IOException ex) {
	        System.out.println("Error reading file '" + fileName + "'");                  
	        // Or we could just do this: 
	        // ex.printStackTrace();
	      }
	    
		DB_HOSTNAME = hostname;
		DB_USER = username;
		DB_PASSWORD = password;
		DB_NAME = "ospedale";
	}
	
	public static void setPermissions()
	{
		String query = "SELECT CodGruppo FROM Gruppo_Permessi WHERE NomeGruppo = 'Paziente'";
		ResultSet rs = Globals.dbms.selectQuery(query);
		
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				Globals.PERMESSO_PAZIENTE = rs.getInt(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "SELECT CodGruppo FROM Gruppo_Permessi WHERE NomeGruppo = 'Medico'";
		rs = Globals.dbms.selectQuery(query);
		
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				Globals.PERMESSO_MEDICO = rs.getInt(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "SELECT CodGruppo FROM Gruppo_Permessi WHERE NomeGruppo = 'Amministratore'";
		rs = Globals.dbms.selectQuery(query);
		
		if (rs == null)
			return;
		
		try {
			if (rs.next())
				Globals.PERMESSO_AMMINISTRATORE = rs.getInt(1);
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public static boolean isPersonInDB(String cf)
	{
		String query = "SELECT * FROM Persona WHERE CF = '" + cf + "'";
		ResultSet rs = Globals.dbms.selectQuery(query);
		if (rs == null)
			return false;
		
		try {
			if (rs.next())
				return true;
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean addPersonaToDB(String cfPaziente, String nomePaziente, String cognomePaziente, String dataNascita, String sesso, String indirizzoResidenza, String telefono, String citta, String email)
	{
		String query = "INSERT INTO Persona (CF, Nome, Cognome, Data_Nascita, Sesso, Indirizzo_Residenza, Telefono, Citta, Email) "
					 + "VALUES "
					 + "('" + cfPaziente + "', '" + nomePaziente + "', '" + cognomePaziente + "',' " + dataNascita + "', '" + sesso + "', '" + indirizzoResidenza + "', '" + telefono + "', '" + citta + "', '" + email + "')";
				
		if (!Globals.dbms.insertQuery(query))
			return false;
		
		return true;
	}
	
	public static String getTodayDate()
	{
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
		int meseCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int giornoCorrente = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return (annoCorrente + "/" + meseCorrente + "/" + giornoCorrente);
	}
	
	public static int getPermessoPaziente()
	{
		return PERMESSO_PAZIENTE;
	}
	
	public static int getPermessoMedico()
	{
		return PERMESSO_MEDICO;
	}
	
	public static int getPermessoAmministratore()
	{
		return PERMESSO_AMMINISTRATORE;
	}
	
	public static String getDbHostname()
	{
		return DB_HOSTNAME;
	}
	
	public static String getDbUsername()
	{
		return DB_USER;
	}
	
	public static String getDbPassword()
	{
		return DB_PASSWORD;
	}
	
	public static String getDbName()
	{
		return DB_NAME;
	}
	
	public static dbmsConnection getDbmsConnection()
	{
		return dbms;
	}
}
