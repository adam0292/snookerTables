package snookerTables;

import java.util.Calendar;
import java.util.Date;

public class TimeSlot {
	private Calendar start, end;
	private int rate;
	private Table table;
	
	public TimeSlot(Calendar start, Calendar end, int rate, Table table){
		this.start = start;
		this.end = end;
		this.rate = rate;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public String toString(){
		return "Start time: "+start.getTime()+" End Time: "+end.getTime()+" Rate: "+rate+" Table: ";
	}

}
