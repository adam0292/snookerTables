package snookerTables;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Timer implements Runnable{

	Table table;
	Main main;
	boolean running = false, clock;
    int min = 0;
    int hr = 0;
    float sec = 0;
    
	public Timer(Table table){
		this.table=table;
		clock =false;
		if(clock){
			Date date = new Date();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			hr = calendar.get(Calendar.HOUR_OF_DAY);
			min = calendar.get(Calendar.MINUTE);
			sec = calendar.get(Calendar.SECOND);
		}
	}
	public Timer(Main main){
		this.main=main;
		clock =true;
			Date date = new Date();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			hr = calendar.get(Calendar.HOUR_OF_DAY);
			min = calendar.get(Calendar.MINUTE);
			sec = calendar.get(Calendar.SECOND);
			hr=23;
			min=59;
	}

    public int getHr() {
        return hr;
    }
    /*
     *
     */
    public int getMin() {
        return min;
    }
    /**
     *
     * @return
     */
    public float getSec() {
        return sec;
    }
    /**
     *
     */
  
    public void stop() {
        running = false;
    }
    /**
     *
     */
    
    public void reset(){
    	min=0;
    	hr=0;
    	sec=0;
    }
    public void run() {

        running = true;
        while (running) {

           sec += 0.01;
            if (sec>60.0) {
                min++;
                sec -= 60.0;
            }
            if (min>59) {
                hr++;
                min=0;
            }
            if(clock){
            	if(hr==24){
            		hr=0;
            	}
            }
            try { Thread.sleep(10); }
            catch (InterruptedException e ){}
            if(!clock){
            table.setTime(hr, min, (int)sec);
            table.getOrder().updatePrice();
            }else{
            	main.setClock(hr,  min, (int)sec);
            }
        }
    }
    

}
