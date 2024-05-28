package com.example.quiz_game;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class Activity_video extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

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

        //Disable back button for the activity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Toast for not stop the activity
                //Toast.makeText(MainActivity.this,"BACK",Toast.LENGTH_SHORT);
                // Back is pressed... Finishing the activity
                //finish();
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            }
        });

        setContentView(R.layout.activity_video);

        //video
        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+
                getPackageName()+"/"+R.raw.video));

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}