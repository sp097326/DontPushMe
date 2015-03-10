package com.app2.ocio.DontPushMe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.*;

public class MyPushButtonActivity extends Activity {
    //need a timer, if the app remains XXX seconds unattended congratulate and taunt
    //the user
    int totalPushCount = 0;
    private TextView timerValue;
    private Button mainBigButton;
    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getGlobalPushCount();

        //handling of the timer
        timerValue = (TextView) findViewById(R.id.textClock);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updatedTimerThread,0);

        //set the ListenerForTheButton
        mainBigButton = (Button) findViewById(R.id.btnDontPushME);
        mainBigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                andYetYouPushedMe();
            }
        });

    }

    // a runnable thread to update the Timer and set the time running
    private Runnable updatedTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int minutes = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + minutes + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    //this method should pull the actual count from the APP2 server
    //todo: create the server part, determine technology
    private void getGlobalPushCount() {
        //dummy, initialize to random
        totalPushCount =  (int)Math.random();
    }

    //prepare a file to count the pushes, this file will latter be
    // set on a server to  sync and keep a "world wide"
    public void andYetYouPushedMe() {
        TextView txtView = (TextView)this.findViewById(R.id.txtPersonNumerX);
        String fullText = txtView.getText().toString();
        fullText = "Eres la persona número" + totalPushCount + " en desobedecer.. ve a trabajar en autocontrol!!";
        txtView.setEnabled(true);
    }
}
