package snookerTables;

public class Timer implements Runnable{

	Table table;
	public Timer(Table table){
		this.table=table;
	}
	boolean running = false;
    int min = 0;
    int hr = 0;
    float sec = 0;

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
            try { Thread.sleep(10); }
            catch (InterruptedException e ){}
            
            table.setTime(hr, min, (int)sec);
        }
    }

}
