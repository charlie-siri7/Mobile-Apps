package com.example.brickbuster;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class gameOver extends AppCompatActivity {

    Context context;

    TextView pointsTextView;
    ImageView trophy;
    TextView messageTextView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Sets the background to the "game_over" xml file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        // Variables to store the text views for the trophy image, points, and the text denoting if you win or lose
        trophy = findViewById(R.id.ivNewHighest);
        pointsTextView = findViewById(R.id.tvPoints);
        messageTextView = findViewById(R.id.tvMessage);
        // Gets the points variable from the GameView class and stores it
        int points = getIntent().getExtras().getInt("points");
        int numBricks = getIntent().getExtras().getInt("numBricks");
        /* Checks if the number of points is greater than 480 (denoting all bricks are broken); if
        this is the case, then set the text to show that you won */
        if (points >= numBricks) {
            Log.d("philoPoints", String.valueOf(points));
            Log.d("philoBricks", String.valueOf(numBricks));
            trophy.setVisibility(View.VISIBLE);
            messageTextView.setText("Yay! You didn't fail!");
        }
        // sets the points text view to the amount of points
        pointsTextView.setText("" + points);
    }

    public void restart(View view) {
        Intent intent = new Intent(gameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
