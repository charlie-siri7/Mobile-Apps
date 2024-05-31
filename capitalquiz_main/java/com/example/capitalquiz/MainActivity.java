package com.example.capitalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer player;
    TextView title;
    Button play;
    Button missedQuestionsButton;
    Button typedTestButton;
    Button missedTypedTestButton;
    Button muteBtn;
    static ArrayList<Integer> missedQuestions = new ArrayList<Integer>();
    static ArrayList<Integer> missedQuestions2 = new ArrayList<Integer>();
    static String mode = "all";
    static String testType = "";
    static String noMissed = "";
    static boolean muted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.titleTextView);
        play = findViewById(R.id.MQplayButton);
        missedQuestionsButton = findViewById(R.id.missedMQButton);
        typedTestButton = findViewById(R.id.typePlayButton);
        missedTypedTestButton = findViewById(R.id.missedTypedButton);
        muteBtn = findViewById(R.id.muteButtonMain);

        player = MediaPlayer.create(MainActivity.this, R.raw.i_need_a_hoco_date);
        if (!muted) {
            player.start();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.start();
            }
        });

        typedTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "all";
                player.release();
                Intent typeIntent = new Intent(getApplicationContext(), typedQuizActivity.class);
                startActivity(typeIntent);
            }
        });
        missedTypedTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "missed";
                player.release();
                Intent typeIntent = new Intent(getApplicationContext(), typedQuizActivity.class);
                startActivity(typeIntent);
            }
        });
        play.setOnClickListener(this);
        missedQuestionsButton.setOnClickListener(this);
        muteBtn.setOnClickListener(this);
        Log.d("MainActivity: missedQuestions = ", String.valueOf(missedQuestions));
        Log.d("MainActivity: missedQuestions2 = ", String.valueOf(missedQuestions2));
        if (missedQuestions.size() > 0) {
            missedQuestionsButton.setVisibility(View.VISIBLE);
        } else {
            missedQuestionsButton.setVisibility(View.INVISIBLE);
            if (noMissed.equals("MQ")) {
                new AlertDialog.Builder(this)
                        .setTitle("Yay!!!")
                        .setMessage("You answered all of the multiple choice questions right! feel free to try again!")
                        .setNegativeButton("Cancel", (dialogInterface, i) -> this.closeContextMenu())
                        .setCancelable(true)
                        .show();
            }
        }
        if (missedQuestions2.size() > 0) {
            missedTypedTestButton.setVisibility(View.VISIBLE);
        } else {
            missedTypedTestButton.setVisibility(View.INVISIBLE);
            if (noMissed.equals("typed")) {
                new AlertDialog.Builder(this)
                        .setTitle("Yay!!!")
                        .setMessage("You answered all of the typed questions right! feel free to try again!")
                        .setNegativeButton("Cancel", (dialogInterface, i) -> this.closeContextMenu())
                        .setCancelable(true)
                        .show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if (clickedButton.getId() == R.id.MQplayButton) {
            testType = "MQ";
            mode = "all";
            if (missedQuestions.size() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("WARNING.")
                        .setMessage("Pressing the play button will reset all missed questions")
                        .setPositiveButton("Continue", (dialogInterface, i) -> startGame())
                        .setNegativeButton("Cancel", (dialogInterface, i) -> this.closeContextMenu())
                        .setCancelable(true)
                        .show();
            } else {
                startGame();
            }
        } else if (clickedButton.getId() == R.id.missedMQButton) {
            testType = "MQ";
            mode = "missed";
            player.release();
            Intent startIntent = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(startIntent);
        }
        if (clickedButton.getId() == R.id.muteButtonMain) {
            if (muted) {
                player.start();
            } else {
                player.pause();
            }
            muted = !muted;
        }
    }

    public void startGame() {
        //mode = "all";
        player.release();
        Intent startIntent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivity(startIntent);
    }
}