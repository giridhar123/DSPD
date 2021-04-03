package Globals.EditCredentials;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

public class EditCredentialsWindow implements Boundary {
	
	private Control previousControl;
	private EditCredentialsControl control;
	private JFrame frame;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField cittaField;
	private JTextField indirizzoField;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField repeatPasswordField;
	private JComboBox<String> giornoComboBox;
	private JComboBox<String> meseComboBox;
	private JComboBox<String> annoComboBox;
	private JComboBox<String> sessoComboBox;
	private JTextField cfField;
	private JTextField mailField;
	private JTextField numberField;
	/**
	 * Launch the application.
	 */
	public void open(EditCredentialsWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
					control.fillContent();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditCredentialsWindow(EditCredentialsControl control, Control previousControl) {
		this.control=control;
		this.previousControl=previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 613, 554);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton backButton = new JButton("Indietro");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		backButton.setBounds(483, 463, 89, 23);
		frame.getContentPane().add(backButton);
		
		nomeField = new JTextField();
		nomeField.setBounds(137, 37, 190, 20);
		frame.getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		cognomeField = new JTextField();
		cognomeField.setColumns(10);
		cognomeField.setBounds(137, 68, 190, 20);
		frame.getContentPane().add(cognomeField);
		
		cittaField = new JTextField();
		cittaField.setColumns(10);
		cittaField.setBounds(137, 99, 190, 20);
		frame.getContentPane().add(cittaField);
		
		indirizzoField = new JTextField();
		indirizzoField.setColumns(10);
		indirizzoField.setBounds(137, 130, 190, 20);
		frame.getContentPane().add(indirizzoField);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(14, 40, 49, 14);
		frame.getContentPane().add(lblNome);
		
		JLabel lblNewLabel = new JLabel("Cognome");
		lblNewLabel.setBounds(13, 72, 72, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblCitt = new JLabel("Citt\u00E0");
		lblCitt.setBounds(14, 102, 49, 14);
		frame.getContentPane().add(lblCitt);
		
		JLabel lblNewLabel_1 = new JLabel("Indirizzo");
		lblNewLabel_1.setBounds(14, 133, 49, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblSesso = new JLabel("Sesso");
		lblSesso.setBounds(14, 192, 49, 14);
		frame.getContentPane().add(lblSesso);
		
		sessoComboBox = new JComboBox<String>();
		sessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		sessoComboBox.setBounds(137, 188, 72, 22);
		frame.getContentPane().add(sessoComboBox);
		
		JLabel lblDataDiNascita = new JLabel("Data di nascita");
		lblDataDiNascita.setBounds(14, 236, 107, 14);
		frame.getContentPane().add(lblDataDiNascita);
		
		giornoComboBox = new JComboBox<String>();
		giornoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Giorno", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setBounds(137, 232, 72, 22);
		frame.getContentPane().add(giornoComboBox);
		
		meseComboBox = new JComboBox<String>();
		meseComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Mese", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setBounds(220, 232, 66, 22);
		frame.getContentPane().add(meseComboBox);
		
		annoComboBox = new JComboBox<String>();
		annoComboBox.insertItemAt("Anno", 0);
		annoComboBox.setSelectedIndex(0);
		
		for(int i = 1940; i < 2001; i++) {
			String s = Integer.toString(i);
		annoComboBox.insertItemAt(s, i-1939);
		}
		annoComboBox.setBounds(303, 232, 80, 22);
		frame.getContentPane().add(annoComboBox);
		
		
		JLabel lblVecchiaPassword = new JLabel("Vecchia password");
		lblVecchiaPassword.setBounds(14, 281, 107, 14);
		frame.getContentPane().add(lblVecchiaPassword);
		
		oldPasswordField = new JPasswordField();
		oldPasswordField.setColumns(10);
		oldPasswordField.setBounds(137, 278, 190, 20);
		frame.getContentPane().add(oldPasswordField);
		
		JLabel lblNuovaPassword = new JLabel("Nuova password");
		lblNuovaPassword.setBounds(14, 324, 107, 14);
		frame.getContentPane().add(lblNuovaPassword);
		
		JLabel lblRipetiPassword = new JLabel("Ripeti password");
		lblRipetiPassword.setBounds(14, 363, 107, 14);
		frame.getContentPane().add(lblRipetiPassword);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setColumns(10);
		newPasswordField.setBounds(137, 321, 190, 20);
		frame.getContentPane().add(newPasswordField);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setColumns(10);
		repeatPasswordField.setBounds(137, 360, 190, 20);
		frame.getContentPane().add(repeatPasswordField);
		
		JButton salvaButton = new JButton("Salva");
		salvaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(salvaButton.isEnabled())
					control.saveButtonPressed();
			}
		});
		salvaButton.setBounds(388, 463, 89, 23);
		frame.getContentPane().add(salvaButton);
		
		cfField = new JTextField();
		cfField.setEditable(false);
		cfField.setColumns(10);
		cfField.setBounds(137, 157, 190, 20);
		frame.getContentPane().add(cfField);
		
		JLabel lblCodiceFiscale = new JLabel("Codice fiscale");
		lblCodiceFiscale.setBounds(14, 160, 89, 14);
		frame.getContentPane().add(lblCodiceFiscale);
		
		JLabel lblVecchiaEmail = new JLabel("Email");
		lblVecchiaEmail.setBounds(14, 400, 107, 14);
		frame.getContentPane().add(lblVecchiaEmail);
		
		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(137, 397, 190, 20);
		frame.getContentPane().add(mailField);
		
		JLabel lblVecchioNumero = new JLabel("Numero di telefono");
		lblVecchioNumero.setBounds(14, 429, 117, 14);
		frame.getContentPane().add(lblVecchioNumero);
		
		numberField = new JTextField();
		numberField.setColumns(10);
		numberField.setBounds(137, 427, 190, 20);
		frame.getContentPane().add(numberField);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			
				System.exit(0);
			}
		});
		mnFile.add(mntmEsci);
		
		JMenu menu = new JMenu("?");
		menuBar.add(menu);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutControl aboutControl = new AboutControl();
				aboutControl.openWindow();			
			}
		});
		menu.add(mntmAbout);
	}
		
	public void closeFrame()
	{
		frame.dispose();
	}
	
	
// nomeField
	
	public void setNomeFieldEnable(boolean enable)
	{
		nomeField.setEnabled(false);
	}
	
	public void setNomeFieldText(String text)
	{
		nomeField.setText(text);
	}
	
	public String getNomeFieldText()
	{
		return nomeField.getText();
	}
	
	public void setNomeFieldTextBackgroundColor(Color color)
	{
		nomeField.setBackground(color);
	}

// cognomeField
	
	public void setCognomeFieldEnable(boolean enable)
	{
		cognomeField.setEnabled(false);
	}
	
	public void setCognomeFieldText(String text)
	{
		cognomeField.setText(text);
	}
	
	public String getCognomeFieldText()
	{
		return cognomeField.getText();
	}
	
	public void setCognomeFieldTextBackgroundColor(Color color)
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
		
	/*
	 *  INDIRIZZO Field
	 */
	
	public String getIndirizzoFieldText()
	{
		return indirizzoField.getText();
	}
	
	
	public void setIndirizzoFieldText(String text)
	{
		indirizzoField.setText(text);
	}
	
	public void setIndirizzoEnable(boolean enable)
	{
		indirizzoField.setEnabled(false);
	}
	
	public void setIndirizzoFieldTextBackgroundColor(Color color)
	{
		indirizzoField.setBackground(color);
	}
	

	/*
	 *  CF Field
	 */
	
	public String getCfFieldText()
	{
		return cfField.getText();
	}
	
	
	public void setCfFieldText(String text)
	{
		cfField.setText(text);
	}
	
	public void setCfEnable(boolean enable)
	{
		cfField.setEnabled(false);
	}
	
	/*
	 * CITTA field 
	 */
	public String getCittaFieldText()
	{
		return cittaField.getText();
	}
	
	
	public void setCittaFieldText(String text)
	{
		cittaField.setText(text);
	}
	
	public void setCittaEnable(boolean enable)
	{
		cittaField.setEnabled(false);
	}
	
	public void setCittaFieldTextBackgroundColor(Color color)
	{
		cittaField.setBackground(color);
	}
	
	//SESSO FIELD
	
	public void setSessoComboBoxEnable(boolean enable)
	{
		sessoComboBox.setEnabled(false);
	}
	
	public void setSessoComboBoxSelection(int selection)
	{
		sessoComboBox.setSelectedIndex(selection);
	}
	
	public String getSessoComboBoxText()
	{
		return sessoComboBox.getSelectedItem().toString();
	}

	//Password field
	
	
	public String getVecchiaPassword() 
	{
		return new String(oldPasswordField.getPassword());		
	}
	public void setVecchiaPasswordFieldBackgroundColor(Color color)
	{
		oldPasswordField.setBackground(color);
	}
	
	public String getNuovaPassword()
	{
		return new String(newPasswordField.getPassword());
	}
	
	public void setNuovaPasswordFieldBackgroundColor(Color color)
	{
		newPasswordField.setBackground(color);
	}
	
	
	public String getRipetiPassword()
	{
		return new String(repeatPasswordField.getPassword());
	}
	
	public void setRipetiPasswordFieldBackgroundColor(Color color)
	{
		repeatPasswordField.setBackground(color);
	}
	
	//MAIL field
	
	public String getMailFieldText() {
		
		return mailField.getText();
	}
	
	public void setMailFieldText(String text) {
		
		mailField.setText(text);
	}
	
	public void setMailFieldTextBackgroundColor(Color color)
	{
		mailField.setBackground(color);
	}
	
	//Numero di cellulare Field
	
	
	public void setNumberFieldText(String text)
	{
		numberField.setText(text);
	}
	public String getNumberFieldText()
	{
		return numberField.getText();
	}

	public void setNumberFieldTextBackgroundColor(Color color)
	{
		numberField.setBackground(color);
	}
}

