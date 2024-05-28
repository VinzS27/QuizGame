package com.example.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class Activity_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        // Hide the system bars.
        Window window = getWindow();
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(window, window.getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        setContentView(R.layout.activity_intro);

        Thread mIntroThread;
        mIntroThread = new Thread() {
            @Override public  void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException ignored) {
                }
                finally {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };
        mIntroThread.start();
    }
}