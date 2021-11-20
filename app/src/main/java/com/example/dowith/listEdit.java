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
    int selected;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_edit);

        //Calendar 클래스의 인스턴스를 반환해서 myCalendar에 저장 (Calendar는 추상클래스이므로 인스턴스 생성 불가)
        Calendar myCalendar = Calendar.getInstance();

        //에디트텍스트 변수 listStartDate 생성, XML의 listStartDate에 대응시킴
        EditText listStartDate = (EditText) findViewById(R.id.listStartDate);
        //에디트텍스트 변수 listEndDate 생성, XML의 listEndDate에 대응시킴
        EditText listEndDate = (EditText) findViewById(R.id.listEndDate);
        //에디트텍스트 변수 listStartTime 생성, XML의 listStartTime에 대응시킴
        EditText listStartTime = (EditText) findViewById(R.id.listStartTime);
        //에디트텍스트 변수 listEndTime 생성, XML의 listEndTime에 대응시킴
        EditText listEndTime = (EditText) findViewById(R.id.listEndTime);
        //에디트텍스트 변수 ListType 생성, XML의 ListType에 대응시킴
        EditText ListType = (EditText) findViewById(R.id.ListType);
        //버튼 변수 btnListCancel 생성, XML의 btnListCancel에 대응시킴
        Button btnListCancel = (Button) findViewById(R.id.btnListCancel);
        //버튼 변수 btnListSave 생성, XML의 btnListSave에 대응시킴
        Button btnListSave = (Button) findViewById(R.id.btnListSave);

        //클릭 시 동작하는 리스너 정의
        View.OnClickListener datecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이트피커 다이얼로그 박스 생성
                new DatePickerDialog(listEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //연, 월, 일 정보를 받아와서 "2021년 01월 01일" 형식으로 저장
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                        //listStartDate를 클릭해서 동작됐으면 listStartDate에 데이터 저장
                        if (v.getId() == R.id.listStartDate) listStartDate.setText(sdf.format(myCalendar.getTime()));
                            //listEndDate를 클릭해서 동작됐으면 listEndDate에 데이터 저장
                        else if (v.getId() == R.id.listEndDate) listEndDate.setText(sdf.format(myCalendar.getTime()));

                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };

        //listStartDate 클릭 시 리스너 datecl이 동작하도록 연결함
        listStartDate.setOnClickListener(datecl);
        //listEndDate 클릭 시 리스너 datecl이 동작하도록 연결함
        listEndDate.setOnClickListener(datecl);

        //클릭 시 동작하는 리스너 정의
        View.OnClickListener timecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendar 클래스의 인스턴스 반환해서 mcurrentTime에 저장
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                //타임피커 다이얼로그 박스 생성
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(listEdit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        //선택한 시간이 12를 넘을 경우 PM으로 변경, -12시간하여 출력
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        //listStartTime를 클릭해서 동작됐으면 listStartTime에 데이터 저장, 형식은 "AM 12시 00분"
                        if (v.getId() == R.id.listStartTime) listStartTime.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                            //listEndTime를 클릭해서 동작됐으면 listEndTime에 데이터 저장, 형식은 "AM 12시 00분"
                        else if (v.getId() == R.id.listEndTime) listEndTime.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        };

        //listStartTime 클릭 시 리스너 timecl이 동작하도록 연결함
        listStartTime.setOnClickListener(timecl);
        //listEndTime 클릭 시 리스너 timecl이 동작하도록 연결함
        listEndTime.setOnClickListener(timecl);

        //ListType 클릭 시 동작하는 리스너 정의
        ListType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //종류에 나열할 내용을 String 배열로 미리 정의
                final String[] typeArray = new String[] {"공부", "운동", "아침루틴", "저녁루틴", "취미"};

                //대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(listEdit.this);

                //라디오버튼 형태로 출력되도록 지정
                dlg.setSingleChoiceItems(typeArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //선택한 값 저장
                        selected = i;
                    }
                });

                //선택 버튼 클릭 시 동작
                dlg.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //선택한 값이 출력되도록 설정
                        ListType.setText(typeArray[selected]);
                    }
                });

                //취소 버튼 생성
                dlg.setNegativeButton("취소", null);
                //대화상자를 화면에 출력
                dlg.show();
            }
        });

        btnListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnListSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 연동하는 코드 추가 필요
                finish();
            }
        });

    }
}