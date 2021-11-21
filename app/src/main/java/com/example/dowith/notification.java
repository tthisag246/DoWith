package com.example.dowith;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class notification extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        //방 꾸미기 페이지를 보여줌

        back = (ImageButton) findViewById(R.id.back_noti);


        cl = new View.OnClickListener() {  //뒤로가기 버튼 누르면 화면 종료되도록 설정
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back_noti:
                        finish();
                        break;
                }
            }
        };
        back.setOnClickListener(cl);


        // 먼저 리소스 파일인 Arraylist를 만듦
        ArrayList <notiItem> noti_words = new ArrayList<notiItem>();
        noti_words.add(new notiItem("'네트워크 과제 제출' 일정이 종료되었습니다!"));
        noti_words.add(new notiItem("'공부안하면내가개' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'연말정산' 이벤트가 등록되었습니다. 확인해보세요!"));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'체력거지' 스터디에서 새 공지를 등록했습니다."));
        noti_words.add(new notiItem("'토익 990점 맞자' 스터디에서 새로운 공지를 등록했습니다."));
        noti_words.add(new notiItem("'눈나나' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'플젝 과제 제출' 일정이 종료되었습니다!"));
        noti_words.add(new notiItem("'김냠' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'먹고죽자' 스터디에서 새 공지를 등록했습니다."));
        noti_words.add(new notiItem("'토익 990점 맞자' 스터디에서 새로운 공지를 등록했습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'플젝 과제 제출' 일정이 종료되었습니다!"));
        noti_words.add(new notiItem("'김냠' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'눈나나' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'플젝 과제 제출' 일정이 종료되었습니다!"));
        noti_words.add(new notiItem("'김냠' 님이 친구 추가하셨습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'먹고죽자' 스터디에서 새 공지를 등록했습니다."));
        noti_words.add(new notiItem("'토익 990점 맞자' 스터디에서 새로운 공지를 등록했습니다."));
        noti_words.add(new notiItem("'매일 30분 운동하기' 일정 시작 5분 전입니다!"));
        noti_words.add(new notiItem("'플젝 과제 제출' 일정이 종료되었습니다!"));
        noti_words.add(new notiItem("'김냠' 님이 친구 추가하셨습니다."));

        //ArrayAdapter를 만들어줌
        //android.R.layout에서 제공하는(단순히 TextView가 하나 있는 xml)custom layout을 이용함
        //ArrayList words 를 마지막 인수로 넣어줌
        notiAdapter notiAdapter =
                new notiAdapter(this, R.layout.noti_item, noti_words);



        //notifacation.xml파일에 있는 listView를 불러옴
        ListView listView = (ListView) findViewById(R.id.noti_list);

        //SetAdapter를 이용해 ListView와 ArrayAdapter를 연결함
        listView.setAdapter(notiAdapter);

    }

}
