package com.example.dowith;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editCharacter extends AppCompatActivity {
    View.OnClickListener cl;
    ImageButton checkbtn, rebtn;
    Button sad, ang;
    ImageView imageview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_character);
        checkbtn = (ImageButton) findViewById(R.id.check);
        rebtn = (ImageButton) findViewById(R.id.re);
        sad  = (Button) findViewById(R.id.sad);
        ang = (Button) findViewById(R.id.angry);
        imageview = (ImageView) findViewById(R.id.sdmain);


        //체크 버튼 클릭하면 메인 페이지로
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch ( view.getId()){
                    case R.id.check:
                        finish();
                        break;
                    case R.id.re:
                        finish();//인텐트 종료
                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                        Intent intent = getIntent(); //인텐트
                        startActivity(intent); //액티비티 열기
                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                        break;
                    case R.id.angry:
                        imageview.setImageResource(R.drawable.sd_angry);
                        break;
                    case R.id.sad:
                        imageview.setImageResource(R.drawable.sd_sad);
                        break;
                }
            }
        };
        checkbtn.setOnClickListener(cl);
        rebtn.setOnClickListener(cl);
        ang.setOnClickListener(cl);
        sad.setOnClickListener(cl);



        //캐릭터 꾸미기 페이지를 보여줌

        final ImageButton button1 = (ImageButton) findViewById(R.id.back_editC);



        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //뒤로가기 버튼 실행 시 경고문구 뜸
                AlertDialog.Builder dlg = new AlertDialog.Builder(editCharacter.this);
                dlg.setTitle("경고"); //제목
                dlg.setMessage("지금까지의 작업이 모두 삭제됩니다.\n정말 종료하시겠습니까?"); // 메시지
                dlg.setIcon(R.drawable.ic_baseline_warning_24); // 아이콘 지정

                // 확인 버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //이전 화면(메인화면)으로 돌아감
                        finish();
                        //토스트 메시지
                        Toast.makeText(editCharacter.this,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
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
