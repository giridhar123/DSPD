package Globals.CadutaConnessione;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Globals.Boundary;
import Globals.Globals;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class CadutaConnessioneDialog extends JDialog  implements Boundary{

	private final JPanel contentPanel = new JPanel();
	private CadutaConnessioneControl control;
	private JButton connectButton;

	/**
	 * Launch the application.
	 */
	public void open(CadutaConnessioneDialog dialog) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dialog.setResizable(false);
					dialog.setLocationRelativeTo(null);
					dialog.setAlwaysOnTop (true);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.setVisible(true);
					
					//Chiusura programma se si esce dalla finestra
					dialog.addWindowListener(new WindowAdapter() {
					    @Override
					    public void windowClosing(WindowEvent windowEvent) {
					    	System.exit(0);
					    }
					});
				} catch (Exception e) {
					if(Globals.DEBUG)
						e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public CadutaConnessioneDialog(CadutaConnessioneControl control) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.control = control;
		setBounds(100, 100, 450, 183);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		connectButton = new JButton("Connettiti");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Executors.newSingleThreadScheduledExecutor().execute(() -> {
					control.connectButtonPressed();
					}
				);
			}
		});
		connectButton.setBounds(164, 104, 117, 29);
		contentPanel.add(connectButton);
		
		JLabel lblConnessioneAssentePremere = new JLabel("<html><div align = \"center\">Connessione assente.<br/>\nPremere il tasto \"Connettiti\" per provare a instaurare nuovamente la connessione.</div></html>");
		lblConnessioneAssentePremere.setBounds(65, 21, 329, 71);
		contentPanel.add(lblConnessioneAssentePremere);
	}
	
	public void closeFrame()
	{
		this.dispose();
	}
	
	public void setConnectButtonEnabled(boolean status)
	{
		connectButton.setEnabled(status);
	}
}
