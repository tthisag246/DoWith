package com.example.dowith;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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


public class place extends Fragment {

    // 변수 선언
    private View view;
    View placeAddView;
    ArrayList<placeItem> placeList;
    placeAdapter placeListAdapter, placeSearchAdapter;
    ListView placeListView;
    ImageButton btnAddPlace;
    public static String placeName;
    SearchView placeSearchView;
    ArrayList<placeItem> placeSearchList;

    private static String IP_ADDRESS = "dowith0server.dothome.co.kr";
    private static String TAG = "placeDB";
    String mJsonString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.place,container,false);

        // xml에 생성한 위젯을 변수에 대입
        placeSearchView = (SearchView) view.findViewById(R.id.placeSearchView);
        placeSearchList = new ArrayList<placeItem>();
        placeSearchAdapter = new placeAdapter(getActivity(), R.layout.place_item, placeSearchList);
        btnAddPlace = (ImageButton) view.findViewById(R.id.btnAddPlace);
        placeListView = (ListView) view.findViewById(R.id.placeListView);
        placeList = new ArrayList<placeItem>();
        // 커스텀 리스트뷰 placeAdapter 변수 placeListAdapter 생성, place_item으로 형식 지정, placeList 배열 적용
        placeListAdapter = new placeAdapter(getActivity(), R.layout.place_item, placeList);

        // listAdapter를 listView에 적용
        placeListView.setAdapter(placeListAdapter);

        // 쿼리 리스너 지정
        placeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeSearchList.clear();

                for(int i = 0; i < placeList.size(); i++) {
                    if ( placeList.get(i).getItem_name().contains(newText)) {
                        placeSearchList.add(placeList.get(i));
                    }
                }
                placeListView.setAdapter(placeSearchAdapter);
                return true;
            }
        });

        // placeList를 null로 초기화, size를 0으로 설정
        placeList.clear();
        // placeListAdapter 갱신
        placeListAdapter.notifyDataSetChanged();

        // DB에서 데이터 가져오는 객체 생성
        GetData task = new GetData();
        // http 링크를 실행
        task.execute( "http://" + IP_ADDRESS + "/dowith/place_getjson.php", "");

        // 공간 목록 아이템 클릭 리스너 지정
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(place.this.getActivity());
                dlg.setTitle("\""+ placeList.get(arg2).getItem_name() + "\"에 입장합니다.");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        placeName = placeList.get(arg2).getItem_name();
                        // Intent 생성, place_library의 클래스 placeLibrary를 넘김
                        Intent intent = new Intent(getActivity(), placeLibrary.class);
                        // place_library 액티비티 실행
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        // 공간 생성 클릭 리스너
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // place_add 파일을 인플레이트하여 placeAddView에 대입
                placeAddView = (View) View.inflate(place.this.getActivity(), R.layout.place_add, null);

                // 대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(place.this.getActivity());
                TextView dlgTitle = (TextView) v.findViewById(R.id.placeAddTitle);
                dlg.setCustomTitle(dlgTitle);
                dlg.setIcon(R.drawable.add);
                dlg.setView(placeAddView);

                // 변수 선언
                EditText place_name_edit;
                EditText place_desc_edit;
                RadioGroup placeCateRG = (RadioGroup) placeAddView.findViewById(R.id.placeTheme);
                place_name_edit = (EditText) placeAddView.findViewById(R.id.placeTitle);
                place_desc_edit = (EditText) placeAddView.findViewById(R.id.placeMemo);

                dlg.setPositiveButton("생성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String place_name = place_name_edit.getText().toString();
                        String place_desc = place_desc_edit.getText().toString();
                        RadioButton selectPlaceCate = (RadioButton) placeAddView.findViewById(placeCateRG.getCheckedRadioButtonId());
                        String p_cate_id = (selectPlaceCate.getText().toString().equals("도서관") ? "1" : "2" );

                        // DB에 데이터 삽입하는 객체 생성
                        InsertData task = new InsertData();
                        // http 링크를 실행
                        task.execute("http://" + IP_ADDRESS + "/dowith/place_insert.php", place_name, place_desc, p_cate_id);

                        // 공간명 저장
                        placeName = place_name;
                        // Intent 생성, place_library의 클래스 placeLibrary를 넘김
                        Intent intent = new Intent(getActivity(), placeLibrary.class);
                        // place_library 액티비티 실행
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(place.this.getActivity(),
                    "공간을 추가합니다...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //Toast.makeText(place.this.getActivity(), result, Toast.LENGTH_SHORT);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String place_name = (String) params[1];
            String place_desc = (String) params[2];
            String p_cate_id = (String) params[3];

            String serverURL = (String)params[0];
            String postParameters = "place_name=" + place_name + "&place_desc=" + place_desc + "&p_cate_id=" + p_cate_id;


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

            progressDialog = ProgressDialog.show(place.this.getActivity(),
                    "데이터를 불러오는 중...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(place.this.getActivity(), result, Toast.LENGTH_SHORT);
            Log.d(TAG, "response - " + result);

            if (result == null){

                Toast.makeText(place.this.getActivity(), errorString, Toast.LENGTH_SHORT);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "place_name=" + params[1];


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

        String TAG_JSON="places";
        String TAG_ID = "place_id";
        String TAG_NAME = "place_name";
        String TAG_DESC ="place_desc";
        String TAG_CATE ="p_cate_id";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String place_id = item.getString(TAG_ID);
                String place_name = item.getString(TAG_NAME);
                String place_desc = item.getString(TAG_DESC);
                String p_cate_id = item.getString(TAG_CATE);

                placeItem placeItem = new placeItem();

                placeItem.setItem_id(place_id);
                placeItem.setItem_name(place_name);
                placeItem.setItem_desc(place_desc);
                placeItem.setItem_cate(p_cate_id.equals("1") ? "도서관" : "회의실");

                placeList.add(placeItem);
                placeListAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}