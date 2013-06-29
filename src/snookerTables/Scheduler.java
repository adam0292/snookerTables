package snookerTables;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class Scheduler extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel container, main;
	private ArrayList<JCheckBox> tableCheckBoxList;
	private ArrayList<Table> snookerTables, poolTables;
	private ArrayList<TimeSlot> timeSlots;
	private JList<String> list;
	private int startTime;
	private int endTime;
	private JRadioButton full, half, free;
	private JSpinner startTimeSpinner, endTimeSpinner;
	
	public Scheduler(ArrayList<Table> snookerTables, ArrayList<Table> poolTables){
		this.setVisible(true);
		this.setSize(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		container = new JPanel(new BorderLayout());
		this.add(container);
		tableCheckBoxList = new ArrayList<JCheckBox>();
		
		timeSlots = new ArrayList<TimeSlot>();
		
		this.snookerTables=snookerTables;
		this.poolTables=poolTables;
		main = new JPanel();
		container.add(main, BorderLayout.CENTER);
		JButton add = new JButton("Add");
		add.setActionCommand("add");
		add.addActionListener(this);
		main.add(add);
		JPanel bottom = new JPanel();
		container.add(bottom, BorderLayout.SOUTH);
		createTablesCheckboxes();
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel selectionModel = list.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			}
		});
//		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		bottom.add(list);
		
		
		full = new JRadioButton("Full", true);
		half = new JRadioButton("Half");
		free = new JRadioButton("Free");
		JPanel east = new JPanel();
		container.add(east, BorderLayout.EAST);

		JPanel timerSpin = new JPanel(new GridLayout(0,1));
		east.add(timerSpin);
		
		startTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor);
		startTimeSpinner.setValue(new Date(0));

		timerSpin.add(startTimeSpinner);
		endTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor);
		endTimeSpinner.setValue(new Date(0));
		timerSpin.add(endTimeSpinner);

		startTimeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Date startDate = (Date)startTimeSpinner.getValue();
				Date endDate = (Date)endTimeSpinner.getValue();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(startDate);
				startTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
				startTime += calendar.get(Calendar.MINUTE);
				calendar.setTime(endDate);
				endTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
				endTime += calendar.get(Calendar.MINUTE);
				
				
			if(startTime>endTime){
				endTimeSpinner.setValue(startDate);
			}
				
			}
		});
		endTimeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Date startDate = (Date)startTimeSpinner.getValue();
				Date endDate = (Date)endTimeSpinner.getValue();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(startDate);
				startTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
				startTime += calendar.get(Calendar.MINUTE);
				calendar.setTime(endDate);
				endTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
				endTime += calendar.get(Calendar.MINUTE);
				
				
			if(startTime>endTime){
				startTimeSpinner.setValue(endDate);
			}
				
			}
			
		});
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(full);
		bg.add(half);
		bg.add(free);
		
		JPanel ratePanel = new JPanel();
		container.add(ratePanel, BorderLayout.NORTH);
		ratePanel.add(full);
		ratePanel.add(half);
		ratePanel.add(free);
		
		validate();
	}
	
	private void createTablesCheckboxes(){
		for(int i=0; i<Globals.NUMSNOOKERTABLES; i++){
		JCheckBox check = new JCheckBox("Table "+(i+1));
		tableCheckBoxList.add(check);
//		check.setActionCommand("table"+(i+1));
//		check.addActionListener(this);
		main.add(check);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("add".equals(e.getActionCommand())){
			for(int i=0; i<tableCheckBoxList.size(); i++){
				if(tableCheckBoxList.get(i).isSelected()){
					
					
					int rate;
					if(full.isSelected()){
						rate=Globals.FULL;
					}else if(half.isSelected()){
						rate=Globals.HALF;
					}else{
						rate=Globals.FREE;
					}
					Date startDate = (Date)startTimeSpinner.getValue();
					Date endDate = (Date)endTimeSpinner.getValue();
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(startDate);
					startTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
					startTime += calendar.get(Calendar.MINUTE);
					calendar.setTime(endDate);
					endTime = calendar.get(Calendar.HOUR_OF_DAY)*60;
					endTime += calendar.get(Calendar.MINUTE);
					TimeSlot time = new TimeSlot(startTime, endTime,rate, Globals.SNOOKER, snookerTables.get(i));
					timeSlots.add(time);
					displayTimes();
					tableCheckBoxList.get(i).setSelected(false);
				}
			}
		}
		
	}
	
	private void displayTimes(){
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i=0; i<timeSlots.size(); i++){
			listModel.addElement(timeSlots.get(i).toString());
		}
		list.setModel(listModel);
		validate();
	}

}
