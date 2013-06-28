package snookerTables;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
					Calendar start = GregorianCalendar.getInstance();
					Calendar end = GregorianCalendar.getInstance();
					start.set(0,0,0,6,0,0);
					end.set(0,0,0,7,0,0);
					TimeSlot time = new TimeSlot(start, end, Globals.SNOOKER, snookerTables.get(i));
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
