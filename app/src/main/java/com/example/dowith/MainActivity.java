package com.example.dowith;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //하단 네비게이션 변수명 지정
        private View view;
        private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() { //광고 초기화
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdview = findViewById(R.id.adView); //배너광고 레이아웃 가져오기
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER); //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("\n" + "ca-app-pub-3940256099942544/6300978111");




        ImageButton btn_noti = (ImageButton) findViewById(R.id.notiicon); //notification버튼 지정하는 버튼 변수명
        btn_noti.setOnClickListener(new View.OnClickListener() { //notification 버튼을 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notification.class);  //Intent 생성하여 notification의 클래스 notification을 넘김
                startActivity(intent);  // notification 액티비티 실행
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavi); //하단 네비게이션 경로지정

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new home()).commit(); //FrameLayout에 fragment.xml 띄우기

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_fragment1: getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new home()).commit();
                    break;
                    case R.id.item_fragment2: getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new list()).commit();
                    break;
                    case R.id.item_fragment3: getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new place()).commit();
                    break;
                    case R.id.item_fragment4: getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new study()).commit();
                    break;
                    case R.id.item_fragment5: getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new my()).commit();
                    break;
            }
            //각 fragment를 저장소에서 가져오는 콜백 메소드
            return true;
            }
        });

    }


}
