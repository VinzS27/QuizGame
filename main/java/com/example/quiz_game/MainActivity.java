package com.example.quiz_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private EditText password;
    private MediaPlayer mediaPlayer;
    private MediaPlayer startPlayer;
    private AlertDialog alertDialog;
    private ImageView imageView1, imageView2, imageView3;
    private RelativeLayout relativeLayout;
    private Animation zoom,pulse,rotation,slide;

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
                //Close bar when click back button
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            }
        });

        setContentView(R.layout.activity_main);

        //custom alert
        alertDialog = new MaterialAlertDialogBuilder(this,R.style.AlertDialog)
                .setView(R.layout.custom_alert_dialog)
                .setCancelable(false)
                .create();

        //audio
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.intro_db);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        //animation
        imageView1 = findViewById(R.id.auguri_vecchietta);
        imageView2 = findViewById(R.id.sfida);
        imageView3 = findViewById(R.id.accedi_ora);
        start = findViewById(R.id.start);
        password = findViewById(R.id.password);
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        slide = AnimationUtils.loadAnimation(this, R.anim.slide_right);

        imageView1.startAnimation(zoom);
        new Handler(Looper.getMainLooper()).postDelayed(() -> imageView1.startAnimation(pulse), 1500);
        new Handler(Looper.getMainLooper()).postDelayed(() -> imageView1.startAnimation(pulse), 2500);
        imageView2.startAnimation(zoom);
        imageView3.startAnimation(zoom);
        start.startAnimation(zoom);
        password.startAnimation(zoom);

        //button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = password.getText().toString();
                if(s.equals("fifty") || s.equals("Fifty") || s.equals("fifty ") || s.equals("Fifty ")) {
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
                    Intent intent = new Intent(MainActivity.this, Activity_QeA.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }else{
                    startPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
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
                    alertDialog.show();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog.hide(), 1000);
                }
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