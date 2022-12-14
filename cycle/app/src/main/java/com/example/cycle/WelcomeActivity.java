package com.example.cycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        Button welcome = findViewById(R.id.button_welcome);
        welcome.setOnClickListener(intent_WelcometoSignIn);
    }

    private final View.OnClickListener intent_WelcometoSignIn = view -> {
        Intent welcome_to_signIn = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(welcome_to_signIn);
    };
}