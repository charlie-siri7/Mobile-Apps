package com.example.capitalquiz;

import static com.example.capitalquiz.MainActivity.missedQuestions;
import static com.example.capitalquiz.MainActivity.missedQuestions2;
import static com.example.capitalquiz.MainActivity.mode;
import static com.example.capitalquiz.MainActivity.muted;
import static com.example.capitalquiz.MainActivity.noMissed;
import static com.example.capitalquiz.MainActivity.testType;

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

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    public static String oldQuestions[] = {
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

    public static String questions[] = {
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
    public static String oldAnswers[] = {
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

    public static String answers[] = {
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

    public static String choices[][] = {
//            {"Birmingham", "Huntsville", "Mobile", "Montgomery"},
//            {"Anchorage", "Badger", "Fairbanks", "Juneau"},
//            {"Chandler", "Mesa", "Phoenix", "Tucson"},
//            {"Fayetteville", "Fort Smith", "Little Rock", "Springdale"},
//            {"Los Angeles", "Sacramento", "San Diego", "San Jose"},
//            {"Aurora", "Colorado Springs", "Denver", "Fort Collins"},
//            {"Bridgeport", "Hartford", "New Haven", "Stamford"},
//            {"Dover", "Middletown", "Newark", "Wilmington"},
//            {"Jacksonville", "Miami", "Tallahassee", "Tampa"},
//            {"Atlanta", "Augusta", "Columbus", "Macon"}
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""}
    };

    MediaPlayer player;
    MediaPlayer effectPlayer;
    ArrayList<Integer> questionOrder = new ArrayList<>();
    ArrayList<String> newQuestions = new ArrayList<>(Arrays.asList(oldQuestions));
    ArrayList<String> newAnswers = new ArrayList<>(Arrays.asList(oldAnswers));
    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button answerA, answerB, answerC, answerD;
    Button submitBtn, muteBtn;

    int score = 0;
    int totalQuestions = newQuestions.size();
    int questionNumber = 0;
    String selectedAnswer = "";
    int counter = 0;
    Random random = new Random();
    ArrayList<Integer> oldMissedQuestions = new ArrayList<>(missedQuestions);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        player = MediaPlayer.create(QuizActivity.this, R.raw.f4datrap);
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
        for (int h = 0; h < answers.length; h++) {
            Log.d("benny", oldQuestions[questionOrder.get(h)]);
            newQuestions.add(h, oldQuestions[questionOrder.get(h)]);
            newAnswers.add(h, oldAnswers[questionOrder.get(h)]);
        }
        for (int i = 0; i < answers.length; i++) {
            int correctQuestion = random.nextInt(4);
            for (int j = 0; j < 4; j++) {
                Log.d("choices:" , i + j + choices[i][j]);
            }
            Log.d("Choices list", choices[i][0]);
            while ((choices[i][0] == choices[i][1]) || (choices[i][0] == choices[i][2]) || (choices[i][0] == choices[i][3]) || (choices[i][1] == choices[i][2]) || (choices[i][1] == choices[i][3]) || (choices[i][2] == choices[i][3])) {
                for (int j = 0; j < 4; j++) {
                    Log.d("j", String.valueOf(j));
                    String wrongAnswer = answers[i];
                    while (wrongAnswer == answers[i]) {
                        wrongAnswer = answers[random.nextInt(answers.length)];
                        choices[i][j] = wrongAnswer;
                    }
                }
                choices[i][correctQuestion] = answers[i];
            }
            for (int k = 0; k < 4; k++) {
                Log.d("choices", String.valueOf(choices[i][k]));
            }
            Log.d("choices", "--------------------------");
        }
        //oldMissedQuestions = missedQuestions;
        Log.d("MQ", String.valueOf(missedQuestions));
        if (missedQuestions.size() > 0) {
            for (int i = 0; i < missedQuestions.size(); i++) {
                Log.d("missed questions", String.valueOf(missedQuestions.get(i)));
                //oldMissedQuestions.add(missedQuestions.get(i));
                Log.d("# and item to add to new list", i + ", " + missedQuestions.get(i));
                oldMissedQuestions.add(i, missedQuestions.get(i));
            }
            Log.d("OMQ", String.valueOf(oldMissedQuestions));
            for (int i = 0; i < missedQuestions.size(); i++) {
                oldMissedQuestions.remove(0);
            }
            Log.d("OMQ - 2", String.valueOf(oldMissedQuestions));
            //Collections.copy(oldMissedQuestions, missedQuestions);
        }
        missedQuestions.clear();
        Log.d("OMQ - 3", String.valueOf(oldMissedQuestions));
        Log.d("MQ - 3", String.valueOf(missedQuestions));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);
        answerA = findViewById(R.id.AButton);
        answerB = findViewById(R.id.BButton);
        answerC = findViewById(R.id.CButton);
        answerD = findViewById(R.id.DButton);
        submitBtn = findViewById(R.id.submitButton);
        muteBtn = findViewById(R.id.muteButton);

        //resetButtonColors();

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
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
        if (mode == "all") {
            if (clickedButton.getId() == R.id.submitButton) {
                if (selectedAnswer == "") {
                    new AlertDialog.Builder(this)
                            .setTitle("Bro...")
                            .setMessage("You gotta select an answer before submitting, genius.")
                            .setPositiveButton("Ok", (dialogInterface, i) -> this.closeContextMenu())
                            .setCancelable(true)
                            .show();
                } else {
                    if (selectedAnswer.equals(answers[questionNumber])) {
                        score++;
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(QuizActivity.this, R.raw.king_happy_01);
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
                            effectPlayer = MediaPlayer.create(QuizActivity.this, R.raw.king_crying_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Oh no! You missed it!")
                                .setMessage("The correct answer was " + answers[questionNumber] + " and your score is currently " + score + " out of " + (questionNumber + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                        missedQuestions.add(questionNumber);
                    }
                    questionNumber++;
                }
                //loadNewQuestion();
            } else {
                if (clickedButton.getId() != R.id.muteButton) {
                    selectedAnswer = clickedButton.getText().toString();
                    resetButtonColors();
                    clickedButton.setBackgroundColor(Color.BLUE);
                }
            }
        } else if (mode == "missed") {
            if (clickedButton.getId() == R.id.submitButton) {
                if (selectedAnswer == "") {
                    new AlertDialog.Builder(this)
                            .setTitle("Bro...")
                            .setMessage("You gotta select an answer before submitting, genius.")
                            .setPositiveButton("Ok", (dialogInterface, i) -> this.closeContextMenu())
                            .setCancelable(true)
                            .show();
                } else {
                    if (selectedAnswer.equals(answers[oldMissedQuestions.get(counter)])) {
                        score++;
                        if (!muted) {
                            effectPlayer = MediaPlayer.create(QuizActivity.this, R.raw.king_happy_01);
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
                            effectPlayer = MediaPlayer.create(QuizActivity.this, R.raw.king_crying_01);
                            effectPlayer.start();
                        }
                        new AlertDialog.Builder(this)
                                .setTitle("Oh no! You missed it!")
                                .setMessage("The correct answer was " + answers[oldMissedQuestions.get(counter)] + " and your score is currently " + score + " out of " + (counter + 1))
                                .setPositiveButton("Ok", (dialogInterface, i) -> loadNewQuestion())
                                .setCancelable(true)
                                .show();
                        missedQuestions.add(oldMissedQuestions.get(counter));
                    }
                    counter++;
                }
                //loadNewQuestion();
            } else {
                if (clickedButton.getId() != R.id.muteButton) {
                    selectedAnswer = clickedButton.getText().toString();
                    resetButtonColors();
                    clickedButton.setBackgroundColor(Color.BLUE);
                }
            }
        }
        if (clickedButton.getId() == R.id.muteButton) {
            if (muted) {
                player.start();
            } else {
                player.pause();
            }
            muted = !muted;
        }
    }

    void loadNewQuestion() {
        selectedAnswer = "";
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
            resetButtonColors();
            if (questionNumber == totalQuestions) {
                finishQuiz();
                return;
            }

            questionTextView.setText("What is the capital of " + questions[questionNumber] + "?");
            answerA.setText(choices[questionNumber][0]);
            answerB.setText(choices[questionNumber][1]);
            answerC.setText(choices[questionNumber][2]);
            answerD.setText(choices[questionNumber][3]);
        } else if (mode == "missed") {
            resetButtonColors();
            Log.d("counter, MQ array size", counter + ", " + oldMissedQuestions.size());
            if (counter >= oldMissedQuestions.size()) {
                finishQuiz();
                return;
            }

            questionTextView.setText("What is the capital of " + questions[oldMissedQuestions.get(counter)] + "?");
            answerA.setText(choices[oldMissedQuestions.get(counter)][0]);
            answerB.setText(choices[oldMissedQuestions.get(counter)][1]);
            answerC.setText(choices[oldMissedQuestions.get(counter)][2]);
            answerD.setText(choices[oldMissedQuestions.get(counter)][3]);
        }
    }

    void finishQuiz() {
        Log.d("Missed questions array: ", String.valueOf(oldMissedQuestions));
        for (int i = 0; i < missedQuestions.size(); i++) {
            Log.d("Benny", String.valueOf(missedQuestions.get(i)));
        }
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
                player = MediaPlayer.create(QuizActivity.this, R.raw.yb);
                player.start();
            }
        } else if (score >= passCondition * 0.6) {
            passStatus = "Yay!!! you passed!!!";
            if (!muted) {
                player.stop();
                player = MediaPlayer.create(QuizActivity.this, R.raw.yb);
                player.start();
            }
        } else {
            passStatus = "Haha you failed";
            if (!muted) {
                player.stop();
                player = MediaPlayer.create(QuizActivity.this, R.raw.robbery);
                player.start();
            }
        }
        Log.d("array", String.valueOf(missedQuestions));
        if (missedQuestions.size() < 1) {
            noMissed = "MQ";
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

    void restartQuiz() {
        score = 0;
        questionNumber = 0;
        loadNewQuestion();
    }

    void mainMenu() {
        player.stop();
        Log.d("QuizActivity: missedQuestions = ", String.valueOf(missedQuestions));
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("missedQuestions", missedQuestions);
        startActivity(intent);
    }

    void resetButtonColors() {
        answerA.setBackgroundColor(Color.GRAY);
        answerB.setBackgroundColor(Color.GRAY);
        answerC.setBackgroundColor(Color.GRAY);
        answerD.setBackgroundColor(Color.GRAY);
    }
}