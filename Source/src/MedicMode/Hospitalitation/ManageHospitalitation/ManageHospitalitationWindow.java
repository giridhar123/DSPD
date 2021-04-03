package MedicMode.Hospitalitation.ManageHospitalitation;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;
import MedicMode.Hospitalitation.ManageQueue.ManageQueueControl;
import MedicMode.Hospitalitation.NewHospitalitation.NewHospitalitationControl;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageHospitalitationWindow implements Boundary{

	private JFrame frame;
	private ManageHospitalitationControl control;
	private Control previousControl;
	
	private JButton manageQueueButton;

	/**
	 * Launch the application.
	 */
	public void open(ManageHospitalitationWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.updateManageQueueButton();
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
	public ManageHospitalitationWindow(ManageHospitalitationControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 308, 245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton newHospitalitationButton = new JButton("Nuovo Ricovero");
		newHospitalitationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(newHospitalitationButton.isEnabled()) {
					NewHospitalitationControl newHospitalitationControl = new NewHospitalitationControl(control);
					control.closeWindow();
					newHospitalitationControl.openWindow();
				}
			}
		});
		newHospitalitationButton.setBounds(71, 39, 143, 29);
		frame.getContentPane().add(newHospitalitationButton);
		
		manageQueueButton = new JButton("Gestione Coda");
		manageQueueButton.setEnabled(false);
		manageQueueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(manageQueueButton.isEnabled()) {
					ManageQueueControl manageQueueControl = new ManageQueueControl(control);
					control.closeWindow();
					manageQueueControl.openWindow();
				}
			}
		});
		manageQueueButton.setBounds(71, 96, 143, 29);
		frame.getContentPane().add(manageQueueButton);
		
		JButton btnIndietro = new JButton("Indietro");
		btnIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(btnIndietro.isEnabled()) {
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		btnIndietro.setBounds(196, 154, 89, 23);
		frame.getContentPane().add(btnIndietro);
		
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
	
	public void setManageQueueButtonEnable(boolean status)
	{
		manageQueueButton.setEnabled(status);
	}
}
