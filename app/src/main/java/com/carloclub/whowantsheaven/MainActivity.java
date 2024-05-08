package com.carloclub.whowantsheaven;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    Button buttonRules;
    TextView questionTextView;
    TextView stepTextView;
    ImageView imageView;
    View line0;
    View line1;

    Timer timer;
    TimerDown timerDown;
    int step = 0;
    boolean pause = false;
    boolean FalseAnswer = false;
    boolean isGameOver = true;
    Questions allQuestions;
    Question currentQuestion;
    String lang = Constants.LANG_RU;
    int distanceBetweenLines = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        allQuestions = new Questions();
        questionTextView = findViewById(R.id.questionTextView);
        stepTextView = findViewById(R.id.stepTextView);
        line0 = findViewById(R.id.line0);
        line1 = findViewById(R.id.line1);
        imageView = findViewById(R.id.imageView);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        button50 = findViewById(R.id.button50);
        buttonChange = findViewById(R.id.buttonChange);
        buttonAdvice = findViewById(R.id.buttonAdvice);
        buttonRules = findViewById(R.id.buttonRules);
        initButtons();
        timer = new Timer();
    }

    private void initButtons() {
        buttonAnswer1.setText(R.string.start_button);
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer1.setOnClickListener(v -> {
            if (isGameOver) {
                startGame();
            } else {
                enterAnswer(1);
            }
        });
        buttonAnswer2.setText(R.string.write_review_button);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer2.setOnClickListener(v -> {
            if (isGameOver) {
                writeReview();
            } else {
                enterAnswer(2);
            }
        });
        buttonAnswer3.setText(R.string.exit_button);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer3.setOnClickListener(v -> {
            if (isGameOver) {
                finish();
            } else {
                enterAnswer(3);
            }
        });
        buttonAnswer4.setOnClickListener(v -> enterAnswer(4));
        buttonAnswer4.setVisibility(View.INVISIBLE);
        button50.setOnClickListener(v -> {
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
        });
        buttonChange.setOnClickListener(v -> {
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

        });
        buttonAdvice.setOnClickListener(v -> {
            if (currentQuestion != null && currentQuestion.hint != null) {
                showAdvice(currentQuestion.hint);
            }
        });
        buttonRules.setOnClickListener(v -> showRules());
    }

    private void writeReview() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void startGame() {
        moveImageView(step);
        if (!sharedPreferences.getBoolean(Constants.SHOULD_SHOW_RULES_DIALOG, false)) {
            showRules();
            sharedPreferences
                    .edit()
                    .putBoolean(Constants.SHOULD_SHOW_RULES_DIALOG, true)
                    .apply();
        }
        step = 1;
        isGameOver = false;
        buttonAnswer4.setVisibility(View.VISIBLE);
        currentQuestion = allQuestions.getQuestion(step, lang);
        showQuestion();
    }

    private void moveImageView(int position) {
        if (distanceBetweenLines == 0) {
            distanceBetweenLines = line1.getTop() - line0.getTop();
        }
        imageView.animate()
                .translationY(distanceBetweenLines * position)
                .setDuration(1000)
                .start();
    }

    private void showRules() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_rules);
        TextView textView = dialog.findViewById(R.id.rulesTextView);
        textView.setText(R.string.game_over);// todo use another text
        dialog.show();
    }

    private void showAdvice(String text) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_advice);
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
            runOnUiThread(() -> {
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
                    initButtons();
                } else if (step == Constants.QUIZ_SIZE) {
                    isGameOver = true;
                    step++;
                    questionTextView.setText(R.string.congratulations);
                    initButtons();
                } else {
                    moveImageView(step);
                    step++;
                    currentQuestion = allQuestions.getQuestion(step, lang);
                    showQuestion();
                }
            });
        }
    }
}
