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

public class Outro extends Fragment {

    static final int DELAY_OUTRO = 20000; // milliseconds
    Handler mHandler;

    public static Outro newInstance() {
        Outro fragment = new Outro();
        return fragment;
    }

    public Outro() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outro, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.btn_back_to_lobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToIntro();
            }
        });

        mHandler.postDelayed(myTask, DELAY_OUTRO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHandler.removeCallbacks(myTask);
    }

    Runnable myTask = new Runnable() {
        @Override
        public void run() {
            redirectToIntro();
            mHandler.postDelayed(this, DELAY_OUTRO);
        }
    };

    private void redirectToIntro() {
        Fragment newFragment = new Intro();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
