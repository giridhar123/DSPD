package GuestMode;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;
import Globals.About.AboutControl;
import Globals.Book.BookControl;

public class GuestModeWindow implements Boundary{

	private JFrame frame;
	private GuestModeControl control;
	private Control previousControl;

	/**
	 * Launch the application.
	 */
	public void open(GuestModeWindow window) {
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
	 * @param previousControl 
	 */
	public GuestModeWindow(GuestModeControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Benvenuto nella modalit\u00E0 ospite");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(99, 34, 250, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JButton prenotaButton = new JButton("Prenota");
		prenotaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(prenotaButton.isEnabled()) {
					BookControl bookControl = new BookControl(control, false);
					bookControl.openWindow();
					control.closeWindow();
				}
			}
		});
		prenotaButton.setBounds(139, 97, 144, 53);
		frame.getContentPane().add(prenotaButton);
		
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
		backButton.setBounds(165, 192, 97, 25);
		frame.getContentPane().add(backButton);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mntmEsci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmEsci);
		
		JMenu menu = new JMenu("?");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(menu);
		
		JMenuItem mntmGuida = new JMenuItem("Guida");
		mntmGuida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutControl aboutControl = new AboutControl();
				aboutControl.openWindow();
			}
		});
		menu.add(mntmGuida);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
}
