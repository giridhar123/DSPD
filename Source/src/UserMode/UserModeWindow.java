package UserMode;

import java.awt.EventQueue;
import Globals.Globals;
import Globals.About.AboutControl;
import Globals.Book.BookControl;
import Globals.EditCredentials.EditCredentialsControl;
import Globals.ManageVisit.ManageVisitControl;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import Globals.Boundary;
import Globals.Control;

public class UserModeWindow implements Boundary{

	private JFrame frame;
	private UserModeControl control;
	private Control previousControl;

	/**
	 * Launch the application.
	 */
	public void open(UserModeWindow window) {
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
	public UserModeWindow(UserModeControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 451, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton bookButton = new JButton("Prenota");
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
		bookButton.setBounds(83, 40, 250, 50);
		frame.getContentPane().add(bookButton);
		
		JLabel welcomeText = new JLabel("");
		welcomeText.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcomeText.setBounds(40, 40, 333, 33);
		frame.getContentPane().add(welcomeText);
		
		JButton showManageVisitButton = new JButton("Gestione Visite");
		showManageVisitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(showManageVisitButton.isEnabled()) {
					control.closeWindow();
					ManageVisitControl manageVisitControl = new ManageVisitControl(control);
					manageVisitControl.openWindow();
				}
			}
		});
		showManageVisitButton.setBounds(83, 101, 250, 50);
		frame.getContentPane().add(showManageVisitButton);
		
		JButton logoutButton = new JButton("Logout");
		//tasto logout
		logoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(logoutButton.isEnabled()) {
					Globals.utente = null;
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		logoutButton.setBounds(335, 227, 89, 27);
		frame.getContentPane().add(logoutButton);
		
		JButton editCredentialsButton = new JButton("Modifica credenziali");
		editCredentialsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(editCredentialsButton.isEnabled()) {
					control.closeWindow();
					EditCredentialsControl editCredentialsControl = new EditCredentialsControl(control);
					editCredentialsControl.openWindow();
					control.closeWindow();
				}
			}
		});
		editCredentialsButton.setBounds(83, 165, 250, 50);
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
}
