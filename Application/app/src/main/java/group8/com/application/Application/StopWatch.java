package group8.com.application.Application;

/**
 * Created by Kristiyan on 5/11/2015.
 */
public class StopWatch {

    private long startTime = 0;
    private long stopTime = 0;
    public int[] minutesUNIX;
    public int[] minutesPassed;
    private boolean running = false;


    //start the stopwatch
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    //stop the stopwatch
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    //elapsed time in milliseconds
    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);

        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    //elapsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);

        } else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }

    //elapsed time in minutes
    public long getElapsedTimeMin() {
        long elapsed;
        if (running) {
            elapsed = (((System.currentTimeMillis() - startTime) / 1000) / 60);

        } else {
            elapsed = (((stopTime - startTime) / 1000) / 60);
        }
        return elapsed;
    }

    public void saveMinutes() {
        int elapsed = 0;

        //stopwatch is running
        while (running){
            int counter = 0;
            int temp = 0;

            temp = elapsed;

            //get the minutes
            elapsed = (int) (((System.currentTimeMillis() - startTime) / 1000) / 60);

            if (elapsed > temp){
                counter++;
                temp = elapsed;
            }
            for ( int i = 0; i <= counter;i++){
                minutesUNIX[i] = elapsed;
                minutesPassed[i] = i;
            }
        }

    }

}
