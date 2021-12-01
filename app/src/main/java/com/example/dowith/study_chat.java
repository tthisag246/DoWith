package com.example.dowith;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class study_chat extends AppCompatActivity {

    TextView stname;

    private static String IP_ADDRESS = "dowith0server.dothome.co.kr";
    private static String TAG = "studyChatDB";

    String mJsonString;

    ArrayList<studyChatItem> chatList;

    studyChatAdapter chatListAdapter;

    Handler chatHandler;

    test t = new test();
    Thread th = new Thread(t);

    Boolean exittf = true;



    class test implements Runnable {

        @Override
        public void run() {
            while (exittf == true) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                chatHandler.sendEmptyMessage(0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_chat);



        stname = (TextView) findViewById(R.id.stName);
        stname.setText(study.studyName);

        //ArrayList<placeItem> 변수 placeSearchList 생성
        chatList = new ArrayList<studyChatItem>();
        //placeAdapter 변수 placeListAdapter 생성, place_item으로 형식 지정, placeList 배열 적용
        chatListAdapter = new studyChatAdapter(this, R.layout.study_chat_item, chatList);
        //리스트뷰 변수 placeListView 생성, XML의 placeList에 대응시킴
        ListView chatListView = (ListView) findViewById(R.id.chatListView);

        //adapter를 placeListView에 적용
        chatListView.setAdapter(chatListAdapter);

        chatList.clear();
        chatListAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/dowith/chat_getjson.php", "");


        chatHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/dowith/chat_getjson.php", "");
                chatList.clear();
                chatListAdapter.notifyDataSetChanged();
            }
        };

        t = new test();
        th = new Thread(t);
        th.start();
        //버튼 변수 back1 생성, XML의 back1에 대응시킴
        ImageButton back1 = (ImageButton) findViewById(R.id.back1);

        //뒤로가기 버튼 클릭 시 이전 화면으로 이동
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exittf = false;
                th.interrupt();
                while ( !th.isInterrupted() ) {
                    SystemClock.sleep(500);
                }
                study_chat.this.finish();

            }
        });

        //버튼 변수 back1 생성, XML의 back1에 대응시킴
        ImageButton btnChatSend = (ImageButton) findViewById(R.id.btnChatSend);
        EditText chatContent = (EditText) findViewById(R.id.chatContent);

        //보내기 버튼 클릭 시 채팅 전송
        btnChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_chat_id = "11";
                String s_chat_content = chatContent.getText().toString();
                String s_chat_time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

                study_chat.InsertData task = new study_chat.InsertData();
                task.execute("http://" + IP_ADDRESS + "/dowith/chat_insert.php", s_chat_id, s_chat_content, s_chat_time);

                chatContent.setText("");
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(study_chat.this,
                    "채팅을 보냅니다...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //Toast.makeText(study_chat.this, result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String s_chat_id = (String) params[1];
            String s_chat_content = (String) params[2];
            String s_chat_time = (String) params[3];

            String serverURL = (String)params[0];
            String postParameters = "s_chat_id=" + s_chat_id + "&s_chat_content=" + s_chat_content + "&s_chat_time=" + s_chat_time;


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

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(study_chat.this,
                    null, null, true, true);
            progressDialog.hide();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //Toast.makeText(study_chat.this, result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "response - " + result);

            if (result == null){

                //Toast.makeText(study_chat.this, errorString, Toast.LENGTH_SHORT).show();
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "s_chat_id=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){

        String TAG_JSON="study_chats";
        String TAG_ID = "s_chat_id";
        String TAG_CONT ="s_chat_content";
        String TAG_TIME ="s_chat_time";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String s_chat_id = item.getString(TAG_ID);
                String s_chat_content = item.getString(TAG_CONT);
                String s_chat_time = item.getString(TAG_TIME);

                studyChatItem studyChatItem = new studyChatItem();

                studyChatItem.setItem_id(s_chat_id);
                studyChatItem.setItem_content(s_chat_content);
                studyChatItem.setItem_time(s_chat_time.substring(0,5));

                chatList.add(studyChatItem);
                chatListAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}