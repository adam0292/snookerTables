package snookerTables;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JPanel container, main, tableChecks, cardPanel;
	private ArrayList<JCheckBox> tableCheckBoxList, weekDayCheckBoxList;
	private ArrayList<Table> tables;
	private JButton next, previous;
	private JFrame addFrame;
	private JCheckBox selectAllTables, selectAllWeek;
	private boolean openAddSch = false;

	public ArrayList<Table> getTables() {
		return tables;
	}

	// public ArrayList<Table> getPoolTables() {
	// return poolTables;
	// }

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
	private ArrayList<JCheckBox> weekDayBoxList;
	private JComboBox<String> startMinCombo, startHourCombo, endMinCombo,
			endHourCombo;
	private JPanel rateBar, weekDays, comboPanel, addFrameContainer;
	private int eventFramePos;
	private JLabel addLabel;

	public Scheduler(ArrayList<Table> snookerTables, ArrayList<Table> poolTables) {
		this.setVisible(true);
		this.setSize(700, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		container = new JPanel(new BorderLayout());
		this.add(container);


		tablePanels = new ArrayList<JTabbedPane>();
		timeSlots = new ArrayList<TimeSlot>();
		dListModel = new DefaultListModel[10][7];

		tables = poolTables;
		for (int i = 0; i < snookerTables.size(); i++) {
			tables.add(snookerTables.get(i));
		}
		main = new JPanel(new BorderLayout());

		// JPanel checkboxes = new JPanel(new GridLayout(0, 1));
		// main.add(checkboxes, BorderLayout.CENTER);
		container.add(main, BorderLayout.CENTER);
		JButton add = new JButton("Add");
		add.setActionCommand("add");
		add.addActionListener(this);
		main.add(add, BorderLayout.SOUTH);

		JPanel eastPanel = new JPanel(new GridLayout(8, 1));
		main.add(eastPanel, BorderLayout.EAST);
		JButton remove = new JButton("Remove");
		eastPanel.add(remove);
		remove.setActionCommand("remove");
		remove.addActionListener(this);

		JButton clearTable = new JButton("Clear Table");
		eastPanel.add(clearTable);
		clearTable.setActionCommand("clearTable");
		clearTable.addActionListener(this);

		JPanel bottom = new JPanel();
		container.add(bottom, BorderLayout.SOUTH);

		// createTablesCheckboxes();
		// checkboxes.add(tableChecks, BorderLayout.CENTER);

		// full = new JRadioButton("Full");
		// half = new JRadioButton("Half", true);
		// free = new JRadioButton("Free");
		// JPanel timePanel = new JPanel();
		// container.add(timePanel, BorderLayout.EAST);

		// JCheckBox monday = new JCheckBox("Monday");
		// JCheckBox tuesday = new JCheckBox("Tuesday");
		// JCheckBox wednesday = new JCheckBox("Wednesday");
		// JCheckBox thursday = new JCheckBox("Thursday");
		// JCheckBox friday = new JCheckBox("Friday");
		// JCheckBox saturday = new JCheckBox("Saturday");
		// JCheckBox sunday = new JCheckBox("Sunday");
		// weekDayCheckBoxList.add(sunday);
		// weekDayCheckBoxList.add(monday);
		// weekDayCheckBoxList.add(tuesday);
		// weekDayCheckBoxList.add(wednesday);
		// weekDayCheckBoxList.add(thursday);
		// weekDayCheckBoxList.add(friday);
		// weekDayCheckBoxList.add(saturday);
		// JPanel weekPanel = new JPanel(new GridLayout(0, 3));
		// for (int i = 0; i < weekDayCheckBoxList.size(); i++) {
		// weekPanel.add(weekDayCheckBoxList.get(i));
		// }
		// checkboxes.add(weekPanel, BorderLayout.SOUTH);

		// JPanel comboPanel = new JPanel(new GridLayout(0,2));
		// timePanel.add(comboPanel);

		// DefaultComboBoxModel<String> startHourModel = new
		// DefaultComboBoxModel<>();
		// for(int i=0; i<10; i++){
		// startHourModel.addElement("0"+i);
		// }
		// for(int i=10; i<24; i++){
		// startHourModel.addElement(""+i);
		// }
		// startHourCombo = new JComboBox<String>(startHourModel);
		// startHourCombo.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// int startHour =
		// Integer.parseInt((String)startHourCombo.getSelectedItem());
		// int endHour =
		// Integer.parseInt((String)endHourCombo.getSelectedItem());
		// int startMin =
		// Integer.parseInt((String)startMinCombo.getSelectedItem());
		// int endMin = Integer.parseInt((String)endMinCombo.getSelectedItem());
		// if(startHour>endHour){
		// endHourCombo.setSelectedItem(startHourCombo.getSelectedItem());
		// }else if(startHour==endHour){
		// if(startMin>endMin){
		// endMinCombo.setSelectedItem(startMinCombo.getSelectedItem());
		// }
		// }
		//
		// }
		// });
		// comboPanel.add(startHourCombo);
		// DefaultComboBoxModel<String> startMimModel = new
		// DefaultComboBoxModel<>();
		// startMimModel.addElement("00");
		// startMimModel.addElement("15");
		// startMimModel.addElement("30");
		// startMimModel.addElement("45");
		// startMinCombo = new JComboBox<String>(startMimModel);
		// startMinCombo.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// int startHour =
		// Integer.parseInt((String)startHourCombo.getSelectedItem());
		// int endHour =
		// Integer.parseInt((String)endHourCombo.getSelectedItem());
		// int startMin =
		// Integer.parseInt((String)startMinCombo.getSelectedItem());
		// int endMin = Integer.parseInt((String)endMinCombo.getSelectedItem());
		// if(startHour==endHour){
		//
		// if(startMin>endMin){
		// endMinCombo.setSelectedItem(startMinCombo.getSelectedItem());
		// }
		// }
		//
		// }
		// });
		// comboPanel.add(startMinCombo);
		//
		// DefaultComboBoxModel<String> endHourModel = new
		// DefaultComboBoxModel<>();
		// for(int i=0; i<10; i++){
		// endHourModel.addElement("0"+i);
		// }
		// for(int i=10; i<24; i++){
		// endHourModel.addElement(""+i);
		// }
		// endHourCombo = new JComboBox<String>(endHourModel);
		// endHourCombo.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// int startHour =
		// Integer.parseInt((String)startHourCombo.getSelectedItem());
		// int endHour =
		// Integer.parseInt((String)endHourCombo.getSelectedItem());
		// int startMin =
		// Integer.parseInt((String)startMinCombo.getSelectedItem());
		// int endMin = Integer.parseInt((String)endMinCombo.getSelectedItem());
		// if(startHour>endHour){
		// startHourCombo.setSelectedItem(endHourCombo.getSelectedItem());
		// }else if(startHour==endHour){
		// if(startMin>endMin){
		// startMinCombo.setSelectedItem(endMinCombo.getSelectedItem());
		// }
		// }
		//
		// }
		// });
		// comboPanel.add(endHourCombo);
		//
		//
		// DefaultComboBoxModel<String> endMimModel = new
		// DefaultComboBoxModel<>();
		// endMimModel.addElement("00");
		// endMimModel.addElement("15");
		// endMimModel.addElement("30");
		// endMimModel.addElement("45");
		// endMinCombo = new JComboBox<String>(endMimModel);
		// endMinCombo.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// int startHour =
		// Integer.parseInt((String)startHourCombo.getSelectedItem());
		// int endHour =
		// Integer.parseInt((String)endHourCombo.getSelectedItem());
		// int startMin =
		// Integer.parseInt((String)startMinCombo.getSelectedItem());
		// int endMin = Integer.parseInt((String)endMinCombo.getSelectedItem());
		// if(startHour==endHour){
		// if(startMin>endMin){
		// startMinCombo.setSelectedItem(endMinCombo.getSelectedItem());
		// }
		// }
		//
		// }
		// });
		// comboPanel.add(endMinCombo);

		// ButtonGroup bgRate = new ButtonGroup();
		// bgRate.add(full);
		// bgRate.add(half);
		// bgRate.add(free);

		// JPanel ratePanel = new JPanel();
		// container.add(ratePanel, BorderLayout.NORTH);
		// ratePanel.add(full);
		// ratePanel.add(half);
		// ratePanel.add(free);

		tableTab = new JTabbedPane();
		createTableTabs();
		main.add(tableTab, BorderLayout.CENTER);

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
		schecdPane = new JPanel[10][7];
		lists = new JList[10][7];
		listsSelection = new ListSelectionModel[10][7];
		for (int i = 0; i < 10; i++) {
			JTabbedPane table = new JTabbedPane();
			tablePanels.add(table);
			tableTab.add("Table " + (i), table);
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
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				tablePanels.get(i).add(Globals.getDayName(j), schecdPane[i][j]);
			}
		}

	}

	private void createTablesCheckboxes() {
		tableChecks = new JPanel(new GridLayout(3, 0));
		tableCheckBoxList = new ArrayList<JCheckBox>();
		selectAllTables = new JCheckBox("Select All");
		selectAllTables.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectAllTables.isSelected()) {
					for (int i = 0; i < tableCheckBoxList.size(); i++) {
						tableCheckBoxList.get(i).setSelected(true);
					}
				} else {
					for (int i = 0; i < tableCheckBoxList.size(); i++) {
						tableCheckBoxList.get(i).setSelected(false);
					}
				}

			}
		});

		tableChecks.add(selectAllTables);
		for (int i = 0; i < 10; i++) {
			JCheckBox check = new JCheckBox("Table " + (i));
			check.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (selectAllTables.isSelected()) {
						selectAllTables.setSelected(false);
					}
				}
			});
			tableCheckBoxList.add(check);
			tableChecks.add(check);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("add".equals(e.getActionCommand())) {
			if (!openAddSch) {
				addEventFrame();
			} else {
				addFrame.setVisible(true);
			}
		} else if ("remove".equals(e.getActionCommand())) {
			removeEvent();
		} else if ("clearTable".equals(e.getActionCommand())) {

			for (int i = 0; i < 10; i++) {
				if (tablePanels.get(i).isVisible()) {
					if (JOptionPane.showConfirmDialog(this,
							"Clear all schedule data for Table " + i + "?",
							"Reset Table " + i, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						for (int j = 0; j < timeSlots.size(); j++) {
							if (timeSlots.get(j).getTable().getTableNumber() == i) {
								timeSlots.remove(j);
								writeConfig();
								for (int k = 0; k < 7; k++) {
									dListModel[i][k].clear();
									lists[i][k].setModel(dListModel[i][k]);
								}
							}
						}
					}
				}
			}
		}

	}

	private boolean addEvent() {
		boolean tableSelected = false;
		boolean daySelected = false;
		ArrayList<TimeSlot> toAdd = new ArrayList<TimeSlot>();
		boolean fail = false;
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
					startTime = Integer.parseInt((String) startHourCombo
							.getSelectedItem()) * 60;
					startTime += Integer.parseInt((String) startMinCombo
							.getSelectedItem());
					endTime = Integer.parseInt((String) endHourCombo
							.getSelectedItem()) * 60;
					endTime += Integer.parseInt((String) endMinCombo
							.getSelectedItem());
					TimeSlot time = new TimeSlot(Globals.ID, startTime,
							endTime, weekDays, rate, Globals.SNOOKER,
							tables.get(i));
					if (checkOverlap(time)) {
						toAdd.add(time);

					} else {
						JOptionPane.showMessageDialog(this, "Overlap Error");
						fail = true;
						break;
					}
					// tableCheckBoxList.get(i).setSelected(false);

				} else {
					JOptionPane.showMessageDialog(this,
							"Choose at least one day");
					fail = true;
				}

			}
		}
		if (!tableSelected) {
			JOptionPane.showMessageDialog(this, "Choose at least one table");
			fail = true;
		}
		if (!fail) {
			for (int i = 0; i < toAdd.size(); i++) {
				timeSlots.add(toAdd.get(i));
			}
			writeConfig();
			displayTimes();
			return true;
		}

		return false;
	}

	private void addEventFrame() {
		openAddSch = true;
		addFrame = new JFrame();
		addFrame.setSize(400, 200);
		eventFramePos = 0;
		addFrame.setVisible(true);
		addFrameContainer = new JPanel(new BorderLayout());
		addFrame.add(addFrameContainer);
		cardPanel = new JPanel(new CardLayout());
		addFrameContainer.add(cardPanel, BorderLayout.CENTER);

		addLabel = new JLabel("Select Table(s)");
		addFrameContainer.add(addLabel, BorderLayout.NORTH);
		JPanel bottomButton = new JPanel();
		addFrameContainer.add(bottomButton, BorderLayout.SOUTH);
		previous = new JButton("Back");
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventFramePos--;
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.previous(cardPanel);
				if (eventFramePos == 0) {
					previous.setEnabled(false);
				}
				if (eventFramePos == 2) {
					next.setText("Next");
				}

			}
		});
		bottomButton.add(previous);
		previous.setEnabled(false);

		next = new JButton("Next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (eventFramePos < 4) {
					eventFramePos++;
				}
				if (eventFramePos == 4) {
					eventFramePos--;
					if (addEvent()) {
						addFrame.dispose();
						openAddSch = false;
					}
				} else {
					CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
					cardLayout.next(cardPanel);
					previous.setEnabled(true);
					if (eventFramePos == 3) {
						next.setText("Finish");
					}
				}
			}
		});
		bottomButton.add(next);

		rateBar = new JPanel();
		full = new JRadioButton("Full");
		half = new JRadioButton("Half", true);
		free = new JRadioButton("Free");
		// rateBar.add(full);
		rateBar.add(half);
		rateBar.add(free);
		ButtonGroup bgRate = new ButtonGroup();
		bgRate.add(full);
		bgRate.add(half);
		bgRate.add(free);

		createTablesCheckboxes();
		// addFrameContainer.add(tableChecks, BorderLayout.CENTER);

		weekDayCheckBoxList = new ArrayList<JCheckBox>();
		weekDayBoxList = new ArrayList<JCheckBox>();
		selectAllWeek = new JCheckBox("Select All");
		weekDayCheckBoxList.add(selectAllWeek);
		selectAllWeek.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectAllWeek.isSelected()) {
					for (int i = 0; i < weekDayBoxList.size(); i++) {
						weekDayBoxList.get(i).setSelected(true);
					}
				} else {
					for (int i = 0; i < weekDayBoxList.size(); i++) {
						weekDayBoxList.get(i).setSelected(false);
					}
				}

			}
		});
		JCheckBox monday = new JCheckBox("Monday");
		weekDayBoxList.add(monday);
		JCheckBox tuesday = new JCheckBox("Tuesday");
		weekDayBoxList.add(tuesday);
		JCheckBox wednesday = new JCheckBox("Wednesday");
		weekDayBoxList.add(wednesday);
		JCheckBox thursday = new JCheckBox("Thursday");
		weekDayBoxList.add(thursday);
		JCheckBox friday = new JCheckBox("Friday");
		weekDayBoxList.add(friday);
		JCheckBox saturday = new JCheckBox("Saturday");
		weekDayBoxList.add(saturday);
		JCheckBox sunday = new JCheckBox("Sunday");
		weekDayBoxList.add(sunday);
		weekDayCheckBoxList.add(sunday);
		weekDayCheckBoxList.add(monday);
		weekDayCheckBoxList.add(tuesday);
		weekDayCheckBoxList.add(wednesday);
		weekDayCheckBoxList.add(thursday);
		weekDayCheckBoxList.add(friday);
		weekDayCheckBoxList.add(saturday);
		for (int i = 0; i < weekDayBoxList.size(); i++) {
			weekDayBoxList.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (selectAllWeek.isSelected()) {
						selectAllWeek.setSelected(false);
					}

				}
			});
		}
		weekDays = new JPanel();
		for (int i = 0; i < weekDayCheckBoxList.size(); i++) {
			weekDays.add(weekDayCheckBoxList.get(i));
		}

		comboPanel = new JPanel(new GridLayout(2, 3));
		DefaultComboBoxModel<String> startHourModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < 10; i++) {
			startHourModel.addElement("0" + i);
		}
		for (int i = 10; i < 24; i++) {
			startHourModel.addElement("" + i);
		}
		startHourCombo = new JComboBox<String>(startHourModel);
		startHourCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int startHour = Integer.parseInt((String) startHourCombo
						.getSelectedItem());
				int endHour = Integer.parseInt((String) endHourCombo
						.getSelectedItem());
				int startMin = Integer.parseInt((String) startMinCombo
						.getSelectedItem());
				int endMin = Integer.parseInt((String) endMinCombo
						.getSelectedItem());
				if (startHour > endHour) {
					endHourCombo.setSelectedItem(startHourCombo
							.getSelectedItem());
				} else if (startHour == endHour) {
					if (startMin > endMin) {
						endMinCombo.setSelectedItem(startMinCombo
								.getSelectedItem());
					}
				}

			}
		});
		JLabel startTimeLabel = new JLabel("Start Time:");
		comboPanel.add(startTimeLabel);
		comboPanel.add(startHourCombo);
		DefaultComboBoxModel<String> startMimModel = new DefaultComboBoxModel<>();
		startMimModel.addElement("00");
		startMimModel.addElement("15");
		startMimModel.addElement("30");
		startMimModel.addElement("45");
		startMinCombo = new JComboBox<String>(startMimModel);
		startMinCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int startHour = Integer.parseInt((String) startHourCombo
						.getSelectedItem());
				int endHour = Integer.parseInt((String) endHourCombo
						.getSelectedItem());
				int startMin = Integer.parseInt((String) startMinCombo
						.getSelectedItem());
				int endMin = Integer.parseInt((String) endMinCombo
						.getSelectedItem());
				if (startHour == endHour) {

					if (startMin > endMin) {
						endMinCombo.setSelectedItem(startMinCombo
								.getSelectedItem());
					}
				}

			}
		});
		comboPanel.add(startMinCombo);

		DefaultComboBoxModel<String> endHourModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < 10; i++) {
			endHourModel.addElement("0" + i);
		}
		for (int i = 10; i < 24; i++) {
			endHourModel.addElement("" + i);
		}
		endHourCombo = new JComboBox<String>(endHourModel);
		endHourCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int startHour = Integer.parseInt((String) startHourCombo
						.getSelectedItem());
				int endHour = Integer.parseInt((String) endHourCombo
						.getSelectedItem());
				int startMin = Integer.parseInt((String) startMinCombo
						.getSelectedItem());
				int endMin = Integer.parseInt((String) endMinCombo
						.getSelectedItem());
				if (startHour > endHour) {
					startHourCombo.setSelectedItem(endHourCombo
							.getSelectedItem());
				} else if (startHour == endHour) {
					if (startMin > endMin) {
						startMinCombo.setSelectedItem(endMinCombo
								.getSelectedItem());
					}
				}

			}
		});
		JLabel endHourLabel = new JLabel("End Time:");
		comboPanel.add(endHourLabel);
		comboPanel.add(endHourCombo);

		DefaultComboBoxModel<String> endMimModel = new DefaultComboBoxModel<>();
		endMimModel.addElement("00");
		endMimModel.addElement("15");
		endMimModel.addElement("30");
		endMimModel.addElement("45");
		endMinCombo = new JComboBox<String>(endMimModel);
		endMinCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int startHour = Integer.parseInt((String) startHourCombo
						.getSelectedItem());
				int endHour = Integer.parseInt((String) endHourCombo
						.getSelectedItem());
				int startMin = Integer.parseInt((String) startMinCombo
						.getSelectedItem());
				int endMin = Integer.parseInt((String) endMinCombo
						.getSelectedItem());
				if (startHour == endHour) {
					if (startMin > endMin) {
						startMinCombo.setSelectedItem(endMinCombo
								.getSelectedItem());
					}
				}

			}
		});
		comboPanel.add(endMinCombo);

		cardPanel.add(tableChecks);
		cardPanel.add(weekDays);
		cardPanel.add(rateBar);
		cardPanel.add(comboPanel);

		// addFrame.pack();
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
					bw.write(timeSlots.get(i).getTable().getTableNumber()
							+ "\n");
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
		for (int i = 0; i < 10; i++) {

			if (tablePanels.get(i).isVisible()) {
				for (int j = 0; j < 7; j++) {
					if (schecdPane[i][j].isVisible()) {

						table = i;
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
						timeSlots.get(j).getDayOfWeek()[day] = false;
						// timeSlots.remove(j);
						boolean tester = false;
						for (int k = 0; k < 7; k++) {
							if (timeSlots.get(j).getDayOfWeek()[k]) {
								tester = true;
							}
						}
						if (!tester) {
							timeSlots.remove(j);
						}
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
					if (timeSlots.get(j).getTable().equals(tables.get(i))) {
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
				int tableNum = Integer.parseInt(br.readLine());
				br.readLine();
				TimeSlot time = new TimeSlot(id, startT, endT, weekD, rate,
						type, tables.get(tableNum));
				timeSlots.add(time);
				displayTimes();
			}
		} catch (IOException ex) {

		}
	}

}
