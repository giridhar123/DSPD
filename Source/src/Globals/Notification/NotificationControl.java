package Globals.Notification;

import Globals.Control;

public class NotificationControl implements Control{
	
	private NotificationDialog dialog;
	private String message;
	
	public NotificationControl(String message)
	{
		this.message = message;
	}
	
	public void openWindow()
	{
		dialog = new NotificationDialog(this, message);
		dialog.open(dialog);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
}
