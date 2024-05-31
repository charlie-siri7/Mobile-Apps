package com.example.lilkioskaudioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    Button forgiveMeBtn;
    Button runItUpBtn;
    Button BIGButton;
    Button pauseBtn;
    Button stopBtn;
    String songPlaying = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forgiveMeBtn = this.findViewById(R.id.forgiveMeButton);
        runItUpBtn = this.findViewById(R.id.runItUpButton);
        BIGButton = this.findViewById(R.id.BIGButton);
        pauseBtn = this.findViewById(R.id.pauseButton);
        stopBtn = this.findViewById(R.id.stopButton);

        forgiveMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong("Forgive Me");
            }
        });

        runItUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong("Run It Up");
            }
        });

        BIGButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong("B.I.G.");
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    player.pause();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlayer();
            }
        });
    }

    private void playSong(String song) {
        if (songPlaying != song) {
            if (songPlaying != "") {
                Log.d("HelpMe","Benny");
                stopPlayer();
            }
            if (song.equals("Forgive Me")) {
                player = MediaPlayer.create(MainActivity.this, R.raw.forgiveme);
            } else if (song.equals("Run It Up")) {
                player = MediaPlayer.create(MainActivity.this, R.raw.runitup);
            } else if (song.equals("B.I.G.")) {
                player = MediaPlayer.create(MainActivity.this, R.raw.big);
            }
            songPlaying = song;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            songPlaying = "";
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}