package com.hfad.queer_hack_2020_submission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String FIRST_TIME = "first time";
    private FirebaseAuth mAuth;

    private boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.first = getIntent().getBooleanExtra(FIRST_TIME, false);


        TextView coolText = findViewById(R.id.pet_name);
        coolText.setText("Hello again");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(this.first){
            CharSequence addedMsg = "Welcome, " + this.mAuth.getCurrentUser().getEmail() + "!";
            int duration = Snackbar.LENGTH_SHORT;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.pet_name), addedMsg, duration);
            snackbar.setAction("Log Out", new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    signOut();
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Logged Out", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            snackbar.show();
            this.first = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_logout:
                this.signOut();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void goToShop(View v){
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
}