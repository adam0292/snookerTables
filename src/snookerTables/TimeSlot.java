package snookerTables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSlot {
	private int start, end;
	private int rate;
	private Table table;
	
	public TimeSlot(int start, int end, int rate, int type, Table table){
		this.start = start;
		this.end = end;
		this.rate = rate;
		this.table = table;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public String toString(){
		int startHour = start/60;
		int startMinute = start%60;
		String startTime;
		if(startHour<10){
			startTime = "0"+startHour;
		}else{
			startTime = ""+startHour;
		}
		if(startMinute<10){
			startTime = startTime.concat(":0"+startMinute);
		}else{
			startTime = startTime.concat(":"+startMinute);
		}
		int endHour = end/60;
		int endMinute = end%60;
		String endTime;
		if(endHour<10){
			endTime = "0"+endHour;
		}else{
			endTime = ""+endHour;
		}
		if(startMinute<10){
			endTime = endTime.concat(":0"+endMinute);
		}else{
			endTime = endTime.concat(":"+endMinute);
		}
		
		return "Start time: "+startTime+" End Time: "+endTime+" Rate: "+rate+" Table: "+table.getTableName();
	}

}
