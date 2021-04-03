package Globals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dbmsConnection.dbmsConnection;

public abstract class Globals {
	//True to print debug info
	public final static boolean DEBUG = true;
	public final static boolean SMS = false;
	public final static boolean MAIL = false;
	
	//Database credentials
	private static String DB_HOSTNAME;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String DB_NAME;

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
	
	public static String getTodayDate()
	{
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
		int meseCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int giornoCorrente = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return (annoCorrente + "-" + meseCorrente + "-" + giornoCorrente);
	}
	
	public static String getCurrentHoursAndMinutesAndSeconds()
	{
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		int ora = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int minuti = calendar.get(Calendar.MINUTE);        // gets hour in 12h format
		int secondi = calendar.get(Calendar.SECOND);
		
		return new String(ora + ":" + minuti + ":" + secondi);
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
