package com.example.additiontrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgain;
    ConstraintLayout gameLayout;
    boolean gameActive;
    TextView introTextView;

    // this method is in charge of replaying the game. once the countdown timer finishes, the play again button appears, the media player will sound,
    // gameActive is set to false.
    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
        playAgain.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        gameActive = true;

        //the code below - I am using a timer to count down from 30 seconds - in milliseconds below.
        //onTick is the actual text view of the counter counting down. onFinish I have a media player that will play
        //an airhorn when the timer is finished.
        new CountDownTimer(30150, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgain.setVisibility(View.VISIBLE);
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mediaPlayer.start();
                gameActive = false;
            }
        }.start();
    }

    //here we need to figure out what button the user pressed. we create a method to determine this. We put tags on our button to allow us to locate the button pressed.
    //set the buttons onClick to this chooseAnswer method. if the button id selected is the same as our locationOfCorrectAnswer, set text for result to "Correct", increment score,
    //go to next question.
    public void chooseAnswer(View view) {
        if (gameActive) {
            if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
                resultTextView.setText("Correct!");
                score++;
            } else {
                resultTextView.setText("Wrong :(");
            }
            numberOfQuestions++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            newQuestion();
        }
    }

    //this method is activated when the GO! button is pressed. the button becomes invisible, and the gameLayout is shown.
    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        introTextView.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
        gameActive = true;
    }

    //We are asking our new question here. when this method runs, we are using Rand to come up with random numbers to ask. for our questions, we want to limit the possibilities
    //to numbers between 1 and 20 - just to keep the questions from being too hard. We also use Rand below to randomly pick the other numbers to populate our answer buttons.
    public void newQuestion() {
        //Rand - this his how we get a random number. below, for a & b we use rand.nextInt() and inside we put the bound - the highest int not including the number
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        //this is a loop we will use for looping through our ArrayList answers. why - we want to come up with 4 numbers for our buttons - 3 random up to 40, and one of them
        // has to be the correct answer. created locationOfCorrectAnswer to randomly select a number up to 4. if it happens to be the same as our for loop i (which would be
        // the correct answer), then we add that to our answers array. if the answer does not equal the locationOfCorrectAnswer, we want to pick a random number from 1 to 41
        // to fill up the other 3 buttons. we will save those to our answers array as well. we will then set the text of each of our buttons accordingly.
        for(int i = 0; i < 4; i++) {
            if(i == locationOfCorrectAnswer) {
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == (a+b)) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        //here we are setting the random answers to the different buttons on our app.
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    //initializing the items on our app. we don't want the game layout showing until they press the goButton
    //so initially the gameLayout that contains all of our items is set to invisible.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgain = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        introTextView = findViewById(R.id.introTtextView);

        newQuestion();

        gameLayout.setVisibility(View.INVISIBLE);
        introTextView.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.VISIBLE);

    }



}
