package com.dark.webprog26.firebaseoffline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnJSONReadFromAssetsListener, View.OnClickListener{

    private static final String TAG = "MainActivity_TAG";

    private static final int QUESTIONS_NUM = 4;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private int questionIndex = 0;
    private TextView mTextView;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tvQuestionText);
        mButton = (Button) findViewById(R.id.btnNext);
        mButton.setOnClickListener(this);
        mDatabase = FirebaseApplication.getFirebaseDatabase();
        if(mDatabase != null){
            Log.i(TAG, "Firebase database initialized");
            mReference = mDatabase.getReference("questions");
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                Log.i(TAG, "database exists");
                                getNextQuestion(questionIndex);
                            } else {
                                Log.i(TAG, "database not exists");
                                new JSONLoaderThread(getAssets(), MainActivity.this, "data.json").start();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i(TAG, "databaseError: " + databaseError.getMessage());
                        }
                    });
        } else {
            Log.i(TAG, "Firebase database is null");
        }
    }

    @Override
    public void jsonReadFromAssets(String jsonString) {
        List<Question> questionsList = new ArrayList<>();
        if(jsonString != null){
            JSONObject questionsJSONObject = null;
            try {
                questionsJSONObject = new JSONObject(jsonString);
            } catch (JSONException e){
                e.printStackTrace();
            }

            if(questionsJSONObject != null){
                JSONArray jsonArray = null;
                try{
                    jsonArray = questionsJSONObject.getJSONArray("questions");
                    Log.i(TAG, "Array has " + jsonArray.length() + " questions");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                if(jsonArray != null){
                    JSONObject singleQuestionObject = null;
                    try{
                        for (int i = 0; i < jsonArray.length(); i++) {
                            singleQuestionObject = jsonArray.getJSONObject(i);
                            Question question = null;
                            if(singleQuestionObject != null){
                                JSONArray jsonAnswers = singleQuestionObject.getJSONArray("answers");
                                int answersLength = jsonAnswers.length();
                                List<Answer> answers = new ArrayList<>();
                                for(int j = 0; j < answersLength; j++){
                                    JSONObject answerObject = jsonAnswers.getJSONObject(j);
                                    Answer answer = new Answer(answerObject.getLong("id"),
                                                                answerObject.getString("text"),
                                                                answerObject.getDouble("points"),
                                                                answerObject.getLong("nextQuestionId"));
                                    answers.add(answer);
                                }
                                question = new Question(
                                        singleQuestionObject.getLong("id"),
                                        singleQuestionObject.getString("text"),
                                        answers,
                                        singleQuestionObject.getInt("type"),
                                        singleQuestionObject.getString("questionImageName"));
                            }
                            if(question != null){
                                Log.i(TAG, question.toString());
                                questionsList.add(question);
                            }
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            } else {
                Log.i(TAG, "jsonObject is null");
            }
        } else {
            Log.i(TAG, "jsonString is null");
        }

        if(questionsList.size() > 0){
            for(Question question: questionsList){
                mReference.child(String.valueOf(question.getId())).setValue(question);
            }
        }
        getNextQuestion(questionIndex);
    }

    private void getNextQuestion(int questionIndex){
        mReference.child(String.valueOf(questionIndex)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long answersNum = (long) dataSnapshot.child("answersNum").getValue();
                List<Answer> answers  = new ArrayList<Answer>();
                for(int i = 0; i < answersNum; i++){
                    Answer answer = dataSnapshot.child("answers").child(String.valueOf(i)).getValue(Answer.class);
                    answers.add(answer);
                }
                Log.i(TAG, "answers size " + answers.size());
                Question question = new Question(

                        (long) dataSnapshot.child("id").getValue(),
                        String.valueOf(dataSnapshot.child("questionString").getValue()),
                        answers,
                        (long) dataSnapshot.child("mQuestionType").getValue(),
                        String.valueOf(dataSnapshot.child("questionImageName").getValue())

                );
                mTextView.setText(question.getQuestionString());
                Log.i(TAG, question.toString());
                for(Answer answer: question.getAnswers()){
                    Log.i(TAG, answer.getAnswerText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(questionIndex < (QUESTIONS_NUM - 1)){
            questionIndex++;
        } else{
            questionIndex = 0;
        }
        getNextQuestion(questionIndex);
    }
}
