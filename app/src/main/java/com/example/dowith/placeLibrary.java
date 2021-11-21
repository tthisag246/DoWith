package com.example.dowith;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class placeLibrary extends AppCompatActivity {

    Button btnLibrarySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_library);

        btnLibrarySwitch = (Button) findViewById(R.id.btnLibrarySwitch);

        btnLibrarySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 생성, place_library_locked의 클래스 placeLibraryLocked를 넘김
                Intent intent = new Intent(getApplicationContext(), placeLibraryLocked.class);
                //place_library_locked 액티비티 실행
                startActivity(intent);

                //다이얼로그로 바꾸기
            }
        });
    }
}