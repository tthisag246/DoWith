package com.example.dowith;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    Button btn7,btn9;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //알림 설정 레이아웃을 표시해준다
        btn7 = (Button) findViewById(R.id.button7);
        btn9 = (Button) findViewById(R.id.button9);
        btn7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });//버튼을 누르면 메인화면으로 가는 명령어
        btn9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });//버튼을 누르면 메인화면으로 가는 명령어
    }
}