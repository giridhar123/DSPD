package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Globals.Globals;

public class Ricetta {
	private String nre;
	private String codTipo;
	private String nomeTipo;
	private String priorita;
	private int codReparto;
	private String nomeReparto;
	private String dataEmissione;
	private ArrayList<String> documentiNecessari;
	
	public Ricetta(String nre)
	{
		this.nre = nre;
		init();
	}
	
	private void init()
	{
		documentiNecessari = new ArrayList<String>();
		String query = "SELECT CodTipo, NomeTipo, Priorita, CodReparto, NomeReparto, dataEmissione "
				+ " FROM Ricetta, Tipo, Reparto "
				+ " WHERE codNre = '" + nre + "' "
				+ " AND refTipo = codTipo "
				+ " AND refReparto = CodReparto";
		ResultSet rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			if (rs.next())
			{
				codTipo = rs.getString(1);
				nomeTipo = rs.getString(2);
				priorita = rs.getString(3);
				codReparto = rs.getInt(4);
				nomeReparto = rs.getString(5);
				dataEmissione = rs.getString(6);
			}
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
		
		query = "SELECT NomeDocumento "
				+ " FROM Ricetta, Richiede, Documento "
				+ " WHERE codNre = '" + nre + "' "
				+ " AND Ricetta.RefTipo = Richiede.RefTipo "
				+ " AND RefDocumento = CodDocumento";
		rs = Globals.getDbmsConnection().selectQuery(query);
		if (rs == null)
			return;
		
		try {
			while (rs.next())
				documentiNecessari.add(rs.getString(1));
		} catch (SQLException e) {
			if (Globals.DEBUG)
				e.printStackTrace();
		}
	}
	
	public String getNre()
	{
		return nre;
	}
	
	public String getPriorita()
	{
		return priorita;
	}
	
	public String getNomeTipo()
	{
		return nomeTipo;
	}
	
	public int getCodReparto()
	{
		return codReparto;
	}

	public String getNomeReparto()
	{
		return nomeReparto;
	}
	
	public boolean needDocuments()
	{
		if (documentiNecessari.isEmpty())
			return false;
		
		return true;
	}
	
	public ArrayList<String> getDocumentiNecessari()
	{
		return documentiNecessari;
	}
	
	public String getDataEmissione()
	{
		return dataEmissione;
	}
	
	public int comparePriority(String other)
	{
		int myPriority = 1; //Priorit� P
		int otherPriority = 1; //Priorit� P

		if (priorita.equals("D"))
			myPriority = 2;
		else if (priorita.equals("B"))
			myPriority = 3;
		else if (priorita.equals("U"))
			myPriority = 4;

		if (other.equals("D"))
			otherPriority = 2;
		else if (other.equals("B"))
			otherPriority = 3;
		else if (other.equals("U"))
			otherPriority = 4;
		
		if (myPriority == otherPriority)
			return 0;
		else if (myPriority > otherPriority)
			return 1;
		else return -1;
	}
}
