package com.dark.webprog26.firebaseoffline;

import java.util.List;

/**
 * Created by webpr on 10.03.2017.
 */

public class OrdinaryQuestion extends Question {

    public OrdinaryQuestion(long id, String mQuestionString, List<Answer> answers, int questionType, String questionImageName) {
        super(id, mQuestionString, answers, questionType, questionImageName);
        try {
            setQuestionType(Question.FIRST_ORDER_QUESTION);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected boolean shallShowHelp() {
        return false;
    }
}
