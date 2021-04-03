package MedicMode.VisiteAmbulatorio.NewReport;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Globals.Boundary;
import Globals.Control;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

public class NewReportWindow implements Boundary {

	private JFrame frame;
	private NewReportControl control;
	private Control previousControl;
	private JTextArea reportFieldText;

	/**
	 * Launch the application.
	 */
	public void open(NewReportWindow window) {
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
	public NewReportWindow(NewReportControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 463, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(null);
		
		reportFieldText = new JTextArea();
		reportFieldText.setBounds(6, 30, 438, 190);
		frame.getContentPane().add(reportFieldText);
		reportFieldText.setColumns(10);
		
		JLabel lblScriviIlReferto = new JLabel("Scrivi il referto");
		lblScriviIlReferto.setBounds(186, 6, 173, 16);
		frame.getContentPane().add(lblScriviIlReferto);
		
		JButton okButton = new JButton("Ok");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(okButton.isEnabled())
					control.okButtonPressed();
			}
		});
		okButton.setBounds(260, 224, 117, 29);
		frame.getContentPane().add(okButton);
		
		JButton annullaButton = new JButton("Annulla");
		annullaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(annullaButton.isEnabled()) {
					control.closeWindow();
					previousControl.openWindow();
				}
			}
		});
		annullaButton.setBounds(109, 224, 117, 29);
		frame.getContentPane().add(annullaButton);
	}

	@Override
	public void closeFrame() {
		frame.dispose();
	}
	
	public String getReportFieldText()
	{
		return reportFieldText.getText();
	}
}
