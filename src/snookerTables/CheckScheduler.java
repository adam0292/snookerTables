package snookerTables;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CheckScheduler implements Runnable{

	private Scheduler scheduler;
	private int hr, min, sec;
	private boolean running = false;
	public CheckScheduler(Scheduler scheduler){
		this.scheduler=scheduler;
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		hr = calendar.get(Calendar.HOUR_OF_DAY);
		min = calendar.get(Calendar.MINUTE);
		sec = calendar.get(Calendar.SECOND);
	}
	@Override
	public void run() {
		running=true;
		while(running){
		sec += 0.1;
        if (sec>60.0) {
            min++;
            sec -= 60.0;
        }
        if (min>59) {
            hr++;
            min=0;
        }
        System.out.println("Test");
        try { Thread.sleep(100); }
        catch (InterruptedException e ){}
        
		}
		
	}

}
