package com.dark.webprog26.firebaseoffline;

/**
 * Created by webpr on 10.03.2017.
 */

public class Answer {

    public long id;
    public String mAnswerText;
    public boolean isCorrect;
    public int mPoints;
    public long nextQuestionId;
    public double mPointsDouble;

    public Answer() {
    }

    public Answer(long id, String mAnswerText, double mPoints, long nextQuestionId) {
        this.id = id;
        this.mAnswerText = mAnswerText;
        this.mPointsDouble = mPoints;
        this.nextQuestionId = nextQuestionId;
    }

    public String getAnswerText() {
        return mAnswerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int mPoints) {
        this.mPoints = mPoints;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNextQuestionId() {
        return nextQuestionId;
    }

    public double getmPointsDouble() {
        return mPointsDouble;
    }

    public void setmPointsDouble(double mPointsDouble) {
        this.mPointsDouble = mPointsDouble;
    }

    public void setNextQuestionId(long nextQuestionId) {
        this.nextQuestionId = nextQuestionId;


    }
}
