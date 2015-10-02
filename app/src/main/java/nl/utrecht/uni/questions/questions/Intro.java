package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Intro extends Fragment {

    static final int ANIMATION_DURATION = 1000; // milliseconds
    static final int ANIMATION_DELAY = 15000; // milliseconds

    TextView currentQuestion, q1, q2, q3, q4, q5, q6;
    Animation animationSlideInLeft, animationSlideOutRight;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                redirectToQuestion();
            }
        });

        getActivity().findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToQuestion();
            }
        });

        q1 = (TextView) getActivity().findViewById(R.id.example_question1);
        q2 = (TextView) getActivity().findViewById(R.id.example_question2);
        q3 = (TextView) getActivity().findViewById(R.id.example_question3);
        q4 = (TextView) getActivity().findViewById(R.id.example_question4);
        q5 = (TextView) getActivity().findViewById(R.id.example_question5);
        q6 = (TextView) getActivity().findViewById(R.id.example_question6);

        animationSlideInLeft = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.slide_in_left);
        animationSlideOutRight = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.slide_out_right);
        animationSlideInLeft.setDuration(ANIMATION_DURATION);
        animationSlideOutRight.setDuration(ANIMATION_DURATION);
        animationSlideOutRight.setStartOffset(ANIMATION_DELAY);
        animationSlideInLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentQuestion == q1) {
                    q1.startAnimation(animationSlideOutRight);
                } else if (currentQuestion == q2) {
                    q2.startAnimation(animationSlideOutRight);
                } else if (currentQuestion == q3) {
                    q3.startAnimation(animationSlideOutRight);
                } else if (currentQuestion == q4) {
                    q4.startAnimation(animationSlideOutRight);
                } else if (currentQuestion == q5) {
                    q5.startAnimation(animationSlideOutRight);
                } else if (currentQuestion == q6) {
                    q6.startAnimation(animationSlideOutRight);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSlideOutRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentQuestion == q1) {
                    currentQuestion = q2;
                    q2.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.INVISIBLE);
                    q2.setVisibility(View.VISIBLE);
                    q3.setVisibility(View.INVISIBLE);
                    q4.setVisibility(View.INVISIBLE);
                    q5.setVisibility(View.INVISIBLE);
                    q6.setVisibility(View.INVISIBLE);
                } else if (currentQuestion == q2) {
                    currentQuestion = q3;
                    q3.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.INVISIBLE);
                    q2.setVisibility(View.INVISIBLE);
                    q3.setVisibility(View.VISIBLE);
                    q4.setVisibility(View.INVISIBLE);
                    q5.setVisibility(View.INVISIBLE);
                    q6.setVisibility(View.INVISIBLE);
                } else if (currentQuestion == q3) {
                    currentQuestion = q4;
                    q4.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.INVISIBLE);
                    q2.setVisibility(View.INVISIBLE);
                    q3.setVisibility(View.INVISIBLE);
                    q4.setVisibility(View.VISIBLE);
                    q5.setVisibility(View.INVISIBLE);
                    q6.setVisibility(View.INVISIBLE);
                } else if (currentQuestion == q4) {
                    currentQuestion = q5;
                    q5.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.INVISIBLE);
                    q2.setVisibility(View.INVISIBLE);
                    q3.setVisibility(View.INVISIBLE);
                    q4.setVisibility(View.INVISIBLE);
                    q5.setVisibility(View.VISIBLE);
                    q6.setVisibility(View.INVISIBLE);
                } else if (currentQuestion == q5) {
                    currentQuestion = q6;
                    q6.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.INVISIBLE);
                    q2.setVisibility(View.INVISIBLE);
                    q3.setVisibility(View.INVISIBLE);
                    q4.setVisibility(View.INVISIBLE);
                    q5.setVisibility(View.INVISIBLE);
                    q6.setVisibility(View.VISIBLE);
                } else if (currentQuestion == q6) {
                    currentQuestion = q1;
                    q1.startAnimation(animationSlideInLeft);
                    q1.setVisibility(View.VISIBLE);
                    q2.setVisibility(View.INVISIBLE);
                    q3.setVisibility(View.INVISIBLE);
                    q4.setVisibility(View.INVISIBLE);
                    q5.setVisibility(View.INVISIBLE);
                    q6.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        currentQuestion = q1;
        q1.startAnimation(animationSlideInLeft);
        q1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        q1.clearAnimation();
        q2.clearAnimation();
        q3.clearAnimation();
        q4.clearAnimation();
        q5.clearAnimation();
        q6.clearAnimation();
    }

    private void redirectToQuestion() {
        Fragment newFragment = new Question();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
