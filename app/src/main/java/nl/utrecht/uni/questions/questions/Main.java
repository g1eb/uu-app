package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.firebase.client.Firebase;

public class Main extends AppCompatActivity {

    Firebase fbRef; // Firebase reference
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment newFragment = new Intro();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Firebase.setAndroidContext(this);
        fbRef = new Firebase("https://uu-app.firebaseio.com/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        removeUIElements();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
    }

    private void removeUIElements() {
        // Hide the status bar, action bar, navigation
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        // Enforce landscape layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void printQuestion() {
        if (question != null && !question.equals("")) {
            fbRef.child("questions").push().setValue(question);
        }
    }
}
