package com.carloclub.whowantsheaven;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    Button buttonAdvice;
    Button buttonChage;

    Button button50;
    Timer timer;
    TimerDown mtimerDown;
    TimerFly mTimerFly;
    TextView ViewQuetion;
    TextView ViewStep;
    int Step=0;
    boolean pause=false;
    boolean FalseAnswer=false;
    boolean GameOver = true;
    Questions allQuestions;
    Question CurrentQuestion;
    String Lang = "Ru";
    LinearLayout Layout1;
    int heightPixels;
    int PixStep=250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allQuestions = new Questions();
        ViewQuetion = findViewById(R.id.ViewQuetion);
        ViewStep = findViewById(R.id.ViewStep);
        Layout1 = findViewById(R.id.Layout1);

        heightPixels = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        PixStep = Math.round(heightPixels/17);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameOver) StartGame();
                else EnterAnswer(1);
            }
        });

        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameOver) showAdvice("Оставить отзыв можно будет после окончательной публикации приложения");
                else EnterAnswer(2);
            }
        });

        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameOver) finish();
                else  EnterAnswer(3);
            }
        });


        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAnswer(4);
            }
        });
        buttonAnswer4.setVisibility(View.INVISIBLE);

        button50 = findViewById(R.id.button50);
        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentQuestion.TrueAnswer==2) buttonAnswer1.setVisibility(View.INVISIBLE);
                else buttonAnswer2.setVisibility(View.INVISIBLE);
                if(CurrentQuestion.TrueAnswer==4) buttonAnswer3.setVisibility(View.INVISIBLE);
                else buttonAnswer4.setVisibility(View.INVISIBLE);
            }
        });

        buttonChage = findViewById(R.id.buttonChage);
        buttonChage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause||GameOver) return;
                Question newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                if (newQuestion.TextQuestion.equals(CurrentQuestion.TextQuestion))  newQuestion = allQuestions.getQuestion(Step, Lang);
                CurrentQuestion = newQuestion;
                ShowQuestion();

            }
        });

        buttonAdvice = findViewById(R.id.buttonAdvice);
        buttonAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentQuestion != null &&  CurrentQuestion.Hint != null) {
                    showAdvice(CurrentQuestion.Hint);
                }
            }
        });

        int needH = heightPixels-PixStep-Math.round(PixStep/3*3);
        int WW = Layout1.getLayoutParams().width;
        Layout1.setLayoutParams(new LinearLayout.LayoutParams(WW,needH));


        timer = new Timer();
        mTimerFly = new TimerFly();
        timer.schedule(mTimerFly, 100, 50);
    }

    void StartGame(){
        Step = 1;
        GameOver = false;
        buttonAnswer4.setVisibility(View.VISIBLE);
        CurrentQuestion = allQuestions.getQuestion(Step, Lang);
        ShowQuestion();

    }
    void showAdvice(String text){
        Dialog D = new Dialog(MainActivity.this);
        D.setContentView(R.layout.advice);
        TextView textAdvice = D.findViewById(R.id.textAdvice);
        textAdvice.setText(text);
        D.show();

    }
    void EnterAnswer(int nAns){
        if (pause||GameOver) return;

        int truAns=CurrentQuestion.TrueAnswer;
        if (truAns==1) buttonAnswer1.setBackgroundResource(R.drawable.rombgreen);
        if (truAns==2) buttonAnswer2.setBackgroundResource(R.drawable.rombgreen);
        if (truAns==3) buttonAnswer3.setBackgroundResource(R.drawable.rombgreen);
        if (truAns==4) buttonAnswer4.setBackgroundResource(R.drawable.rombgreen);

        if (nAns==1 && truAns!=1) buttonAnswer1.setBackgroundResource(R.drawable.romborange);
        if (nAns==2 && truAns!=2) buttonAnswer2.setBackgroundResource(R.drawable.romborange);
        if (nAns==4 && truAns!=4) buttonAnswer4.setBackgroundResource(R.drawable.romborange);
        if (nAns==3 && truAns!=3) buttonAnswer3.setBackgroundResource(R.drawable.romborange);
        GameOver = (nAns!=truAns);
        mtimerDown = new TimerDown();
        pause=true;
        timer.schedule(mtimerDown, 1500, 1500);

      }

    void ShowQuestion(){
        ViewQuetion.setText(CurrentQuestion.TextQuestion);
        buttonAnswer1.setText("А. "+CurrentQuestion.Answer1);
        buttonAnswer2.setText("Б. "+CurrentQuestion.Answer2);
        buttonAnswer3.setText("В. "+CurrentQuestion.Answer3);
        buttonAnswer4.setText("Г. "+CurrentQuestion.Answer4);
        ViewStep.setText(String.valueOf(Step)+"/15");
    }

    class TimerDown extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    // Обновляем фоны всех кнопок
                    buttonAnswer1.setBackgroundResource(R.drawable.romb);
                    buttonAnswer2.setBackgroundResource(R.drawable.romb);
                    buttonAnswer3.setBackgroundResource(R.drawable.romb);
                    buttonAnswer4.setBackgroundResource(R.drawable.romb);
                    mtimerDown.cancel();
                    mtimerDown=null;
                    pause=false;

                    buttonAnswer1.setVisibility(View.VISIBLE);
                    buttonAnswer2.setVisibility(View.VISIBLE);
                    buttonAnswer3.setVisibility(View.VISIBLE);
                    buttonAnswer4.setVisibility(View.VISIBLE);

                    if (GameOver) {ViewQuetion.setText("GAME OVER");Step=0;}
                    else if (Step==15) {
                        GameOver=true;
                        Step++;
                        ViewQuetion.setText("Поздравляем с победой!!!");
                    } else {
                        Step++;
                        CurrentQuestion = allQuestions.getQuestion(Step, Lang);
                        ShowQuestion();
                    }
                }
            });
        }
    }

    class TimerFly extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                   int needH = heightPixels - (Step)*PixStep-(int)Math.round(PixStep/4*5.4);
                   if (Step==0) needH=needH-Math.round(PixStep/2);;
                   int WW = Layout1.getLayoutParams().width;
                   int HH = Layout1.getLayoutParams().height;
                   if (needH==HH) return;
                   if (HH>needH) HH=HH-2;
                   if (HH<needH) HH=HH+1;
                    Layout1.setLayoutParams(new LinearLayout.LayoutParams(WW,HH));
                }
            });
        }
    }

}