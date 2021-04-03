package AdministratorMode.ManageTurn;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Globals.Boundary;
import Globals.Control;
import Globals.About.AboutControl;

import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ManageTurnWindow implements Boundary{

	private JFrame frame;
	private Control previousControl;
	private ManageTurnControl control;
	
	private JButton confirmButton;
	private JComboBox<String> repartoAmbulatorioComboBox;
	private JComboBox<String> lunediComboBox;
	private JComboBox<String> martediComboBox;
	private JComboBox<String> mercolediComboBox;
	private JComboBox<String> giovediComboBox;
	private JComboBox<String> venerdiComboBox;
	private JComboBox<String> sabatoComboBox;
	private JComboBox<String> domenicaComboBox;

	/**
	 * Launch the application.
	 */
	public void open(ManageTurnWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					control.aggiungiRepartiAmbulatori();
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
	public ManageTurnWindow(ManageTurnControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 460, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><div align = \"center\">Seleziona un reparto o un ambulatorio</div></html>");
		lblNewLabel.setBounds(111, 6, 217, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Luned\u00EC");
		lblNewLabel_1.setBounds(6, 102, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		repartoAmbulatorioComboBox = new JComboBox<String>();
		repartoAmbulatorioComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				control.onComboBoxSelectionChanged();
			}
		});
		repartoAmbulatorioComboBox.setBounds(111, 44, 217, 27);
		frame.getContentPane().add(repartoAmbulatorioComboBox);
		
		giovediComboBox = new JComboBox<String>();
		giovediComboBox.setBounds(119, 189, 173, 27);
		frame.getContentPane().add(giovediComboBox);
		
		sabatoComboBox = new JComboBox<String>();
		sabatoComboBox.setBounds(119, 246, 173, 27);
		frame.getContentPane().add(sabatoComboBox);
		
		lunediComboBox = new JComboBox<String>();
		lunediComboBox.setBounds(119, 98, 173, 27);
		frame.getContentPane().add(lunediComboBox);
		
		martediComboBox = new JComboBox<String>();
		martediComboBox.setBounds(119, 130, 173, 27);
		frame.getContentPane().add(martediComboBox);
		
		mercolediComboBox = new JComboBox<String>();
		mercolediComboBox.setBounds(119, 159, 173, 27);
		frame.getContentPane().add(mercolediComboBox);
		
		venerdiComboBox = new JComboBox<String>();
		venerdiComboBox.setBounds(119, 217, 173, 27);
		frame.getContentPane().add(venerdiComboBox);
		
		domenicaComboBox = new JComboBox<String>();
		domenicaComboBox.setBounds(119, 275, 173, 27);
		frame.getContentPane().add(domenicaComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Marted\u00EC");
		lblNewLabel_2.setBounds(6, 134, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Mercoled\u00EC");
		lblNewLabel_3.setBounds(6, 163, 73, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Gioved\u00EC");
		lblNewLabel_4.setBounds(6, 193, 61, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Venerd\u00EC");
		lblNewLabel_5.setBounds(6, 221, 61, 16);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Sabato");
		lblNewLabel_6.setBounds(6, 250, 61, 16);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Domenica");
		lblNewLabel_7.setBounds(6, 279, 61, 16);
		frame.getContentPane().add(lblNewLabel_7);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				control.closeWindow();
				previousControl.openWindow();
			}
		});
		indietroButton.setBounds(327, 335, 117, 29);
		frame.getContentPane().add(indietroButton);
		
		confirmButton = new JButton("Conferma");
		confirmButton.setEnabled(false);
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(confirmButton.isEnabled())
					control.confirmButtonPressed();
			}
		});
		confirmButton.setBounds(211, 335, 117, 29);
		frame.getContentPane().add(confirmButton);
		
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
				AboutControl aboutControl=new AboutControl();
				aboutControl.openWindow();
			}
		});
		menu.add(mntmAbout);
	}
	
	public void closeFrame()
	{
		frame.dispose();
	}
	
	/*
	 * RepartoAmbulatorioComboBox
	 */
	public void addItemToRepartoAmbulatorioComboBox(String item)
	{
		repartoAmbulatorioComboBox.addItem(item);
	}
	
	public String getRepartoAmbulatorioComboBoxSelected()
	{
		return repartoAmbulatorioComboBox.getSelectedItem().toString();
	}
	
	/*
	 * LunedìComboBox
	 */
	public void addItemToLunediComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < lunediComboBox.getItemCount(); ++i)
			if (item.equals(lunediComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			lunediComboBox.addItem(item);
	}
	
	public String getLunediComboBoxSelected()
	{
		return lunediComboBox.getSelectedItem().toString();
	}
	
	public void clearLunediComboBox()
	{
		lunediComboBox.removeAllItems();
	}
	
	/*
	 * MartedìComboBox
	 */
	public void addItemToMartediComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < martediComboBox.getItemCount(); ++i)
			if (item.equals(martediComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			martediComboBox.addItem(item);
	}
	
	public String getMartediComboBoxSelected()
	{
		return martediComboBox.getSelectedItem().toString();
	}
	
	public void clearMartediComboBox()
	{
		martediComboBox.removeAllItems();
	}
	
	/*
	 * MercoledìComboBox
	 */
	public void addItemToMercolediComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < mercolediComboBox.getItemCount(); ++i)
			if (item.equals(mercolediComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			mercolediComboBox.addItem(item);
	}
	
	public String getMercolediComboBoxSelected()
	{
		return mercolediComboBox.getSelectedItem().toString();
	}
	
	public void clearMercolediComboBox()
	{
		mercolediComboBox.removeAllItems();
	}
	
	/*
	 * GiovedìComboBox
	 */
	public void addItemToGiovediComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < giovediComboBox.getItemCount(); ++i)
			if (item.equals(giovediComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			giovediComboBox.addItem(item);
	}
	
	public String getGiovediComboBoxSelected()
	{
		return giovediComboBox.getSelectedItem().toString();
	}
	
	public void clearGiovediComboBox()
	{
		giovediComboBox.removeAllItems();
	}
	
	/*
	 * VenerdìComboBox
	 */
	public void addItemToVenerdiComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < venerdiComboBox.getItemCount(); ++i)
			if (item.equals(venerdiComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			venerdiComboBox.addItem(item);
	}
	
	public String getVenerdiComboBoxSelected()
	{
		return venerdiComboBox.getSelectedItem().toString();
	}
	
	public void clearVenerdiComboBox()
	{
		venerdiComboBox.removeAllItems();
	}
	
	/*
	 * SabatoComboBox
	 */
	public void addItemToSabatoComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < sabatoComboBox.getItemCount(); ++i)
			if (item.equals(sabatoComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			sabatoComboBox.addItem(item);
	}
	
	public String getSabatoComboBoxSelected()
	{
		return sabatoComboBox.getSelectedItem().toString();
	}
	
	public void clearSabatoComboBox()
	{
		sabatoComboBox.removeAllItems();
	}
	
	/*
	 * DomenicaComboBox
	 */
	public void addItemToDomenicaComboBox(String item)
	{
		boolean alreadyIn = false;
		
		for (int i = 0; i < domenicaComboBox.getItemCount(); ++i)
			if (item.equals(domenicaComboBox.getItemAt(i)))
				alreadyIn = true;
		
		if (!alreadyIn)
			domenicaComboBox.addItem(item);
	}
	
	public String getDomenicaComboBoxSelected()
	{
		return domenicaComboBox.getSelectedItem().toString();
	}
	
	public void clearDomenicaComboBox()
	{
		domenicaComboBox.removeAllItems();
	}
	
	public void setDomenicaComboBoxEnable(boolean status)
	{
		domenicaComboBox.setEnabled(status);
	}
	
	/*
	 * ConfirmButton
	 */
	
	public void setConfirmButtonEnable(boolean status)
	{
		confirmButton.setEnabled(status);
	}
}
