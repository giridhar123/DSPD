package Globals.About;

import java.awt.EventQueue;

import Globals.Globals;

import javax.swing.JFrame;

import Globals.Boundary;
import javax.swing.JLabel;

public class AboutWindow implements Boundary{

	private JFrame frame;
	private AboutControl control;

	/**
	 * Launch the application.
	 */
	public void open(AboutWindow window) {
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
	public AboutWindow(AboutControl control) {
		this.control = control;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><div align = \"center\">\nSviluppato da:<br/>\nDavide Lavalle<br/>\nGiuseppe Peri<br/>\nSalvo Pisciotta<br/>\nDomenico Catalano<br/>\n<br/>\nUniversità degli Studi di Palermo<br/>\nCorso di Ingegneria del Software<br/>\nAnno Accademico 2018-2019<br/>\n</div></html>");
		lblNewLabel.setBounds(123, 6, 210, 266);
		frame.getContentPane().add(lblNewLabel);
	}

	public void closeFrame()
	{
		frame.dispose();
	}
}
