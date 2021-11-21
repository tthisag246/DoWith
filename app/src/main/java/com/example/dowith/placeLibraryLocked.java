package com.example.dowith;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class placeLibraryLocked extends AppCompatActivity {

    Button btnLockedSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_library_locked);

        btnLockedSwitch = (Button) findViewById(R.id.btnLockedSwitch);

        btnLockedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}