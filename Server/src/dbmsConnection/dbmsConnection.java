package dbmsConnection;

import Globals.Globals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;

public class dbmsConnection{
	
	private Connection conn = null;	//Connessione
	private String URL = "jdbc:mysql://" + Globals.getDbHostname() + ":3306/" + Globals.getDbName() + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";

	//Funzione per connettersi al DataBase
    public void connect() {
    	
    	Properties properties = new Properties();
    	properties.setProperty("user", Globals.getDbUsername());
    	properties.setProperty("password", Globals.getDbPassword());
    	properties.setProperty("useSSL", "false");
    	properties.setProperty("autoReconnect", "true");

        try {
            // Carico il driver MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Setto la connessione con il DB
            conn = DriverManager.getConnection(URL, properties);

        } catch (MySQLNonTransientConnectionException e) {
            if (Globals.DEBUG)
            	System.out.println("Impossibile connettersi");
        }
        catch(Exception exc)
        {
        	if (Globals.DEBUG)
        		exc.printStackTrace();
        }
    }
    
    /* Funzione checkConnection
     * Se c'è connessione con il DB ritorna true
     * altrimenti
     * Ritorna false e se la finestra di caduta connessione non è aperta, la apre
     */
    public boolean checkConnection()
    {	
    	try {
			if (conn != null && conn.isValid(1)) //Se la connessione esiste ed è valida (Il DB risponde)
				return true;
		} catch (SQLException e) {
			if(Globals.DEBUG)
				e.printStackTrace();
		}
    	
    	return false;
    }

    // Funzione per eseguire le query di tipo SELECT
    public ResultSet selectQuery(String query)
    {
    	ResultSet rs = null;
    	
    	//Controllo la connessione con il DB
    	if (!checkConnection())
    		return null;
    	
    	try
    	{
    		Statement st = conn.createStatement();
    		rs = st.executeQuery(query);
    	}
    	catch (Exception exc)
    	{
    		if (Globals.DEBUG)
    			exc.printStackTrace();
    	}
    	
    	return rs;
    }
    
    // Funzione per eseguire le query di tipo DELETE
    public boolean deleteQuery(String query)
    {	
    	//Controllo la connessione con il DB
    	if (!checkConnection())
    		return false;
    	
    	try
    	{
    		Statement st = conn.createStatement();
    		st.execute(query);
    	}
    	catch (Exception exc)
    	{
    		if (Globals.DEBUG)
    			exc.printStackTrace();
    	}
    	return true;
    }
    
    // Funzione per eseguire le query di tipo INSERT
    public boolean insertQuery(String query)
    {
    	//Controllo la connessione con il DB
    	if (!checkConnection())
    		return false;
    	
    	try
    	{
    		Statement st = conn.createStatement();
    		st.execute(query);
    	}
    	catch (Exception exc)
    	{
    		if (Globals.DEBUG)
    			exc.printStackTrace();
    	}
    	
    	return true;
    }
    
    //Funzione per eseguire un'update
    public boolean updateQuery(String query)
    {
    	//Controllo la connessione con il DB
    	if (!checkConnection())
    		return false;
    	
    	try
    	{
    		Statement st = conn.createStatement();
    		st.execute(query);
    	}
    	catch (Exception exc)
    	{
    		if (Globals.DEBUG)
    			exc.printStackTrace();
    	}
    	
    	return true;
    }
    
    public void close() throws SQLException
    {
    	if (conn != null)
    	{
    		conn.close();
    		
    		if (Globals.DEBUG)
    			System.out.println("Connessione chiusa correttamente.");
    	}
    }
}
