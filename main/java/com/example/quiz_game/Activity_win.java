package com.example.quiz_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.card.MaterialCardView;

public class Activity_win extends AppCompatActivity {
    
    private ImageView trophy, youwin;
    private MediaPlayer mediaPlayer;
    private MediaPlayer startPlayer;
    private ImageButton play_button;
    private TextView textView;
    private LinearLayout linearLayout2;
    private Animation zoom,fade,pulse;

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
                finish();
            }
        });

        setContentView(R.layout.activity_win);

        //audio
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mario_win);
        mediaPlayer.start();

        //animation
        play_button = findViewById(R.id.play_video);
        play_button.setVisibility(View.GONE);
        trophy = findViewById(R.id.trophy);
        youwin = findViewById(R.id.resultInfo);
        //linearLayout2 = findViewById(R.id.linear_layout2);
        //linearLayout2.setVisibility(View.GONE);
        textView = findViewById(R.id.text);
        textView.setVisibility(View.GONE);

        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        trophy.startAnimation(fade);
        youwin.startAnimation(zoom);
        new Handler(Looper.getMainLooper()).postDelayed(() -> trophy.startAnimation(pulse), 1500);
        new Handler(Looper.getMainLooper()).postDelayed(() -> play_button.setVisibility(View.VISIBLE), 2000);
        new Handler(Looper.getMainLooper()).postDelayed(() -> play_button.startAnimation(fade), 2000);
        new Handler(Looper.getMainLooper()).postDelayed(() -> play_button.startAnimation(pulse), 2500);
        new Handler(Looper.getMainLooper()).postDelayed(() -> textView.setVisibility(View.VISIBLE), 3000);
        new Handler(Looper.getMainLooper()).postDelayed(() -> textView.startAnimation(fade), 3000);

        //button
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                startPlayer = MediaPlayer.create(getApplicationContext(),R.raw.start);
                startPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mPlayer) {
                        startPlayer.start();
                    }
                });

                startPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mPlayer) {
                        startPlayer.release();
                    }
                });
                startActivity(new Intent(Activity_win.this, Activity_video.class));
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}