package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class friendList extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        back = (ImageButton) findViewById(R.id.back_flist);


        cl = new View.OnClickListener() {  //뒤로가기 버튼 누르면 화면 종료되도록 설정
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back_flist:
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(cl);

        //서치뷰 변수 friendSearchView 생성, XML의 friendSearchView에 대응시킴
        SearchView friendSearchView = (SearchView) findViewById(R.id.friendSearchView);
        //ArrayList<friendItem> 변수 friendSearchList 생성
        ArrayList<friendItem> friendSearchList = new ArrayList<friendItem>();
        //커스텀 리스트뷰 friendAdapter 변수 생성, friend_item으로 형식 지정, friendSearchList 배열 적용
        friendAdapter friendSearchAdapter = new friendAdapter(this, R.layout.friend_item, friendSearchList);
        //공간 목록을 담을 ArrayList<friendItem> 변수 friendList 선언
        ArrayList<friendItem> friendList = new ArrayList<friendItem>();
        //friendAdapter 변수 friendListAdapter 생성, friend_item으로 형식 지정, friendList 배열 적용
        friendAdapter friendListAdapter = new friendAdapter(this, R.layout.friend_item, friendList);
        //리스트뷰 변수 friendListView 생성, XML의 friendList에 대응시킴
        ListView friendListView = (ListView) findViewById(R.id.friendListView);

        //adapter를 friendListView에 적용
        friendListView.setAdapter(friendListAdapter);
        //friendSearchView로 검색 시 작동하는 리스너 생성
        friendSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendSearchList.clear();

                for (int i = 0; i < friendList.size(); i++) {
                    if (friendList.get(i).getItem_title().contains(newText)) {
                        friendSearchList.add(friendList.get(i));
                    }
                }
                friendListView.setAdapter(friendSearchAdapter);
                return true;
            }
        });
        // 먼저 리소스 파일인 Arraylist를 만듦
        friendList.add(new friendItem("공부안하면내가개"));
        friendList.add(new friendItem("념념냠냠"));
        friendList.add(new friendItem("지현쓰"));
        friendList.add(new friendItem("가은금은"));
        friendList.add(new friendItem("한민민"));
        friendList.add(new friendItem("U혜"));
        friendList.add(new friendItem("hansadfn"));
        friendList.add(new friendItem("엠비씨유료방송"));
        friendList.add(new friendItem("고앵"));
        friendList.add(new friendItem("하리보젤리"));
        friendList.add(new friendItem("슐웩"));
        friendList.add(new friendItem("오만원"));
        friendList.add(new friendItem("테스트"));
        friendList.add(new friendItem("what"));
        friendList.add(new friendItem("쿠우"));
        friendList.add(new friendItem("둘째동생"));


        //freind_list_view.xml파일에 있는 listView를 불러옴
        ListView listView = (ListView) findViewById(R.id.friendListView);

        //친구목록 누르면 이벤트 발생
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(friendList.this);
                dlg.setTitle(friendList.get(arg2).getItem_title());
                dlg.setMessage("방문하시겠습니까?");
                dlg.setPositiveButton("방문", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent 생성, friend_library의 클래스 friendLibrary를 넘김
                        Intent intent = new Intent(friendList.this, friendRoom.class);
                        //friend_library 액티비티 실행
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }
}
    