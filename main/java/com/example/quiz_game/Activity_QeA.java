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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Activity_QeA extends AppCompatActivity {

    private TextView quiztext, aans, bans, cans, dans;
    private List<QeA_item> questionsItems;
    private int currentQuestions = 0;
    private int correct = 0, wrong = 0;
    private MediaPlayer mediaPlayer;
    private MediaPlayer answerPlayer;
    private AlertDialog alertDialog, alertDialog2;
    private ImageView imageView1;
    private Animation zoom,fade;
    private MaterialCardView mCardView1,mCardView2,mCardView3,
            mCardView4,mCardView5;

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

        setContentView(R.layout.activity_qea);

        //audio
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.new_divide);
        mediaPlayer.start();

        //text
        quiztext = findViewById(R.id.quizText);
        aans = findViewById(R.id.aanswer);
        bans = findViewById(R.id.banswer);
        cans = findViewById(R.id.canswer);
        dans = findViewById(R.id.danswer);

        //animation
        //animation
        imageView1 = findViewById(R.id.e_quiz);
        mCardView1 = findViewById(R.id.card1);
        mCardView2 = findViewById(R.id.card2);
        mCardView3 = findViewById(R.id.card3);
        mCardView4 = findViewById(R.id.card4);
        mCardView5 = findViewById(R.id.card5);
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        imageView1.startAnimation(fade);
        mCardView1.startAnimation(zoom);
        mCardView2.startAnimation(zoom);
        mCardView3.startAnimation(zoom);
        mCardView4.startAnimation(zoom);
        mCardView5.startAnimation(zoom);


        //random -> array di testi e img, 1 card
        alertDialog = new MaterialAlertDialogBuilder(this,R.style.AlertDialog)
                .setView(R.layout.custom_alert_dialog)
                .setCancelable(false)
                .create();
        //random -> array di testi e img, 1 card
        alertDialog2 = new MaterialAlertDialogBuilder(this,R.style.AlertDialog)
                .setView(R.layout.custom_alert_dialog2)
                .setCancelable(false)
                .create();

        loadAllQuestions();

        Collections.shuffle(questionsItems);
        setQuestionScreen(currentQuestions);

        aans.setOnClickListener(view -> {
            if (questionsItems.get(currentQuestions).getAnswer1().equals(questionsItems.get(currentQuestions).getCorrect())) {
                correct++;
                correctSound();
                aans.setBackgroundResource(R.color.green);
                aans.setTextColor(getResources().getColor(R.color.white,null));
                alertDialog2.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog2.hide(), 1000);
            } else {
                wrong++;
                wrongSound();
                aans.setBackgroundResource(R.color.red);
                aans.setTextColor(getResources().getColor(R.color.white,null));
                currentQuestions-=1;
                alertDialog.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog.hide(), 1000);
            }

            if (currentQuestions < questionsItems.size()-1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    currentQuestions++;
                    setQuestionScreen(currentQuestions);
                    aans.setBackgroundResource(R.color.background_color);
                    aans.setTextColor(getResources().getColor(R.color.white,null));
                }, 1000);
            } else {
                Intent intent = new Intent(Activity_QeA.this,Activity_win.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                mediaPlayer.stop();
                startActivity(intent);
                finish();
            }
        });

        bans.setOnClickListener(view -> {
            if (questionsItems.get(currentQuestions).getAnswer2().equals(questionsItems.get(currentQuestions).getCorrect())) {
                correct++;
                correctSound();
                bans.setBackgroundResource(R.color.green);
                bans.setTextColor(getResources().getColor(R.color.white,null));
                alertDialog2.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog2.hide(), 1000);
            } else {
                wrong++;
                wrongSound();
                bans.setBackgroundResource(R.color.red);
                bans.setTextColor(getResources().getColor(R.color.white,null));
                currentQuestions-=1;
                alertDialog.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog.hide(), 1000);
            }

            if (currentQuestions < questionsItems.size()-1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    currentQuestions++;
                    setQuestionScreen(currentQuestions);
                    bans.setBackgroundResource(R.color.background_color);
                    bans.setTextColor(getResources().getColor(R.color.white,null));
                }, 1000);
            } else {
                Intent intent = new Intent(Activity_QeA.this, Activity_win.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                mediaPlayer.stop();
                startActivity(intent);
                finish();
            }
        });

        cans.setOnClickListener(view -> {
            if (questionsItems.get(currentQuestions).getAnswer3().equals(questionsItems.get(currentQuestions).getCorrect())) {
                correct++;
                correctSound();
                cans.setBackgroundResource(R.color.green);
                cans.setTextColor(getResources().getColor(R.color.white,null));
                alertDialog2.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog2.hide(), 1000);
            } else {
                wrong++;
                wrongSound();
                cans.setBackgroundResource(R.color.red);
                cans.setTextColor(getResources().getColor(R.color.white,null));
                currentQuestions-=1;
                alertDialog.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog.hide(), 1000);
            }

            if (currentQuestions < questionsItems.size()-1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    currentQuestions++;
                    setQuestionScreen(currentQuestions);
                    cans.setBackgroundResource(R.color.background_color);
                    cans.setTextColor(getResources().getColor(R.color.white,null));
                }, 1000);
            } else {
                Intent intent = new Intent(Activity_QeA.this, Activity_win.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                mediaPlayer.stop();
                startActivity(intent);
                finish();
            }
        });

        dans.setOnClickListener(view -> {
            if (questionsItems.get(currentQuestions).getAnswer4().equals(questionsItems.get(currentQuestions).getCorrect())) {
                correct++;
                correctSound();
                dans.setBackgroundResource(R.color.green);
                dans.setTextColor(getResources().getColor(R.color.white,null));
                alertDialog2.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog2.hide(), 1000);
            } else {
                wrong++;
                wrongSound();
                dans.setBackgroundResource(R.color.red);
                dans.setTextColor(getResources().getColor(R.color.white,null));
                currentQuestions-=1;
                alertDialog.show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> alertDialog.hide(), 1000);
            }

            if (currentQuestions < questionsItems.size()-1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    currentQuestions++;
                    setQuestionScreen(currentQuestions);
                    dans.setBackgroundResource(R.color.background_color);
                    dans.setTextColor(getResources().getColor(R.color.white, null));
                }, 1000);
            } else {
                Intent intent = new Intent(Activity_QeA.this, Activity_win.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                mediaPlayer.stop();
                startActivity(intent);
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

    private void correctSound(){
        answerPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correct);
        answerPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mPlayer) {
                answerPlayer.start();
            }
        });

        answerPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                answerPlayer.release();
            }
        });
    }
    private void wrongSound(){
        answerPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
        answerPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mPlayer) {
                answerPlayer.start();
            }
        });

        answerPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                answerPlayer.release();
            }
        });
    }

    private void setQuestionScreen(int currentQuestions) {
        quiztext.setText(questionsItems.get(currentQuestions).getQuestions());
        aans.setText(questionsItems.get(currentQuestions).getAnswer1());
        bans.setText(questionsItems.get(currentQuestions).getAnswer2());
        cans.setText(questionsItems.get(currentQuestions).getAnswer3());
        dans.setText(questionsItems.get(currentQuestions).getAnswer4());
    }

    private void loadAllQuestions() {
        questionsItems = new ArrayList<>();
        String jsonquiz = loadJsonFromAsset("questions.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonquiz);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);

                String questionsString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");

                questionsItems.add(new QeA_item(questionsString, answer1String, answer2String,
                        answer3String, answer4String, correctString));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAsset(String s) {
        String json = "";
        try {
            InputStream inputStream = getAssets().open(s);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}