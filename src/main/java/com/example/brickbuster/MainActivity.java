package com.example.brickbuster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button easy;
    Button normal;
    Button hard;
    Button start;
    // difficulty is static so that the class for the gameplay can see its value
    static String difficulty = "";
    MediaPlayer bennyPlayer;
    Context context;

//    public MainActivity(Context context) {
//        super(context);
//        this.context = context;
//        bennyPlayer = MediaPlayer.create(context, R.raw.i_need_a_homecoming_date);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        easy = findViewById(R.id.easyButton);
        normal = findViewById(R.id.normalButton);
        hard = findViewById(R.id.hardButton);
        start = findViewById(R.id.playButton);

        resetButtons();

        easy.setOnClickListener(this);
        normal.setOnClickListener(this);
        hard.setOnClickListener(this);
        start.setVisibility(View.INVISIBLE);
        //bennyPlayer.start();
    }

    public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public void onClick(View v) {
        // Sets difficulty; makes the button for the selected difficulty blue and the rest gray
        if (v.getId() == R.id.easyButton) {
            difficulty = "easy";
            resetButtons();
            easy.setBackgroundColor(Color.BLUE);
        } else if (v.getId() == R.id.normalButton) {
            difficulty = "normal";
            resetButtons();
            normal.setBackgroundColor(Color.BLUE);
        } else if (v.getId() == R.id.hardButton) {
            difficulty = "hard";
            resetButtons();
            hard.setBackgroundColor(Color.BLUE);
        }
        // Makes the start button visible only after a difficulty is selected
        start.setVisibility(View.VISIBLE);
    }

    void resetButtons() {
        // Makes every button grayed out
        easy.setBackgroundColor(Color.GRAY);
        normal.setBackgroundColor(Color.GRAY);
        hard.setBackgroundColor(Color.GRAY);
    }

    public static String getDifficulty() {
        return difficulty;
    }
}