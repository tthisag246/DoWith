package com.example.dowith;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class listAdd extends AppCompatActivity {
    int selected;

    private static String IP_ADDRESS = "dowith0server.dothome.co.kr";
    private static String TAG = "list_insert";
    int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_add);


        //Calendar 클래스의 인스턴스를 반환해서 myCalendar에 저장 (Calendar는 추상클래스이므로 인스턴스 생성 불가)
        Calendar myCalendar = Calendar.getInstance();

        //에디트텍스트 변수 listTitle 생성, XML의 listTitle에 대응시킴
        EditText listTitle = (EditText) findViewById(R.id.listTitle);
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
        //에디트텍스트 변수 listMemo 생성, XML의 listMemo에 대응시킴
        EditText listMemo = (EditText) findViewById(R.id.listMemo);

        //버튼 변수 btnListCancel 생성, XML의 btnListCancel에 대응시킴
        Button btnListCancel = (Button) findViewById(R.id.btnListCancel);
        //버튼 변수 btnListSave 생성, XML의 btnListSave에 대응시킴
        Button btnListSave = (Button) findViewById(R.id.btnListSave);


        //클릭 시 동작하는 리스너 정의
        View.OnClickListener datecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이트피커 다이얼로그 박스 생성
                new DatePickerDialog(listAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //연, 월, 일 정보를 받아와서 "2021년 01월 01일" 형식으로 저장
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                        //listStartDate를 클릭해서 동작됐으면 listStartDate에 데이터 저장
                        if (v.getId() == R.id.listStartDate)
                            listStartDate.setText(sdf.format(myCalendar.getTime()));
                            //listEndDate를 클릭해서 동작됐으면 listEndDate에 데이터 저장
                        else if (v.getId() == R.id.listEndDate)
                            listEndDate.setText(sdf.format(myCalendar.getTime()));

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
                mTimePicker = new TimePickerDialog(listAdd.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        String state = "AM";
//                        //선택한 시간이 12를 넘을 경우 PM으로 변경, -12시간하여 출력
//                        if (selectedHour > 12) {
//                            selectedHour -= 12;
//                            state = "PM";
//                        }

                        String SHour = (selectedHour < 10 ? "0" : "") + String.valueOf(selectedHour);
                        String SMinute = (selectedMinute < 10 ? "0" : "") + String.valueOf(selectedMinute);

                        //listStartTime를 클릭해서 동작됐으면 listStartTime에 데이터 저장, 형식은 "12시 00분"
                        if (v.getId() == R.id.listStartTime)
                            listStartTime.setText(SHour + "시 " + SMinute + "분");
                            //listEndTime를 클릭해서 동작됐으면 listEndTime에 데이터 저장, 형식은 "12시 00분"
                        else if (v.getId() == R.id.listEndTime)
                            listEndTime.setText(SHour + "시 " + SMinute + "분");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("시간 선택");
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
                final String[] typeArray = new String[]{"공부", "운동", "아침루틴", "저녁루틴", "취미"};

                //대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(listAdd.this);

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

        //저장 버튼을 클릭했을 때
        btnListSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //변수명 설정
                //String td_id = "1234";
                String td_name = listTitle.getText().toString();
                String td_content = listMemo.getText().toString();
                String td_cate = String.valueOf(selected);
                //시작 날짜, 시간 문자열 변경
                String a = listStartDate.getText().toString();
                String a1 = listStartTime.getText().toString();
                a = a.replace("/", "-");
                a1 = a1.replace("시 ", ":");
                a1 = a1.replace("분", ":00");

                //종료 날짜, 시간 문자열 변경
                String b = listEndDate.getText().toString();
                String b1 = listEndTime.getText().toString();
                b = b.replace("/", "-");
                b1 = b1.replace("시 ", ":");
                b1 = b1.replace("분", ":00");

                String td_start = a + " " + a1;
                String td_finish = b + " " + b1;
                String td_yn = "1";

                SaveData task = new SaveData();
                task.execute("http://" + IP_ADDRESS + "/dowith/list_insert.php", td_name, td_content, td_cate, td_start
                ,td_finish, td_yn);

                finish();
            }
        });

    }

    class SaveData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        //작업 이전에 수행할 동작 구현
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(listAdd.this,
                    "Please Wait", null, true, true);
        }

        //결과 파라미터를 리턴->스레드 작업이 끝났을 때의 동작 구현
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            //파라미터를 배열로 전달받는 백그라운드 작업
            //String td_id = (String) params[1];
            String td_name = (String) params[1];
            String td_content = (String) params[2];
            String td_cate = (String) params[3];
            String td_start = (String) params[4];
            String td_finish = (String) params[5];
            String td_yn = (String) params[6];

            String serverURL = (String) params[0];
            String postParameters ="td_name=" + td_name + "&td_content=" + td_content+ "&td_cate=" +
                    td_cate+ "&td_start=" + td_start+ "&td_finish=" + td_finish + "&td_yn=" + td_yn;



            try {
                //URL 서버와 연결
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //httpURL 연결 시간 지정
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                //바이트 단위 데이터 출력을 위한 최상위 스트림 클래스
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                //http 상태 코드
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                //문자 단위 입출력을 위한 하위 스트림 클래스
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();


            //오류가 발생하면 오류내용 출력
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }

    }
}
