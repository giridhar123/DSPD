package Globals.CadutaConnessione;

import Globals.Control;
import Globals.Globals;

public class CadutaConnessioneControl implements Control{
	
	private CadutaConnessioneDialog dialog;
	private boolean dialogOpened = false;
	
	public void connectButtonPressed()
	{
		dialog.setConnectButtonEnabled(false);
		Globals.getDbmsConnection().connect();
		if (Globals.getDbmsConnection().checkConnection())
			Globals.setPermissions();
		dialog.setConnectButtonEnabled(true);
	}
	
	public void openWindow()
	{
		dialog = new CadutaConnessioneDialog(this);
		dialog.open(dialog);
		dialogOpened = true;
	}
	
	public void closeWindow()
	{	
		dialog.closeFrame();
		dialogOpened = false;
	}

	public boolean isDialogOpened()
	{
		return dialogOpened;
	}
}
