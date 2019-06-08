package com.example.badanie;

import android.content.Context;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Chronometer implements Runnable {



    Context mContext;
    long mStartTime;
    boolean mIsRunning;

    public Chronometer(Context context) {
        mContext = context;
    }

    public Chronometer(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
    }

    public void start() {
        if (mStartTime == 0) {
            mStartTime = System.currentTimeMillis();
        }
        mIsRunning = true;
    }

    public void stop() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = simpleDateFormat.format(calendar.getTime());
            ((StopwatchActivity) mContext).updateTimerText(String.format(time));

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}