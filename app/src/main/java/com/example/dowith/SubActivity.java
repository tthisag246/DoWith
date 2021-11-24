package com.example.dowith;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SubActivity extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_setting);
        //언어 설정 레이아웃을 표시
        //안녕하세요ㅛㅛㅛㅛㅛㅛ
        //sdfdssdffsdsfdasfasdf
        //dddddddddddddddddddddd
        back = (ImageButton) findViewById(R.id.back_lang);


        cl = new View.OnClickListener() {  //뒤로가기 버튼 누르면 화면 종료되도록 설정
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back_lang:
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(cl);
    }
}
