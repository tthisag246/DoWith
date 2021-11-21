package com.example.dowith;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class study_join extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_join);

        //서치뷰 변수 joinSearchView 생성, XML의 joinSearchView에 대응시킴
        SearchView joinSearchView = (SearchView) findViewById(R.id.joinSearchView);
        //ArrayList<studyitem> 변수 studySearchList 생성
        ArrayList<studyitem> studySearchList = new ArrayList<studyitem>();
        //커스텀 리스트뷰 studyadapter 변수 생성, study_item으로 형식 지정, studySearchadapter 배열 적용
        studyadapter studySearchadapter = new studyadapter(this, R.layout.study_item, studySearchList);

        //리스트뷰 변수 joinlistView 생성, XML의 joinList에 대응시킴
        ListView joinlistView = (ListView) findViewById(R.id.joinList);
        //버튼 변수 back2 생성, XML의 back2에 대응시킴
        ImageButton back2 = (ImageButton) findViewById(R.id.back2);

        //joinlist에 리스트 추가
        ArrayList <studyitem> joinlist = new ArrayList<studyitem>();
        joinlist.add(new studyitem("배구 모임"));
        joinlist.add(new studyitem("영어 회화"));
        joinlist.add(new studyitem("가가중 3-2 study"));
        joinlist.add(new studyitem("드로잉 공부"));
        joinlist.add(new studyitem("뜨개질 기초"));
        joinlist.add(new studyitem("C언어 응용"));
        joinlist.add(new studyitem("프랑스어 기초"));
        joinlist.add(new studyitem("논술 심화"));
        joinlist.add(new studyitem("공리 학원 2학년 모임"));
        joinlist.add(new studyitem("제과제빵 따라하기"));
        joinlist.add(new studyitem("Python 기초"));
        joinlist.add(new studyitem("문문여고 1-2반"));
        joinlist.add(new studyitem("수학 mentoring"));
        joinlist.add(new studyitem("야구 동호회"));
        joinlist.add(new studyitem("네일아트 꿀팁"));
        joinlist.add(new studyitem("간단 코디"));
        joinlist.add(new studyitem("전통 문학 배움터"));
        joinlist.add(new studyitem("간편한 1인 요리"));

        //joinSearchView 검색 시 작동하는 리스너 생성
        joinSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                studySearchList.clear();

                for(int i = 0; i < joinlist.size(); i++) {
                    if ( joinlist.get(i).getItem_title().contains(newText)) {
                        studySearchList.add(joinlist.get(i));
                    }
                }
                joinlistView.setAdapter(studySearchadapter);
                return true;
            }
        });

        //ArrayAdapter<String> 형 변수 선언, 리스트뷰 형식 지정, joinlist 배열 적용
        studyadapter studyadapter = new studyadapter(this, R.layout.study_item, joinlist);
        //adapter를 joinlistView에 적용
        joinlistView.setAdapter(studyadapter);

        //joinlistView 클릭하면 실행하는 코드
        joinlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Intent 생성, study_chat 화면 전환
                Intent intent = new Intent(study_join.this, study_chat.class);
                //study_chat 액티비티 실행
                startActivity(intent);
            }
        });
        //뒤로가기 버튼 클릭 시 이전 화면으로 이동
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
