package com.example.dowith;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class friendRoom extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;
    private View view;
    ImageView imageView;
    GestureDetector detector;
    float prevX, prevY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_room);

        imageView = findViewById(R.id.sd);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
              case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
              case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - prevX;
                float dy = event.getY() - prevY;
                Log.v(TAG, "dx : " + dx + " dy :: " + dy);
                v.setX(v.getX() + dx); v.setY(v.getY() + dy);
                break;
              case MotionEvent.ACTION_CANCEL:
              case MotionEvent.ACTION_UP:
                break;
            }
            return true;
        }
    });


        //뒤로가기 아이콘에 변수 back 연결
        back = (ImageButton) findViewById(R.id.back_froom);


        cl = new View.OnClickListener() {  //뒤로가기 버튼 누르면 화면 종료되도록 설정
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back_froom:
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(cl);

    }
}