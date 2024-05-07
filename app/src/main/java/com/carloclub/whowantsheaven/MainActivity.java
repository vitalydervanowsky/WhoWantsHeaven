package com.carloclub.whowantsheaven;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    Button buttonAdvice;
    Button buttonChange;
    Button button50;
    TextView questionTextView;
    TextView stepTextView;
    LinearLayout layout1;

    Timer timer;
    TimerDown timerDown;
    TimerFly timerFly;
    int step = 0;
    boolean pause = false;
    boolean FalseAnswer = false;
    boolean isGameOver = true;
    Questions allQuestions;
    Question currentQuestion;
    String lang = Constants.LANG_RU;
    int heightInPixels;
    int stepInPixels = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allQuestions = new Questions();
        questionTextView = findViewById(R.id.questionTextView);
        stepTextView = findViewById(R.id.stepTextView);
        layout1 = findViewById(R.id.layout1);

        heightInPixels = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        stepInPixels = Math.round(heightInPixels / 17);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameOver) {
                    startGame();
                } else {
                    enterAnswer(1);
                }
            }
        });

        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameOver) {
                    writeReview();
                } else {
                    enterAnswer(2);
                }
            }
        });

        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameOver) {
                    finish();
                } else {
                    enterAnswer(3);
                }
            }
        });


        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnswer(4);
            }
        });
        buttonAnswer4.setVisibility(View.INVISIBLE);

        button50 = findViewById(R.id.button50);
        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion != null) {
                    if (currentQuestion.trueAnswer == 2) {
                        buttonAnswer1.setVisibility(View.INVISIBLE);
                    } else {
                        buttonAnswer2.setVisibility(View.INVISIBLE);
                    }
                    if (currentQuestion.trueAnswer == 4) {
                        buttonAnswer3.setVisibility(View.INVISIBLE);
                    } else {
                        buttonAnswer4.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        buttonChange = findViewById(R.id.buttonChange);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause || isGameOver) return;
                Question newQuestion = allQuestions.getQuestion(step, lang);
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                if (newQuestion.question.equals(currentQuestion.question)) {
                    newQuestion = allQuestions.getQuestion(step, lang);
                }
                currentQuestion = newQuestion;
                showQuestion();

            }
        });

        buttonAdvice = findViewById(R.id.buttonAdvice);
        buttonAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion != null && currentQuestion.hint != null) {
                    showAdvice(currentQuestion.hint);
                }
            }
        });

        int needH = heightInPixels - stepInPixels - Math.round(stepInPixels / 3 * 3);
        int WW = layout1.getLayoutParams().width;
        layout1.setLayoutParams(new LinearLayout.LayoutParams(WW, needH));


        timer = new Timer();
        timerFly = new TimerFly();
        timer.schedule(timerFly, 100, 50);
    }

    private void writeReview() {
//        showAdvice("Оставить отзыв можно будет после окончательной публикации приложения");
        String packageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void startGame() {
        step = 1;
        isGameOver = false;
        buttonAnswer4.setVisibility(View.VISIBLE);
        currentQuestion = allQuestions.getQuestion(step, lang);
        showQuestion();
    }

    private void showAdvice(String text) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.advice);
        TextView textAdvice = dialog.findViewById(R.id.textAdvice);
        textAdvice.setText(text);
        dialog.show();
    }

    private void enterAnswer(int userAnswer) {
        if (pause || isGameOver) return;

        int trueAnswer = currentQuestion.trueAnswer;
        if (trueAnswer == 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.rombgreen);
        }
        if (trueAnswer == 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.rombgreen);
        }
        if (trueAnswer == 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.rombgreen);
        }
        if (trueAnswer == 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.rombgreen);
        }

        if (userAnswer == 1 && trueAnswer != 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.romborange);
        }
        if (userAnswer == 2 && trueAnswer != 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.romborange);
        }
        if (userAnswer == 4 && trueAnswer != 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.romborange);
        }
        if (userAnswer == 3 && trueAnswer != 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.romborange);
        }
        isGameOver = userAnswer != trueAnswer;
        timerDown = new TimerDown();
        pause = true;
        timer.schedule(timerDown, 1500, 1500);
    }

    private void showQuestion() {
        questionTextView.setText(currentQuestion.question);
        buttonAnswer1.setText(getString(R.string.option1_label, currentQuestion.answer1));
        buttonAnswer2.setText(getString(R.string.option2_label, currentQuestion.answer2));
        buttonAnswer3.setText(getString(R.string.option3_label, currentQuestion.answer3));
        buttonAnswer4.setText(getString(R.string.option4_label, currentQuestion.answer4));
        stepTextView.setText(getString(R.string.step_label, step, Constants.QUIZ_SIZE));
    }

    class TimerDown extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // Обновляем фоны всех кнопок
                    buttonAnswer1.setBackgroundResource(R.drawable.romb);
                    buttonAnswer2.setBackgroundResource(R.drawable.romb);
                    buttonAnswer3.setBackgroundResource(R.drawable.romb);
                    buttonAnswer4.setBackgroundResource(R.drawable.romb);
                    timerDown.cancel();
                    timerDown = null;
                    pause = false;

                    buttonAnswer1.setVisibility(View.VISIBLE);
                    buttonAnswer2.setVisibility(View.VISIBLE);
                    buttonAnswer3.setVisibility(View.VISIBLE);
                    buttonAnswer4.setVisibility(View.VISIBLE);

                    if (isGameOver) {
                        questionTextView.setText(R.string.game_over);
                        step = 0;
                    } else if (step == Constants.QUIZ_SIZE) {
                        isGameOver = true;
                        step++;
                        questionTextView.setText(R.string.congratulations);
                    } else {
                        step++;
                        currentQuestion = allQuestions.getQuestion(step, lang);
                        showQuestion();
                    }
                }
            });
        }
    }

    class TimerFly extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    int needH = heightInPixels - (step) * stepInPixels - (int) Math.round(stepInPixels / 4 * 5.4);
                    if (step == 0) needH = needH - Math.round(stepInPixels / 2);
                    int WW = layout1.getLayoutParams().width;
                    int HH = layout1.getLayoutParams().height;
                    if (needH == HH) return;
                    if (HH > needH) HH = HH - 2;
                    if (HH < needH) HH = HH + 1;
                    layout1.setLayoutParams(new LinearLayout.LayoutParams(WW, HH));
                }
            });
        }
    }
}
