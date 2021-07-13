package com.github.example.richtexteditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.github.irshulx.richtexteditor.R;

public class splashscreen extends AppCompatActivity {
    private static int splashTimeOut=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        }, splashTimeOut);
//        logoo=(ImageView)findViewById(R.id.logoo);
//        compro=(TextView)findViewById(R.id.compro);
//
//
//        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mysplashscreen);
//        logoo.startAnimation(myanim);
//        compro.startAnimation(myanim);
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(MainActivity.this, Home.class);
//                startActivity(i);
//                finish();
//            }
//        },splashTimeOut);


    }
}
