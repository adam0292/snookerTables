package snookerTables;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CheckScheduler implements Runnable {

	private Scheduler scheduler;
	private int hr, min, sec;
	private boolean running = false;
	private Calendar calendar;
	private ArrayList<TimeSlot> timeSlots;
	private int dayOfWeek;
	private TimeSlot[] current;
	private int[] manual;

	public CheckScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
		calendar = GregorianCalendar.getInstance();
		current = new TimeSlot[10];
		manual= new int[10];
		for(int i=0; i<manual.length; i++){
			manual[i]=-1;
		}
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			calendar.setTime(new Date());
			int mins = calendar.get(Calendar.SECOND);
			if(mins==15||mins==30||mins==45||mins==00){
			
			boolean[] stillActive = new boolean[10];

			timeSlots = scheduler.getTimeSlots();
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			for (int i = 0; i < timeSlots.size(); i++) {
				if (timeSlots.get(i).getDayOfWeek()[dayOfWeek]) {
					if (timeSlots.get(i).getStart() <= (calendar
							.get(Calendar.HOUR_OF_DAY) * 60 + calendar
							.get(Calendar.MINUTE))
							&& timeSlots.get(i).getEnd() > (calendar
									.get(Calendar.HOUR_OF_DAY) * 60 + calendar
									.get(Calendar.MINUTE))) {
						stillActive[timeSlots.get(i).getTable().getTableNumber()]=true;
						if (timeSlots.get(i).getActive()) {
							if(manual[timeSlots.get(i).getTable().getTableNumber()]==timeSlots.get(i).getTable().getRate()){
							}else{
								if(timeSlots.get(i).getTable().getRate()!=timeSlots.get(i).getRate()){
									String newText = timeSlots.get(i).getTable().getRateButton().getText().concat("*");
									timeSlots.get(i).getTable().getRateButton().setText(newText);
									manual[timeSlots.get(i).getTable().getTableNumber()]=timeSlots.get(i).getTable().getRate();
								}
							}
						} else {
							timeSlots.get(i).getTable()
									.changePrice(timeSlots.get(i).getRate());
							timeSlots.get(i).setActive(true);
							if (current[timeSlots.get(i).getTable().getTableNumber()] != null) {
								current[timeSlots.get(i).getTable().getTableNumber()].setActive(false);
							}
							current[timeSlots.get(i).getTable().getTableNumber()] = timeSlots.get(i);
							manual[timeSlots.get(i).getTable().getTableNumber()]=-1;
						}
					}
				}
			}
			for(int i=0; i<stillActive.length; i++){
				if(stillActive[i]==false){
					scheduler.getTables().get(i).changePrice(Globals.FULL);
				}
			}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}

	}

}
