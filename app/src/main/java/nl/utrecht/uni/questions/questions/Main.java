package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.Firebase;

public class Main extends AppCompatActivity {

    Firebase fbRef; // Firebase reference

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

    private void removeUIElements() {
        // Hide the status bar, action bar, navigation
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    public void logQuestion(String question) {
        fbRef.child("questions").push().setValue(question);
    }

    public void printLabel(final String question) {
        logQuestion(question);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        new LabelPrinter(question).execute();
                    }
                }, 1000);
    }
}
