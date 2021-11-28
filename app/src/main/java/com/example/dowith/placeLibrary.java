package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class placeLibrary extends AppCompatActivity {

    TextView placeNameView;
    Button btnLibrarySwitch;
    static public String timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_library);

        placeNameView = (TextView) findViewById(R.id.placeNameView);

        placeNameView.setText(place.placeName);

        btnLibrarySwitch = (Button) findViewById(R.id.btnLibrarySwitch);

        //btnAddPlace 버튼을 클릭하면 실행하는 코드
        btnLibrarySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText timeInput = new EditText(v.getContext());

                FrameLayout container = new FrameLayout(v.getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 100;
                params.rightMargin = 100;

                timeInput.setLayoutParams(params);

                container.addView(timeInput);

                AlertDialog.Builder dlg = new AlertDialog.Builder(placeLibrary.this);
                dlg.setTitle("몇 시간 잠글까요?");
                dlg.setView(container);
                dlg.setPositiveButton("잠금", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timeValue = timeInput.getText().toString();
                        //Intent 생성, place_library_locked의 클래스 placeLibraryLocked를 넘김
                        Intent intent = new Intent(getApplicationContext(), placeLibraryLocked.class);
                        //place_library_locked 액티비티 실행
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();


            }
        });
    }
}