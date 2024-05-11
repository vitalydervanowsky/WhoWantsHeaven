package com.carloclub.whowantsheaven;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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
    ConstraintLayout lineLayout;

    Timer timer;
    TimerDown timerDown;
    int step = 0;
    boolean pause = false;
    boolean isGameOver = true;
    Questions allQuestions;
    Question currentQuestion;
    String lang = Constants.LANG_RU;
    int distanceBetweenLines = 0;
    Dialog dialog;
    Dialog dialogAI;
    Button buttonThank;
    SharedPreferences sharedPreferences;
    private MediaPlayer correctMediaPlayer;
    private MediaPlayer incorrectMediaPlayer;

    boolean isUsedChange = false;
    boolean isUsed50 = false;
    boolean isUsedAdvise = false;
    boolean isUsedAI = false;
    Button buttonAI;

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
        lineLayout = findViewById(R.id.lineLayout);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_advice);
        buttonThank = dialog.findViewById(R.id.buttonThank);
        dialogAI = new Dialog(MainActivity.this);
        dialogAI.setContentView(R.layout.dialog_ai);
        buttonAI = findViewById(R.id.buttonAI);
        initButtons();
        showStartMenu();
        timer = new Timer();
        moveImageView(0);
        initMediaPlayers();
    }

    private void showStartMenu() {
        buttonAI.setVisibility(View.INVISIBLE);
        buttonChange.setVisibility(View.INVISIBLE);
        button50.setVisibility(View.INVISIBLE);
        buttonAdvice.setVisibility(View.INVISIBLE);

        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.INVISIBLE);

        buttonAnswer1.setText(R.string.start_button);
        buttonAnswer2.setText(R.string.write_review_button);
        buttonAnswer3.setText(R.string.exit_button);
    }

    private void initButtons() {
        buttonAnswer1.setOnClickListener(v -> {
            if (isGameOver) {
                startGame();
            } else {
                enterAnswer(1);
            }
        });

        buttonAnswer2.setOnClickListener(v -> {
            if (isGameOver) {
                writeReview();
            } else {
                enterAnswer(2);
            }
        });

        buttonAnswer3.setOnClickListener(v -> {
            if (isGameOver) {
                finish();
            } else {
                enterAnswer(3);
            }
        });
        buttonAnswer4.setOnClickListener(v -> enterAnswer(4));
        button50.setOnClickListener(v -> {
            if (currentQuestion != null && !isUsed50) {
                button50.setBackgroundResource(R.drawable.binocularused);
                isUsed50 = true;
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
            if (isUsedChange) return;
            buttonAnswer1.setVisibility(View.VISIBLE);
            buttonAnswer2.setVisibility(View.VISIBLE);
            buttonAnswer3.setVisibility(View.VISIBLE);
            buttonAnswer4.setVisibility(View.VISIBLE);
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
            buttonChange.setBackgroundResource(R.drawable.chageused);
            isUsedChange = true;
        });
        buttonAdvice.setOnClickListener(v -> {
            if (currentQuestion != null && currentQuestion.hint != null) {
                showAdvice(currentQuestion.hint);
            }
        });
        buttonAI.setOnClickListener(v -> {
            if (currentQuestion != null && currentQuestion.hint != null) {
                showAdviceAI(currentQuestion.hintAI);
            }
        });
        buttonRules.setOnClickListener(v -> showRules());

        buttonThank.setOnClickListener(v -> dialog.hide());

        Button buttonOK = dialogAI.findViewById(R.id.buttonAIOK);
        buttonOK.setOnClickListener(v -> dialogAI.hide());
    }

    private void writeReview() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void startGame() {
        findViewById(R.id.MainLayout).setBackground(ContextCompat.getDrawable(this, R.drawable.heaven));
        lineLayout.setVisibility(View.VISIBLE);
        isUsedChange = false;
        buttonChange.setVisibility(View.VISIBLE);
        buttonChange.setBackground(ContextCompat.getDrawable(this, R.drawable.flag));
        isUsed50 = false;
        button50.setVisibility(View.VISIBLE);
        button50.setBackground(ContextCompat.getDrawable(this, R.drawable.binoculars));
        isUsedAdvise = false;
        buttonAdvice.setVisibility(View.VISIBLE);
        buttonAdvice.setBackground(ContextCompat.getDrawable(this, R.drawable.phone));
        isUsedAI = false;
        buttonAI.setVisibility(View.VISIBLE);
        buttonAI.setBackground(ContextCompat.getDrawable(this, R.drawable.ai));
        step = 0;
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
        currentQuestion = allQuestions.getQuestion(step, lang);
        showQuestion();
    }

    private void moveImageView(int position) {
        if (distanceBetweenLines == 0) {
            distanceBetweenLines = line1.getTop() - line0.getTop();
        }
        imageView.animate()
                .translationY(distanceBetweenLines * position - 10)
                .setDuration(1800)
                .start();
    }

    private void showRules() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_rules);
        //TextView textView = dialog.findViewById(R.id.rulesTextView);
        //textView.setText(R.string.game_over);//
        dialog.show();
    }

    private void showAdviceAI(String text) {
        if (isUsedAI) return;
        //Dialog dialog = new Dialog(MainActivity.this);
        //dialog.setContentView(R.layout.dialog_advice);
        TextView textAdvice = dialogAI.findViewById(R.id.textAdvice);
        textAdvice.setText(text);
        dialogAI.show();
        isUsedAI = true;
        buttonAI.setBackground(ContextCompat.getDrawable(this, R.drawable.aiused));
    }

    private void showAdvice(String text) {
        if (isUsedAdvise) return;
        //Dialog dialog = new Dialog(MainActivity.this);
        //dialog.setContentView(R.layout.dialog_advice);
        TextView textAdvice = dialog.findViewById(R.id.textAdvice);
        textAdvice.setText(text);
        dialog.show();
        buttonAdvice.setBackgroundResource(R.drawable.phoneused);
        isUsedAdvise = true;
    }

    private void enterAnswer(int userAnswer) {
        if (pause || isGameOver) return;

        int trueAnswer = currentQuestion.trueAnswer;
        if (trueAnswer == 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.rombgood);
        }

        if (userAnswer == 1 && trueAnswer != 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 2 && trueAnswer != 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 4 && trueAnswer != 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 3 && trueAnswer != 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.rombbad);
        }
        isGameOver = userAnswer != trueAnswer;
        timerDown = new TimerDown();
        pause = true;
        timer.schedule(timerDown, 1500, 1500);
        if (isGameOver) {
            //incorrectMediaPlayer.start();
            if(step>10)step=10;
            else if (step>5)step=5;
            else step = 0;
            isGameOver=false;
            correctMediaPlayer.start();
        } else {
            correctMediaPlayer.start();
        }
        moveImageView(step);
    }

    private void showQuestion() {
        //stepTextView.setVisibility(View.VISIBLE);
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
        questionTextView.setText(currentQuestion.question);
        buttonAnswer1.setText(getString(R.string.option1_label, currentQuestion.answer1));
        buttonAnswer2.setText(getString(R.string.option2_label, currentQuestion.answer2));
        buttonAnswer3.setText(getString(R.string.option3_label, currentQuestion.answer3));
        buttonAnswer4.setText(getString(R.string.option4_label, currentQuestion.answer4));
        stepTextView.setText(getString(R.string.step_label, step, Constants.QUIZ_SIZE));
    }

    private void initMediaPlayers() {
        if (correctMediaPlayer == null) {
            correctMediaPlayer = MediaPlayer.create(this, R.raw.correct);
        }
        if (incorrectMediaPlayer == null) {
            incorrectMediaPlayer = MediaPlayer.create(this, R.raw.incorrect);
        }
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
                    //questionTextView.setText(R.string.game_over);
                    isGameOver=false;
                    if(step>10)step=11;
                    else if (step>5)step=6;
                    else step = 1;
                    currentQuestion = allQuestions.getQuestion(step, lang);
                    showQuestion();
                } else if (step == Constants.QUIZ_SIZE) {
                    isGameOver = true;
                    step++;
                    questionTextView.setText(R.string.congratulations);
                    showStartMenu();
                } else {
                    //moveImageView(step);
                    step++;
                    currentQuestion = allQuestions.getQuestion(step, lang);
                    showQuestion();
                }
            });
        }
    }
}
