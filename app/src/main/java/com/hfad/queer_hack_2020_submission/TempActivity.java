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

public class TempActivity extends AppCompatActivity {

    private static final String TAG = "TempActivity";
    public static final String QUESTION_NUM = "question number";
    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private List<Question> usersQuestions = new ArrayList<>();
    private int currentQuestionNumber;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionText = findViewById(R.id.question_text);

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
                                currentQuestionNumber = getIntent().getIntExtra(QUESTION_NUM, 1);
                                updateUI();
                            }
                        }
                    }
                });
    }

    private void updateUI(){
        if(currentQuestionNumber > usersQuestions.size()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.FIRST_TIME, true);
            startActivity(intent);
        } else {
            questionText.setText(usersQuestions.get(currentQuestionNumber - 1).getQuestion());
        }
    }

    public void proceedToNext(View v){
        currentQuestionNumber++;
        updateUI();
    }


}