package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //알림 설정 레이아웃을 표시해준다
        back = (ImageButton) findViewById(R.id.back_reg);


        final ImageButton button1 = (ImageButton) findViewById(R.id.back_reg);
        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                AlertDialog.Builder dlg = new AlertDialog.Builder(register.this);
                dlg.setTitle("주의"); //제목
                dlg.setMessage("지금까지 입력하신 내용이 삭제됩니다. \n정말 나가시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.ic_baseline_warning_24); // 아이콘 지정
                // 확인 버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //이전 화면(메인화면)으로 돌아감
                        finish();
                        //토스트 메시지
                        Toast.makeText(register.this,"입력이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                //취소 버튼 클릭시 동작
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show(); //대화상자 보여줌
            }
        });

        Button btn8 = (Button) findViewById(R.id.userbutton8);
        btn8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }//버튼을 눌렀을때 로그인 화면으로 가는 명령어이다
        });
    }
}