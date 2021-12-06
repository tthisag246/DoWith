package com.example.dowith;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

// 내비게이션 바 이동을 위해 Fragment 클래스를 상속받음
public class home extends Fragment
{

    private View view;
    ImageView imageView;
    GestureDetector detector;
    float prevX, prevY;

    //캐릭터 이동 메서드
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.home,container,false);
        imageView = (ImageView) view.findViewById(R.id.sd);

        //캐릭터 이미지를 움직이는 함수
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    //터치했을 때
                    case MotionEvent.ACTION_DOWN:
                        prevX = event.getX();
                        prevY = event.getY();
                    break;
                    //터치 뒤 드래그 할 때
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getX() - prevX;
                        float dy = event.getY() - prevY;
                        v.setX(v.getX() + dx); v.setY(v.getY() + dy);
                        break;
                        //터치 해제했을 때
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        break;
                } return true;
            }
        });



        Button btn_cha = (Button) view.findViewById(R.id.btnEditC); //btnEditC버튼 지정하는 버튼 변수명
        btn_cha.setOnClickListener(new View.OnClickListener() { //btnEditC 버튼을 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editCharacter.class);  //Intent 생성하여 edit_character의 클래스 editCharacter를 넘김
                startActivity(intent);  // edit_character 액티비티 실행
            }
        });

        Button btn_room = (Button) view.findViewById(R.id.btnEditR); //btnEditR버튼 지정하는 버튼 변수명
        btn_room.setOnClickListener(new View.OnClickListener() { //btnEditR 버튼을 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editRoom.class);  //Intent 생성하여 edit_room의 클래스 editroom을 넘김
                startActivity(intent);  // edit_room 액티비티 실행
            }
        });

        ImageButton btn_friend = (ImageButton) view.findViewById(R.id.friend_list); //friendlist버튼 지정하는 버튼 변수명
        btn_friend.setOnClickListener(new View.OnClickListener() { //friendlist 버튼을 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), friendList.class);  //Intent 생성하여 friend_list의 클래스 friendList을 넘김
                startActivity(intent);  // friend_list의 액티비티 실행
            }
        });
        return view; //홈 화면 그대로 보여줌
    }
}