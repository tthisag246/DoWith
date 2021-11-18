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

import java.util.ArrayList;

public class study_join extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_join);

        //리스트뷰 변수 joinlistView 생성, XML의 joinList에 대응시킴
        ListView joinlistView = (ListView) findViewById(R.id.joinList);
        //버튼 변수 back2 생성, XML의 back2에 대응시킴
        ImageButton back2 = (ImageButton) findViewById(R.id.back2);

        //joinlist에 리스트 추가
        ArrayList <String> joinlist = new ArrayList<String>();
        joinlist.add("운동 스터디");
        joinlist.add("영어 회화 스터디");
        joinlist.add("수학 멘토멘티");
        joinlist.add("가가중 3-2반 스터디 모임");
        joinlist.add("드로잉 공부");

        //ArrayAdapter<String> 형 변수 선언, 리스트뷰 형식 지정, joinlist 배열 적용
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, joinlist);
        //adapter를 joinlistView에 적용
        joinlistView.setAdapter(adapter);

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