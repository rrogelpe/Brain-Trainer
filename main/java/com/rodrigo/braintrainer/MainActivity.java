package com.rodrigo.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridLayout grid;
    Random random;
    int red;
    int blue;
    int green;
    int purple;
    int[] colorArray;
    int dec1;
    int dec2;
    int answer;
    int right = 0;
    int wrong = 0;
    TextView sum;
    TextView resultView;
    //TextView resultView2;
    CountDownTimer c;
    HashMap <Integer, Integer> colorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = Color.argb(255, 240, 37, 47);
        blue = Color.argb(255,37,216,240);
        green = Color.argb(255,41,240,37);
        purple = Color.argb(255,192,37,240);

        colorArray = new int[]{red,blue,green,purple};
        colorMap = new HashMap<>();

        grid = (GridLayout) findViewById(R.id.gridLayout);

        for (int i=0; i<grid.getChildCount(); i++){
            ((Button) grid.getChildAt(i)).setVisibility(View.INVISIBLE);
        }
    }

    public void checkAnswer(View view){
        Button button = (Button) view;
        String userChoice = button.getText().toString();
        if (Integer.parseInt(userChoice) == answer){
            displayRightOrWrong("Correct!");
            colorMap.clear();
            for (int i=0; i<grid.getChildCount(); i++){
                ((Button)grid.getChildAt(i)).setText("");
            }
            right++;
            // run method showing new sum and button texts
            generateProblemAndAnswers();
        }else{
            wrong++;
            displayRightOrWrong("Incorrect");

        }
        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText(right + "/" + wrong);
    }

    public void displayRightOrWrong(final String s){

        final TextView resultView2 = (TextView) findViewById(R.id.resultText2);

        new CountDownTimer(1000+10,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                resultView2.setVisibility(View.VISIBLE);
                resultView2.setText(s);
            }

            @Override
            public void onFinish() {
                //resultView.setText("");
                resultView2.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    public void generateProblemAndAnswers(){
        random = new Random();
        dec1 = 1 + random.nextInt(100);
        dec2 = 1 + random.nextInt(100);
        sum = (TextView) findViewById(R.id.sumView);
        sum.setText(dec1 + " + " + dec2);
        answer = dec1 + dec2;

        ((Button) grid.getChildAt(random.nextInt(colorArray.length))).setText(String.valueOf(answer));

        for (int i=0; i<grid.getChildCount(); i++){

            if (((Button) grid.getChildAt(i)).getText() == ""){
                ((Button) grid.getChildAt(i)).setText(String.valueOf((answer/2) + random.nextInt(answer)));
            }

            int color = colorArray[random.nextInt(colorArray.length)];
            while (colorMap.containsKey(color)){
                color = colorArray[random.nextInt(colorArray.length)];
            }
            ((Button) grid.getChildAt(i)).setBackgroundColor(color);
            colorMap.put(color,1);
        }
    }

    public void startGame(View view){
        setEnabledButtons(true);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setVisibility(View.INVISIBLE);

        resultView = (TextView) findViewById(R.id.resultText);
        resultView.setVisibility(View.INVISIBLE);

        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setVisibility(View.INVISIBLE);

        final TextView secondsView = (TextView) findViewById(R.id.secondsView);
        secondsView.setVisibility(View.VISIBLE);

        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText("0/0");
        scoreView.setVisibility(View.VISIBLE);

        c = new CountDownTimer(30000 + 100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsView.setText(":" + String.valueOf((int) millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                secondsView.setText(":0s");
                Button restartButton = (Button) findViewById(R.id.restartButton);
                colorMap.clear();
                setEnabledButtons(false);
                restartButton.setVisibility(View.VISIBLE);
                resultView.setVisibility(View.VISIBLE);
                resultView.setText("Your Score: " + right + "/" + wrong);
            }
        }.start();

        for (int i=0; i<grid.getChildCount(); i++){
            ((Button) grid.getChildAt(i)).setVisibility(View.VISIBLE);
        }
        //call method here
        generateProblemAndAnswers();
    }

    public void setEnabledButtons(boolean b){
        for (int i=0; i<grid.getChildCount(); i++){
            ((Button) grid.getChildAt(i)).setEnabled(b);
        }
    }
}
