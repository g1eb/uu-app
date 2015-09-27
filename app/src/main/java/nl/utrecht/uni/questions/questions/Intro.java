package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Intro extends Fragment {

    static final int DELAY_QUESTION = 3000; // milliseconds
    Handler mHandler;

    TextView exampleQuestion;
    String[] questions;
    int qIndex;

    public static Intro newInstance() {
        Intro fragment = new Intro();
        return fragment;
    }

    public Intro() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        qIndex = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(changeQuestion);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.video_still).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new Video();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        getActivity().findViewById(R.id.intro_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new Question();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Setup tempting example question
        questions = getResources().getStringArray(R.array.tempting_example_questions);
        exampleQuestion = (TextView) getActivity().findViewById(R.id.tempting_example_question);
        exampleQuestion.setText(questions[qIndex]);
        mHandler.postDelayed(changeQuestion, DELAY_QUESTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    Runnable changeQuestion = new Runnable() {
        @Override
        public void run() {
            // Update example question
            qIndex = ( qIndex == 5 ) ? 0 : qIndex;
            exampleQuestion.setText(questions[qIndex++]);
            mHandler.postDelayed(this, DELAY_QUESTION);
        }
    };
}
