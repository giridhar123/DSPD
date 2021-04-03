package Main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import LogInAndSignUP.Login.LoginControl;
import LogInAndSignUP.SignUp.SignUpControl;
import GuestMode.GuestModeControl;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import Globals.Boundary;
import Globals.Globals;
import Globals.About.AboutControl;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow implements Boundary{

	private JFrame frame;
	private MainControl control;

	private JButton accediButton;
	private JButton registratiButton;
	private JButton modalitaOspiteButton;

	/**
	 * Create the application.
	 * @param control 
	 */
	public MainWindow(MainControl control) {
		this.control = control;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 356, 356);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		accediButton = new JButton("Accedi");
		accediButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LoginControl loginControl = new LoginControl(control);
				loginControl.openWindow();
				control.closeWindow();
			}
		});
		
		accediButton.setBounds(109, 109, 117, 29);
		frame.getContentPane().add(accediButton);
		
		registratiButton = new JButton("Registrati");
		registratiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SignUpControl signUpControl = new SignUpControl(control);
				signUpControl.openWindow();
				control.closeWindow();
			}
		});
		
		registratiButton.setBounds(109, 150, 117, 29);
		frame.getContentPane().add(registratiButton);
		
		JLabel lblDspdHospital = new JLabel("OSPEDALE D.S.P.D");
		lblDspdHospital.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDspdHospital.setBounds(50, 51, 228, 29);
		frame.getContentPane().add(lblDspdHospital);
		
		modalitaOspiteButton = new JButton("Modalit\u00E0 Ospite");
		modalitaOspiteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		modalitaOspiteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				GuestModeControl guestModeControl = new GuestModeControl(control);
				guestModeControl.openWindow();
				control.closeWindow();
			}
		});
		modalitaOspiteButton.setBounds(96, 191, 147, 29);
		frame.getContentPane().add(modalitaOspiteButton);
		
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
		
		JMenu mnNewMenu = new JMenu("?");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutControl aboutControl = new AboutControl();
				aboutControl.openWindow();
			}
		});
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.add(mntmNewMenuItem);
	}
	
	public void open(MainWindow window)
	{
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
	
	public void closeFrame()
	{
		frame.dispose();
	}
}
