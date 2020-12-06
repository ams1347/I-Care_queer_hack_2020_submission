package com.hfad.queer_hack_2020_submission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StandardQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_question);
    }

    /**
     * This method updates the user's score and moves on to the next question
     * @param v
     */
    public void yesClicked(View v)
    {
        // user updates points
        // move to next page (for now it's set to the main page)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    } // yesClicked()

    public void noClicked(View v)
    {
        // user updates points
        // move to next page (for now it's set to the main page)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}