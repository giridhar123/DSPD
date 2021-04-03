package Main;
import Globals.Globals;
import Main.MainControl;

public class Main {

	private static MainControl control;

	public static void main(String[] args){
		Globals.init();
		control = new MainControl();
		control.openWindow();	//Apro la schermata principale
	}
}
