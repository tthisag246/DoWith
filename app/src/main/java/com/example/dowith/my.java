package com.example.dowith;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class my extends Fragment {

    Button btn,btn1,btn2,btn4,btn5; //버튼을 btn이란 이름으로 선언
    private View view;
    EditText txt;
    RadioGroup RaGr;
    RadioButton gr;
    View.OnClickListener cl;
    AlertDialog.Builder dia;
    DialogInterface.OnClickListener ok, not;
    View infoview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.my,container,false);

        btn = (Button) view.findViewById(R.id.button);//각변수와 맞는 버튼을 지정해줌
        btn1 = (Button) view.findViewById(R.id.button1);
        btn2 = (Button) view.findViewById(R.id.button2);
        Button btnQuit = view.findViewById(R.id.button5);
        btn4 = (Button) view.findViewById(R.id.button4);

        txt = (EditText) view.findViewById(R.id.text);
        btn5 = (Button) view.findViewById(R.id.button3);

        ok = new DialogInterface.OnClickListener() {
            //팝업창에서 확인 버튼을 눌렀을때 필요한 명령어
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText nam, age, tel, gen;
                nam = (EditText) infoview.findViewById(R.id.name1);
                String s;
                s = "이름=" + nam.getText().toString() ;
                Toast.makeText(getActivity(), "입력을 완료하였습니다",
                        Toast.LENGTH_SHORT).show();

            }
        };
        not = new DialogInterface.OnClickListener() {
            //팝업창에서 취소 버튼을 눌렀을때 필요한 명령어
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "입력을 취소하였습니다",
                        Toast.LENGTH_SHORT).show();
            }
        };

        cl = new View.OnClickListener() {
            //쿠폰 팝업창쪽
            @Override
            public void onClick(View view) {
                dia = new AlertDialog.Builder(getActivity());
                TextView dlgtitle = (TextView) view.findViewById(R.id.coupontitle);
                dia.setCustomTitle(dlgtitle);
                infoview = (View) View.inflate(getActivity(), R.layout.myinfo, null);
                dia.setView(infoview);
                dia.setPositiveButton("완료", ok);
                dia.setNegativeButton("취소", not);
                dia.show();

            }
        };
        btn5.setOnClickListener(cl);
        //버튼을 누르면 쿠폰입력하는 팝업이 뜸
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showDialog(); }//클릭했을때 팝업창 표시를 이끈다
        });



        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), account.class);
                startActivity(intent);
            }
        });//버튼이 눌렸을때 해당 클래스로 가는 명령어이다
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), notificationSetting.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SubActivity.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shingu.ac.kr/"));
                startActivity(intent);//버튼 누르면 사이트로 이동함
            }
        });

        return view;
    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())//팝업창 생성

                .setIcon(R.drawable.account_round)
                .setTitle("로그아웃")//쿠폰의 타이틀
                .setMessage("로그아웃 하시겠습니까???")//쿠폰의 메세지
                .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "로그아웃 됐습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);
                    }//긍정 버튼 눌를때 나오는 텍스트
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                    }//부정 버튼 눌를때 나오는 텍스트
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}