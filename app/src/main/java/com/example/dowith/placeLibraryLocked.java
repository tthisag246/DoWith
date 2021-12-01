package com.example.dowith;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class placeLibraryLocked extends AppCompatActivity {

    Button btnLockedSwitch;
    long timeCountInMilliSeconds = 1 * 60000;

    ProgressBar timerProgressBar;

    TextView leftTime;

    CountDownTimer lockedTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_library_locked);

        btnLockedSwitch = (Button) findViewById(R.id.btnLockedSwitch);
        timerProgressBar = (ProgressBar) findViewById(R.id.timerProgressBar);
        leftTime = (TextView) findViewById(R.id.leftTime);
        leftTime.setText(placeLibrary.timeValue);

        btnLockedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockedTimer.onFinish();
            }
        });

        timeCountInMilliSeconds = (long) (Double.parseDouble(leftTime.getText().toString()) * 60 * 60 * 1000);

        setProgressBarValues();

        lockedTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long l) {
                leftTime.setText(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                        TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));

                timerProgressBar.setProgress((int) (l / 1000));
            }

            @Override
            public void onFinish() {
                cancel();
                finish();
            }
        }.start();

        lockedTimer.start();

    }

    void setProgressBarValues() {
        timerProgressBar.setMax((int) timeCountInMilliSeconds / 1000);
        timerProgressBar.setProgress((int) timeCountInMilliSeconds / 1000);
    }
}