package com.example.shiyanhui.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Timer mTimer;
    private float mCounter;

    @InjectView(R.id.counterText)
    TextView mCounterText;
    private Handler mHandler = new Handler();
    private Boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
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

            case R.id.change:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
