package com.example.campusEats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
Handler handler;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        handler = new Handler();


        handler.postDelayed(new Runnable() {
            public void run() {
                intent = new Intent(SplashActivity.this, MainActivity.class);

                /* 禁止從 MainActivity返回到 SplashScreen */
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(SplashActivity.this,MainActivity.class);

                startActivity(intent);
            }
        }, 2000); // 3000 milliseconds delay

    }


}