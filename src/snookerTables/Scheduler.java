package snookerTables;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class Scheduler extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel container, main, tableChecks;
	private ArrayList<JCheckBox> tableCheckBoxList, weekDayCheckBoxList;
	private ArrayList<Table> snookerTables, poolTables;
	private ArrayList<TimeSlot> timeSlots;
	private ArrayList<JPanel> weekPanels;
	private ArrayList<JTabbedPane> tablePanels;
	private JList<String>[][] lists;
	private ListSelectionModel[][] listsSelection;
	private int startTime;
	private int endTime, firstSelected, lastSelected;
	private JPanel[][] schecdPane;;
	private JRadioButton full, half, free;
	private JSpinner startTimeSpinner, endTimeSpinner;
	private JTabbedPane tableTab;
	private DefaultListModel<String>[][] dListModel;
	private BufferedReader br;
	private File file;

	public Scheduler(ArrayList<Table> snookerTables, ArrayList<Table> poolTables) {
		this.setVisible(true);
		this.setSize(700, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		container = new JPanel(new BorderLayout());
		this.add(container);
		tableCheckBoxList = new ArrayList<JCheckBox>();
		weekDayCheckBoxList = new ArrayList<JCheckBox>();
		tablePanels = new ArrayList<JTabbedPane>();
		timeSlots = new ArrayList<TimeSlot>();
		dListModel = new DefaultListModel[Globals.NUMSNOOKERTABLES][7];

		this.snookerTables = snookerTables;
		this.poolTables = poolTables;
		main = new JPanel(new BorderLayout());

		JPanel checkboxes = new JPanel(new GridLayout(0, 1));
		main.add(checkboxes, BorderLayout.CENTER);
		JPanel outermain = new JPanel(new GridLayout(0, 1));
		outermain.add(main);
		container.add(outermain, BorderLayout.CENTER);
		JButton add = new JButton("Add");
		add.setActionCommand("add");
		add.addActionListener(this);
		main.add(add, BorderLayout.SOUTH);

		JPanel eastPanel = new JPanel();
		main.add(eastPanel, BorderLayout.EAST);
		JButton remove = new JButton("Remove");
		eastPanel.add(remove);
		remove.setActionCommand("remove");
		remove.addActionListener(this);

		JPanel bottom = new JPanel();
		container.add(bottom, BorderLayout.SOUTH);
		tableChecks = new JPanel(new GridLayout(3, 0));
		createTablesCheckboxes();
		checkboxes.add(tableChecks, BorderLayout.CENTER);
		// list = new JList<String>();
		// list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// ListSelectionModel selectionModel = list.getSelectionModel();
		// selectionModel.addListSelectionListener(new ListSelectionListener() {
		//
		// @Override
		// public void valueChanged(ListSelectionEvent e) {
		// ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		// }
		// });
		// // list.setLayoutOrientation(JList.VERTICAL_WRAP);
		// list.setVisibleRowCount(-1);
		// bottom.add(list);

		full = new JRadioButton("Full", true);
		half = new JRadioButton("Half");
		free = new JRadioButton("Free");
		JPanel timePanel = new JPanel();
		container.add(timePanel, BorderLayout.EAST);

		JCheckBox monday = new JCheckBox("Monday");
		JCheckBox tuesday = new JCheckBox("Tuesday");
		JCheckBox wednesday = new JCheckBox("Wednesday");
		JCheckBox thursday = new JCheckBox("Thursday");
		JCheckBox friday = new JCheckBox("Friday");
		JCheckBox saturday = new JCheckBox("Saturday");
		JCheckBox sunday = new JCheckBox("Sunday");
		weekDayCheckBoxList.add(sunday);
		weekDayCheckBoxList.add(monday);
		weekDayCheckBoxList.add(tuesday);
		weekDayCheckBoxList.add(wednesday);
		weekDayCheckBoxList.add(thursday);
		weekDayCheckBoxList.add(friday);
		weekDayCheckBoxList.add(saturday);
		JPanel weekPanel = new JPanel(new GridLayout(0, 3));
		for (int i = 0; i < weekDayCheckBoxList.size(); i++) {
			weekPanel.add(weekDayCheckBoxList.get(i));
		}
		checkboxes.add(weekPanel, BorderLayout.SOUTH);

		JPanel timerSpin = new JPanel(new GridLayout(0, 1));
		timePanel.add(timerSpin);

		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(
				startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor);
		startTimeSpinner.setValue(new Date(0));

		timerSpin.add(startTimeSpinner);
		endTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(
				endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor);
		endTimeSpinner.setValue(new Date(60 * 60 * 1000));
		timerSpin.add(endTimeSpinner);

		startTimeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				Date startDate = (Date) startTimeSpinner.getValue();
				Date endDate = (Date) endTimeSpinner.getValue();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(startDate);
				startTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
				startTime += calendar.get(Calendar.MINUTE);
				calendar.setTime(endDate);
				endTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
				endTime += calendar.get(Calendar.MINUTE);

				if (startTime > endTime) {
					endTimeSpinner.setValue(startDate);
				}

			}
		});
		endTimeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				Date startDate = (Date) startTimeSpinner.getValue();
				Date endDate = (Date) endTimeSpinner.getValue();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(startDate);
				startTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
				startTime += calendar.get(Calendar.MINUTE);
				calendar.setTime(endDate);
				endTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
				endTime += calendar.get(Calendar.MINUTE);

				if (startTime > endTime) {
					startTimeSpinner.setValue(endDate);
				}

			}

		});

		ButtonGroup bgRate = new ButtonGroup();
		bgRate.add(full);
		bgRate.add(half);
		bgRate.add(free);

		JPanel ratePanel = new JPanel();
		container.add(ratePanel, BorderLayout.NORTH);
		ratePanel.add(full);
		ratePanel.add(half);
		ratePanel.add(free);

		tableTab = new JTabbedPane();
		createTableTabs();
		outermain.add(tableTab);

		CheckScheduler checker = new CheckScheduler(this);
		new Thread(checker).start();

		try {

			file = new File("scheduler.config");
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileReader fr = new FileReader(file.getAbsoluteFile());
			br = new BufferedReader(fr);
			loadScheduler();
			
			

		} catch (IOException ex) {

		}


		validate();
	}

	private boolean checkOverlap(TimeSlot newSlot) {
		boolean[] newWeekday = newSlot.getDayOfWeek();
		int newStartTime = newSlot.getStart();
		int newEndTime = newSlot.getEnd();
		if (newStartTime == newEndTime) {
			return false;
		}
		for (int i = 0; i < newWeekday.length; i++) {
			for (int j = 0; j < timeSlots.size(); j++) {
				if (newSlot.getDayOfWeek()[i] == true
						&& timeSlots.get(j).getDayOfWeek()[i] == true
						&& (newSlot.getTable().equals(timeSlots.get(j)
								.getTable()))) {
					if (newStartTime >= timeSlots.get(j).getStart()) {
						if (timeSlots.get(j).getEnd() > newStartTime) {
							return false;
						}
					}
					if (newEndTime <= timeSlots.get(j).getEnd()) {
						if (timeSlots.get(j).getStart() < newEndTime) {
							return false;
						}
					}
					if (newStartTime < timeSlots.get(j).getStart()
							&& newEndTime > timeSlots.get(j).getEnd()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void createTableTabs() {
		schecdPane = new JPanel[Globals.NUMSNOOKERTABLES][7];
		lists = new JList[Globals.NUMSNOOKERTABLES][7];
		listsSelection = new ListSelectionModel[Globals.NUMSNOOKERTABLES][7];
		for (int i = 0; i < Globals.NUMSNOOKERTABLES; i++) {
			JTabbedPane table = new JTabbedPane();
			tablePanels.add(table);
			tableTab.add("Table " + (i + 1), table);
		}
		for (int i = 0; i < tablePanels.size(); i++) {
			for (int j = 0; j < 7; j++) {
				schecdPane[i][j] = new JPanel();

				JList<String> list = new JList<String>();
				list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				ListSelectionModel selectionModel = list.getSelectionModel();
				listsSelection[i][j] = selectionModel;
				selectionModel
						.addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								ListSelectionModel lsm = (ListSelectionModel) e
										.getSource();
								firstSelected = e.getFirstIndex();
								lastSelected = e.getLastIndex();
							}
						});

				// list.setLayoutOrientation(JList.VERTICAL_WRAP);
				list.setVisibleRowCount(-1);
				lists[i][j] = list;
				schecdPane[i][j].add(list);
			}
		}
		for (int i = 0; i < Globals.NUMSNOOKERTABLES; i++) {
			for (int j = 0; j < 7; j++) {
				tablePanels.get(i).add(Globals.getDayName(j), schecdPane[i][j]);
			}
		}

	}

	private void createTablesCheckboxes() {
		for (int i = 0; i < Globals.NUMSNOOKERTABLES; i++) {
			JCheckBox check = new JCheckBox("Table " + (i + 1));
			tableCheckBoxList.add(check);
			// check.setActionCommand("table"+(i+1));
			// check.addActionListener(this);
			tableChecks.add(check);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("add".equals(e.getActionCommand())) {
			addEvent();
		} else if ("remove".equals(e.getActionCommand())) {
			removeEvent();
		}
		// for(int j=0; j<7; j++){
		// weekDayCheckBoxList.get(j).setSelected(false);
		// }

	}

	private void addEvent() {
		boolean tableSelected = false;
		boolean daySelected = false;
		for (int i = 0; i < tableCheckBoxList.size(); i++) {
			if (tableCheckBoxList.get(i).isSelected()) {
				tableSelected = true;
				boolean[] weekDays = new boolean[7];
				for (int j = 0; j < 7; j++) {
					if (weekDayCheckBoxList.get(j).isSelected()) {
						daySelected = true;
					}
					weekDays[j] = weekDayCheckBoxList.get(j).isSelected();

				}
				if (daySelected) {
					int rate;
					if (full.isSelected()) {
						rate = Globals.FULL;
					} else if (half.isSelected()) {
						rate = Globals.HALF;
					} else {
						rate = Globals.FREE;
					}
					Date startDate = (Date) startTimeSpinner.getValue();
					Date endDate = (Date) endTimeSpinner.getValue();
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(startDate);
					startTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
					startTime += calendar.get(Calendar.MINUTE);
					calendar.setTime(endDate);
					endTime = calendar.get(Calendar.HOUR_OF_DAY) * 60;
					endTime += calendar.get(Calendar.MINUTE);

					calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					TimeSlot time = new TimeSlot(Globals.ID, startTime,
							endTime, weekDays, rate, Globals.SNOOKER,
							snookerTables.get(i));
					if (checkOverlap(time)) {
						timeSlots.add(time);
						writeConfig();
						displayTimes();
					} else {
						JOptionPane.showMessageDialog(this, "Overlap Error");
						break;
					}
					// tableCheckBoxList.get(i).setSelected(false);

				} else {
					JOptionPane.showMessageDialog(this,
							"Choose at least one day");
				}

			}
		}
		if (!tableSelected) {
			JOptionPane.showMessageDialog(this, "Choose at least one table");
		}
	}

	private void writeConfig() {
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < timeSlots.size(); i++) {
			try {
				bw.write("//EVENT START\n");
				bw.write(timeSlots.get(i).getID() + "\n");
				bw.write(timeSlots.get(i).getStart() + "\n");
				bw.write(timeSlots.get(i).getEnd() + "\n");
				for (int j = 0; j < timeSlots.get(i).getDayOfWeek().length; j++) {
					if (timeSlots.get(i).getDayOfWeek()[j]) {
						bw.write(j + "-");
					}
				}
				bw.write("\n" + timeSlots.get(i).getRate() + "\n");
				bw.write(Globals.SNOOKER + "\n");
				bw.write(timeSlots.get(i).getTable().getTableNumber() + "\n");
				bw.write("//EVENT END\n");
				bw.flush();
			} catch (IOException ex) {

			}
			
		}	
		fw.close();
		bw.close();
		} catch (IOException e) {
		}
		
	}

	private void removeEvent() {
		int table = -1;
		int day = -1;
		for (int i = 0; i < Globals.NUMSNOOKERTABLES; i++) {

			if (tablePanels.get(i).isVisible()) {
				for (int j = 0; j < 7; j++) {
					if (schecdPane[i][j].isVisible()) {
						table = 0;
						day = j;
					}
				}
			}
		}
		if (lists[table][day].getSelectedIndex() != -1) {
			int[] selected = lists[table][day].getSelectedIndices();
			for (int i = selected.length - 1; i >= 0; i--) {
				for (int j = 0; j < timeSlots.size(); j++) {
					if (dListModel[table][day].get(i).equals(
							timeSlots.get(j).toString())) {
						timeSlots.remove(j);
						writeConfig();
					}
				}

				dListModel[table][day].remove(selected[i]);
			}
			lists[table][day].setModel(dListModel[table][day]);
		}
	}

	private void displayTimes() {

		for (int i = 0; i < tablePanels.size(); i++) {

			for (int k = 0; k < 7; k++) {
				dListModel[i][k] = new DefaultListModel<String>();
				for (int j = 0; j < timeSlots.size(); j++) {
					if (timeSlots.get(j).getTable()
							.equals(snookerTables.get(i))) {
						if (timeSlots.get(j).getDayOfWeek()[k]) {
							dListModel[i][k].addElement(timeSlots.get(j)
									.toString());
						}
					}
				}
				lists[i][k].setModel(dListModel[i][k]);
				// System.out.println(lists[i][k].getSelectedIndex());
			}

		}
		// DefaultListModel<String> listModel = new DefaultListModel<String>();
		// listModel.addElement("Test");
		// lists.get(0).setModel(listModel);
		validate();
	}

	public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void loadScheduler() {
		try {
			
			while (br.readLine() != null) {
				int id = Integer.parseInt(br.readLine());
				int startT = Integer.parseInt(br.readLine());
				int endT = Integer.parseInt(br.readLine());
				String[] weekDS = br.readLine().split("-");
				boolean[] weekD = new boolean[7];
				for (int i = 0; i < weekDS.length; i++) {
					weekD[Integer.parseInt(weekDS[i])] = true;
				}
				int rate = Integer.parseInt(br.readLine());
				int type = Integer.parseInt(br.readLine());
				int tableNum = Integer.parseInt(br.readLine()) - 1;
				br.readLine();
				TimeSlot time = new TimeSlot(id, startT, endT, weekD, rate,
						type, snookerTables.get(tableNum));
				timeSlots.add(time);
				displayTimes();
			}
		} catch (IOException ex) {

		}
	}

}
