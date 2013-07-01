package snookerTables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel snookerTables, poolTables, container, snookerHolder,
			poolHolder, waitingList, rightCenter, detailPanel;
	private JScrollPane scroll;
	private JLabel clock;
	private int detailShow;
	private ArrayList<Table> snookerTableList, poolTableList;
	private Color color;
	private GridLayout detailGridLay;
	private final int DETAILWIDTH = 3;

	public Main() {

		

		// Set variables with height and width of screen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int height = dimension.height;
		int width = dimension.width;

		// Set title
		setTitle("Snooker Club");
		// Define the main background color
		color = Color.DARK_GRAY;
		// Make the frame visible
		this.setVisible(true);
		// Set default close action
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Set the layout
		this.setLayout(new BorderLayout());
		// Set the color
		this.getContentPane().setBackground(color);
		// Set the look and feel
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create main pane for frame and set color
		container = new JPanel(new BorderLayout());
		container.setBackground(color);
		// Initialise the top panel
		JPanel topPanel = new JPanel(new BorderLayout());

		// Create the menu bar
		JMenuBar menu = new JMenuBar();
		// Create items for the menu and set action commands and listeners
		JMenu file = new JMenu("File");
		JMenuItem settingsMenu = new JMenuItem("Settings");
		settingsMenu.setActionCommand("settings");
		settingsMenu.addActionListener(this);
		file.add(settingsMenu);
		JMenuItem waitingListMenu = new JMenuItem("Waiting List");
		waitingListMenu.setActionCommand("waiting");
		waitingListMenu.addActionListener(this);
		menu.add(file);
		menu.add(waitingListMenu);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		file.add(exit);
		// Add menu to the topPanel
		topPanel.add(menu, BorderLayout.NORTH);

		// Create the top bar
		JPanel topBar = new JPanel(new BorderLayout());
		// add the topBar to the topPanel
		topPanel.add(topBar, BorderLayout.CENTER);
		// Set the color of the topBar
		topBar.setBackground(color);

		// //Create a title label for the frame
		// JLabel name = new JLabel("Snooker Club", JLabel.CENTER);
		// name.setFont(new Font(null, Font.BOLD, 30));
		// name.setForeground(Color.WHITE);
		// topBar.add(name, BorderLayout.CENTER);

		// Add the topPanel to the frame
		this.add(topPanel, BorderLayout.NORTH);

		// Create a timer for the clock
		Timer clockTime = new Timer(this);
		// Create a label for the clock
		clock = new JLabel("", JLabel.CENTER);
		// start the clock
		new Thread(clockTime).start();
		// Set color's and font for the clock
		clock.setForeground(Color.WHITE);
		clock.setFont(new Font(null, Font.PLAIN, 20));
		// Add clock to the topBar
		topBar.add(clock, BorderLayout.SOUTH);

		// Create the scrollPane, set the color and border
		scroll = new JScrollPane(container);
		scroll.setBackground(color);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		// Add the scrollPane to the frame
		this.add(scroll, BorderLayout.CENTER);

		// Create the snookerTables panel and set border and color
		snookerTables = new JPanel(new GridLayout(3, 3, 2, 2));
		snookerTables.setBorder(new EmptyBorder(6, 6, 6, 6));
		snookerTables.setBackground(color);
		// Create the poolTables panel and set border and color
		poolTables = new JPanel();
		poolTables.setBorder(new EmptyBorder(6, 6, 6, 6));
		poolTables.setBackground(color);

		// Create the holder panels fpr the snooker and pool tables and set
		// color
		snookerHolder = new JPanel();
		snookerHolder.setBackground(color);
		snookerHolder.add(snookerTables);
		poolHolder = new JPanel();
		poolHolder.setBackground(color);
		poolHolder.add(poolTables);

		// Create the tabbed pane and add the snooker and pool holders
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.add("Snooker", snookerHolder);
		tabbed.add("Pool", poolHolder);

		// Create the central pane and set color
		JPanel central = new JPanel();
		central.setBackground(color);
		// add the tabbed pain to it
		central.add(tabbed);
		// add the tabbed pain to the central panel
		container.add(central, BorderLayout.CENTER);

		// Create a panel for the right center and set color
		rightCenter = new JPanel();
		rightCenter.setBackground(color);
		// add the pain to the container
		container.add(rightCenter, BorderLayout.EAST);

		// //create a bottomBar and set color
		// bottomBar = new JPanel();
		// bottomBar.setBackground(color);
		// //create the buttons for the bottom bar
		// JButton settings = new JButton("Settings");
		// settings.setActionCommand("settings");
		// settings.addActionListener(this);
		// bottomBar.add(settings);
		// JButton showWaiting = new JButton("Waiting List");
		// showWaiting.setActionCommand("waiting");
		// showWaiting.addActionListener(this);
		// bottomBar.add(showWaiting);
		// //add bottomBar to container
		// container.add(bottomBar, BorderLayout.SOUTH);

		// create a gridLayout
		detailGridLay = new GridLayout(0, 1, 4, 4);
		// create the detailPane with gridLayout, set color
		detailPanel = new JPanel(new GridLayout(0, 1, 4, 4));
		detailPanel.setBackground(color);
		// add to rightCenter
		rightCenter.add(detailPanel);

		// Create the waitingList
		waitingList = new WaitingList(this);
		// Add to right center
		rightCenter.add(waitingList);
		// Hide waitingList
		waitingList.setVisible(false);

		//Initalises the lists for the tables
		snookerTableList = new ArrayList<Table>();
		poolTableList = new ArrayList<Table>();
		// Create the tables
		addTables(Globals.NUMSNOOKERTABLES, Globals.NUMPOOLTABLES);

		// update panels
		resizeTables();

		// On resize refresh tables
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent comp) {
				resizeTables();

			}
		});
		
		new Scheduler(snookerTableList, poolTableList);

	}

	/**
	 * Method call to set the clock
	 */
	public void setClock() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		// Sets the clock label to the current time
		clock.setText(dateFormat.format(cal.getTime()));
	}

	/**
	 * Method to show the order for a chosen table
	 * 
	 * @param table
	 *            The table to show the order for
	 */
	public void toggleOrderVisibility(Table table) {
		// Checks if the order is already visible
		if (table.getOrder().isShown()) { // if it is visible then remove it
			detailPanel.remove(table.getOrder());
			table.getOrder().setShown(false);
			detailShow--;
			// and adjust the size of the panel
			if (detailShow <= DETAILWIDTH && detailShow > 0) {
				detailGridLay.setColumns(detailShow);
			} else if (detailShow == 0) {
				detailGridLay.setColumns(1);
			} else {
				detailGridLay.setColumns(DETAILWIDTH);
			}
		} else { // if it's not visible then add it
			detailShow++;
			if (detailShow <= DETAILWIDTH) { // adjust the size of the panel
				detailGridLay.setColumns(detailShow);
			} else {
				detailGridLay.setColumns(DETAILWIDTH);
			}
			detailPanel.add(table.getOrder(), BorderLayout.WEST);
			table.getOrder().setShown(true);

		}
		resizeTables(); // refresh tables
	}

	/**
	 * Creates the snooker and pool tables
	 * 
	 * @param numSnookerTables
	 *            number of snooker tables
	 * @param numPoolTables
	 *            number of pool tables
	 */
	public void addTables(int numSnookerTables, int numPoolTables) {
		// Creates the snooker tables
		for (int i = 0; i < numSnookerTables; i++) { // adds the snooker tables
			Table table = new Table("Table " + (i + 1), Globals.SNOOKER, this);
			snookerTables.add(table);
			snookerTableList.add(table);
		}
		// Creates the pool tables
		for (int i = 0; i < numPoolTables; i++) { // adds the snooker tables
			Table table = new Table("Table " + (i), Globals.POOL, this);
			poolTables.add(table);
			poolTableList.add(table);
		}
	}

	/**
	 * Sets the price for all snooker tables
	 * @param price The price to set them to
	 */
	public void setSnookerAllPrices(double price) {
		for (int i = 0; i < snookerTableList.size(); i++) {
			snookerTableList.get(i).setPriceConstant(price);
		}
	}
	
	/**
	 * Sets the price for all pool tables
	 * @param price The price to set them to
	 */
	public void setPoolAllPrices(double price) {
		for (int i = 0; i < poolTableList.size(); i++) {
			poolTableList.get(i).setPriceConstant(price);
		}
	}
	
	/**
	 * Updates the panels
	 */
	private void updateUI() {
		this.validate();
		detailPanel.validate();
		snookerTables.validate();
		snookerHolder.validate();
		container.validate();
		scroll.validate();

	}

	/**
	 * Resizes the frame
	 */
	public void resizeTables() {
		if (this.getExtendedState() == Frame.MAXIMIZED_BOTH) { //If window is maximised do nothing
		} else {
			pack(); //resize
		}
		updateUI();
	}

	/**
	 * Main method to create the frame
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
		
	}
	
	public void checkSchedule(){
		System.out.println("Test");
	}

	/**
	 * Method called when an action is performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("settings".equals(e.getActionCommand())) { //if settings is pressed
			new Settings(this); //
		} else if ("waiting".equals(e.getActionCommand())) { //if waiting is pressed
			if (waitingList.isVisible()) {
				waitingList.setVisible(false); //show waiting pane if hidden
			} else {
				waitingList.setVisible(true); //hide waiting pane if visible
			}
			resizeTables();
		}else if("exit".equals(e.getActionCommand())){
			if(JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION)==0){
				System.exit(0);
			}
		}

	}
}
