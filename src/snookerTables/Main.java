package snookerTables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel tables, container, central, waitingList, bottomBar, rightCenter, detailPanel;
	private JScrollPane scroll;
	private JLabel subTitle, clock;
	private int numberOfTables = 0, active, detailShow;
	private ArrayList<Table> tableList;
	private JButton tableReturn, settings;
	private Table showingOrder;
	private Color color;
	private GridLayout detailGridLay;
	private final int DETAILWIDTH=3;

	public Main() {
		tableList = new ArrayList<Table>();
		container = new JPanel(new BorderLayout());
//		add(container);
		color = new Color(56, 142, 142);
		this.setVisible(true);
//		this.setSize(900, 700);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(color);
		
		
		try{ 
//			   UIManager.setLookAndFeel(
//			        UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			}
			catch(Exception e){
			 e.printStackTrace();
			}
		
		JPanel topPanel = new JPanel(new BorderLayout());
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		
		JMenuItem settingsMenu = new JMenuItem("Settings");
		settingsMenu.setActionCommand("settings");
		settingsMenu.addActionListener(this);
		file.add(settingsMenu);
		JMenuItem waitingListMenu = new JMenuItem("Waiting List");
		waitingListMenu.setActionCommand("waiting");
		waitingListMenu.addActionListener(this);
		file.add(waitingListMenu);
		
		menu.add(file);
		topPanel.add(menu, BorderLayout.NORTH);
		JPanel topBar = new JPanel(new BorderLayout());
		topPanel.add(topBar, BorderLayout.CENTER);
		topBar.setBackground(color);
		JLabel name = new JLabel("Snooker Club", JLabel.CENTER);
		name.setFont(new Font(null, Font.BOLD, 30));
		name.setForeground(Color.YELLOW);
		topBar.add(name, BorderLayout.CENTER);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(topPanel, BorderLayout.NORTH);
//		container.setPreferredSize(new Dimension(this.getWidth() - 10, this
//				.getHeight() - 140));
		Timer clockTime = new Timer(this);
		clock = new JLabel("", JLabel.CENTER);
		new Thread(clockTime).start();
		clock.setForeground(Color.YELLOW);
		clock.setFont(new Font(null, Font.PLAIN, 20));
		topBar.add(clock, BorderLayout.SOUTH);
		scroll=new JScrollPane(container);
//		container.setPreferredSize(new Dimension(300,300));
		this.add(scroll, BorderLayout.CENTER);
//		scroll = new JScrollPane();
//		scroll.getViewport().add(central);
//		container.add(scroll, BorderLayout.CENTER);
//		this.add(container);
		tables = new JPanel(new FlowLayout());
		tables.setPreferredSize(new Dimension(680,660));
//		tables.setMaximumSize(new Dimension(200,200));
//		.setPreferredSize(nze(new Dimension(200,200));
//		tables.setR
//		tables = new JPanel(new FlowLayout());
//		tables.setPreferredSize(new Dimension(100,100));
//		tables.setSize(200, 200);

		central = new JPanel();
		
		container.setBackground(color);
		scroll.setBackground(color);
		
		
//		central.setPreferredSize(new Dimension(container.getWidth() - 10,
//				container.getHeight() - 10));
		container.add(central, BorderLayout.CENTER);
		central.setBackground(color);
//		subTitle = new JLabel("Tables", JLabel.CENTER);
//		subTitle.setFont(new Font(null, Font.BOLD, 20));
//		central.add(subTitle, BorderLayout.NORTH);
//		scroller = new JScrollPane(tables);
//		central.add(scroller, BorderLayout.CENTER);
		
		central.add(tables);
		
		tables.setBackground(color);
		// for(int i=1; i<=numberOfTables; i++){
		// Table table = new Table("Table "+i);
		// tables.add(table);
		// }
		
//		int numberPerRow = (this.getWidth() - 100) / 260;
//		Double number = (double) numberOfTables / (double) numberPerRow;
//		int numberPerColumn = (int) Math.ceil(number);
//		scroller.setPreferredSize(new Dimension(this.getWidth() - 40, this
//				.getHeight() - 120));
//		tables.setPreferredSize(new Dimension(this.getWidth() - 100,
//				numberPerColumn * 310));

		rightCenter = new JPanel();
		rightCenter.setBackground(color);
		container.add(rightCenter, BorderLayout.EAST);
		   

		bottomBar = new JPanel();
		bottomBar.setBackground(color);
//		central.add(bottomBar, BorderLayout.SOUTH);
		settings = new JButton("Settings");
		settings.setActionCommand("settings");
		settings.addActionListener(this);
		bottomBar.add(settings);

		JButton showWaiting = new JButton("Waiting List");
		showWaiting.setActionCommand("waiting");
		showWaiting.addActionListener(this);
		bottomBar.add(showWaiting);
		
		detailGridLay = new GridLayout(0, 1, 4, 4);
		detailPanel = new JPanel(detailGridLay);
		detailPanel.setBackground(color);
		rightCenter.add(detailPanel);
		
		waitingList = new WaitingList();
		
		rightCenter.add(waitingList);
		waitingList.setVisible(false);
		
		tableReturn = new JButton("Tables");
		tableReturn.setActionCommand("table");
		tableReturn.addActionListener(this);
		tableReturn.setVisible(false);
		bottomBar.add(tableReturn);
		
		addTables(9);
		
		updateUI();
		pack();
		
//		this.setState(JFrame.NORMAL);
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Dimension dimension = toolkit.getScreenSize();
//		this.setSize(dimension);
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent comp) {
				resizeTables();
			}
		});
		
//		container.setPreferredSize(new Dimension(200,200));
//		pack();

	}
	
	public void setClock() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		clock.setText(dateFormat.format(cal.getTime()));
	}

	public void toggleTablesVisibility() {

		if (active != 0) {
//			central.add(scroller, BorderLayout.CENTER);
			
			central.add(tables, BorderLayout.CENTER);
			
			subTitle.setText("Tables");
			active = 0;
			settings.setVisible(true);
			updateUI();
		} else {
			central.remove(tables);
			updateUI();
		}
	}

	public void toggleOrderVisibility(Table table) {
//		showingOrder = table;
//		showingOrder.getOrder().toggleVisible();
		if(table.getOrder().isShown()){
			detailPanel.remove(table.getOrder());
			table.getOrder().setShown(false);
			detailShow--;
			if(detailShow<=DETAILWIDTH&&detailShow>0){
				detailGridLay.setColumns(detailShow);
				}else if(detailShow==0){
					detailGridLay.setColumns(1);
				}else{
					detailGridLay.setColumns(DETAILWIDTH);
				}
		}else{
			detailShow++;
			if(detailShow<=DETAILWIDTH){
			detailGridLay.setColumns(detailShow);
			}else{
				detailGridLay.setColumns(DETAILWIDTH);
			}
		detailPanel.add(table.getOrder(), BorderLayout.WEST);
		table.getOrder().setShown(true);
		
		}
		resizeTables();
		// if(active!=1){
		// turnActiveOff();
		// central.add(table.getOrder(), BorderLayout.CENTER);
		// table.getOrder().setPreferredSize(new Dimension(this.getWidth()-40,
		// this.getHeight()-120));
		// table.getOrder().setBackground(color);
		// tableReturn.setVisible(true);
		// settings.setVisible(false);
		// active=1;
		// subTitle.setText("Order");
		// updateUI();
		// }else{
		// central.remove(table.getOrder());
		// toggleTablesVisibility();
		// tableReturn.setVisible(false);
		// updateUI();
		// }

	}
	
	public void addTables(int newTables) {
		
//		for(int i=0; i<newTables; i++){
//			Table table = new Table("Table "+(i+1), this);
//			tables.add(table, )
//		}
		if (newTables > numberOfTables) {
			for (int i = numberOfTables; i < newTables; i++) {
				Table table = new Table("Table " + (i + 1), this);
				tables.add(table);
				tableList.add(table);
			}
		}
		if (newTables < numberOfTables) {
			for (int i = numberOfTables; i > newTables; i--) {
				tables.remove(tableList.get(i - 1));
				tableList.remove(i - 1);
			}
		}
		numberOfTables = newTables;
		// tables.removeAll();
		// for(int i=1; i<=numberOfTables; i++){
		// Table table = new Table("Table "+i);
		// tables.add(table);
		// tableList.add(table);
		// }
	}

	public void setAllPrices(double price) {
		for (int i = 0; i < tableList.size(); i++) {
			tableList.get(i).setPriceConstant(price);
		}

	}

	public int getNumberOfTables() {
		return numberOfTables;
	}

	public void newTables(int number) {
		addTables(number);
		resizeTables();
	}

	private void updateUI() {
		this.validate();
		detailPanel.validate();
		tables.validate();
		central.validate();
		container.validate();
		scroll.validate();
		
	}

	public void resizeTables() {
//		scroller.setPreferredSize(new Dimension(this.getWidth() - 40, this
//				.getHeight() - 120));
//		container.setPreferredSize(new Dimension(this.getWidth() - 10, this
//				.getHeight() - 40));
//		int numberPerRow = (this.getWidth() - 40) / 260;
//		Double number = (double) numberOfTables / (double) numberPerRow;
//		int numberPerColumn = (int) Math.ceil(number);
//		// System.out.println(numberPerRow);
//		central.setPreferredSize(new Dimension(container.getWidth() - 10,
//				container.getHeight() - 10));
//		tables.setPreferredSize(new Dimension(this.getWidth() - 100,
//				numberPerColumn * 310));

//		tables.setPreferredSize(new Dimension(this.getWidth()-800, this.getHeight()));
//		central.setPreferredSize(new Dimension(this.getWidth()-800, this.getHeight()));
//		container.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()-40));
//
//		central.setPreferredSize(central.getPreferredSize());
//		pack();
		updateUI();
//		pack();
	}

	public static void main(String[] args) {
		new Main();
	}

//	private void turnActiveOff() {
//		if (active == 0) {
//			toggleTablesVisibility();
//		}
//		if (active == 1) {
//			toggleOrderVisibility(tableList.get(0));
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("settings".equals(e.getActionCommand())) {
			new Settings(this);
		} else if ("table".equals(e.getActionCommand())) {
			toggleOrderVisibility(showingOrder);
		} else if ("waiting".equals(e.getActionCommand())) {
			if (waitingList.isVisible()) {
				waitingList.setVisible(false);
				resizeTables();
			} else {
				waitingList.setVisible(true);
				resizeTables();
			}
		}

	}
}
