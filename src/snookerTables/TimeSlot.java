package snookerTables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSlot {
	private int start, end;
	private boolean[] dayOfWeek;
	private int rate;
	private Table table;
	private boolean active;
	private int id;
	
	public TimeSlot(int id, int start, int end, boolean[] dayOfWeek, int rate, int type, Table table){
		this.start = start;
		this.end = end;
		this.rate = rate;
		this.table = table;
		this.dayOfWeek = dayOfWeek; 
		this.id = id;
		active=false;
		Globals.ID++;
	}

	public Table getTable() {
		return table;
	}

	public int getID(){
		return id;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active=active;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean[] getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(boolean[] dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
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
		if(endMinute<10){
			endTime = endTime.concat(":0"+endMinute);
		}else{
			endTime = endTime.concat(":"+endMinute);
		}
		String days="";
		for(int i=0; i<dayOfWeek.length; i++){
			if(dayOfWeek[i]==true){
				days = days.concat(""+i);
			}
		}
		return "Start time: "+startTime+" End Time: "+endTime+" Rate: "+Globals.getRate(rate);
	}
}
