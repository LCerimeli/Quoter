package com.example.quoter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


// Classe para mostrar uma splash screen quando o app é aberto
public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Direciona para a MainActivity após o delay
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000); // Delay de 2000 ms (2s) para trocar para a MainActivity
    }
}