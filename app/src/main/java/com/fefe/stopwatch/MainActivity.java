package com.fefe.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private TextView timeText;
    private Button start;
    private Button stop;
    private Button reset;

    private long startTime;
    private long elapsedTime = 01;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private void assignViews() {
        timeText = (TextView) findViewById(R.id.timeText);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(this);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    //　ボタンの押せる押せないの設定
    private void setButtonState(boolean start, boolean stop, boolean reset){
        this.start.setEnabled(start);
        this.stop.setEnabled(stop);
        this.reset.setEnabled(reset);
    }

    //　タイマースタート
    private void startTimer(){
        //startTimeの取得
        startTime = SystemClock.elapsedRealtime();

        //　一定時間ごとに現在の経過時間を表示
        //　Handler - > Runnable - > UI
        updateTimer = new Runnable() {
            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elapsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS", Locale.US);
                timeText.setText(sdf.format(t));
                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer, 10);
            }
        };
        handler.postDelayed(updateTimer, 10);

        setButtonState(false, true, false);
    }

    //　ストップボタンの処理
    private void stopTimer(){
        elapsedTime += SystemClock.elapsedRealtime() - startTime;
        handler.removeCallbacks(updateTimer);
        setButtonState(true, false, true);
    }

    //　リセットボタンの処理
    private void resetTimer(){
        elapsedTime = 01;
        timeText.setText("00:00:000");
        setButtonState(true, false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                startTimer();
                break;
            case R.id.stop:
                stopTimer();
                break;
            case R.id.reset:
                resetTimer();
                break;
        }
    }
}