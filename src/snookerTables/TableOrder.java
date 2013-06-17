package snookerTables;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;

public class TableOrder extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Table table;
	private NumberFormat formatter;
	private JLabel timePrice;
	private boolean shown;
	public TableOrder(Table table) {
		
		formatter = NumberFormat.getCurrencyInstance();
//		this.setVisible(false);
//		this.setSize(new Dimension(260,320));
		this.table=table;
		Container container = new Container();
		container.setLayout(new BorderLayout());
		this.add(container);
		JLabel name = new JLabel(table.getTableName(), JLabel.CENTER);
		name.setFont(new Font(null, Font.BOLD, 20));
		container.add(name, BorderLayout.NORTH);
		
		timePrice=new JLabel("Hire Cost: £0.00");
//		timePrice.setFont(new Font(null, Font.BOLD, 10));
		container.add(timePrice, BorderLayout.CENTER);
		
		JButton close  = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(this);
		container.add(close, BorderLayout.SOUTH);
		
	}
	
	public void setShown(boolean shown){
		this.shown=shown;
	}
	
	public boolean isShown(){
		return shown;
	}
	
	public void updatePrice(){
		double price = table.getPrice();
		timePrice.setText("Hire Cost: "+formatter.format(price));
		
		
	}
	
//	public void toggleVisible(){
//		this.setVisible(true);
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if("close".equals(e.getActionCommand())){
			table.getMain().toggleOrderVisibility(table);
		}
		
	}

}
