package com.example.nimit.crackquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        final UserRecord userSession = new UserRecord(SplashActivity.this);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(userSession.getEmail().equals("")) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else {
                    if(userSession.getAccountType().equals("Student")){
                        startActivity(new Intent(SplashActivity.this, StudentDashboardActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, TeacherDashboardActivity.class));
                        finish();
                    }


                }
            }
        },1500);
    }
}
