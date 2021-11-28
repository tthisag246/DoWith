package com.example.dowith;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class list extends Fragment {

    private View view;
    ArrayList<listItem> tdList;
    ListView listView;
    ImageButton btnFilterList, btnSortList, btnAddList;
    listAdapter listAdapter;
    ScrollView listScrollView;

    private static String IP_ADDRESS = "hanmao2.iptime.org";
    private static String TAG = "todoListDB";

    String mJsonString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);


        //일정 목록을 담을 ArrayList<listItem> 변수 tdList 생성
        tdList = new ArrayList<listItem>();
        //리스트뷰 변수 listView 생성, XML의 list에 대응시킴
        listView = (ListView) view.findViewById(R.id.list);
        //커스텀 리스트뷰 listAdapter 변수 listAdapter 생성, list_item으로 형식 지정, tdList 배열 적용
        listAdapter = new listAdapter(getActivity(), R.layout.list_item, tdList);
        //버튼 변수 btnFilterList 생성, XML의 btnFilterList에 대응시킴
        btnFilterList = (ImageButton) view.findViewById(R.id.btnFilterList);
        //버튼 변수 btnSortList 생성, XML의 btnSortList에 대응시킴
        btnSortList = (ImageButton) view.findViewById(R.id.btnSortList);
        //버튼 변수 btnAddList 생성, XML의 btnAddList에 대응시킴
        btnAddList = (ImageButton) view.findViewById(R.id.btnAddList);

        listScrollView = (ScrollView) view.findViewById(R.id.listScrollView);

        listView.setAdapter(listAdapter);

        tdList.clear();
        listAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/dowith/list_getjson.php", "");

//        //add() 메소드로 tdList에 항목 추가
//        //추후에 DB와 연동되는 코드로 수정 필요
//        tdList.add(new listItem("아침 달리기", "운동", "08:00~09:00", "가기 전 스트레칭하기!", false));
//        tdList.add(new listItem("독서 10분", "취미", "09:00~10:00", "아몬드 읽기", false));
//        tdList.add(new listItem("영단어50 외기", "취미", "10:00~11:00", "전날 거 꼭 복습하기", false));
//        tdList.add(new listItem("독서 10분", "취미", "09:00~10:00", "아몬드 읽기", false));
//        tdList.add(new listItem("저녁 운동", "운동", "22:00~23:00", "텀블러 챙기기", false));
//        tdList.add(new listItem("펠트 공예", "취미", "16:00~16:20", "남은거 마저 하기", false));
//        tdList.add(new listItem("아침 달리기", "운동", "08:00~09:00", "가기 전 스트레칭하기!", false));
//        tdList.add(new listItem("독서 10분", "취미", "09:00~10:00", "아몬드 읽기", false));

        //listAdapter를 listView에 적용

        btnFilterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(list.this.getActivity());
                dlg.setTitle("일정 종류");
                dlg.setItems(new String[]{"공부", "운동", "아침루틴", "저녁루틴", "취미"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //필터
                    }
                });
                dlg.setPositiveButton("취소", null);
                dlg.show();
            }
        });

        //btnSortList를 길게 클릭하면 컨텍스트 메뉴 띄움
        registerForContextMenu(btnSortList);

        //listView를 클릭하면 실행하는 코드 (체크 표시)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tdList.get(i).setItem_done(!tdList.get(i).getItem_done());
                listAdapter.notifyDataSetChanged();
            }
        });

        //listView를 길게 클릭하면 컨텍스트 메뉴 띄움
        registerForContextMenu(listView);

        //btnAddList 버튼을 클릭하면 실행하는 코드
        btnAddList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent 생성, list_add의 클래스 listAdd를 넘김
                Intent intent = new Intent(getActivity(), listAdd.class);
                //list_add 액티비티 실행
                startActivity(intent);
            }
        });
        return view;
    }


    //컨텍스트 메뉴를 등록한다.
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getActivity().getMenuInflater();
        if(v == btnSortList) {
            mInflater.inflate(R.menu.list_sort_menu, menu);
        }
        else if(v == listView) {
            mInflater.inflate(R.menu.list_menu, menu);
        }
    }

    //메뉴를 클릭했을 때 동작할 메소드
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listSortTitle:
                //제목으로 정렬
                Comparator<listItem> cmpTitle = new Comparator<listItem>() {
                    @Override
                    public int compare(listItem t1, listItem t2) {
                        return t1.listTitle.compareTo(t2.listTitle);
                    }
                };

                Collections.sort(tdList, cmpTitle);

                listAdapter.notifyDataSetChanged();
                break;
            case R.id.listSortTime:
                //시간으로 정렬
                Comparator<listItem> cmpTime = new Comparator<listItem>() {
                    @Override
                    public int compare(listItem t1, listItem t2) {
                        return t1.listTime.compareTo(t2.listTime);
                    }
                };

                Collections.sort(tdList, cmpTime);

                listAdapter.notifyDataSetChanged();
                break;
            case R.id.listItemEdit:
                //Intent 생성, list_edit의 클래스 listEdit를 넘김
                Intent intent = new Intent(getActivity(), listEdit.class);
                //list_edit 액티비티 실행
                startActivity(intent);
                break;
            case R.id.listItemDelete:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;
                tdList.remove(index);
                listAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(list.this.getActivity(),
                    "데이터를 불러오는 중...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(list.this.getActivity(), result, Toast.LENGTH_SHORT);
            Log.d(TAG, "response - " + result);

            if (result == null){

                Toast.makeText(list.this.getActivity(), errorString, Toast.LENGTH_SHORT);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "list_name=" + params[1];


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

        String TAG_JSON="todo_lists"; //테이블명
        String TAG_ID = "td_id";
        String TAG_NAME ="td_name";
        String TAG_CONTENT ="td_content";
        String TAG_CATE ="td_cate";
        String TAG_START ="td_start";
        String TAG_FINISH ="td_finish";
        String TAG_YN = "td_yn";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String td_id = item.getString(TAG_ID);
                String td_name = item.getString(TAG_NAME);
                String td_content = item.getString(TAG_CONTENT);
                String td_cate = item.getString(TAG_CATE);
                String td_start = item.getString(TAG_START);
                String td_finish = item.getString(TAG_FINISH);
                String td_yn = item.getString(TAG_YN);


                listItem listItem = new listItem();

                listItem.setItem_ID(td_id);
                listItem.setItem_title(td_name);
                listItem.setItem_memo(td_content);
                listItem.setItem_type(td_cate);
                listItem.setItem_time("시작: " + td_start + " 종료:" + td_finish);
                listItem.setItem_done(td_yn == "1"? true:false);


                tdList.add(listItem);
                listAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}