package LogInAndSignUP.Login;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class LoginWindow implements Boundary{

	private JFrame frame;
	private JTextField cfField;
	private JPasswordField passwordField;
	
	private LoginControl control;
	private Control previousControl;

	/**
	 * Launch the application.
	 */
	public void open(LoginWindow window) {
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
	 * @param control 
	 */
	public LoginWindow(LoginControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 320, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		cfField = new JTextField();
		cfField.setBounds(64, 79, 130, 26);
		frame.getContentPane().add(cfField);
		cfField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(64, 140, 130, 26);
		frame.getContentPane().add(passwordField);
		
		JLabel lblCf = new JLabel("Codice Fiscale");
		lblCf.setBounds(64, 62, 130, 16);
		frame.getContentPane().add(lblCf);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(64, 125, 73, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblSchermataDiAccesso = new JLabel("Schermata di accesso");
		lblSchermataDiAccesso.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSchermataDiAccesso.setIcon(new ImageIcon("/Volumes/1TB DAVIDE/Università/Ingegneria del Software/Progetto/DSPD - Copia/Tesina/Source/images/freccia-indietro-ios-7-simbolo-interfaccia_318-33678-2-2.jpg"));
		lblSchermataDiAccesso.setBounds(54, 11, 203, 40);
		frame.getContentPane().add(lblSchermataDiAccesso);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.loginButtonPressed();
			}
		});
		loginButton.addMouseListener(new MouseAdapter() {
			
		});
		loginButton.setBounds(64, 177, 117, 29);
		frame.getContentPane().add(loginButton);
		
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
		backButton.setBounds(74, 215, 97, 25);
		frame.getContentPane().add(backButton);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Esci");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("?");
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			AboutControl aboutControl= new AboutControl();
			aboutControl.openWindow();
			}
		});
		mnNewMenu_1.add(mntmAbout);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
	
	public String getCodiceFiscale() {
		return cfField.getText();
	}
	
	public void setCFFieldBackgroundColor(Color color){
		cfField.setBackground(color);
	}
	
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
	
	public void setPasswordBackgroundColor(Color color) {
		passwordField.setBackground(color);
	}
	
}
