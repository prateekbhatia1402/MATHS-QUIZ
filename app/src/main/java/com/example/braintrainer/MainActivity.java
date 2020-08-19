package com.example.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    TextView scoreTextView,timeTextView,quesTextView,resultTextView;
    GridLayout gridLayout;
    Button button1,button2,button3,button4,gameButton;
    // first operand , second operand and the correct answer
    int op1,op2,ans;
    int totalAttempts=0,correctAttempts=0;
    // order in which options can be displayed
    int[][] buttonOrders={
                        {1,2,3,4},
                        {1,2,4,3},
                        {1,3,2,4},
                        {1,3,4,2},
                        {1,4,2,3},
                        {1,4,3,2},
                        {2,1,3,4},
                        {2,1,4,3},
                        {2,3,1,4},
                        {2,3,4,1},
                        {2,4,1,3},
                        {2,4,3,1},
                        {3,2,1,4},
                        {3,2,4,1},
                        {3,1,2,4},
                        {3,1,4,2},
                        {3,4,2,1},
                        {3,4,1,2},
                        {4,2,3,1},
                        {4,2,1,3},
                        {4,3,2,1},
                        {4,3,1,2},
                        {4,1,2,3},
                        {4,1,3,2}
                        };
    boolean gameOn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int gameduration=30000;// sets duration of game to 30s
        scoreTextView=findViewById(R.id.scoreTextView);
        timeTextView=findViewById(R.id.timeTextView);
        quesTextView=findViewById(R.id.quesTextView);
        resultTextView=findViewById(R.id.resultTextView);
        gridLayout=findViewById(R.id.gridLayout);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        gameButton=findViewById(R.id.gameButton);
        countDownTimer=new CountDownTimer(gameduration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timeleft=(int)millisUntilFinished/1000;
                timeTextView.setText(timeleft+"s");
            }

            @Override
            public void onFinish() {
                gameOn=false;
                gridLayout.setEnabled(false);
                resultTextView.setText("!!WELL PLAYED!!");
                gameButton.setVisibility(View.VISIBLE);
            }
        };
    }

    public void startGame(View view)
    {
    	//initializes or resets the game variables and starts the game
        Button gameButton=(Button) view;
        if(gameButton.getText().toString().equalsIgnoreCase("play!!"))
            gameButton.setText("REPLAY!!");
        gameButton.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        quesTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        countDownTimer.start();
        gameOn=true;
        totalAttempts=0;
        correctAttempts=0;
        resultTextView.setText("");
        updateScore();
        getQuestion();
    }

    public void checkAnswer(View view)
    {
        if(gameOn) {
            Button clickedButton = (Button) view;// reference to button the user clicked
            // checks whether user answered correctly or not
            if (clickedButton.getText().toString().equalsIgnoreCase("" + ans)) {
            	//shows message that answer was correct and updates the score variable
                resultTextView.setText("!RIGHT!");
                correctAttempts++;
            } else {            	
            //shows message that answer was wrond
                resultTextView.setText("!WRONG!");
            }
            totalAttempts++;// increases number of questions attempted till now
            updateScore();// shows updated scores
            getQuestion();// displays the next question
        }
    }

    private void getQuestion(){
        gridLayout.setEnabled(false);
        Random random=new Random();
        // create 2 random numbers and randomly alot them an operator 
        op1=random.nextInt(101); 
        op2=random.nextInt(101);
        int oper=random.nextInt(3);
        char operator;
        // based on the operator calculate the correct answer and the symbol of operator 
        switch(oper)
        {
            case 0:operator='+';
                    ans=op1+op2;
                    break;
            case 1:operator='-';
                    ans=op1-op2;
                    break;
            default:operator='X';
                    ans=op1*op2;
        }
        quesTextView.setText(op1+" "+operator+" "+op2);// display the question
        // calculate 3 wrong answers to display as options
        int[] wOpt=new int[3];
        Arrays.fill(wOpt,ans);
        for(int i=0;i<3;i++)
        {
            do{
                int diff=random.nextInt(19)+1;
                int operat=random.nextInt(100);
                wOpt[i]=(operat<50)?ans+diff:ans-diff;
                boolean canLeave=true;
                for(int j=0;j<3;j++)
                {
                    if(j!=i)
                    {
                        if(wOpt[i]==wOpt[j])
                        {
                            canLeave=false;
                            break;
                        }
                    }
                }
                if(canLeave)break;
            }
            while(true);
        }
        // figure out the order in which options are to be displayed
        int orderNo=random.nextInt(24);
        int[] selButtonOrder=buttonOrders[orderNo];
        //display the options
        for(int i=0;i<4;i++)
        {
            String ansTobePosted="";
            ansTobePosted+=(i<3)?wOpt[i]:ans;
            int buttonNo=selButtonOrder[i];
            switch(buttonNo){
                case 1:button1.setText(ansTobePosted);break;
                case 2:button2.setText(ansTobePosted);break;
                case 3:button3.setText(ansTobePosted);break;
                case 4:button4.setText(ansTobePosted);break;
            }
        }
        gridLayout.setEnabled(true);
    }
    private void updateScore(){
        scoreTextView.setText(correctAttempts+"/"+totalAttempts);// updated scores are shown to user
    }
}
