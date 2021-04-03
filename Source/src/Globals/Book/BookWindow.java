package Globals.Book;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BookWindow implements Boundary{

	private JFrame frame;
	private BookControl control;
	private Control previousControl;
	private JTextField codRicettaField;
	private JTextField cfField;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField indirizzoField;
	private JTextField telefonoField;
	private JComboBox<String> sessoComboBox;
	private JComboBox<String> ambulatorioComboBox;
	private JComboBox<String> giornoComboBox;
	private JComboBox<String> meseComboBox;
	private JComboBox<String> annoComboBox;
	private JTextField repartoField;
	private JTextField cittaField;
	private String priorita;
	private JTextField emailField;
	private JButton bookButton;
	
	   	
	/**
	 * Launch the application.
	 */
	public void open(BookWindow window) {
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
	public BookWindow(BookControl control, Control previousControl){
		this.previousControl = previousControl;
		this.control = control;
		initialize();
	}
	


	/**
	 * Initialize the contents of the frame.
	 */
	//accesso da ospite
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(-8, -57, 656, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
			
		bookButton = new JButton("Prenota");
		bookButton.setEnabled(false);
		bookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(bookButton.isEnabled())
					control.bookButtonPressed();
			}
		});
		bookButton.setBounds(351, 322, 146, 53);
		frame.getContentPane().add(bookButton);
		
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
		backButton.setBounds(521, 351, 85, 21);
		frame.getContentPane().add(backButton);
			
		codRicettaField = new JTextField();
		codRicettaField.setBounds(151, 208, 119, 19);
		codRicettaField.setColumns(10);
		frame.getContentPane().add(codRicettaField);
		
		cfField = new JTextField();
		cfField.setBounds(109, 14, 130, 19);
		frame.getContentPane().add(cfField);
		cfField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(249, 17, 46, 13);
		frame.getContentPane().add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(445, 17, 66, 13);
		frame.getContentPane().add(lblCognome);
		
		JLabel lblNewLabel = new JLabel("Data di Nascita");
		lblNewLabel.setBounds(10, 47, 104, 19);
		frame.getContentPane().add(lblNewLabel);
		
		nomeField = new JTextField();
		nomeField.setBounds(305, 14, 120, 19);
		frame.getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		cognomeField = new JTextField();
		cognomeField.setBounds(521, 14, 96, 19);
		frame.getContentPane().add(cognomeField);
		cognomeField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Sesso");
		lblNewLabel_1.setBounds(445, 50, 46, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblIndirizzzoResidenza = new JLabel("Via");
		lblIndirizzzoResidenza.setBounds(35, 89, 39, 19);
		frame.getContentPane().add(lblIndirizzzoResidenza);
		
		sessoComboBox = new JComboBox<String>();
		sessoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		sessoComboBox.setBounds(521, 48, 39, 17);
		frame.getContentPane().add(sessoComboBox);
		
		indirizzoField = new JTextField();
		indirizzoField.setBounds(72, 89, 146, 19);
		frame.getContentPane().add(indirizzoField);
		indirizzoField.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(240, 138, 55, 13);
		frame.getContentPane().add(lblTelefono);
		
		telefonoField = new JTextField();
		telefonoField.setBounds(305, 135, 117, 19);
		frame.getContentPane().add(telefonoField);
		telefonoField.setColumns(10);
		
		JLabel lblCodiceNreRicetta = new JLabel("Codice NRE ricetta");
		lblCodiceNreRicetta.setBounds(22, 211, 119, 13);
		frame.getContentPane().add(lblCodiceNreRicetta);
		
		JLabel lblReparto = new JLabel("Reparto");
		lblReparto.setBounds(63, 245, 61, 13);
		frame.getContentPane().add(lblReparto);

		
		JLabel lblAmbulatorio = new JLabel("Ambulatorio");
		lblAmbulatorio.setBounds(61, 292, 80, 13);
		frame.getContentPane().add(lblAmbulatorio);
		
		giornoComboBox = new JComboBox<String>();
		giornoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Giorno", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		giornoComboBox.setBounds(109, 46, 65, 21);
		frame.getContentPane().add(giornoComboBox);
		
		
		meseComboBox = new JComboBox<String>();
		meseComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Mese", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		meseComboBox.setBounds(184, 46, 63, 21);
		frame.getContentPane().add(meseComboBox);
		
		annoComboBox = new JComboBox<String>();
		annoComboBox.insertItemAt("Anno", 0);
		annoComboBox.setSelectedIndex(0);
		
		for(int i = 1940; i < 2000; i++) {
			String s = Integer.toString(i);
			annoComboBox.insertItemAt(s, i-1939);
		}
		annoComboBox .setBounds(259, 46, 65, 21);
		frame.getContentPane().add(annoComboBox );
		
		repartoField = new JTextField();
		repartoField.setEnabled(false);
		repartoField.setEditable(false);
		repartoField.setBounds(150, 242, 120, 19);
		frame.getContentPane().add(repartoField);
		repartoField.setColumns(10);
		
		ambulatorioComboBox = new JComboBox<String>();
		ambulatorioComboBox.setEnabled(false);
		ambulatorioComboBox.setBounds(155, 288, 87, 21);
		frame.getContentPane().add(ambulatorioComboBox);
		
		JButton confirmButton = new JButton("Conferma");
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(confirmButton.isEnabled())
					control.confirmPressed();
			}
		});
		confirmButton.setBounds(286, 211, 96, 21);
		frame.getContentPane().add(confirmButton);
		
		cittaField = new JTextField();
		cittaField.setBounds(303, 89, 119, 19);
		frame.getContentPane().add(cittaField);
		cittaField.setColumns(10);
		
		JLabel lblCitt = new JLabel("Citt\u00E0");
		lblCitt.setBounds(249, 92, 46, 13);
		frame.getContentPane().add(lblCitt);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(18, 136, 61, 16);
		frame.getContentPane().add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(72, 132, 146, 26);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Codice Fiscale");
		lblNewLabel_2.setBounds(10, 17, 85, 13);
		frame.getContentPane().add(lblNewLabel_2);

		
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
	 *CODICE NRE
	 */
	
	public String getCodRicettaFieldText() {
		return codRicettaField.getText();
	}
	
	public void setCodRicettaFieldBackgroudColor(Color color) {
		codRicettaField.setBackground(color);
	}
	
	public void setCodRicettaFieldTextEnabled(boolean status)
	{
		codRicettaField.setEnabled(status);
	}
	
	/*
	 *DATA 
	 */
	
	public String getDate() {
		String date = (String) annoComboBox.getSelectedItem()+"/" + meseComboBox.getSelectedItem()+ "/"+giornoComboBox.getSelectedItem()  ;
		return date;
	}
	
	public void setDate(String[] data)
	{
		giornoComboBox.setSelectedItem(data[2]);
		meseComboBox.setSelectedItem(data[1]);
		annoComboBox.setSelectedItem(data[0]);
	}
	
	public void setDateEnabled(boolean status)
	{
		giornoComboBox.setEnabled(status);
		meseComboBox.setEnabled(status);
		annoComboBox.setEnabled(status);
	}
	
	/*
	 *Telefono 
	 */
	
	public String getTelefonoFieldText() {
		return telefonoField.getText();
	}
	
	public void setTelefonoFieldBackgroudColor(Color color){
		telefonoField.setBackground(color);
	}
	
	public void setTelefonoFieldText(String text)
	{
		telefonoField.setText(text);
	}
	
	public void setTelefonoFieldEnabled(boolean status)
	{
		telefonoField.setEnabled(status);
	}
	
	/*
	 *Indirizzo 
	 */
	
	public String getResidenzaFieldText() {
		return indirizzoField.getText();
	}
	
	public void setResidenzaFieldBackgroundColor(Color color) {
		indirizzoField.setBackground(color);
	}
	
	public void setIndirizzoFieldText(String text)
	{
		indirizzoField.setText(text);
	}
	
	public void setIndirizzoFieldEnabled(boolean status)
	{
		indirizzoField.setEnabled(status);
	}
	
	/*
	 * Città
	 */
	
	public String getCittaFieldText() {
		return cittaField.getText();
	}
	
	public void setCittaFieldBackgroundColor(Color color) {
		cittaField.setBackground(color);
	}
	
	public void setCittaFieldText(String text)
	{
		cittaField.setText(text);
	}
	
	public void setCittaFieldEnabled(boolean status)
	{
		cittaField.setEnabled(status);
	}
	
	/*
	 *Codice Fiscale 
	 */
	
	public String getCfFieldText() {
		return cfField.getText();
	}
	
	public void setCfFieldBackgroudColor(Color color) {
		cfField.setBackground(color);
	}
	
	public void setCfFieldText(String text)
	{
		cfField.setText(text);
	}
	
	public void setCfFieldEnabled(boolean status)
	{
		cfField.setEnabled(status);
	}
	
	/*
	 *Nome 
	 */
	
	public String getNomeFieldText() {
		return nomeField.getText();
	}
	
	public void setNomeFieldBackgroundColor(Color color) {
		nomeField.setBackground(color);
	}
	
	public void setNomeFieldText(String text)
	{
		nomeField.setText(text);
	}
	
	public void setNomeFieldEnabled(boolean status)
	{
		nomeField.setEnabled(status);
	}
	
	/*
	 *Cognome 
	 */
	
	public String getCognomeFieldText() {
		return cognomeField.getText();
	}
	
	public void setCognomeFieldBackgroundColor(Color color) {
		cognomeField.setBackground(color);
	}
	
	public void setCognomeFieldText(String text)
	{
		cognomeField.setText(text);
	}
	
	public void setCognomeFieldEnabled(boolean status)
	{
		cognomeField.setEnabled(status);
	}
	
	/*
	 * Email
	 */
	
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
	
	public void setEmailFieldEnabled(boolean status)
	{
		emailField.setEnabled(status);
	}
	
	/*
	 *Sesso 
	 */
	
	public String getSessoComboBoxText() {
		return (String) sessoComboBox.getSelectedItem();
	}
	
	public void setSessoComboBoxIndex(int index)
	{
		sessoComboBox.setSelectedIndex(index);
	}
	
	public void setSessoComboBoxEnabled(boolean status)
	{
		sessoComboBox.setEnabled(status);
	}
	
	/*
	 *Ambulatorio 
	 */
	
	public String getAmbulatorioFieldText(){
		return (String) ambulatorioComboBox.getSelectedItem();
	}
	
	public void insertItemInAmbulatorioComboBoxAt(String text, int index)
	{
		ambulatorioComboBox.insertItemAt(text, index);
		ambulatorioComboBox.setEnabled(true);
		ambulatorioComboBox.setSelectedIndex(0);
	}
	
	public void clearAmbulatorioComboBox()
	{
		ambulatorioComboBox.removeAllItems();
	}
	
	/*
	 * Reparto fieldText
	 */
	
	public String getRepartoFieldText()
	{
		return repartoField.getText();
	}
	
	public void setRepartoFieldText(String text)
	{
		repartoField.setText(text);
	}
	
	/*
	 * BookButton
	 */
	
	public void setBookButtonEnabled(boolean status)
	{
		bookButton.setEnabled(status);
	}
	
	/* 
	 * DataComboBox
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
}
