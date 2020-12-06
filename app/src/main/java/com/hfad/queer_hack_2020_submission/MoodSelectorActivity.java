package com.hfad.queer_hack_2020_submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoodSelectorActivity extends AppCompatActivity {

    private static final String TAG = "MoodSelectorActivity";
    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private List<Question> usersQuestions = new ArrayList<>();
    private int currentQuestionNumber;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_selector);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionText = findViewById(R.id.select_mood_question);

        mDb.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("questions").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Question newQuestion = document.toObject(Question.class);
                                Log.d(TAG, "Question: " + newQuestion.getQuestion());
                                usersQuestions.add(newQuestion);
                                currentQuestionNumber = 1;
//                                updateUI();
                            }
                        }
                    }
                });
    }

    private void updateUI(){
            Intent intent = new Intent(this, StandardImageQuestionActivity.class);
            intent.putExtra(StandardImageQuestionActivity.QUESTION_NUM, currentQuestionNumber);
            startActivity(intent);
    }

    public void proceedToNext(View v){
        currentQuestionNumber++;
        updateUI();
    }

}