package com.carloclub.whowantsheaven;

public class Question {

    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int trueAnswer;
    String hint;
    String hintAI;
    int id;

    public Question(
            String question,
            String answer1,
            String answer2,
            String answer3,
            String answer4,
            int trueAnswer,
            String hint,
            String hintAI,
            int id
    ) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.trueAnswer = trueAnswer;
        this.hint = hint;
        this.hintAI = hintAI;
        this.id = id;
    }
}
