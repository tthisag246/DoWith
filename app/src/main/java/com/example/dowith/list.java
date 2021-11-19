package com.example.dowith;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;

public class list extends Fragment {

    //깃허브 테스트
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.list,container,false);

        //일정 목록을 담을 ArrayList<listItem> 변수 tdList 생성
        ArrayList<listItem> tdList = new ArrayList<listItem>();
        //리스트뷰 변수 listView 생성, XML의 list에 대응시킴
        ListView listView = (ListView) view.findViewById(R.id.list);
        //버튼 변수 btnAddList 생성, XML의 btnAddList에 대응시킴
        ImageButton btnAddList = (ImageButton) view.findViewById(R.id.btnAddList);
        //커스텀 리스트뷰 listAdapter 변수 listAdapter 생성, list_item으로 형식 지정, tdList 배열 적용
        listAdapter listAdapter = new listAdapter(getActivity(), R.layout.list_item, tdList);

        //add() 메소드로 tdList에 항목 추가
        //추후에 DB와 연동되는 코드로 수정 필요
        tdList.add(new listItem("아침 달리기", "운동", "08:00~09:00", "가기 전 스트레칭하기!"));
        tdList.add(new listItem("독서 10분", "취미", "09:00~10:00", "아몬드 읽기"));
        tdList.add(new listItem("아침 달리기", "운동", "08:00~09:00", "가기 전 스트레칭하기!"));
        tdList.add(new listItem("독서 10분", "취미", "09:00~10:00", "아몬드 읽기"));

        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //listAdapter를 listView에 적용
        listView.setAdapter(listAdapter);

//        //listView를 길게 클릭하면 실행하는 코드
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //해당 일정 삭제
//                tdList.remove(position);
//                listAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });

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
}