package Globals.Notification;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Globals.Boundary;
import Globals.Globals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class NotificationDialog extends JDialog implements Boundary {

	private final JPanel contentPanel = new JPanel();
	private NotificationControl control;
	private JLabel warningMessage;

	/**
	 * Launch the application.
	 */
	public void open(NotificationDialog dialog) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dialog.setResizable(false);
					dialog.setLocationRelativeTo(null);
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
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
	public NotificationDialog(NotificationControl control, String message) {
		this.control = control;
		setBounds(100, 100, 450, 313);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton okButton = new JButton("Ok");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				control.closeWindow();
			}
		});
		okButton.setBounds(156, 243, 117, 29);
		contentPanel.add(okButton);
		
		warningMessage = new JLabel(message);
		warningMessage.setBounds(6, 6, 438, 225);
		contentPanel.add(warningMessage);
	}
	
	public void closeFrame()
	{
		this.dispose();
	}
}
