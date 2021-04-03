package LogInAndSignUP.SignUp;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpWindow implements Boundary{

	private JFrame frame;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField cfField;
	private JTextField telefonoField;
	private JTextField emailField;
	private JLabel lblCognome_1;
	private JPasswordField repeatPasswordField;
	private JComboBox<String> sessoComboBox;
	
	private SignUpControl control;
	private Control previousControl;
	private JPasswordField passwordField;
	private JComboBox<String> giornoComboBox;
	private JComboBox<String> meseComboBox;
	private JComboBox<String> annoComboBox;
	private JTextField indirizzoField;
	private JButton confirmButton;
	private JTextField cittaField;

	/**
	 * Launch the application.
	 */
	public void open(SignUpWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
					window.frame.setVisible(true);
				} catch (Exception e) {
					if(Globals.DEBUG)
						e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUpWindow(SignUpControl control, Control previousControl) {
		this.previousControl = previousControl;
		this.control = control;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 506, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNome_1 = new JLabel("Nome");
		lblNome_1.setBounds(6, 127, 97, 25);
		lblNome_1.setIcon(new ImageIcon("/Volumes/1TB DAVIDE/Università/Ingegneria del Software/Progetto/DSPD - Copia/Tesina/Source/images/freccia-indietro-ios-7-simbolo-interfaccia_318-33678-2-2.jpg"));
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNome_1);
		
		nomeField = new JTextField();
		nomeField.setEnabled(false);
		nomeField.setBounds(127, 126, 147, 25);
		frame.getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		cognomeField = new JTextField();
		cognomeField.setEnabled(false);
		cognomeField.setBounds(127, 159, 147, 25);
		frame.getContentPane().add(cognomeField);
		cognomeField.setColumns(10);
		
		cfField = new JTextField();
		cfField.setColumns(10);
		cfField.setBounds(127, 22, 147, 25);
		frame.getContentPane().add(cfField);
		
		telefonoField = new JTextField();
		telefonoField.setEnabled(false);
		telefonoField.setColumns(10);
		telefonoField.setBounds(127, 232, 147, 25);
		frame.getContentPane().add(telefonoField);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setColumns(10);
		emailField.setBounds(127, 306, 147, 25);
		frame.getContentPane().add(emailField);
		
		JLabel lblDataDiNascita = new JLabel("Data di nascita");
		lblDataDiNascita.setBounds(6, 196, 97, 25);
		frame.getContentPane().add(lblDataDiNascita);
		
		JLabel lblCodiceFiscale = new JLabel("Codice Fiscale");
		lblCodiceFiscale.setBounds(6, 23, 97, 25);
		frame.getContentPane().add(lblCodiceFiscale);
		
		JLabel lblSesso = new JLabel("Sesso");
		lblSesso.setBounds(6, 271, 97, 25);
		frame.getContentPane().add(lblSesso);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(6, 307, 97, 25);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 60, 97, 25);
		frame.getContentPane().add(lblPassword);
		
		lblCognome_1 = new JLabel("Cognome");
		lblCognome_1.setVerticalAlignment(SwingConstants.TOP);
		lblCognome_1.setBounds(6, 164, 97, 25);
		frame.getContentPane().add(lblCognome_1);
		
		confirmButton = new JButton("Conferma");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(confirmButton.isEnabled())
					control.onConfirmButtonPressed();
			}
		});
		confirmButton.setEnabled(false);
		confirmButton.setBounds(380, 418, 89, 25);
		frame.getContentPane().add(confirmButton);
		
		JButton backButton = new JButton("Indietro");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					previousControl.openWindow(); //vedere se esiste un modo per impostare MainWindow.setVisible(true)
					control.closeWindow();
				}
			}
		});
		backButton.setBounds(281, 418, 89, 25);
		frame.getContentPane().add(backButton);
		
		JButton checkButton = new JButton("Check");
		checkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(checkButton.isEnabled())
					control.checkButtonPressed();
			}
		});
		checkButton.setBounds(297, 22, 117, 29);
		frame.getContentPane().add(checkButton);
		
		JLabel lblRipetiPassword = new JLabel("Ripeti Password");
		lblRipetiPassword.setBounds(6, 98, 111, 16);
		frame.getContentPane().add(lblRipetiPassword);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setEnabled(false);
		repeatPasswordField.setBounds(127, 96, 147, 26);
		frame.getContentPane().add(repeatPasswordField);
		
		sessoComboBox = new JComboBox<String>();
		sessoComboBox.setEnabled(false);
		sessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		sessoComboBox.setToolTipText("");
		sessoComboBox.setBounds(137, 269, 63, 27);
		frame.getContentPane().add(sessoComboBox);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setBounds(127, 59, 147, 26);
		frame.getContentPane().add(passwordField);
		
		giornoComboBox = new JComboBox<String>();
		giornoComboBox.setEnabled(false);
		giornoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Giorno", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setBounds(127, 201, 97, 21);
		frame.getContentPane().add(giornoComboBox);
		
		meseComboBox = new JComboBox<String>();
		meseComboBox.setEnabled(false);
		meseComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Mese", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setBounds(236, 201, 89, 21);
		frame.getContentPane().add(meseComboBox);
		
		annoComboBox = new JComboBox<String>();
		annoComboBox.setEnabled(false);
		annoComboBox.insertItemAt("Anno", 0);
		annoComboBox.setSelectedIndex(0);
		
		for(int i = 1940; i < 2001; i++) {
			String s = Integer.toString(i);
			annoComboBox.insertItemAt(s, i-1939);
		}
		annoComboBox.setBounds(335, 200, 100, 21);
		frame.getContentPane().add(annoComboBox);
		
		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setBounds(6, 343, 75, 14);
		frame.getContentPane().add(lblIndirizzo);
		
		indirizzoField = new JTextField();
		indirizzoField.setEnabled(false);
		indirizzoField.setBounds(127, 340, 147, 20);
		frame.getContentPane().add(indirizzoField);
		indirizzoField.setColumns(10);
		
		cittaField = new JTextField();
		cittaField.setEnabled(false);
		cittaField.setBounds(127, 379, 147, 19);
		frame.getContentPane().add(cittaField);
		cittaField.setColumns(10);
		
		JLabel lblICampiContrassegnati = new JLabel("Tutti i campi sono obbligatori");
		lblICampiContrassegnati.setBounds(6, 422, 307, 16);
		frame.getContentPane().add(lblICampiContrassegnati);
		
		JLabel lblCitt = new JLabel("CItt\u00E0");
		lblCitt.setBounds(10, 382, 46, 13);
		frame.getContentPane().add(lblCitt);
		
		JLabel lblNumeroDiTelefono = new JLabel("Numero di telefono");
		lblNumeroDiTelefono.setBounds(6, 238, 111, 13);
		frame.getContentPane().add(lblNumeroDiTelefono);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			System.exit(0);
			}
		});
		mnNewMenu.add(mntmEsci);
		
		JMenu menu = new JMenu("?");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(menu);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutControl aboutControl= new AboutControl();
				aboutControl.openWindow();
			}
		});
		menu.add(mntmAbout);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
	
	/*
	 * CF Field
	 */
	
	public void setCfFieldEnable(boolean enable)
	{
		cfField.setEnabled(enable);
	}
	
	public String getCfFieldText()
	{
		return cfField.getText();
	}
	
	public void setCfFieldBackgroundColor(Color color)
	{
		cfField.setBackground(color);
	}
	
	/*
	 * PASS Field
	 */
	
	public void setPassFieldEnable(boolean enable)
	{
		passwordField.setEnabled(enable);
	}
	
	public String getPasswordFieldText()
	{
		return new String(passwordField.getPassword());
	}
	
	public void setPasswordFieldBackgroundColor(Color color)
	{
		passwordField.setBackground(color);
	}

	public void setPassFieldText(String text)
	{
		passwordField.setText(text);
	}
	
	/*
	 * REPEAT PASS Field
	 */

	public void setRepeatPassFieldEnable(boolean enable)
	{
		repeatPasswordField.setEnabled(enable);
	}
	
	public String getRepeatPasswordFieldText()
	{
		return new String(repeatPasswordField.getPassword());
	}
	
	public void setRepeatPasswordBackgroundColor(Color color)
	{
		repeatPasswordField.setBackground(color);
	}
	
	public void setRepeatPasswordFieldText(String text)
	{
		repeatPasswordField.setText(text);
	}
	
	/*
	 * NOME Field
	 */
	
	public void setNomeFieldEnable(boolean enable)
	{
		nomeField.setEnabled(enable);
	}
	
	public void setNomeFieldText(String text)
	{
		nomeField.setText(text);
	}
	
	public String getNomeFieldText()
	{
		return nomeField.getText();
	}
	
	public void setNomeFieldBackgroundColor(Color color)
	{
		nomeField.setBackground(color);
	}
	
	/*
	 * COGNOME Field
	 */
	
	public void setCognomeFieldEnable(boolean enable)
	{
		cognomeField.setEnabled(enable);
	}
	
	public void setCognomeFieldText(String text)
	{
		cognomeField.setText(text);
	}
	
	public String getCognomeFieldText()
	{
		return cognomeField.getText();
	}
	
	public void setCognomeFieldBackgroundColor(Color color)
	{
		cognomeField.setBackground(color);
	}
	
	/*
	 * NASCITA field
	 */
	
	public String getDate() {
		String date = (String) annoComboBox.getSelectedItem()+"/" + meseComboBox.getSelectedItem()+ "/"+giornoComboBox.getSelectedItem();
		return date;
	}
	
	public void setDate(String[] nascita) {
		giornoComboBox.setSelectedItem(nascita[2]);
		meseComboBox.setSelectedItem(nascita[1]);
		annoComboBox.setSelectedItem(nascita[0]);
	}
	
	public void setDateEnabled(boolean enable) {
		giornoComboBox.setEnabled(enable);
		meseComboBox.setEnabled(enable);
		annoComboBox.setEnabled(enable);
	}
	public String getGiorno() 
	{
		return (String) giornoComboBox.getSelectedItem();
	}
	public String getMese()
	{
		return (String) meseComboBox.getSelectedItem();
	}
	public String getAnno()
	{
		return (String) annoComboBox.getSelectedItem();
	}
	
	
	/*
	 * TELEFONO Field
	 */
	
	public void setTelefonoFieldEnable(boolean enable)
	{
		telefonoField.setEnabled(enable);
	}
	
	public void setTelefonoFieldText(String text)
	{
		telefonoField.setText(text);
	}
	
	public String getTelefonoFieldText()
	{
		return telefonoField.getText();
	}
	
	public void setTelefonoFieldBackgroundColor(Color color)
	{
		telefonoField.setBackground(color);
	}
	
	/*
	 * SESSO Combo Box
	 */
	
	public void setSessoComboBoxEnable(boolean enable)
	{
		sessoComboBox.setEnabled(enable);
	}
	
	public void setSessoComboBoxSelection(int selection)
	{
		sessoComboBox.setSelectedIndex(selection);
	}
	
	public String getSessoComboBoxText()
	{
		return sessoComboBox.getSelectedItem().toString();
	}
	
	/*
	 * EMAIL Field
	 */
	
	public void setEmailFieldEnable(boolean enable)
	{
		emailField.setEnabled(enable);
	}
	
	public String getEmailFieldText()
	{
		return emailField.getText();
	}
	
	public void setEmailFieldBackgroundColor(Color color)
	{
		emailField.setBackground(color);
	}
	
	public void setEmailFieldText(String text)
	{
		emailField.setText(text);
	}
	
	/*
	 *  INDIRIZZO Field
	 */
	
	public String getIndirizzoFieldText()
	{
		return indirizzoField.getText();
	}
	
	public void setIndirizzoFieldBackgroundColor(Color color)
	{
		indirizzoField.setBackground(color);
	}
	
	public void setIndirizzoFieldText(String text)
	{
		indirizzoField.setText(text);
	}
	
	public void setIndirizzoEnable(boolean enable)
	{
		indirizzoField.setEnabled(enable);
	}
	
	/*
	 * CITTA field 
	 */
	public String getCittaFieldText()
	{
		return cittaField.getText();
	}
	
	public void setCittaFieldBackgroundColor(Color color)
	{
		cittaField.setBackground(color);
	}
	
	public void setCittaFieldText(String text)
	{
		cittaField.setText(text);
	}
	
	public void setCittaEnable(boolean enable)
	{
		cittaField.setEnabled(enable);
	}
	
	/*
	 *  DATA field
	 */
	
	public void setGiornoFieldBackgroundColor(Color color)
	{
		giornoComboBox.setBackground(color);	
	}
	
	public void setMeseFieldBackgroundColor(Color color)
	{
		meseComboBox.setBackground(color);
	}
	
	public void setAnnoFieldBackgroundColor(Color color)
	{
		annoComboBox.setBackground(color);
	}
	
	
	
	
	
	/*
	 * ConfirmButton
	 */
	
	public void setConfirmButtonEnable(boolean enable)
	{
		confirmButton.setEnabled(enable);
	}
}
