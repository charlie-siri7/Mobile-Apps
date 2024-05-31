package com.example.capitalquiz;

import static com.example.capitalquiz.MainActivity.missedQuestions;
import static com.example.capitalquiz.MainActivity.missedQuestions2;
import static com.example.capitalquiz.MainActivity.mode;
import static com.example.capitalquiz.MainActivity.muted;
import static com.example.capitalquiz.MainActivity.noMissed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class typedQuizActivity extends AppCompatActivity implements View.OnClickListener {
    public static String oldQuestions2[] = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
            "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
            "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
            "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };

    public static String questions2[] = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
            "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
            "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
            "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };
    public static String oldAnswers2[] = {
            "Birmingham", "Juneau", "Phoenix", "Little Rock", "Sacramento",
            "Denver", "Hartford", "Dover", "Tallahassee", "Atlanta",
            "Honolulu", "Boise", "Springfield", "Indianapolis", "Des Moines",
            "Topeka", "Frankfort", "Baton Rouge", "Augusta", "Annapolis",
            "Boston", "Lansing", "St. Paul", "Jackson", "Jefferson City",
            "Helena", "Lincoln", "Carson City", "Concord", "Trenton",
            "Santa Fe", "Albany", "Raleigh", "Bismarck", "Columbus",
            "Oklahoma City", "Salem", "Harrisburg", "Providence", "Columbia",
            "Pierre", "Nashville", "Austin", "Salt Lake City", "Montpelier",
            "Richmond", "Olympia", "Charleston", "Madison", "Cheyenne"
    };

    public static String answers2[] = {
            "Birmingham", "Juneau", "Phoenix", "Little Rock", "Sacramento",
            "Denver", "Hartford", "Dover", "Tallahassee", "Atlanta",
            "Honolulu", "Boise", "Springfield", "Indianapolis", "Des Moines",
            "Topeka", "Frankfort", "Baton Rouge", "Augusta", "Annapolis",
            "Boston", "Lansing", "St. Paul", "Jackson", "Jefferson City",
            "Helena", "Lincoln", "Carson City", "Concord", "Trenton",
            "Santa Fe", "Albany", "Raleigh", "Bismarck", "Columbus",
            "Oklahoma City", "Salem", "Harrisburg", "Providence", "Columbia",
            "Pierre", "Nashville", "Austin", "Salt Lake City", "Montpelier",
            "Richmond", "Olympia", "Charleston", "Madison", "Cheyenne"
    };
    ArrayList<Integer> questionOrder = new ArrayList<>();
    ArrayList<String> newQuestions = new ArrayList<>(Arrays.asList(oldQuestions2));
    ArrayList<String> newAnswers = new ArrayList<>(Arrays.asList(oldAnswers2));
    TextView totalQuestionsTextView;
    TextView questionTextView;
    EditText answerText;
    Button submitBtn;
    Button muteBtn;
    MediaPlayer player;
    MediaPlayer effectPlayer;

    int score = 0;
    int totalQuestions = newQuestions.size();
    int questionNumber = 0;
    String typedAnswer = "";
    int counter = 0;
    Random random = new Random();

    ArrayList<Integer> oldMissedQuestions = new ArrayList<>(missedQuestions2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        player = MediaPlayer.create(typedQuizActivity.this, R.raw.f4datrap);
        if (!muted) {
            player.start();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.start();
            }
        });
        Log.d("ArrayList", String.valueOf(newQuestions));
        for (int g = 0; g < newAnswers.size(); g++) {
            questionOrder.add(g);
        }
        Log.d("Unshuffled numbers ", String.valueOf(questionOrder));
        Collections.shuffle(questionOrder);
        Log.d("Shuffled numbers ", String.valueOf(questionOrder));
        for (int h = 0; h < answers2.length; h++) {
            Log.d("benny", oldQuestions2[questionOrder.get(h)]);
            newQuestions.add(h, oldQuestions2[questionOrder.get(h)]);
            newAnswers.add(h, oldAnswers2[questionOrder.get(h)]);
        }
        if (missedQuestions2.size() > 0) {
            for (int i = 0; i < missedQuestions2.size(); i++) {
                oldMissedQuestions.add(i, missedQuestions2.get(i));
            }
            for (int i = 0; i < missedQuestions2.size(); i++) {
                oldMissedQuestions.remove(0);
            }
        }
        missedQuestions2.clear();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typed_quiz);

        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);
        answerText = findViewById(R.id.answerEditText);
        submitBtn = findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(this);
        muteBtn = findViewById(R.id.muteButtonTyped);
        muteBtn.setOnClickListener(this);


        if (mode == "all") {
            totalQuestionsTextView.setText("Question " + (questionNumber + 1) + " of " + totalQuestions);
        } else if (mode == "missed") {
            totalQuestionsTextView.setText("Question " + (counter + 1) + " of " + oldMissedQuestions.size());
        }

        loadNewQuestion();
    }
    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        typedAnswer = String.valueOf(answerText.getText());
        if (mode == "all") {
            if (clickedButton.getId() == R.id.submitButton) {
                if (typedAnswer.equals("") || typedAnswer.equals(null)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Bro...")
                            .setMessage("You gotta select an answer before submitting, genius.")
                            .setPositiveButton("Ok", (dialogInterface, i) -> this.closeContextMenu())
                            .setCancelable(true)
                            .show();
                } else {
                    if (typedAnswer.equalsIgnoreCase(answers2[questionNumber])) {
                        score++;
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(typedQuizActivity.this, R.raw.king_happy_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Yay you got it right!")
                                .setMessage("You score is currently " + score + " out of  " + (questionNumber + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                    } else {
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(typedQuizActivity.this, R.raw.king_crying_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Oh no! You missed it!")
                                .setMessage("The correct answer was " + answers2[questionNumber] + " and your score is currently " + score + " out of " + (questionNumber + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                        missedQuestions2.add(questionNumber);
                    }
                    questionNumber++;
                }
            } else {
                Log.d("bruh", "This button does not exist.");
            }
        } else if (mode == "missed") {
            Log.d("missed", "Missed mode");
            if (clickedButton.getId() == R.id.submitButton) {
                if (typedAnswer.equals("") || typedAnswer.equals(null)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Bro...")
                            .setMessage("You gotta type something before submitting, genius.")
                            .setPositiveButton("Ok", (dialogInterface, i) -> this.closeContextMenu())
                            .setCancelable(true)
                            .show();
                } else {
                    Log.d("Testing", "typedAnswer = " + typedAnswer + ", answers2[" + counter + "]" + answers2[oldMissedQuestions.get(counter)]);
                    if (typedAnswer.equalsIgnoreCase(answers2[oldMissedQuestions.get(counter)])) {
                        score++;
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(typedQuizActivity.this, R.raw.king_happy_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Yay you got it right!")
                                .setMessage("You score is currently " + score + " out of  " + (counter + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                    } else {
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(typedQuizActivity.this, R.raw.king_crying_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Oh no! You missed it!")
                                .setMessage("The correct answer was " + answers2[oldMissedQuestions.get(counter)] + " and your score is currently " + score + " out of " + (counter + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                        missedQuestions2.add(oldMissedQuestions.get(counter));
                    }
                    counter++;
                }
            }
        }
        if (clickedButton.getId() == R.id.muteButtonTyped) {
            if (muted) {
                player.start();
            } else {
                player.pause();
            }
            muted = !muted;
        }
    }

    void loadNewQuestion() {
        answerText.setText("");
        if (mode == "all") {
            if (questionNumber < totalQuestions) {
                totalQuestionsTextView.setText("Question " + (questionNumber + 1) + " of " + totalQuestions);
            }
        } else if (mode == "missed") {
            if (counter < oldMissedQuestions.size()) {
                totalQuestionsTextView.setText("Question " + (counter + 1) + " of " + oldMissedQuestions.size());
            }
        }
        if (mode == "all") {
            if (questionNumber == totalQuestions) {
                finishQuiz();
                return;
            }
            questionTextView.setText("What is the capital of " + questions2[questionNumber] + "?");
        } else if (mode == "missed") {
            if (counter >= oldMissedQuestions.size()) {
                finishQuiz();
                return;
            }
            questionTextView.setText("What is the capital of " + questions2[oldMissedQuestions.get(counter)] + "?");
        }
    }

    void finishQuiz() {
        String passStatus;
        int passCondition = totalQuestions;
        if (mode == "all") {
            passCondition = totalQuestions;
        } else if (mode == "missed") {
            passCondition = oldMissedQuestions.size();
        }
        if (score == passCondition) {
            passStatus = "YOOOO! You got a perfect score!!!";
            if (!muted) {
                player.stop();
                player = MediaPlayer.create(typedQuizActivity.this, R.raw.yb);
                player.start();
            }
        } else if (score >= passCondition * 0.6) {
            passStatus = "Yay!!! you passed!!!";
            if (!muted) {
                player.stop();
                player = MediaPlayer.create(typedQuizActivity.this, R.raw.yb);
                player.start();
            }
        } else {
            passStatus = "Haha you failed";
            if (!muted) {
                player.stop();
                player = MediaPlayer.create(typedQuizActivity.this, R.raw.robbery);
                player.start();
            }
        }
        if (missedQuestions2.size() < 1) {
            noMissed = "typed";
        } else {
            noMissed = "";
        }
        double fraction = (double) score / (double) passCondition;
        int percentage = (int) (fraction * 100);
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("You score is " + score + " out of " + passCondition + ", giving you a " + percentage + "%")
                .setPositiveButton("Back to main menu", (dialogInterface, i) -> mainMenu() )
                .setCancelable(false)
                .show();
    }

    private void mainMenu() {
        player.stop();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("missedQuestions", missedQuestions2);
        startActivity(intent);
    }


}