package com.example.dowith;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //스플래쉬 작동 코드
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //스플래쉬 화면 후 보일 화면을 로그인 화면으로 정함
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();;
            }
        },4000); // 스플래쉬 화면 3초동안 보이기
    }
    protected void onPause() {
        super.onPause();
        finish();
    }
}