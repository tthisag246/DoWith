package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editRoom extends AppCompatActivity {
    ImageButton back;
    View.OnClickListener cl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_room);
        //방 꾸미기 페이지를 보여줌

        final ImageButton button1 = (ImageButton) findViewById(R.id.back_editR);
        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //뒤로가기 버튼 실행 시 경고문구 뜸
                AlertDialog.Builder dlg = new AlertDialog.Builder(editRoom.this);
                dlg.setTitle("경고"); //제목
                dlg.setMessage("지금까지의 작업이 모두 삭제됩니다.\n정말 종료하시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.ic_baseline_warning_24); // 아이콘 지정

                // 확인 버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //이전 화면(메인화면)으로 돌아감
                        finish();
                        //토스트 메시지
                        Toast.makeText(editRoom.this,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });

                //취소 버튼 클릭시 동작
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show(); //경고문구 보여줌
            }
        });
    }
}