package com.example.shiyanhui.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer mTimer;
    private float mCounter;
    private TextView mCounterText;
    private Handler mHandler = new Handler();
    private Boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        Button reset = (Button)findViewById(R.id.reset);
        mCounterText = (TextView)findViewById(R.id.counterText);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            mCounter += 0.001f;

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int minute = (int)mCounter / 60;
                    int second = (int)mCounter % 60;
                    int mm = (int)((mCounter - second - minute * 60) * 1000);
                    mCounterText.setText(String.format(minute + ":%02d.%03d", second, mm));
                }
            });
        }
    }

    private void stopTimer(){
        mTimer.cancel();
        isStarted = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if(isStarted) return;
                mTimer = new Timer(true);
                mTimer.schedule(new MyTimerTask(), 10, 1);
                isStarted = true;
                break;

            case R.id.stop:
                if(!isStarted) return;
                stopTimer();
                break;

            case R.id.reset:
                if(mTimer == null) return;
                mCounter = 0;
                stopTimer();
                mCounterText.setText("0:00.000");
                break;
        }
    }
}
