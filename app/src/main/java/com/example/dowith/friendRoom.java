package com.example.dowith;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class friendRoom extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_room);

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