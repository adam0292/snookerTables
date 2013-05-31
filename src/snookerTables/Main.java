package snookerTables;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel tables, container;
	private JScrollPane scroller;
	private int numberOfTables = 6;
	private ArrayList<Table> tableList;
	public Main(){
		tableList = new ArrayList<Table>();
		container = new JPanel(new BorderLayout());
        add(container);
		Color color = new Color(56,142,142);
		this.setVisible(true);
		this.setSize(900, 800);
		this.setLayout(new FlowLayout());
		this.getContentPane().setBackground(color);
		JPanel topBar = new JPanel();
		topBar.setBackground(color);
		JLabel name = new JLabel("Tables");
		name.setFont(new Font(null, Font.BOLD, 30));
		topBar.add(name);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		container.add(topBar, BorderLayout.NORTH);
		tables = new JPanel();		
		scroller = new JScrollPane(tables);  
		container.add(scroller, BorderLayout.CENTER); 
		tables.setBackground(color);
//		for(int i=1; i<=numberOfTables; i++){
//			Table table = new Table("Table "+i);
//			tables.add(table);
//		}
		addTables();
		int numberPerRow = (this.getWidth()-100)/260;
		Double number = (double)numberOfTables / (double)numberPerRow;
		int numberPerColumn = (int)Math.ceil(number);
		scroller.setPreferredSize(new Dimension(this.getWidth()-40, this.getHeight()-120));
		tables.setPreferredSize(new Dimension(this.getWidth()-100, numberPerColumn*310));
		
		JButton settings = new JButton("Settings");
		settings.setActionCommand("settings");
		settings.addActionListener(this);
		container.add(settings, BorderLayout.SOUTH);
		
		
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent comp){
				resizeTables();				
			}
		});
		
		
		
		
	}
	
	public void addTables(){
		tables.removeAll();
		for(int i=1; i<=numberOfTables; i++){
			Table table = new Table("Table "+i);
			tables.add(table);
			tableList.add(table);
		}
	}
	
	public void setAllPrices(double price){
		for(int i=0; i<tableList.size(); i++){
			tableList.get(i).setPriceConstant(price);
		}
		
	}
	public int getNumberOfTables(){
		return numberOfTables;
	}
	
	public void newTables(int number){
		numberOfTables=number;
		addTables();
		resizeTables();
	}
	
	private void updateUI(){
		SwingUtilities.updateComponentTreeUI(tables);
		SwingUtilities.updateComponentTreeUI(scroller);
		SwingUtilities.updateComponentTreeUI(container);
	}

	
	public void resizeTables(){
		scroller.setPreferredSize(new Dimension(this.getWidth()-40, this.getHeight()-120));
		int numberPerRow = (this.getWidth()-40)/260;
		Double number = (double)numberOfTables / (double)numberPerRow;
		int numberPerColumn = (int)Math.ceil(number);
//		System.out.println(numberPerRow);
		tables.setPreferredSize(new Dimension(this.getWidth()-100, numberPerColumn*310));
		updateUI();
	}
	
	public static void main(String[] args){
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("settings".equals(e.getActionCommand())){
			new Settings(this);
		}
		
	}
}
