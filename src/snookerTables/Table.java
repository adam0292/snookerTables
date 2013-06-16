package snookerTables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;

public class Table extends JPanel implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TableOrder order;
	private Timer stopWatch = new Timer(this);
	private String tableTime = "0:0:0", tableName;
	private double price =0.00, priceConstant = 1 / 60.0 / 60.0;
	private JLabel timerLabel, priceLabel;
	private boolean running = false;
	private JPanel center, controls;
	private JButton start, priceButton;
	private NumberFormat formatter;
	private Main main;
	public Table(String tableName, Main main) {
		this.main=main;
		this.tableName=tableName;
		order=new TableOrder(this);
		this.setLayout(new BorderLayout());
//		this.setPreferredSize(new Dimension(230, 200));

		JLabel name = new JLabel(tableName, JLabel.CENTER);
		name.setFont(new Font(null, Font.BOLD, 15));
//		name.setPreferredSize(new Dimension(0, 25));
		this.add(name, BorderLayout.NORTH);
		formatter = NumberFormat.getCurrencyInstance();
		
		controls = new JPanel(new FlowLayout());

		start = new JButton("Start");
		start.setActionCommand("start");
		// JButton stop = new JButton("Stop");
		// stop.setActionCommand("stop");
		priceButton = new JButton("Price per Hour: "
				+ formatter.format((priceConstant * 60 * 60.0)));
		priceButton.setActionCommand("price");
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");		
		this.addMouseListener(this);
		controls.add(start);
		// controls.add(stop);
		controls.add(reset);
		JButton detail = new JButton("Details");
		detail.setActionCommand("detail");
		controls.add(detail);
		detail.addActionListener(this);
		
		center = new JPanel(new BorderLayout());
		center.add(controls, BorderLayout.NORTH);
		timerLabel = new JLabel(tableTime, JLabel.CENTER);
		timerLabel.setFont(new Font(null, Font.PLAIN, 40));
		priceLabel = new JLabel(formatter.format(price), JLabel.CENTER);
		priceLabel.setFont(new Font(null, Font.PLAIN, 20));
		center.add(timerLabel, BorderLayout.CENTER);
		center.add(priceLabel, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);

//		JPanel bottomPanel = new JPanel();
		this.add(priceButton, BorderLayout.SOUTH);
//		bottomPanel.add(priceButton, BorderLayout.SOUTH);

//		JButton orders = new JButton("Orders");
//		orders.setActionCommand("orders");
//		bottomPanel.add(orders);
		
		start.addActionListener(this);
//		orders.addActionListener(this);
		priceButton.addActionListener(this);
		reset.addActionListener(this);

		changeBackground(new Color(220, 20, 60));

	}

	public TableOrder getOrder(){
		return order;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("start".equals(e.getActionCommand()) && !running) {
			createTimer();
			changeBackground(new Color(33, 200, 50));
			running = true;
			start.setText("Stop");
			start.setActionCommand("stop");
		} else if ("stop".equals(e.getActionCommand())) {
			stopTimer();
			running = false;
			changeBackground(new Color(237, 145, 33));
			start.setText("Resume");
			start.setActionCommand("start");
		} else if ("price".equals(e.getActionCommand())) {
			try {
				String amount = JOptionPane.showInputDialog("Amount per Hour");
				if (amount != null) {
					setPriceConstant(Double.parseDouble(amount));
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Number required");
			}
		} else if ("reset".equals(e.getActionCommand())) {
			if(JOptionPane.showConfirmDialog(this, "Reset "+getTableName()+"?","Confirm",JOptionPane.YES_NO_OPTION)==0){
				if (running == true) {
					stopTimer();
					running = false;
				}
				setTime(0, 0, 0);
				stopWatch.reset();
				changeBackground(new Color(220, 20, 60));
				start.setText("Start");
				start.setActionCommand("start");
			}
		}else if("detail".equals(e.getActionCommand())){
			main.toggleOrderVisibility(this);
		}

	}

	public Main getMain(){
		return main;
	}
	
	private void changeBackground(Color newColor) {
		this.setBackground(newColor);
		center.setBackground(newColor);
		controls.setBackground(newColor);
	}

	public void setPriceConstant(double perHour) {
		priceConstant = perHour / 3600.0;
		priceButton.setText("Price per Hour: " + formatter.format(perHour));
	}

	public void setTime(int hour, int min, int sec) {
		tableTime = hour + ":" + min + ":" + sec;
		timerLabel.setText(tableTime);
		int timeElapsed = hour * 60 * 60 + min * 60 + sec;
		price = timeElapsed * priceConstant;
		priceLabel.setText(formatter.format(price));
	}
	
	public double getPrice(){
		return price;
	}

	private void createTimer() {
		new Thread(stopWatch).start();
	}

	private void stopTimer() {
		stopWatch.stop();
	}
	
	public String getTableName(){
		return tableName;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		main.toggleOrderVisibility(this);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
