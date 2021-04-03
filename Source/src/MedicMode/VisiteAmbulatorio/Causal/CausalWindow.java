package MedicMode.VisiteAmbulatorio.Causal;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Globals.Boundary;
import Globals.Control;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CausalWindow implements Boundary{

	private JFrame frame;
	private CausalControl control;
	private Control previousControl;
	private JTextArea causalField;

	/**
	 * Launch the application.
	 */
	public void open(CausalWindow window) {
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
	public CausalWindow(CausalControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 313);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblInserisciLaCausale = new JLabel("Inserisci la causale della modifica");
		lblInserisciLaCausale.setBounds(112, 6, 432, 27);
		frame.getContentPane().add(lblInserisciLaCausale);
		
		causalField = new JTextArea();
		causalField.setBounds(6, 35, 438, 196);
		frame.getContentPane().add(causalField);
		causalField.setColumns(10);
		
		JButton confirmButton = new JButton("Conferma");
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(confirmButton.isEnabled())
					control.onConfirmButtonPressed();
			}
		});
		confirmButton.setBounds(248, 243, 117, 29);
		frame.getContentPane().add(confirmButton);
		
		JButton backButton = new JButton("Annulla");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(backButton.isEnabled()) {
					previousControl.openWindow();
					control.closeWindow();
				}
			}
		});
		backButton.setBounds(68, 243, 117, 29);
		frame.getContentPane().add(backButton);
	}

	@Override
	public void closeFrame() {
		frame.dispose();
	}
	
	public String getCausalFieldText()
	{
		return causalField.getText();
	}
}
