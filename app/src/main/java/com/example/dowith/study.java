package com.example.dowith;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class study extends Fragment {

    //데이터베이스 서버의 ip주소 IP_ADDRESS에 적용
    private static String IP_ADDRESS = "hanmao2.iptime.org";
    private static String TAG = "Study_insert";

    private View view;
    View makeview;

    public static String studyName;

    String mJsonString;

    ArrayList<studyitem> studylist;

    studyadapter studylistadapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.study,container,false);
        //스터디 목록을 담을 ArrayList<String> 형의 비어 있는 변수 studylist 선언
        //ArrayList <studyitem> studylist = new ArrayList<studyitem>();
        //리스트뷰 변수 studylistView 생성, XML의 studylist에 대응시킴
        studylist = new ArrayList<studyitem>();
        ListView studylistView = (ListView) view.findViewById(R.id.studylist);
        //버튼 변수 make 생성, XML의 make에 대응시킴
        ImageButton make = (ImageButton) view.findViewById(R.id.make);
        //버튼 변수 join 생성, XML의 join에 대응시킴
        ImageButton join = (ImageButton) view.findViewById(R.id.join);

        //ArrayAdapter<String> 형 변수 선언, 리스트뷰 형식 지정, studylist 배열 적용
        studylistadapter = new studyadapter(getActivity(), R.layout.study_item, studylist);
        //adapter를 studylistView에 적용
        studylistView.setAdapter(studylistadapter);

//        //add() 메소드로 studylist 항목 추
//        studylist.add(new studyitem("수학 mentoring"));
//        studylist.add(new studyitem("드로잉 공부"));
//        studylist.add(new studyitem("영어 회화"));
//        studylist.add(new studyitem("논술 심화"));
//        studylist.add(new studyitem("가가중 3-2 study"));
//        studylist.add(new studyitem("뜨개질 기초"));
//        studylist.add(new studyitem("네일아트 꿀팁"));

        studylist.clear();
        studylistadapter.notifyDataSetChanged();
        study.GetData task = new study.GetData();
        task.execute( "http://" + IP_ADDRESS + "/dowith/Study_getjson.php", "");


        //studylistView 클릭하면 실행하는 코드
        studylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                studyName = studylist.get(arg2).getItem_title();
                //Intent 생성, study_chat에 클래스 study_chat를 넘김
                Intent intent = new Intent(getActivity(), study_chat.class);
                //study_chat 액티비티 실행
                startActivity(intent);
            }
        });


        //스터디 생성 버튼을 누르면 생성할 수 있는 대화상자가 나타나는 코드
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeview = (View) View.inflate(study.this.getActivity(), R.layout.study_make, null);

                //대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(study.this.getActivity());

                dlg.setView(makeview);
                 EditText studyName;
                 EditText studyDesc;

                //EditText 변수 studyName 생성, XML의 studyName에 대응시킴
                studyName = (EditText) makeview.findViewById(R.id.studyName);
                //EditText 변수 studyDesc 생성, XML의 studyDesc에 대응시킴
                studyDesc = (EditText) makeview.findViewById(R.id.studyDesc);

                //PositiveButton을 누르면 입력한 데이터를 InsertData class에 옮기는?
                dlg.setPositiveButton("생성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //랜덤인수 넣어서 id 생성?
                        int max_num_value = 999;
                        int min_num_value = 100;
                        Random random = new Random();
                        int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;

                        String study_id = "2021" + randomNum;
                        String study_name = studyName.getText().toString();
                        String study_desc = studyDesc.getText().toString();

                        InsertData task = new InsertData();
                        //php파일 ip주소 연결
                        task.execute("http://" + IP_ADDRESS + "/dowith/Study_insert.php", study_id,study_name,study_desc);

                        //Intent 생성, study_chat로 화면 전환
                        Intent intent = new Intent(getActivity(), study_chat.class);
                        //study_chat 액티비티 실행
                        startActivity(intent);
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        //스터디 가입 버튼을 누르면 화면 전환하는 코드
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 생성, study_join로 화면 전환
                Intent intent = new Intent(getActivity(), study_join.class);
                //study_join 액티비티 실행
                startActivity(intent);
            }
        });
        return view;
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        // 스레드가 시작하기 전에 수행할 작업(메인 스레드)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //php 파일에 데이터 보내기 전에 화면에 로딩 중 띄우는 코드?
            progressDialog = progressDialog.show(study.this.getActivity(),
            "Please Wait", null, true, true);
        }

        // 스레드 작업이 모두 끝난 후에 수행할 작업(메인 스레드)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        //mysql에 데이터를 넣을 php 파일과 연동하는 코드?
        //데이터베이스에 들어갈 데이터...
        // 스레드가 수행할 작업(생성된 스레드)
        @Override
        protected String doInBackground(String... params) {

            String study_id = (String) params[1];
            String study_name = (String)params[2];
            String study_desc = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "study_id=" + study_id + "&study_name=" + study_name + "&study_desc=" + study_desc;


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

            progressDialog = ProgressDialog.show(study.this.getActivity(),
                    "데이터를 불러오는 중...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(study.this.getActivity(), result, Toast.LENGTH_SHORT);
            Log.d(TAG, "response - " + result);

            if (result == null){

                Toast.makeText(study.this.getActivity(), errorString, Toast.LENGTH_SHORT);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "study_name=" + params[1];


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

        String TAG_JSON="studies";
        String TAG_ID = "study_id";
        String TAG_NAME = "study_name";
        String TAG_DESC ="study_desc";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String study_id = item.getString(TAG_ID);
                String study_name = item.getString(TAG_NAME);
                String study_desc = item.getString(TAG_DESC);

                studyitem studyitem = new studyitem();

                studyitem.setItem_id(study_id);
                studyitem.setItem_name(study_name);
                studyitem.setItem_desc(study_desc);

                studylist.add(studyitem);
                studylistadapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
