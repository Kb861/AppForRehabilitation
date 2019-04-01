package com.example.badanie;

import android.content.Context;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;

    /**
     * Context which is responsible for this instance of the class
     */
    Context mContext;
    /**
     * Starting time
     */
    long mStartTime;
    /**
     * If the class is running or not
     */
    boolean mIsRunning;

    /**
     * Constructor for the class for normal usage
     * @param context the Activity which is responsible fot this insatnce of class
     */
    public Chronometer(Context context) {
        mContext = context;
    }

    /**
     * Constructor which takes context and also an already set starting time
     * this is used mainly for onResume if the application was stopped for any reason
     * @param context
     * @param startTime
     */
    public Chronometer(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
    }

    /**
     * Starts the chronometer
     */
    public void start() {
        if(mStartTime == 0) { //if the start time was not set before! e.g. by second constructor
            mStartTime = System.currentTimeMillis();
        }
        mIsRunning = true;
    }

    /**
     * Stops the chronometer
     */
    public void stop() {
        mIsRunning = false;
    }

    /**
     * Check if the chronometer is running or not
     * @return true if running, false if not running
     */
    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * Get the start time of the class
     * @return start time in milliseconds
     */
    public long getStartTime() {
        return mStartTime;
    }


    @Override
    public void run() {
        while(mIsRunning) {

            long since = System.currentTimeMillis() - mStartTime;

            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS)));
            int millis = (int) since % 1000;
            Calendar calendar=Calendar.getInstance();
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
String time =simpleDateFormat.format(calendar.getTime());

           ((StopwatchActivity) mContext).updateTimerText(String.format(time));

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}