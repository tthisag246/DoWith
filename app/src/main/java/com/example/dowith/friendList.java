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


        // 먼저 리소스 파일인 Arraylist를 만듦
        ArrayList<String> friend_lists = new ArrayList<String>();
        friend_lists.add("공부안하면내가개");
        friend_lists.add("념념냠냠");
        friend_lists.add("지현쓰");
        friend_lists.add("가은금은");
        friend_lists.add("한민민");
        friend_lists.add("U혜");
        friend_lists.add("hansadfn");
        friend_lists.add("엠비씨유료방송");
        friend_lists.add("강쥐");

        //ArrayAdapter를 만들어줌
        //android.R.layout에서 제공하는(단순히 TextView가 하나 있는 xml)custom layout을 이용함
        //ArrayList words 를 마지막 인수로 넣어줌
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.friend_list_view, friend_lists);


        //freind_list_view.xml파일에 있는 listView를 불러옴
        ListView listView = (ListView) findViewById(R.id.friend_listview);

        //SetAdapter를 이용해 ListView와 ArrayAdapter를 연결함
        listView.setAdapter(itemsAdapter);

        //친구목록 누르면 이벤트 발생
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 아이템 이름 가져옴
                ListView listview = (ListView) parent;
                String clickedFriends = (String) listview.getItemAtPosition(position);
                // 토스트 메세지로 표시
//                Toast.makeText(friendList.this, clickedFriends + "을(를) 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
                View friend1View = (View) View.inflate(friendList.this, R.layout.friend1, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(friendList.this);
                dlg.setTitle(clickedFriends);
                dlg.setIcon(R.drawable.ic_baseline_person_pin_24); //아이콘 지정
                dlg.setView(friend1View);
                dlg.setPositiveButton("방문", new DialogInterface.OnClickListener() {
                    //방문 버튼을 누르면
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(friendList.this, friendRoom.class);
                        startActivity(intent);
                    }
                });
                //뒤로가기 버튼을 누르면
                dlg.setNegativeButton("뒤로가기", null);
                dlg.show();

            }
        });
    }
}