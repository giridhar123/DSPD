package MedicMode;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;
import Globals.Book.BookControl;
import Globals.EditCredentials.EditCredentialsControl;
import Globals.ManageVisit.ManageVisitControl;
import MedicMode.Hospitalitation.ManageHospitalitation.ManageHospitalitationControl;
import MedicMode.ManageVisitDoctor.ManageVisitDoctorControl;
import MedicMode.VisiteAmbulatorio.VisiteAmbulatorioControl;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedicModeWindow implements Boundary {

	private JFrame frame;
	private MedicModeControl control;
	private Control previousControl;
	
	private JMenuItem mntmAmbulatorio;
	/**
	 * Launch the application.
	 */
	public void open(MedicModeWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.updateAmbulatorioButton();
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
	public MedicModeWindow(MedicModeControl control, Control previousControl) {
		this.control=control;
		this.previousControl=previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 421, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton bookButton = new JButton("Nuova prenotazione");
		bookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(bookButton.isEnabled()) {
					BookControl bookControl = new BookControl(control, true);
					bookControl.openWindow();
					control.closeWindow();
				}
			}
		});
		
		bookButton.setBounds(113, 26, 159, 53);
		frame.getContentPane().add(bookButton);
		
		JButton manageHospitalitationButton = new JButton("Gestione Ricoveri");
		manageHospitalitationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		manageHospitalitationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(manageHospitalitationButton.isEnabled()) {
					ManageHospitalitationControl manageHospitalitationControl = new ManageHospitalitationControl(control);
					manageHospitalitationControl.openWindow();
					control.closeWindow();
				}
			}
		});
		manageHospitalitationButton.setBounds(113, 171, 159, 53);
		frame.getContentPane().add(manageHospitalitationButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(logoutButton.isEnabled()) {
					Globals.utente = null;
					previousControl.openWindow();
					control.closeWindow();
				}
			}
		});
		logoutButton.setBounds(297, 339, 89, 23);
		frame.getContentPane().add(logoutButton);
		
		JButton bookPatientButton = new JButton("Prenota per paziente");
		bookPatientButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(bookPatientButton.isEnabled()) {
					BookControl bookControl = new BookControl(control, false);
					bookControl.openWindow();
					control.closeWindow();
				}
			}
		});
		bookPatientButton.setBounds(113, 91, 159, 53);
		frame.getContentPane().add(bookPatientButton);
		
		JButton editCredentialsButton = new JButton("Modifica Credenziali");
		editCredentialsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				EditCredentialsControl editCredentialsControl = new EditCredentialsControl(control);
				editCredentialsControl.openWindow();
				control.closeWindow();
			}
		});
		editCredentialsButton.setBounds(113, 252, 159, 53);
		frame.getContentPane().add(editCredentialsButton);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);			}
		});
		mnFile.add(mntmEsci);
		
		JMenu mnGestioneVisite = new JMenu("Gestione Visite");
		menuBar.add(mnGestioneVisite);
		
		JMenuItem mntmVaiAlleMie = new JMenuItem("Le mie visite");
		mntmVaiAlleMie.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ManageVisitControl manageVisitControl=new ManageVisitControl(control);
				manageVisitControl.openWindow();
				control.closeWindow();
			}
		});
		mnGestioneVisite.add(mntmVaiAlleMie);
		
		JMenuItem mntmVaiAlleVisite = new JMenuItem("Visite pazienti");
		mntmVaiAlleVisite.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ManageVisitDoctorControl manageVisitDoctorControl = new ManageVisitDoctorControl(control);
				manageVisitDoctorControl.openWindow();
				control.closeWindow();
			}
		});
		mnGestioneVisite.add(mntmVaiAlleVisite);
		
		mntmAmbulatorio = new JMenuItem("Ambulatorio");
		mntmAmbulatorio.setEnabled(false);
		mntmAmbulatorio.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				VisiteAmbulatorioControl visiteAmbulatorioControl = new VisiteAmbulatorioControl(control);
				visiteAmbulatorioControl.openWindow();
				control.closeWindow();
			}
		});
		mnGestioneVisite.add(mntmAmbulatorio);
		
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
	
	public void setAmbulatorioButtonEnable(boolean status)
	{
		mntmAmbulatorio.setEnabled(status);
	}
}
