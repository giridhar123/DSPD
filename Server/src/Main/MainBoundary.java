package Main;

public class MainBoundary {

	public void printAdviseEmailSent(String avviso, String email)
	{
		System.out.println("Email contenente il seguente avviso:\n" + avviso + "\nInviata a: " + email + "\n");
	}
	
	public void printAdviseSmsSent(String avviso, String telefono)
	{
		System.out.println("SmS contenente il seguente avviso:\n" + avviso + "\nInviato a: " + telefono + "\n");
	}
	
	public void printRegistrationEmailSent(String email)
	{
		System.out.println("Email di registrazione inviata a: " + email + "\n");
	}
	
	public void printRegistrationSmsSent(String telefono)
	{
		System.out.println("SmS di registrazione inviato a: " + telefono + "\n");
	}
	
	public void sendEmailVisitConfirm(String nre, String email)
	{
		System.out.println("Email di conferma prenotazione relativa al nre: " + nre + " inviata a: " + email + "\n");
	}
	
	public void sendSmsVisitConfirm(String nre, String telefono)
	{
		System.out.println("SmS di conferma prenotazione relativa al nre: " + nre + " inviato a: " + telefono + "\n");
	}
	
	public void printRemindEmailSent(String nre, String email)
	{
		System.out.println("Email di remind relativo al nre: " + nre + " inviato a: " + email + "\n");
	}
	
	public void printRemindSmsSent(String nre, String telefono)
	{
		System.out.println("SmS di remind relativo al nre: " + nre + " inviato a: " + telefono + "\n");
	}
}
