package MedicMode.Hospitalitation.NewHospitalitation;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class NewHospitalitationWindow implements Boundary {

	private JFrame frame;
	private Control previousControl;
	private NewHospitalitationControl control;
	private JTextField cfField;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField emailField;
	private JTextField telefonoField;
	private JTextField indirizzoField;
	
	private JButton hospitalizeButton;
	private JComboBox<String> giornoNascitaComboBox;
	private JComboBox<String> meseNascitaComboBox;
	private JComboBox<String> annoNascitaComboBox;
	private JComboBox<String> sessoComboBox;
	private JButton confirmDataButton;
	private JButton mettiInCodaButton;
	private JLabel postiLettoLabel;
	private JTextField cittaField;
	
	/**
	 * Launch the application.
	 */
	public void open(NewHospitalitationWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setResizable(false);			//Finestra non ridimensionabile
					window.frame.setLocationRelativeTo(null);   //Apertura al centro
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
	public NewHospitalitationWindow(NewHospitalitationControl control, Control previousControl) {
		this.control=control;
		this.previousControl=previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 481, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton backButton = new JButton("Indietro");
		backButton.setBounds(307, 408, 89, 23);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					previousControl.openWindow();
					control.closeWindow();
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(backButton);
		
		JLabel lblNewLabel = new JLabel("Codice Fiscale");
		lblNewLabel.setBounds(6, 33, 100, 16);
		frame.getContentPane().add(lblNewLabel);
		
		cfField = new JTextField();
		cfField.setBounds(125, 28, 130, 26);
		frame.getContentPane().add(cfField);
		cfField.setColumns(10);
		
		nomeField = new JTextField();
		nomeField.setEnabled(false);
		nomeField.setBounds(125, 56, 130, 26);
		frame.getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblInserireIDati = new JLabel("Inserire i dati del paziente");
		lblInserireIDati.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblInserireIDati.setBounds(144, 6, 259, 16);
		frame.getContentPane().add(lblInserireIDati);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(6, 61, 61, 16);
		frame.getContentPane().add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(6, 91, 61, 16);
		frame.getContentPane().add(lblCognome);
		
		JLabel lblDataNascita = new JLabel("Data Nascita");
		lblDataNascita.setBounds(6, 125, 94, 16);
		frame.getContentPane().add(lblDataNascita);
		
		JLabel lblEmail = new JLabel("EMail");
		lblEmail.setBounds(6, 188, 61, 16);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(6, 216, 61, 16);
		frame.getContentPane().add(lblTelefono);
		
		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setBounds(6, 244, 61, 16);
		frame.getContentPane().add(lblIndirizzo);
		
		JButton checkButton = new JButton("Check");
		checkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(checkButton.isEnabled())
					control.checkButtonPressed();
			}
		});
		checkButton.setBounds(286, 28, 117, 29);
		frame.getContentPane().add(checkButton);
		
		cognomeField = new JTextField();
		cognomeField.setEnabled(false);
		cognomeField.setBounds(125, 86, 130, 26);
		frame.getContentPane().add(cognomeField);
		cognomeField.setColumns(10);
		
		giornoNascitaComboBox = new JComboBox<String>();
		giornoNascitaComboBox.setEnabled(false);
		giornoNascitaComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Giorno", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoNascitaComboBox.setBounds(125, 121, 66, 27);
		frame.getContentPane().add(giornoNascitaComboBox);
		
		meseNascitaComboBox = new JComboBox<String>();
		meseNascitaComboBox.setEnabled(false);
		meseNascitaComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Mese", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseNascitaComboBox.setBounds(189, 121, 100, 27);
		frame.getContentPane().add(meseNascitaComboBox);
		
		annoNascitaComboBox = new JComboBox<String>();
		annoNascitaComboBox.setEnabled(false);
		annoNascitaComboBox.insertItemAt("Anno", 0);
		annoNascitaComboBox.setBounds(296, 121, 117, 27);
		
		for(int i = 1940; i < 2001; i++) {
			String s = Integer.toString(i);
			annoNascitaComboBox.insertItemAt(s, i-1939);
		}
		
		annoNascitaComboBox.setSelectedIndex(0);
		
		frame.getContentPane().add(annoNascitaComboBox);
		
		emailField = new JTextField();
		emailField.setEnabled(false);
		emailField.setBounds(125, 183, 130, 26);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		telefonoField = new JTextField();
		telefonoField.setEnabled(false);
		telefonoField.setBounds(125, 210, 130, 26);
		frame.getContentPane().add(telefonoField);
		telefonoField.setColumns(10);
		
		indirizzoField = new JTextField();
		indirizzoField.setEnabled(false);
		indirizzoField.setBounds(125, 239, 130, 26);
		frame.getContentPane().add(indirizzoField);
		indirizzoField.setColumns(10);
		
		postiLettoLabel = new JLabel("");
		postiLettoLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		postiLettoLabel.setBounds(54, 357, 350, 38);
		frame.getContentPane().add(postiLettoLabel);
		
		hospitalizeButton = new JButton("Ricovera");
		hospitalizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(hospitalizeButton.isEnabled())
					control.hospitalizeButtonPressed();
			}
		});
		hospitalizeButton.setBounds(159, 405, 117, 29);
		frame.getContentPane().add(hospitalizeButton);
		hospitalizeButton.setVisible(false);
		
		JLabel lblSesso = new JLabel("Sesso");
		lblSesso.setBounds(6, 160, 61, 16);
		frame.getContentPane().add(lblSesso);
		
		sessoComboBox = new JComboBox<String>();
		sessoComboBox.setEnabled(false);
		sessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		sessoComboBox.setBounds(125, 156, 66, 27);
		frame.getContentPane().add(sessoComboBox);
		
		confirmDataButton = new JButton("Conferma Dati");
		confirmDataButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(confirmDataButton.isEnabled())
					control.confirmDataButtonPressed();
			}
		});
		confirmDataButton.setEnabled(false);
		confirmDataButton.setBounds(144, 318, 117, 29);
		frame.getContentPane().add(confirmDataButton);
		
		mettiInCodaButton = new JButton("Metti in coda");
		mettiInCodaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(mettiInCodaButton.isEnabled())
					control.mettiInCodaButtonPressed();
			}
		});
		mettiInCodaButton.setBounds(159, 405, 117, 29);
		frame.getContentPane().add(mettiInCodaButton);
		
		JLabel lblCitt = new JLabel("Citt\u00E0");
		lblCitt.setBounds(6, 277, 49, 14);
		frame.getContentPane().add(lblCitt);
		
		cittaField = new JTextField();
		cittaField.setEnabled(false);
		cittaField.setBounds(125, 271, 130, 26);
		frame.getContentPane().add(cittaField);
		cittaField.setColumns(10);
		mettiInCodaButton.setVisible(false);
		
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
				AboutControl aboutControl=new AboutControl();
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
	
	public String getNascitaDate() {
		String date = (String) annoNascitaComboBox.getSelectedItem()+"/" + meseNascitaComboBox.getSelectedItem()+ "/"+giornoNascitaComboBox.getSelectedItem();
		return date;
	}
	
	public void setNascitaDate(String[] nascita) {
		giornoNascitaComboBox.setSelectedItem(nascita[2]);
		meseNascitaComboBox.setSelectedItem(nascita[1]);
		annoNascitaComboBox.setSelectedItem(nascita[0]);
	}
	
	public void setNascitaDateEnabled(boolean enable) {
		giornoNascitaComboBox.setEnabled(enable);
		meseNascitaComboBox.setEnabled(enable);
		annoNascitaComboBox.setEnabled(enable);
	}
	
	public void setAnnoNascitaBackgroundColor(Color color)
	{
		annoNascitaComboBox.setBackground(color);
	}
	
	public void setMeseNascitaBackgroundColor(Color color)
	{
		meseNascitaComboBox.setBackground(color);
	}
	
	public void setGiornoNascitaBackgroundColor(Color color)
	{
		giornoNascitaComboBox.setBackground(color);
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
	
	public void setIndirizzoFieldEnable(boolean enable)
	{
		indirizzoField.setEnabled(enable);
	}
	
	/*
	 *  CITTA' Field
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
	
	public void setCittaFieldEnable(boolean enable)
	{
		cittaField.setEnabled(enable);
	}
	
	
	/*
	 * hospitalizeButton
	 */
	
	public void sethospitalizeButtonVisible(boolean visible)
	{
		hospitalizeButton.setVisible(visible);
	}
	
	/*
	 * Metti in coda Button
	 */
	
	public void setMettiInCodaButtonVisible(boolean visible)
	{
		mettiInCodaButton.setVisible(visible);
	}
	
	/*
	 * Confirm data Button
	 */
	
	public void setConfirmDataButtonEnable(boolean enable)
	{
		confirmDataButton.setEnabled(enable);
	}
	
	public void setPostiLettoLabelText(String aText)
	{
		postiLettoLabel.setText(aText);
	}
}


