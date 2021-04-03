package Globals.Book.ModifyBook;

import java.awt.EventQueue;
import Globals.Globals;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;
import Globals.Entities.Prenotazione;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ModifyBookWindow implements Boundary{

	private JFrame frame;
	private ModifyBookControl control;
	private Control previousControl;
	private JTextField NREtextField;
	private JTextField tipoVisitatextField;
	private JTextField dataTextField;
	private JLabel lblTipo;
	private JLabel lblData;
	private JLabel lblNre;
	private JTextField ambulatorioTextField;
	private JTextField oraFinetextField;
	private JTextField oraIniziotextField;
	private JLabel lblOraInizio;
	private JLabel lblOraFine;
	private JLabel lblAmbulatorio;
	private JTextField repartoTextField;
	private JLabel lblReparto;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmEsci;
	private JMenu menu;
	private JMenuItem mntmAbout;
	private Prenotazione prenotazione;
	
	/**
	 * Launch the application.
	 */
	public void open(ModifyBookWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.fillContents();
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
	public ModifyBookWindow(ModifyBookControl control, Control previousControl, Prenotazione prenotazione) {
		this.control = control;
		this.previousControl = previousControl;
		this.prenotazione = prenotazione;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 576, 365);
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
		
		backButton.setBounds(373, 269, 130, 21);
		frame.getContentPane().add(backButton);
		
		NREtextField = new JTextField();
		NREtextField.setBounds(72, 32, 96, 19);
		NREtextField.setEditable(false);
		frame.getContentPane().add(NREtextField);
		NREtextField.setColumns(10);
		
		tipoVisitatextField = new JTextField();
		tipoVisitatextField.setBounds(267, 32, 96, 19);
		tipoVisitatextField.setEditable(false);
		frame.getContentPane().add(tipoVisitatextField);
		tipoVisitatextField.setColumns(10);
		
		dataTextField = new JTextField();
		dataTextField.setBounds(444, 32, 96, 19);
		dataTextField.setEditable(false);
		frame.getContentPane().add(dataTextField);
		dataTextField.setColumns(10);
		
		ambulatorioTextField = new JTextField();
		ambulatorioTextField.setBounds(72, 127, 96, 19);
		ambulatorioTextField.setEditable(false);
		frame.getContentPane().add(ambulatorioTextField);
		ambulatorioTextField.setColumns(10);
		
		oraFinetextField = new JTextField();
		oraFinetextField.setBounds(359, 78, 96, 19);
		oraFinetextField.setEditable(false);
		frame.getContentPane().add(oraFinetextField);
		oraFinetextField.setColumns(10);
		
		oraIniziotextField = new JTextField();
		oraIniziotextField.setBounds(147, 78, 96, 19);
		oraIniziotextField.setEditable(false);
		frame.getContentPane().add(oraIniziotextField);
		oraIniziotextField.setColumns(10);
		
		repartoTextField = new JTextField();
		repartoTextField.setBounds(267, 127, 96, 19);
		repartoTextField.setEditable(false);
		frame.getContentPane().add(repartoTextField);
		repartoTextField.setColumns(10);
		
		
		JButton modificaDatabutton = new JButton("Modifica data");
		modificaDatabutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(modificaDatabutton.isEnabled()) {
					control.modifyBookButtonPressed();
					control.closeWindow();
				}
			}
		});
		modificaDatabutton.setBounds(28, 257, 109, 21);
		frame.getContentPane().add(modificaDatabutton);
		
		JButton eliminaPrenotazioneButton = new JButton("Elimina prenotazione");
		eliminaPrenotazioneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(eliminaPrenotazioneButton.isEnabled()) {
					control.deleteBookButtonPressed();
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		eliminaPrenotazioneButton.setBounds(177, 257, 130, 21);
		if (!prenotazione.getCfPaziente().equals(Globals.utente.getCf()))
			eliminaPrenotazioneButton.setVisible(false);
		
		frame.getContentPane().add(eliminaPrenotazioneButton);
		
		lblNre = new JLabel("NRE");
		lblNre.setBounds(29, 35, 46, 13);
		frame.getContentPane().add(lblNre);
		
		lblTipo = new JLabel("Tipo Visita");
		lblTipo.setBounds(191, 35, 66, 13);
		frame.getContentPane().add(lblTipo);
		
		lblData = new JLabel("Data");
		lblData.setBounds(388, 35, 46, 13);
		frame.getContentPane().add(lblData);
		
		
		lblOraInizio = new JLabel("Ora Inizio");
		lblOraInizio.setBounds(72, 81, 65, 13);
		frame.getContentPane().add(lblOraInizio);
		
		lblOraFine = new JLabel("Ora Fine");
		lblOraFine.setBounds(303, 81, 51, 13);
		frame.getContentPane().add(lblOraFine);
		
		lblAmbulatorio = new JLabel("Ambulatorio");
		lblAmbulatorio.setBounds(10, 130, 65, 13);
		frame.getContentPane().add(lblAmbulatorio);
	
		
		lblReparto = new JLabel("Reparto");
		lblReparto.setBounds(211, 130, 46, 13);
		frame.getContentPane().add(lblReparto);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			
				System.exit(0);
			}
		});
		mnFile.add(mntmEsci);
		
		menu = new JMenu("?");
		menuBar.add(menu);
		
		mntmAbout = new JMenuItem("About");
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
	
	public void setNreFieldText(String text)
	{
		 NREtextField.setText(text);
	}
	
	public void setTipoVisitaFieldText(String text)
	{
		tipoVisitatextField.setText(text);
	}
	
	public void setDataFieldText(String text)
	{
		dataTextField.setText(text);
	}
	
	public void setOraInizioFieldText(String text)
	{
		 oraIniziotextField.setText(text);
	}
	
	public void setOraFineFieldText(String text)
	{
		oraFinetextField.setText(text);
	}
	
	public void setAmbulatorioFieldText(String text)
	{
		ambulatorioTextField.setText(text);
	}
	
	public void setRepartoFieldText(String text)
	{
		repartoTextField.setText(text);
	}
}
