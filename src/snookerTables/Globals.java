package snookerTables;

import java.awt.Color;

public class Globals {

	static final int SNOOKER = 0;
	static final int POOL = 1;
	
	static final int FULL=0;
	static final int HALF=1;
	static final int FREE=2;
	
	static final int NUMSNOOKERTABLES=9;
	static final int NUMPOOLTABLES=1;
	
	static int ID = 0;
	
	static final Color mainBackgroundColor = Color.DARK_GRAY;
	static final Color tableStopColor = new Color(220, 20, 60);
	static final Color tablePauseColor = new Color(237, 145, 33);
	static final Color tableRunColor = new Color(33, 200, 50);
	
	public static String getRate(int rateIndex){
		if(rateIndex==0){
			return "Full";
		}
		if(rateIndex==1){
			return "Half";
		}
		if(rateIndex==2){
			return "Free";
		}
		return"";
	}
	
	public static String getDayName(int dayIndex){
		if(dayIndex==0){
			return "Sunday";
		}
		if(dayIndex==1){
			return "Monday";
		}
		if(dayIndex==2){
			return "Tuesday";
		}
		if(dayIndex==3){
			return "Wednesday";
		}
		if(dayIndex==4){
			return "Thursday";
		}
		if(dayIndex==5){
			return "Friday";
		}
		if(dayIndex==6){
			return "Saturday";
		}
		return "";
	}
	
}
