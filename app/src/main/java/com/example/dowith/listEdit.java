package com.example.dowith;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class listEdit extends AppCompatActivity {

    // 변수 선언
    int selected;
    EditText listStartDate, listEndDate, listStartTime, listEndTime, ListType;
    Button btnListCancel, btnListSave;
    View.OnClickListener datecl, timecl;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_edit);

        // Calendar 클래스의 인스턴스를 반환해서 myCalendar에 저장 (Calendar는 추상클래스이므로 인스턴스 생성 불가)
        Calendar myCalendar = Calendar.getInstance();

        // xml에 생성한 위젯을 변수에 대입
        listStartDate = (EditText) findViewById(R.id.listStartDate);
        listEndDate = (EditText) findViewById(R.id.listEndDate);
        listStartTime = (EditText) findViewById(R.id.listStartTime);
        listEndTime = (EditText) findViewById(R.id.listEndTime);
        ListType = (EditText) findViewById(R.id.ListType);
        btnListCancel = (Button) findViewById(R.id.btnListCancel);
        btnListSave = (Button) findViewById(R.id.btnListSave);

        // 날짜 클릭 리스너 설정
        datecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이트피커 다이얼로그 박스 생성
                new DatePickerDialog(listEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 연, 월, 일 정보를 받아와서 "2021/01/01" 형식으로 저장
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                        // 시작 날짜 클릭 시 실행
                        if (v.getId() == R.id.listStartDate)
                            listStartDate.setText(sdf.format(myCalendar.getTime()));
                        // 종료 날짜 클릭 시 실행
                        else if (v.getId() == R.id.listEndDate)
                            listEndDate.setText(sdf.format(myCalendar.getTime()));

                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };
        // 날짜 클릭 리스너 지정
        listStartDate.setOnClickListener(datecl);
        listEndDate.setOnClickListener(datecl);

        // 시간 클릭 리스너 설정
        timecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendar 클래스의 인스턴스 반환해서 mcurrentTime에 저장
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                // 타임피커 다이얼로그 박스 생성
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(listEdit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // 0~9는 앞에 0을 붙여 두자리로 출력
                        String SHour = (selectedHour < 10 ? "0" : "") + String.valueOf(selectedHour);
                        String SMinute = (selectedMinute < 10 ? "0" : "") + String.valueOf(selectedMinute);

                        // 시작 시간 클릭 시 실행
                        if (v.getId() == R.id.listStartTime)
                            // "12시 00분" 형식
                            listStartTime.setText(SHour + "시 " + SMinute + "분");
                            // 종료 시간 클릭 시 실행
                        else if (v.getId() == R.id.listEndTime)
                            // "12시 00분" 형식
                            listEndTime.setText(SHour + "시 " + SMinute + "분");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("시간 선택");
                mTimePicker.show();
            }
        };
        // 시간 클릭 리스너 지정
        listStartTime.setOnClickListener(timecl);
        listEndTime.setOnClickListener(timecl);

        // 일정 종류 클릭 리스너 지정
        ListType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 종류에 나열할 내용을 String 배열로 미리 정의
                final String[] typeArray = new String[]{"공부", "운동", "아침루틴", "저녁루틴", "취미"};

                // 대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(listEdit.this);
                // 라디오버튼 형태로 출력되도록 지정
                dlg.setSingleChoiceItems(typeArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 선택한 값 저장
                        selected = i;
                    }
                });

                // 선택 버튼 클릭 시 동작
                dlg.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 선택한 값이 출력되도록 설정
                        ListType.setText(typeArray[selected]);
                    }
                });

                // 취소 버튼 생성
                dlg.setNegativeButton("취소", null);
                // 대화상자를 화면에 출력
                dlg.show();
            }
        });

        // 취소 클릭 리스너 지정
        btnListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 액티비티 종료
                finish();
            }
        });

        // 저장 클릭 리스너 지정
        btnListSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 액티비티 종료
                finish();
            }
        });

    }
}