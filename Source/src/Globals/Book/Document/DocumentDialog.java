package Globals.Book.Document;

import Globals.Book.BookControl;

import javax.swing.JButton;
import javax.swing.JDialog;
import Globals.Boundary;
import Globals.Control;

import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class DocumentDialog extends JDialog implements Boundary{
	private DocumentControl control;
	private Control previousControl;
	private JTable documentTable;

	/**
	 * Launch the application.
	 */
	public void open(DocumentDialog dialog) {
		try {
			control.fillTable();
			dialog.setResizable(false);			//Finestra non ridimensionabile
			dialog.setLocationRelativeTo(null);   //Apertura al centro
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DocumentDialog(DocumentControl control, BookControl previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 416, 181);
		getContentPane().add(scrollPane);
		
		documentTable = new JTable();
		documentTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Documenti necessari"
			}
		));
		
		scrollPane.setViewportView(documentTable);
		
		JButton yesButton = new JButton("SI");
		yesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (previousControl.openCalendar())
				{
					control.closeWindow();
					previousControl.closeWindow();
				}					
			}
		});
		yesButton.setBounds(186, 232, 85, 21);
		getContentPane().add(yesButton);
		
		JButton noButton = new JButton("NO");
		noButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				control.closeWindow();
			}
		});
		noButton.setBounds(289, 232, 85, 21);
		getContentPane().add(noButton);
		
		JLabel lblSeiInPossesso = new JLabel("Sei in possesso dei documenti necessari alla vista?");
		lblSeiInPossesso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSeiInPossesso.setBounds(20, 204, 406, 18);
		getContentPane().add(lblSeiInPossesso);
	}
	
	public void closeFrame()
	{
		this.dispose();
	}
	
	public void addRowToTable(String text)
	{
		DefaultTableModel model = (DefaultTableModel) documentTable.getModel();
		model.addRow(new Object[] {text});
	}
}
