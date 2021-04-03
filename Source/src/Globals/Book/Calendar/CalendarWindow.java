package Globals.Book.Calendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.alee.laf.button.WebButton;
import Globals.Boundary;
import Globals.Control;
import Globals.Globals;


public class CalendarWindow extends CalendarData implements Boundary {
	int maxMemoSize = 255;
	static Color bg = new Color(96, 125, 139);
	static Color accent = new Color(0, 188, 212);
	private JFrame mainFrame;
	JPanel calOpPanel;
	WebButton todayBut;
	JLabel todayLab;
	WebButton lMonBut;
	JLabel curMMYYYYLab;
	WebButton nMonBut;
	JPanel calPanel;
	WebButton[] weekDaysName;
	JPanel infoPanel;
	JLabel infoClock;
	JPanel memoPanel;	
	JLabel selectedDate;
	JScrollPane memoAreaSP;
	JPanel memoSubPanel;
	WebButton saveBut;
	WebButton backBut;
	JPanel frameBottomPanel;
	TitledBorder memoBorder;
	JPanel buttonPanel;
	static ButtonGroup buttonGroup;
	JLabel bottomInfo = new JLabel("Per non perdere la priorita, prenotare nei giorni segnati di verde");
	WebButton[][] dateButs = new WebButton[CALENDAR_HEIGHT][CALENDAR_WIDTH];
	CalendarWindow.ListenForCalOpButtons lForCalOpButtons = new CalendarWindow.ListenForCalOpButtons();
	CalendarWindow.listenForDateButs lForDateButs = new CalendarWindow.listenForDateButs();
	final String[] WEEK_DAY_NAME = { "DOM", "LUN", "MAR", "MER", "GIO", "VEN", "SAB" };
	final String title = "Calendario Visite";
	
	private CalendarControl control;
	private Control previousControl;
	
	public static Font loadFont(String name, float size){
		InputStream is = CalendarWindow.class.getResourceAsStream("/resources/"+name);
		Font title = null;
		try {
			title = Font.createFont(Font.TRUETYPE_FONT, is);
			title = title.deriveFont(size);
		} catch (FontFormatException ex) {
			if(Globals.DEBUG)
				ex.printStackTrace();
		}
		catch (IOException ex){
			if(Globals.DEBUG)
				ex.printStackTrace();}
		return title;
	}
	
	/**
	 * Launch the application.
	 */
	public void open(CalendarWindow window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
						window.setCurrentDate();
						window.initializeGUI();
						
				} catch (Exception e) {
					if(Globals.DEBUG)
						e.printStackTrace();
				}
			}
		});
	}

	public CalendarWindow(CalendarControl control, Control previousControl) {
		this.control = control;
		this.previousControl = previousControl;
		//CalendarWindow.ThreadConrol threadCnl = new CalendarWindow.ThreadConrol();
		//threadCnl.start();
	}
	
	public void initializeGUI(){
		initializeMainFrame();
		setCalenderOperationButtons();
		setCalendarPanel();
		setWeekdayButtons();
		setDateButtons();
		showCal();
		setInfoPanel();
		setMemoPanel(this);
		setSubPanels();
	}
	
	private void showCal() {
		String fontColor = "black";
		int currentYaer = CalendarWindow.this.today.get(1);
		int currentMonth = CalendarWindow.this.today.get(2);
		int currentDay = CalendarWindow.this.today.get(5);
		int mese = this.month;
		int anno = this.year;
		
		
		for (int i = 0; i < CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CALENDAR_WIDTH; j++) {
	
				this.dateButs[i][j].setText("<html><font color=" + fontColor + ">" + this.calDates[i][j] + "</font></html>");
				
				if (this.calDates[i][j] == 0) {
					this.dateButs[i][j].setVisible(false);
				} else {
					this.dateButs[i][j].setVisible(true);
				}

				this.dateButs[i][j].setEnabled(false);
						
						
				int giorno = this.calDates[i][j];
				this.dateButs[i][j].setEnabled(false);
						
				if(anno > currentYaer) {
					this.dateButs[i][j].setEnabled(true);
				}
						
				else if(anno == currentYaer) {
					if(mese > currentMonth) {
						this.dateButs[i][j].setEnabled(true);
					}
							
					else if(mese == currentMonth) {
						if(giorno > currentDay)
							this.dateButs[i][j].setEnabled(true);
					}
				}
				
				if (j == 0) {
					this.dateButs[i][j].setEnabled(false);
				}	
			}			
		}
		
		control.updateButtonsForPriority();
	}
	
	private void initializeMainFrame(){
		mainFrame = new JFrame("Memo Calendar");
		mainFrame.setDefaultCloseOperation(3);
		mainFrame.setSize(700, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		/*try {
	    	WebLookAndFeel.install();
		} catch (Exception e) {
			bottomInfo.setText("ERROR : LookAndFeel setting failed");
		}*/
	}
	
	
	private class ListenForCalOpButtons implements ActionListener {
		private ListenForCalOpButtons() {}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == CalendarWindow.this.lMonBut) {
				CalendarWindow.this.moveMonth(-1);
			} else if (e.getSource() == CalendarWindow.this.nMonBut) {
				CalendarWindow.this.moveMonth(1);
			} 
			CalendarWindow.this.curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5><font color=\"#FFFFFF\">"
					+ (CalendarWindow.this.month + 1 < 10 ? "&nbsp;" : "") + (CalendarWindow.this.month + 1) + " / "
					+ CalendarWindow.this.year + "</font></font></th></tr></table></html>");
			
			CalendarWindow.this.showCal();
		}
	}

	private class listenForDateButs implements ActionListener {
		private listenForDateButs() {}
		public void actionPerformed(ActionEvent e) {
			int k = 0;
			int l = 0;
			for (int i = 0; i < CALENDAR_HEIGHT; i++) {
				for (int j = 0; j < CALENDAR_WIDTH; j++) {
					if (e.getSource() == CalendarWindow.this.dateButs[i][j]) {
						k = i;
						l = j;
					}
				}
			}
			if ((k != 0) || (l != 0)) {
				CalendarWindow.this.day = CalendarWindow.this.calDates[k][l];
			}
			CalendarWindow.this.cal = new GregorianCalendar(CalendarWindow.this.year, CalendarWindow.this.month,CalendarWindow.this.day);

			CalendarWindow.this.selectedDate.setText(
					"<Html><font size=3><font color=\"#FFFFFF\">"  + CalendarWindow.this.day + "/"+ (CalendarWindow.this.month + 1) + "/"
							+ CalendarWindow.this.year + "&nbsp;</font></font></html>");

			//reset radioButton
			for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
		           AbstractButton button = buttons.nextElement();
		           buttonGroup.clearSelection();
		           button.setEnabled(true);
		    }
			
			buttonPanel.setVisible(true);
			control.disableButtons(buttonGroup);
		}
	}

	/*private class ThreadConrol extends Thread {
		private ThreadConrol() {}

		public void run() {
			boolean msgCntFlag = false;
			int num = 0;
			String curStr = new String();
			try {
				for (;;) {
					CalendarWindow.this.today = Calendar.getInstance();
					String amPm = CalendarWindow.this.today.get(9) == 0 ? "AM" : "PM";
					String hour;
					if (CalendarWindow.this.today.get(10) == 0) {
						hour = "12";
					} else {
						if (CalendarWindow.this.today.get(10) == 12) {
							hour = " 0";
						} else {
							hour = (CalendarWindow.this.today.get(10) < 10 ? " " : "") + CalendarWindow.this.today.get(10);
						}
					}
					String min = (CalendarWindow.this.today.get(12) < 10 ? "0" : "") + CalendarWindow.this.today.get(12);
					String sec = (CalendarWindow.this.today.get(13) < 10 ? "0" : "") + CalendarWindow.this.today.get(13);
					//CalendarWindow.this.infoClock.setText(amPm + " " + hour + ":" + min + ":" + sec);
					sleep(1000L);
					String infoStr = CalendarWindow.this.bottomInfo.getText();
					if ((infoStr != " ") && ((!msgCntFlag) || (curStr != infoStr))) {
						num = 5;
						msgCntFlag = true;
						curStr = infoStr;
					} else if ((infoStr != " ") && (msgCntFlag)) {
						if (num > 0) {
							num--;
						} else {
							msgCntFlag = false;
							CalendarWindow.this.bottomInfo.setText(" ");
						}
					}
				}
			} catch (InterruptedException e) {
				System.out.println("Thread:Error");
			}
		}
	}*/
	
	public void setCalendarPanel(){
		calOpPanel.setLayout(new GridBagLayout());
		GridBagConstraints calOpGC = new GridBagConstraints();
		calOpGC.gridx = 1;
		calOpGC.gridy = 1;
		calOpGC.gridwidth = 2;
		calOpGC.gridheight = 1;
		calOpGC.weightx = 1.0D;
		calOpGC.weighty = 1.0D;
		calOpGC.insets = new Insets(5, 5, 0, 0);
		calOpGC.anchor = 17;
		calOpGC.fill = 0;
		calOpGC.gridwidth = 3;
		calOpGC.gridx = 2;
		calOpGC.gridy = 1;
		calOpGC.anchor = 10;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 1;
		calOpGC.gridy = 2;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 2;
		calOpGC.gridy = 2;
		calOpPanel.add(lMonBut, calOpGC);
		calOpGC.gridwidth = 2;
		calOpGC.gridx = 3;
		calOpGC.gridy = 2;
		calOpPanel.add(curMMYYYYLab, calOpGC);
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 5;
		calOpGC.gridy = 2;
		calOpPanel.add(nMonBut, calOpGC);
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 6;
		calOpGC.gridy = 2;
		calPanel = new JPanel();
		calPanel.setBackground(bg);
		calPanel.setLayout(new GridLayout(0, 7, 2, 2));
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	}
	
	public void setCalenderOperationButtons(){
		calOpPanel = new JPanel();
		calOpPanel.setBackground(bg);
		lMonBut = new WebButton("<");
		lMonBut.setToolTipText("Mese precedente");
		lMonBut.addActionListener(lForCalOpButtons);
		curMMYYYYLab = new JLabel(
				"<html><table width=100><tr><th><font size=5><font color=\"#FFFFFF\">" + (month + 1 < 10 ? "&nbsp;" : "")
						+ (month + 1) + " / " + year + "</font></font></th></tr></table></html>");
		nMonBut = new WebButton(">");
		nMonBut.setToolTipText("Mese successivo");
		nMonBut.addActionListener(lForCalOpButtons);
	}
	
	public void setWeekdayButtons(){
		weekDaysName = new WebButton[CALENDAR_WIDTH];
		for (int i = 0; i < CALENDAR_WIDTH; i++) {
			weekDaysName[i] = new WebButton(WEEK_DAY_NAME[i]);
			weekDaysName[i].setDrawLines(true, true, true, true);
			weekDaysName[i].setDrawSides(false, false, false, false);
			weekDaysName[i].setContentAreaFilled(false);
			weekDaysName[i].setForeground(Color.WHITE);
			if (i == 0 || i == 6) {
				weekDaysName[i].setBottomBgColor(accent);
				weekDaysName[i].setTopBgColor(accent);
			} else {
				weekDaysName[i].setBottomBgColor(new Color(150, 150, 150));
			}
			weekDaysName[i].setOpaque(true);
			weekDaysName[i].setFont(loadFont("Oxygen-Regular.ttf", 12f));
			weekDaysName[i].setFocusPainted(false);
			weekDaysName[i].setDrawBottom(false);
			calPanel.add(weekDaysName[i]);
		}
	}
	
	public void setInfoPanel(){
		infoPanel = new JPanel();
		infoPanel.setBackground(bg);
		infoPanel.setLayout(new BorderLayout());
		infoClock = new JLabel("", 4);
		infoClock.setBackground(bg);
		infoClock.setForeground(Color.WHITE);
		infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		infoPanel.add(infoClock, "North");
		selectedDate = new JLabel("<Html><font size=3><font color=\"#FFFFFF\">" + (today.get(2) + 1) + "/" + today.get(5) + "/"
				+ today.get(1) + "&nbsp;</font></font></html>", 2);
		selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
	}
	
	
	
	public void setMemoPanel(CalendarWindow memo){
		memoPanel = new JPanel();
		memoPanel.setBackground(bg);
		memoPanel.setForeground(Color.WHITE);
		memoBorder = BorderFactory.createTitledBorder("Memo");
		memoBorder.setTitleColor(Color.WHITE);
		memoPanel.setBorder(memoBorder);
		memoSubPanel = new JPanel();
		memoSubPanel.setBackground(bg);
		
		buttonPanel = new JPanel();
		buttonGroup = new ButtonGroup();
		JRadioButton time_1 = new JRadioButton("09:00/09:30");
		JRadioButton time_2 = new JRadioButton("09:30/10:00");
		JRadioButton time_3 = new JRadioButton("10:00/10:30");
		JRadioButton time_4 = new JRadioButton("10:30/11:00");
		JRadioButton time_5 = new JRadioButton("11:00/11:30");
		JRadioButton time_6 = new JRadioButton("11:30/12:00");
		JRadioButton time_7 = new JRadioButton("12:00/12:30");
		JRadioButton time_8 = new JRadioButton("12:30/13:00");
		
		JRadioButton time_9 = new JRadioButton("15:00/15:30");
		JRadioButton time_10 = new JRadioButton("15:30/16.00");
		JRadioButton time_11 = new JRadioButton("16:00/16:30");
		JRadioButton time_12 = new JRadioButton("16:30/17:00");
		JRadioButton time_13 = new JRadioButton("17:00/17:30");
		JRadioButton time_14 = new JRadioButton("17:30/18:00");
		JRadioButton time_15 = new JRadioButton("18:00/18:30");
		JRadioButton time_16 = new JRadioButton("18:30/19:00");
		
		buttonGroup.add(time_1);
		buttonGroup.add(time_2);
		buttonGroup.add(time_3);
		buttonGroup.add(time_4);
		buttonGroup.add(time_5);
		buttonGroup.add(time_6);
		buttonGroup.add(time_7);
		buttonGroup.add(time_8);
		buttonGroup.add(time_9);
		buttonGroup.add(time_10);
		buttonGroup.add(time_11);
		buttonGroup.add(time_12);
		buttonGroup.add(time_13);
		buttonGroup.add(time_14);
		buttonGroup.add(time_15);
		buttonGroup.add(time_16);
		
		buttonPanel.add(time_1);
		buttonPanel.add(time_2);
		buttonPanel.add(time_3);
		buttonPanel.add(time_4);
		buttonPanel.add(time_5);
		buttonPanel.add(time_6);
		buttonPanel.add(time_7);
		buttonPanel.add(time_8);
		buttonPanel.add(time_9);
		buttonPanel.add(time_10);
		buttonPanel.add(time_11);
		buttonPanel.add(time_12);
		buttonPanel.add(time_13);
		buttonPanel.add(time_14);
		buttonPanel.add(time_15);
		buttonPanel.add(time_16);
		
		buttonPanel.setVisible(false);
		
		saveBut = new WebButton("Salva");
		saveBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
		           AbstractButton button = buttons.nextElement();

		            if (button.isSelected())
		            	control.saveInDB(button.getText());
		        }
				showCal();
			}
		});
		
		memoSubPanel.add(saveBut);
		
		backBut = new WebButton("Indietro");
		backBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.closeWindow();
				previousControl.openWindow();
			}
		});
		
		memoSubPanel.add(backBut);

		memoPanel.setLayout(new BorderLayout());
		memoPanel.add(selectedDate, "North");
		memoPanel.add(buttonPanel, "Center");
		memoPanel.add(memoSubPanel, "South");
	}
	
	
	public void setSubPanels(){
		bottomInfo.setForeground(Color.WHITE);
		bottomInfo.setBackground(bg);
		JPanel frameSubPanelWest = new JPanel();
		frameSubPanelWest.setBackground(bg);
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(calOpPanel, "North");
		frameSubPanelWest.add(calPanel, "Center");

		JPanel frameSubPanelEast = new JPanel();
		frameSubPanelEast.setBackground(bg);
		Dimension infoPanelSize = infoPanel.getPreferredSize();
		infoPanelSize.height = 65;
		infoPanel.setPreferredSize(infoPanelSize);
		frameSubPanelEast.setLayout(new BorderLayout());
		frameSubPanelEast.add(infoPanel, "North");
		frameSubPanelEast.add(memoPanel, "Center");

		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWestSize.width = 410;
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

		frameBottomPanel = new JPanel();
		frameBottomPanel.setBackground(bg);
		frameBottomPanel.add(bottomInfo);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(frameSubPanelWest, "West");
		mainFrame.add(frameSubPanelEast, "Center");
		mainFrame.add(frameBottomPanel, "South");
		mainFrame.setVisible(true);
	}
	
	public void setDateButtons(){
		for (int i = 0; i < CALENDAR_HEIGHT; i++) {
			for (int j = 0; j < CALENDAR_WIDTH; j++) {
				this.dateButs[i][j] = new WebButton();
				this.dateButs[i][j].setAnimate(true);
				this.dateButs[i][j].setBorderPainted(false);
				this.dateButs[i][j].setContentAreaFilled(false);
				this.dateButs[i][j].setBackground(Color.WHITE);
				this.dateButs[i][j].setOpaque(true);
				this.dateButs[i][j].setDrawLines(true, true, true, true);
				this.dateButs[i][j].addActionListener(this.lForDateButs);
				this.dateButs[i][j].setDrawSides(false, false, false, false);
				this.dateButs[i][j].setDrawShade(false);
				this.dateButs[i][j].setFont(loadFont("Oxygen-Regular.ttf", 12f));
				this.calPanel.add(this.dateButs[i][j]);
			}
		}
	}

	@Override
	public void closeFrame() {
		mainFrame.dispose();
	}
}
