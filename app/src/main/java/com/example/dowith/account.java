package com.example.dowith;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class account extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    ImageButton back;
    View.OnClickListener cl;

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }//데이터 피커를 사용하기 위해 필요한 명령어
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);
        //계정 설정 레이아웃을 표시함

        back = (ImageButton) findViewById(R.id.back_acc);


        cl = new View.OnClickListener() {  //뒤로가기 버튼 누르면 화면 종료되도록 설정
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back_acc:
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(cl);


        EditText et_Date = (EditText) findViewById(R.id.Date);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(account.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }//선택한 년 월 일을 받게 됨
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy 년/ MM 월/dd 일";    // 출력형식 예)2018년/11월/28일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.Date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }
}