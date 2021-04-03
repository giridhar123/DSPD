package AdministratorMode.ManagePermission;

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
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ManagePermissionWindow implements Boundary{

	private JFrame frame;
	private Control previousControl;
	private ManagePermissionControl control;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField emailField;
	private JTextField telefonoField;
	private JTextField indirizzoField;
	private JTextField cfField;
	private JTextField specializzazioneField;
	private JTextField cittaField;
	private JComboBox<String> sessoComboBox;
	private JComboBox<String> annoComboBox;
	private JComboBox<String> meseComboBox;
	private JComboBox<String> giornoComboBox;
	private JButton updateButton;
	private JButton searchButton;
	private JComboBox<String> gruppoPermessoComboBox;

	/**
	 * Launch the application.
	 */
	public void open(ManagePermissionWindow window) {
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
	public ManagePermissionWindow(ManagePermissionControl control, Control previousControl ) {
		this.previousControl=previousControl;
		this.control=control;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 18));
		frame.setBounds(100, 100, 535, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton backButton = new JButton("Indietro");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					previousControl.openWindow();
					control.closeWindow();
				}
			}
		});
		backButton.setBounds(387, 527, 89, 23);
		frame.getContentPane().add(backButton);
		
		JLabel lblNewLabel = new JLabel("Pagina gestione permessi.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(180, 0, 250, 23);
		frame.getContentPane().add(lblNewLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(141, 74, 162, 23);
		frame.getContentPane().add(nomeField);
		setNomeFieldEnable(false);
		nomeField.setColumns(10);
		
		cognomeField = new JTextField();
		cognomeField.setBounds(141, 110, 162, 22);
		frame.getContentPane().add(cognomeField);
		setCognomeFieldEnable(false);
		this.cognomeField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(141, 145, 162, 22);
		frame.getContentPane().add(emailField);
		this.setEmailFieldEnable(false);
		
		telefonoField = new JTextField();
		telefonoField.setColumns(10);
		telefonoField.setBounds(141, 180, 162, 22);
		frame.getContentPane().add(telefonoField);
		this.setTelefonoFieldEnable(false);
		
		indirizzoField = new JTextField();
		indirizzoField.setColumns(10);
		indirizzoField.setBounds(141, 215, 162, 22);
		frame.getContentPane().add(indirizzoField);
		this.setIndirizzoEnable(false);
		
		gruppoPermessoComboBox = new JComboBox<String>();
		gruppoPermessoComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				control.onGruppoPermessiComboBoxSelectionChanged();
			}
		});
		gruppoPermessoComboBox.setEnabled(false);
		gruppoPermessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Paziente", "Medico", "Amministratore"}));
		gruppoPermessoComboBox.setBounds(141, 393, 136, 23);
		frame.getContentPane().add(gruppoPermessoComboBox);
		
		
		giornoComboBox = new JComboBox<String>();
		giornoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Giorno", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setBounds(141, 330, 80, 22);
		frame.getContentPane().add(giornoComboBox);
		
		
		meseComboBox = new JComboBox<String>();
		meseComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Mese", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setBounds(237, 330, 80, 22);
		frame.getContentPane().add(meseComboBox);
		
		
		annoComboBox = new JComboBox<String>();
		annoComboBox.insertItemAt("Anno", 0);
		annoComboBox.setSelectedIndex(0);
		
		for(int i = 1940; i < 2000; i++) {
			String s = Integer.toString(i);
			annoComboBox.insertItemAt(s, i-1939);
		}
		annoComboBox.setBounds(329, 330, 80, 22);
		frame.getContentPane().add(annoComboBox);
		//disabilita i campi di data dopo creazione
		this.setDateEnabled(false);
		
		cfField = new JTextField();
		cfField.setBounds(141, 42, 162, 22);
		frame.getContentPane().add(cfField);
		cfField.setColumns(10);
		
		searchButton = new JButton("Cerca");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(searchButton.isEnabled())
					control.searchButtonPressed();
			}
		});
		searchButton.setBounds(340, 39, 117, 29);
		frame.getContentPane().add(searchButton);
		
		updateButton = new JButton("Modifica Dati");
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(updateButton.isEnabled())
					control.updateButtonPressed();
			}
		});
		updateButton.setBounds(255, 526, 122, 25);
		frame.getContentPane().add(updateButton);
		this.setUpdateButtonEnable(false);
		
		JLabel lblCodiceFiscale = new JLabel("Codice Fiscale");
		lblCodiceFiscale.setBounds(12, 45, 86, 16);
		frame.getContentPane().add(lblCodiceFiscale);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(22, 77, 86, 16);
		frame.getContentPane().add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(22, 113, 86, 16);
		frame.getContentPane().add(lblCognome);
		
		JLabel lblNumeroDiTelefono = new JLabel("Telefono");
		lblNumeroDiTelefono.setBounds(22, 183, 121, 16);
		frame.getContentPane().add(lblNumeroDiTelefono);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(22, 148, 86, 16);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setBounds(22, 218, 86, 16);
		frame.getContentPane().add(lblIndirizzo);
		
		specializzazioneField = new JTextField();
		specializzazioneField.setBounds(141, 435, 136, 22);
		frame.getContentPane().add(specializzazioneField);
		specializzazioneField.setColumns(10);
		this.setSpecializzazioneEnable(false);
		
		JLabel lblSpecializzazione = new JLabel("Specializzazione");
		lblSpecializzazione.setBounds(22, 437, 96, 16);
		frame.getContentPane().add(lblSpecializzazione);
		
		
		JLabel lblGruppiPermessi = new JLabel("Gruppo Permesso");
		lblGruppiPermessi.setBounds(22, 396, 109, 16);
		frame.getContentPane().add(lblGruppiPermessi);
		
		sessoComboBox = new JComboBox<String>();
		sessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		sessoComboBox.setBounds(141, 285, 37, 22);
		frame.getContentPane().add(sessoComboBox);
		this.setSessoComboBoxEnable(false);
		
		JLabel lblDataDiNascita = new JLabel("Data di Nascita");
		lblDataDiNascita.setBounds(22, 333, 107, 16);
		frame.getContentPane().add(lblDataDiNascita);
		
		JLabel lblSesso = new JLabel("Sesso");
		lblSesso.setBounds(22, 288, 56, 16);
		frame.getContentPane().add(lblSesso);
		
		cittaField = new JTextField();
		cittaField.setBounds(141, 250, 162, 22);
		frame.getContentPane().add(cittaField);
		cittaField.setColumns(10);
		this.setCittaEnable(false);
		
		JLabel lblCitt = new JLabel("Citt\u00E0");
		lblCitt.setBounds(22, 256, 56, 16);
		frame.getContentPane().add(lblCitt);
		
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
	
	public void setCfFieldBackgroundColor(Color color) {
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
	
	
	public void setCittaFieldText(String text)
	{
		cittaField.setText(text);
	}
	
	public void setCittaEnable(boolean enable)
	{
		cittaField.setEnabled(enable);
	}
	/*
	 * Gruppopermessi
	 */
	public int getGruppoPermessiBoxIndex() {
		return gruppoPermessoComboBox.getSelectedIndex();
	}
	
	public void setGruppoPermessiBox(int gruppo) {
		gruppoPermessoComboBox.setSelectedIndex(gruppo-1);
	}
	
	public void setGruppoPermessiBoxEnable(boolean enable) {
		gruppoPermessoComboBox.setEnabled(enable);
	}
	
	/*
	 * Specializzazione
	 */
	
	public String getSpecializzazioneFieldText() {
		return specializzazioneField.getText();
	}
	
	public void setSpecializzazioneFieldText(String text) {
		specializzazioneField.setText(text);
	}
	
	public void setSpecializzazioneEnable(boolean enable) {
		specializzazioneField.setEnabled(enable);
	}
	
	public void setSpecializzazioneBackgroundColor(Color color) {
		specializzazioneField.setBackground(color);
	}
	/*
	 * UpdateButtons
	 */

	public void setUpdateButtonEnable(boolean enable){
		updateButton.setEnabled(enable);
	}
}