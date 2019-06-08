package com.example.badanie.Models;

import android.content.Context;

import com.example.badanie.StopwatchActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Chronometer implements Runnable {

    private final Context mContext;
    private long mStartTime;
    private boolean mIsRunning;

    public Chronometer(Context context) {
        mContext = context;
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