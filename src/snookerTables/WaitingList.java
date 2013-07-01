package snookerTables;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WaitingList extends JPanel implements ActionListener {

	 /** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = true;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(20, 20);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> waiting;
	private JList<String> list;
	private ArrayList<Integer> selected;
	private Table table;
	private int minIndex=-1, maxIndex=-1;
	private Color color=Color.BLACK;
	private Main main;
	
	public WaitingList(Main main){
		super();
        setOpaque(false);

        this.main=main;
//        this.setBackground(color);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int height = dimension.height;
		int width = dimension.width;
//		this.setSize(width/5,(int)(height/2.5));
//		setPreferredSize(new Dimension(width/5,(int)(height/2.5)));
		this.setSize(200,300);
		setPreferredSize(new Dimension(220, 300));
		waiting = new ArrayList<String>();
		selected = new ArrayList<Integer>();
		list = new JList<String>();
//		list.setBackground(Color.DARK_GRAY);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel selectionModel = list.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
//				int firstIndex = e.getFirstIndex();
//		        int lastIndex = e.getLastIndex();
//		        boolean isAdjusting = e.getValueIsAdjusting();
		        minIndex = lsm.getMinSelectionIndex();
	            maxIndex = lsm.getMaxSelectionIndex();
//	            selected.clear();
//	            for (int i = minIndex; i <= maxIndex; i++) {
//	                if (lsm.isSelectedIndex(i)) {
//	                    selected.add(i);
//	                }
//	            }
				
			}
		});
//		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		
		JPanel bottomPanel = new JPanel(new GridLayout(0,1));
//		bottomPanel.setBackground(color);
		JPanel topBottom = new JPanel();
//		topBottom.setBackground(color);
		bottomPanel.add(topBottom);
		JButton add = new JButton("Add");
		add.setActionCommand("add");
		add.addActionListener(this);
		topBottom.add(add);
		
		JButton remove = new JButton("Remove");
		remove.setActionCommand("remove");
		remove.addActionListener(this);
		topBottom.add(remove);
		
		
//		JPanel bottomBottom = new JPanel();
//		bottomPanel.add(bottomBottom);
		JButton hide = new JButton("Hide");
		hide.setActionCommand("hide");
		hide.addActionListener(this);
		topBottom.add(hide);
		JPanel container = new JPanel(new BorderLayout());
//		container.setBackground(color);
//		container.setPreferredSize(new Dimension(150,250));
		this.add(container);
		
		JLabel title = new JLabel("Waiting List", JLabel.CENTER);
		title.setFont(new Font(null, Font.BOLD, 16));
//		title.setForeground(Color.WHITE);
		container.add(title, BorderLayout.NORTH);
		container.add(bottomPanel, BorderLayout.SOUTH);
		
		JScrollPane listScroller = new JScrollPane(list);
//		listScroller.setBackground(Color.GRAY);
		listScroller.setPreferredSize(new Dimension(this.getWidth()-20, this.getHeight()-70));
		container.add(listScroller, BorderLayout.CENTER);
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

	public void removeName(){
//		if(waiting.size()<=toRemove || toRemove==-1){
//		}else{
//		for(int i=0; i<toRemove.size();i++){
//			System.out.println(toRemove.get(i));
//			waiting.remove(toRemove.get(i));
//			System.out.println(waiting.get(toRemove.get(i)));
//		}
		if(!(maxIndex==-1||minIndex==-1)){
		for(int i=maxIndex; i>=minIndex; i--){
			waiting.remove(i);
		}
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
			removeName();
			list.setSelectedIndex(0);
//			selected=-1;
		}else if("add".equals(e.getActionCommand())){
			String toAdd = JOptionPane.showInputDialog("Name");
			if(!(toAdd==null)){
				addName(toAdd);
				list.setSelectedIndex(0);
			}
		}else if("hide".equals(e.getActionCommand())){
			this.setVisible(false);
			main.resizeTables();
			
		}
		
		
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(), 
	shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Draws shadow borders if any.
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X position
                    shadowOffset,// Y position
                    width - strokeSize - shadowOffset, // width
                    height - strokeSize - shadowOffset, // height
                    arcs.width, arcs.height);// arc Dimension
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap, 
		height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap, 
		height - shadowGap, arcs.width, arcs.height);

        //Sets strokes to default, is better.
        graphics.setStroke(new BasicStroke());
    }
	
}
