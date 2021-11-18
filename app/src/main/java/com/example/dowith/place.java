package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class place extends Fragment {

    private View view;
    View placeEnterView, placeAddView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.place,container,false);

        //서치뷰 변수 placeSearchView 생성, XML의 placeSearchView에 대응시킴
        SearchView placeSearchView = (SearchView) view.findViewById(R.id.placeSearchView);
        //ArrayList<placeItem> 변수 placeSearchList 생성
        ArrayList<placeItem> placeSearchList = new ArrayList<placeItem>();
        //커스텀 리스트뷰 placeAdapter 변수 생성, place_item으로 형식 지정, placeSearchList 배열 적용
        placeAdapter placeSearchAdapter = new placeAdapter(getActivity(), R.layout.place_item, placeSearchList);
        //공간 목록을 담을 ArrayList<placeItem> 변수 placeList 선언
        ArrayList<placeItem> placeList = new ArrayList<placeItem>();
        //placeAdapter 변수 placeListAdapter 생성, place_item으로 형식 지정, placeList 배열 적용
        placeAdapter placeListAdapter = new placeAdapter(getActivity(), R.layout.place_item, placeList);
        //리스트뷰 변수 placeListView 생성, XML의 placeList에 대응시킴
        ListView placeListView = (ListView) view.findViewById(R.id.placeListView);
        //버튼 변수 btnAddPlace 생성, XML의 btnAddPlace에 대응시킴
        ImageButton btnAddPlace = (ImageButton) view.findViewById(R.id.btnAddPlace);

        //adapter를 placeListView에 적용
        placeListView.setAdapter(placeListAdapter);

        //placeSearchView로 검색 시 작동하는 리스너 생성
        placeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeSearchList.clear();

                for(int i = 0; i < placeList.size(); i++) {
                    if ( placeList.get(i).getItem_title().contains(newText)) {
                        placeSearchList.add(placeList.get(i));
                    }
                }
                placeListView.setAdapter(placeSearchAdapter);
                return true;
            }
        });

        //add() 메소드로 placeList에 항목 추가
        //DB데이터 불러오는 코드로 수정해야 함
        placeList.add(new placeItem("8시간 폰 잠금방", "도서관"));
        placeList.add(new placeItem("IT소프트웨어과 2학년 B반 공부방", "도서관"));
        placeList.add(new placeItem("백투더오피스 회의실", "회의실"));
        placeList.add(new placeItem("hihi", "도서관"));
        placeList.add(new placeItem("놀고 싶다...", "도서관"));


        //placeListView를 클릭하면 실행하는 코드
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(place.this.getActivity());
                dlg.setTitle("\""+ placeList.get(arg2).getItem_title() + "\"에 입장합니다.");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent 생성, place_library의 클래스 placeLibrary를 넘김
                        Intent intent = new Intent(getActivity(), placeLibrary.class);
                        //place_library 액티비티 실행
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        //btnAddPlace 버튼을 클릭하면 실행하는 코드
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //place_add 파일을 인플레이트하여 placeAddView에 대입
                placeAddView = (View) View.inflate(place.this.getActivity(), R.layout.place_add, null);

                //대화상자 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(place.this.getActivity());
                //dlg.setTitle("공간 생성");
                TextView dlgTitle = (TextView) v.findViewById(R.id.placeAddTitle);
                dlg.setCustomTitle(dlgTitle);
                dlg.setIcon(R.drawable.add);
                dlg.setView(placeAddView);
                dlg.setPositiveButton("생성", new DialogInterface.OnClickListener() {

                    //DB에 저장하는 코드 필요

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), placeLibrary.class);
                        startActivity(intent);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        return view;
    }
}