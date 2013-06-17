package snookerTables;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WaitingList extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> waiting;
	private JList<String> list;
	private int selected=-1;
	
	public WaitingList(){
//		this.setPreferredSize(new Dimension(200, 200));
		
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(170,300));
		waiting = new ArrayList<String>();
		
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel selectionModel = list.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
//				int firstIndex = e.getFirstIndex();
//		        int lastIndex = e.getLastIndex();
//		        boolean isAdjusting = e.getValueIsAdjusting();
		        int minIndex = lsm.getMinSelectionIndex();
	            int maxIndex = lsm.getMaxSelectionIndex();
	            for (int i = minIndex; i <= maxIndex; i++) {
	                if (lsm.isSelectedIndex(i)) {
	                    selected = i;
	                }
	            }
				
			}
		});
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		
		JPanel bottomPanel = new JPanel();
		
		JButton add = new JButton("Add");
		add.setActionCommand("add");
		add.addActionListener(this);
		bottomPanel.add(add);
		
		JButton remove = new JButton("Remove");
		remove.setActionCommand("remove");
		remove.addActionListener(this);
		bottomPanel.add(remove);
		
		
		
//		JButton hide = new JButton("Hide");
//		hide.setActionCommand("hide");
//		hide.addActionListener(this);
//		bottomPanel.add(hide, BorderLayout.EAST);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listScroller, BorderLayout.CENTER);

	}
	
	public void addName(String name){
			waiting.add(waiting.size()+1+" - "+name);
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			for(int i=0; i<waiting.size(); i++){
				listModel.addElement(waiting.get(i));
			}
			list.setModel(listModel);
			validate();
	}

	public void removeName(int toRemove){
		if(waiting.size()<=toRemove || toRemove==-1){
		}else{
		waiting.remove(toRemove);
		for(int i=0; i<waiting.size(); i++){
			String selected = waiting.get(i);
			String[] split = selected.split("-", 2);
			waiting.set(i, i+1+" -"+split[1]);
		}
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i=0; i<waiting.size(); i++){
			listModel.addElement(waiting.get(i));
		}
		list.setModel(listModel);
		validate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("remove".equals(e.getActionCommand())){
			removeName(selected);
			selected=-1;
		}else if("add".equals(e.getActionCommand())){
			String toAdd = JOptionPane.showInputDialog("Name");
			if(!(toAdd==null)){
				addName(toAdd);
			}
		}
//		}else if("hide".equals(e.getActionCommand())){
//			this.setVisible(false);
//		}
		
		
	}
	
}
