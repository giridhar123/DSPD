package Globals.Book.Document;

import java.util.Iterator;
import Globals.Book.BookControl;
import Globals.Entities.Ricetta;
import Globals.Notification.NotificationControl;
import Globals.Control;

public class DocumentControl implements Control {
	
	private DocumentDialog dialog;
	private BookControl previousControl;
	private NotificationControl notificationControl;
	
	private Ricetta ricetta;
	
	public DocumentControl(BookControl previousControl, Ricetta ricetta)
	{
		this.previousControl = previousControl;
		this.ricetta = ricetta;
	}
	
	public void openWindow()
	{
		dialog = new DocumentDialog(this, previousControl);
		dialog.open(dialog);
	}
	
	public void closeWindow()
	{
		dialog.closeFrame();
	}
	
	public void fillTable()
	{
		Iterator<String> iterator = ricetta.getDocumentiNecessari().iterator();
		
		while (iterator.hasNext())
			dialog.addRowToTable(iterator.next());
	}
}
