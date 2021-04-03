package Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Globals.Globals;
import Mail.MailControl;
import Sms.SmsControl;

public class Main {

	public static void main(String[] args)
	{
		Globals.init();
		MainControl mainControl = new MainControl();
		mainControl.start();
	}
}
