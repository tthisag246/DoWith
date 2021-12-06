package com.example.dowith;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;
import android.widget.PopupMenu;
import android.widget.ScrollView;
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

public class list extends Fragment {

    // 변수 선언
    private View view;
    ArrayList<listItem> tdList = new ArrayList<listItem>();
    ListView listView;
    ImageButton btnFilterList, btnSortList, btnAddList;
    listAdapter listAdapter;
    ScrollView listScrollView;
    View.OnClickListener cl;

    private static final String IP_ADDRESS = "dowith0server.dothome.co.kr";
    private static final String TAG = "todoListDB";
    String mJsonString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);

        // xml에 생성한 위젯을 변수에 대입
        btnFilterList = (ImageButton) view.findViewById(R.id.btnFilterList); // 일정 필터 버튼
        btnSortList = (ImageButton) view.findViewById(R.id.btnSortList); // 일정 정렬 버튼
        btnAddList = (ImageButton) view.findViewById(R.id.btnAddList); // 일정 추가 버튼
        listScrollView = (ScrollView) view.findViewById(R.id.listScrollView); // 스크롤뷰
        listView = (ListView) view.findViewById(R.id.list); // 일정 목록 리스트뷰

        // 커스텀 리스트뷰 listAdapter 변수 listAdapter 생성, list_item으로 형식 지정, tdList 배열 적용
        listAdapter = new listAdapter(getActivity(), R.layout.list_item, tdList);

        // listAdapter를 listView에 적용
        listView.setAdapter(listAdapter);

        // tdList를 null로 초기화, size를 0으로 설정
        tdList.clear();
        // listAdapter 갱신
        listAdapter.notifyDataSetChanged();

        // DB에서 데이터 가져오는 객체 생성
        GetData task = new GetData();
        // http 링크를 실행
        task.execute( "http://" + IP_ADDRESS + "/dowith/list_getjson.php", "");

        // 일정 정렬, 필터 클릭 리스너 설정
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 팝업 메뉴 변수 선언
                PopupMenu popup = new PopupMenu(getActivity(), view);
                // 클릭한 버튼에 따라 실행할 switch문
                switch ( view.getId() ) {
                    // 일정 필터 버튼 클릭 시 실행
                    case R.id.btnFilterList :
                        getActivity().getMenuInflater().inflate(R.menu.list_filter_menu, popup.getMenu());
                        break;

                    // 일정 정렬 버튼 클릭 시 실행
                    case R.id.btnSortList :
                        getActivity().getMenuInflater().inflate(R.menu.list_sort_menu, popup.getMenu());
                        // 팝업 메뉴 아이템 클릭 리스너 지정
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                // 클릭한 메뉴 아이템에 따라 실행할 switch문
                                switch ( menuItem.getItemId() ) {
                                    // 제목 클릭 시 실행
                                    case R.id.listSortTitle :
                                        Comparator<listItem> cmpTitle = new Comparator<listItem>() {
                                            @Override
                                            public int compare(listItem t1, listItem t2) {
                                                // 제목으로 비교
                                                return t1.getItem_title().compareTo(t2.getItem_title());
                                            }
                                        };
                                        // 정렬
                                        Collections.sort(tdList, cmpTitle);
                                        break;
                                    // 시간 클릭 시 실행
                                    case R.id.listSortTime :
                                        Comparator<listItem> cmpTime = new Comparator<listItem>() {
                                            @Override
                                            public int compare(listItem t1, listItem t2) {
                                                // 시간으로 비교
                                                return t1.getItem_time().compareTo(t2.getItem_time());
                                            }
                                        };
                                        // 정렬
                                        Collections.sort(tdList, cmpTime);
                                        break;
                                }
                                // listAdapter 갱신
                                listAdapter.notifyDataSetChanged();
                                return false;
                            }
                        });
                }
                popup.show();
            }
        };
        // 일정 정렬, 필터 클릭 리스너 지정
        btnFilterList.setOnClickListener(cl);
        btnSortList.setOnClickListener(cl);

        // 일정 목록 아이템 클릭 리스너 지정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 완료 여부를 토글로 입력
                tdList.get(i).setItem_done(!tdList.get(i).getItem_done());
                // listAdapter 갱신
                listAdapter.notifyDataSetChanged();
            }
        });

        // 일정 목록 아이템 롱클릭 리스너 지정
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 팝업 메뉴 변수 선언
                PopupMenu popup = new PopupMenu(getActivity(), view);
                getActivity().getMenuInflater().inflate(R.menu.list_menu, popup.getMenu());

                // 팝업 메뉴 아이템 클릭 리스너 지정
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // 클릭한 메뉴 아이템에 따라 실행할 switch문
                        switch (menuItem.getItemId()) {
                            // 편집 클릭 시 실행
                            case R.id.listItemEdit:
                                // Intent 생성, list_edit의 클래스 listEdit를 넘김
                                Intent intent = new Intent(getActivity(), listEdit.class);
                                // list_edit 액티비티 실행
                                startActivity(intent);
                                break;
                            // 삭제 클릭 시 실행
                            case R.id.listItemDelete:
                                // 해당 일정 삭제
                                tdList.remove(i);
                                // listAdapter 갱신
                                listAdapter.notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });

        // 일정 추가 버튼 클릭 리스너 지정
        btnAddList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Intent 생성, list_add의 클래스 listAdd를 넘김
                Intent intent = new Intent(getActivity(), listAdd.class);
                // list_add 액티비티 실행
                startActivity(intent);
            }
        });
        return view;
    }

    // DB에서 데이터 가져오는 클래스 선언
    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        // AsyncTask의 작업을 시작하기 전에 가장 먼저 한 번 호출됨
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(list.this.getActivity(),
                    "데이터를 불러오는 중...", null, true, true);
        }

        // AsyncTask의 모든 작업이 완료된 후 가장 마지막에 한 번 호출
        // doInBackground() 메서드의 최종 값을 받기 위해 사용
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            // 만약 결괏값이 비었다면
            if (result == null){
                // 로그 출력
                Log.d(TAG, "null error - " + errorString);
            }
            else {
                mJsonString = result;
                // showResult 메서드 호출
                showResult();
            }
        }

        // 스레드에 의해 처리될 내용을 담기 위한 메서드
        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "list_name=" + params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // httpURL 연결 시간 지정
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                // 바이트 단위 데이터 출력을 위한 최상위 스트림 클래스
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // http 상태 코드
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                // 문자 단위 입출력을 위한 하위 스트림 클래스
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

    // 결과 출력 메서드
    private void showResult(){
        // 테이블명과 컬럼명을 상수로 지정
        //테이블명
        String TAG_JSON="todo_lists";
        //컬럼명
        String TAG_ID = "td_id";
        String TAG_NAME ="td_name";
        String TAG_CONTENT ="td_content";
        String TAG_CATE ="td_cate";
        String TAG_START ="td_start";
        String TAG_FINISH ="td_finish";
        String TAG_YN = "td_yn";

        try {
            // 중괄호에 들어갈 속성 정의
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            // jsonArray 길이만큼 반복
            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                // item의 데이터를 가져옴
                String td_id = item.getString(TAG_ID);
                String td_name = item.getString(TAG_NAME);
                String td_content = item.getString(TAG_CONTENT);
                String td_cate = item.getString(TAG_CATE);
                String td_start = item.getString(TAG_START);
                String td_finish = item.getString(TAG_FINISH);
                String td_yn = item.getString(TAG_YN);

                // listItem 객체 생성
                listItem listItem = new listItem();

                // listItem의 필드에 값 대입
                listItem.setItem_ID(td_id);
                listItem.setItem_title(td_name);
                listItem.setItem_memo(td_content);
                switch (td_cate) {
                    case "1" :
                        listItem.setItem_type("공부");
                        break;
                    case "2" :
                        listItem.setItem_type("운동");
                        break;
                    case "3" :
                        listItem.setItem_type("아침루틴");
                        break;
                    case "4" :
                        listItem.setItem_type("저녁루틴");
                        break;
                    case "5" :
                        listItem.setItem_type("취미");
                        break;
                }
                listItem.setItem_time(td_start.substring(0,5) + " ~ " + td_finish.substring(0,5));
                listItem.setItem_done(td_yn == "1" ? true : false);

                // tdList에 listItem 추가
                tdList.add(listItem);
                // listAdapter 갱신
                listAdapter.notifyDataSetChanged();
            }

        // 오류 발생하면 showResult 태그 붙여 로그 출력
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}