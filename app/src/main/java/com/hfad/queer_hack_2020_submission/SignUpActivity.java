package com.hfad.queer_hack_2020_submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mConfirmPasswordField = findViewById(R.id.confirm_password);
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        attemptToLogIn(currentUser);
    }

    private void attemptToLogIn(FirebaseUser currentUser) {
        if (currentUser != null) {
            Map<String, Object> newUser = new HashMap<>();
            newUser.put("userID", currentUser.getUid());
            newUser.put("userEmail", currentUser.getEmail());
            newUser.put("coins", 0);
            mDb.collection("users").document(currentUser.getUid())
                    .set(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "User successfully created");
                        }
                    });
            Question personalQuestion1 = new Question("How's your mood today?", "moodScale");
            Question personalQuestion2 = new Question("Did you eat 3 square meals today?");
            Question personalQuestion3 = new Question("Did you drink at least 8 cups of water today?");
            CollectionReference userQs = mDb.collection("users").document(currentUser.getUid())
                    .collection("questions");
            userQs.add(personalQuestion1)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "q1 added");
                        }
                    });
            userQs.add(personalQuestion2)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "q2 added");
                        }
                    });
            userQs.add(personalQuestion3)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "q3 added");
                        }
                    });
//            Intent intent = new Intent(this, TempActivity.class);
//            intent.putExtra(TempActivity.QUESTION_NUM, 1);
//            startActivity(intent);

            Intent intent = new Intent(this, MoodSelectorActivity.class);
            startActivity(intent);

        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Please input an email to sign up.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String user_password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(user_password)) {
            mPasswordField.setError("Please input a password to sign up.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        String confirm_password = mConfirmPasswordField.getText().toString();
        if (TextUtils.isEmpty(confirm_password)) {
            mConfirmPasswordField.setError("Please type your password again to confirm.");
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }
        if (!user_password.equals(confirm_password)) {
            mConfirmPasswordField.setError("Your passwords do not match!");
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }
        return valid;
    }

    public void createAccount(View view) {
        if (!validateForm()) {
            return;
        }
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail : success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            attemptToLogIn(user);
                        } else {
                            // If registration fails, display a message to the user.
                            Exception e = task.getException();
                            Log.w(TAG, "createUserWithEmail : failure", e);
                            Toast.makeText(SignUpActivity.this, "Registration failed: " + e.getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                            attemptToLogIn(null);
                        }
                    }
                });
    }

    public void logInInstead(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }


}