package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;

public class Table extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Timer stopWatch = new Timer(this);
	private String tableTime = "0:0:0";
	private double price =0, priceConstant = 1 / 60.0 / 60.0;
	private JLabel timerLabel, priceLabel;
	private boolean running = false;
	private JPanel center, controls;
	private JButton start, priceButton;
	private NumberFormat formatter;
	public Table(String tableName) {

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250, 300));

		JLabel name = new JLabel(tableName, JLabel.CENTER);
		name.setFont(new Font(null, Font.BOLD, 15));
		name.setPreferredSize(new Dimension(0, 25));
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
		
		controls.add(start);
		// controls.add(stop);
		controls.add(reset);

		center = new JPanel(new BorderLayout());
		center.add(controls, BorderLayout.NORTH);
		timerLabel = new JLabel(tableTime, JLabel.CENTER);
		timerLabel.setFont(new Font(null, Font.PLAIN, 40));
		priceLabel = new JLabel(formatter.format(price), JLabel.CENTER);
		priceLabel.setFont(new Font(null, Font.PLAIN, 20));
		center.add(timerLabel, BorderLayout.CENTER);
		center.add(priceLabel, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);

		this.add(priceButton, BorderLayout.SOUTH);

		start.addActionListener(this);
		// stop.addActionListener(this);
		priceButton.addActionListener(this);
		reset.addActionListener(this);

		changeBackground(new Color(220, 20, 60));

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

	private void createTimer() {
		new Thread(stopWatch).start();
	}

	private void stopTimer() {
		stopWatch.stop();
	}

}
