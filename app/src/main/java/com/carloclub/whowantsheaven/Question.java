package com.carloclub.whowantsheaven;

public class Question {

    String TextQuestion;
    String Answer1;
    String Answer2;
    String Answer3;
    String Answer4;
    int TrueAnswer;
    String Hint;
    int id;

    public Question (String Q, String A1, String A2, String A3, String A4, int TA, String H, int ID) {
        TextQuestion = Q;
        Answer1 = A1;
        Answer2 = A2;
        Answer3 = A3;
        Answer4 = A4;
        TrueAnswer = TA;
        Hint = H;
        id = ID;
    }
}