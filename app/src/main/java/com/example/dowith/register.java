package com.example.dowith;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class register extends AppCompatActivity {
    ImageButton back;
    Calendar myCalendar = Calendar.getInstance();
    private static String IP_ADDRESS = "dowith0server.dothome.co.kr";
    private static String TAG = "user_insert";

    private EditText mEditTextuseremail;
    private EditText mEditTextname;
    private EditText mEditTexpw;
    private EditText mEditTextbirthdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //알림 설정 레이아웃을 표시해준다
        back = (ImageButton) findViewById(R.id.back_reg);
        mEditTextuseremail = (EditText)findViewById(R.id.editText_usrmail);
        mEditTextname = (EditText)findViewById(R.id.editText_usrname);
        mEditTexpw = (EditText)findViewById(R.id.editText_usrpw);
        mEditTextbirthdate = (EditText)findViewById(R.id.editText_usrbirth);

        //데이트 피커쪽
        View.OnClickListener datecl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이트피커 다이얼로그 박스 생성
                new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //연, 월, 일 정보를 받아와서 "2021년 01월 01일" 형식으로 저장
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                        //mEditTextbirthdate를 클릭해서 동작됐으면 listStartDate에 데이터 저장
                        mEditTextbirthdate.setText(sdf.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };

        mEditTextbirthdate.setOnClickListener(datecl);


        Button buttonInsert = (Button)findViewById(R.id.userbutton8);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = mEditTextuseremail.getText().toString();
                String user_name = mEditTextname.getText().toString();
                String user_pw = mEditTexpw.getText().toString();
                String user_birth_date = mEditTextbirthdate.getText().toString().replace("/", "-");

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/dowith/user_insert.php", user_email, user_name, user_pw, user_birth_date);

                finish();
                Toast.makeText( register.this, "가입 완료되었습니다",Toast.LENGTH_SHORT).show();
            }
        });

        final ImageButton button1 = (ImageButton) findViewById(R.id.back_reg);
        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                AlertDialog.Builder dlg = new AlertDialog.Builder(register.this);
                dlg.setTitle("주의"); //제목
                dlg.setMessage("지금까지 입력하신 내용이 삭제됩니다. \n정말 나가시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.ic_baseline_warning_24); // 아이콘 지정
                // 확인 버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //이전 화면(메인화면)으로 돌아감
                        finish();
                        //토스트 메시지
                        Toast.makeText(register.this,"입력이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                //취소 버튼 클릭시 동작
                dlg.setNegativeButton("취소", null);
                dlg.show(); //대화상자 보여줌
            }
        });

    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(register.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String user_email = (String)params[1];
            String user_name = (String)params[2];
            String user_pw = (String)params[3];
            String user_birth_date = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "user_email=" + user_email + "&user_name=" + user_name + "&user_pw=" + user_pw + "&user_birth_date=" + user_birth_date;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}
