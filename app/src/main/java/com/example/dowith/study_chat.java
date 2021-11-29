package com.example.dowith;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class study_chat extends AppCompatActivity {

    TextView stname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_chat);

        stname = (TextView) findViewById(R.id.stName);
        stname.setText(study.studyName);

        //버튼 변수 back1 생성, XML의 back1에 대응시킴
        ImageButton back1 = (ImageButton) findViewById(R.id.back1);

        //뒤로가기 버튼 클릭 시 이전 화면으로 이동
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
