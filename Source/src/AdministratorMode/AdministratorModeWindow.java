package AdministratorMode;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;
import Globals.Book.BookControl;
import Globals.EditCredentials.EditCredentialsControl;
import Globals.ManageVisit.ManageVisitControl;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import AdministratorMode.ManagePermission.ManagePermissionControl;
import AdministratorMode.ManageTurn.ManageTurnControl;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministratorModeWindow implements Boundary {

	private JFrame frame;
	private Control previousControl;
	private AdministratorModeControl control;
	/**
	 * Launch the application.
	 */
	public void open(AdministratorModeWindow window) {
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
	public AdministratorModeWindow(AdministratorModeControl control, Control previousControl) {
		this.previousControl=previousControl;
		this.control=control;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 503, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton managePermissionButton = new JButton("Gestione Permessi");
		managePermissionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(managePermissionButton.isEnabled()) {
					ManagePermissionControl managePermissionControl = new ManagePermissionControl(control);
					managePermissionControl.openWindow();
					control.closeWindow();
				}
			}
		});
		managePermissionButton.setBounds(135, 192, 159, 56);
		frame.getContentPane().add(managePermissionButton);
		
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
		logoutButton.setBounds(378, 349, 89, 23);
		frame.getContentPane().add(logoutButton);
		
		JButton bookButton = new JButton("Prenota ");
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		bookButton.setBounds(135, 48, 159, 56);
		frame.getContentPane().add(bookButton);
		
		JButton manageVisitButton = new JButton("Gestione visite");
		manageVisitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(manageVisitButton.isEnabled()) {
					ManageVisitControl manageVisitControl = new ManageVisitControl(control);
					manageVisitControl.openWindow();
					control.closeWindow();
				}
			}
		});
		manageVisitButton.setBounds(135, 126, 159, 56);
		frame.getContentPane().add(manageVisitButton);
		
		JButton gestioneTurniButton = new JButton("Gestione Turni");
		gestioneTurniButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(gestioneTurniButton.isEnabled()) {
					ManageTurnControl manageTurnControl = new ManageTurnControl(control);
					manageTurnControl.openWindow();
					control.closeWindow();
				}
			}
		});
		gestioneTurniButton.setBounds(135, 260, 159, 56);
		frame.getContentPane().add(gestioneTurniButton);
		
		JButton editCredentialsButton = new JButton("Modifica Credenziali");
		editCredentialsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				EditCredentialsControl editCredentialsControl = new EditCredentialsControl(control);
				editCredentialsControl.openWindow();
				control.closeWindow();
			}
		});
		editCredentialsButton.setBounds(135, 332, 159, 56);
		frame.getContentPane().add(editCredentialsButton);
		
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
}
