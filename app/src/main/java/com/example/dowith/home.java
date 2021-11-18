package com.example.dowith;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class home extends Fragment // Fragment 클래스를 상속받아야한다
{

    private View view;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.home,container,false); //
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