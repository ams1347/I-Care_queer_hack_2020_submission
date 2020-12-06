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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardImageQuestionActivity extends AppCompatActivity {

    private static final String TAG = "StandardImageQuest";
    public static final String QUESTION_NUM = "question number";
    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;

    private List<Question> usersQuestions = new ArrayList<>();
    private int currentQuestionNumber;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_image_standard);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentUser = mAuth.getCurrentUser();

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
                            }
                            currentQuestionNumber = getIntent().getIntExtra(QUESTION_NUM, 2);
                            updateUI();
                        }
                    }
                });

    }

    private void updateUI(){
        Log.d(TAG, "updateUI");
        if(currentQuestionNumber > usersQuestions.size()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.FIRST_TIME, true);
            startActivity(intent);
        } else {
            if(usersQuestions.get(currentQuestionNumber - 1).getQuestion().equals("How's your mood today?")){
                proceedToNext();
            }
            questionText.setText(usersQuestions.get(currentQuestionNumber - 1).getQuestion());
        }
    }

    public void proceedToNext(){
        Log.d(TAG, "proceed");
        currentQuestionNumber++;
        updateUI();
    }



    /**
     * This method updates the user's score and moves on to the next question
     * @param v
     */
    public void yesClicked(View v)
    {
        Log.d(TAG, "yesclicked");
        // user gets coins
        mDb.collection("users").document(currentUser.getUid())
            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User newUser = documentSnapshot.toObject(User.class);
                newUser.setCoins(newUser.getCoins() + 3);
                mDb.collection("users").document(currentUser.getUid())
                        .set(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "User coins successfully updated");
                            }
                        });
            }
        });
        // proceed to next, which will move to next page if needed
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        proceedToNext();

    } // yesClicked()

    public void noClicked(View v)
    {
        Log.d(TAG, "noclicked");
        // user does not get coins
        // proceed to next, which will move to next page if needed
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        proceedToNext();
    }

}