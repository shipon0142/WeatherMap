package com.example.shipon.weathermap.weather_api_collection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.shipon.weathermap.MainActivity;
import com.example.shipon.weathermap.R;

public class splashActivity extends AppCompatActivity {
TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        text=findViewById(R.id.TextID);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanim);
        text.startAnimation(animation);
        new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    Intent iuu = new Intent(splashActivity.this,MainActivity.class);
                    startActivity(iuu);
                    finish();
                }
            }
        }.start();
    }
}
