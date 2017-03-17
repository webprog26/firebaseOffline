package com.dark.webprog26.firebaseoffline;

import android.util.Log;

import java.util.List;

/**
 * Created by webpr on 15.03.2017.
 */

public class Question {

    public static final int FIRST_ORDER_QUESTION = 0;
    public static final int SECOND_ORDER_QUESTION = 1;

    private long mId;
    private String mQuestionString;
    private List<Answer> mAnswers;
    protected long mQuestionType;
    private long mAnswersNum;
    private String mQuestionImageName;
    private boolean hasImage;

    public Question() {
    }

    public Question(long id, String mQuestionString, List<Answer> answers, long questionType, String questionImageName) {
        this.mId = id;
        this.mQuestionString = mQuestionString;
        this.mAnswers = answers;
        this.mQuestionImageName = questionImageName;
        this.hasImage = (questionImageName != null && !questionImageName.equalsIgnoreCase("null"));
        if(answers != null){
            this.mAnswersNum = answers.size();
        }

        try{
            setQuestionType(questionType);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    protected String getQuestionString() {
        return mQuestionString;
    }

    protected List<Answer> getAnswers() {
        return mAnswers;
    }

    public long getQuestionType() {
        return mQuestionType;
    }

    public void setQuestionType(long questionType) throws Exception {
        if(questionType != FIRST_ORDER_QUESTION && questionType != SECOND_ORDER_QUESTION){
            throw new Exception("Wrong question type");
        } else {
            this.mQuestionType = questionType;
        }
    }

    public long getAnswersNum() {
        return mAnswersNum;
    }

    public String getQuestionImageName() {
        return mQuestionImageName;
    }

    public void setQuestionImageName(String mQuestionImageName) {
        this.mQuestionImageName = mQuestionImageName;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    protected boolean shallShowHelp(){
        return false;
    }

    @Override
    public String toString() {
        return "question with id" + getId() + "\n"
                + "text " + getQuestionString() + "\n"
                + "type " + getQuestionType() + "\n"
                + "has " + getAnswersNum() + " answers" + "\n"
                + "has image " + isHasImage();
    }
}
