package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Outro extends Fragment {

    static final int FADE_IN_DURATION = 1500; // milliseconds
    static final int FADE_OUT_DURATION = 500; // milliseconds
    static final int ANIMATION_DELAY = 7500; // milliseconds
    static final int PROGRESS_BAR_MAX = 300;
    static final int PROGRESS_BAR_UPDATE_DELAY = 75; //milliseconds

    Animation fadeIn, fadeOut;
    TextView step, instructionText1, instructionText2, instructionText3;
    ImageView instructionImage1, instructionImage2, instructionImage3;
    int currentInstruction;

    private ProgressBar progressBar;
    Handler mHandler;
    int progress;

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
        mHandler.removeCallbacks(SmoothIncrement);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progress = 0;
        step = (TextView) getActivity().findViewById(R.id.outro_step);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress);
        progressBar.setMax(PROGRESS_BAR_MAX);

        instructionText1 = (TextView) getActivity().findViewById(R.id.instruction_text_1);
        instructionText1.setText(Html.fromHtml(getResources().getString(R.string.instruction_text1)));
        instructionText2 = (TextView) getActivity().findViewById(R.id.instruction_text_2);
        instructionText2.setText(Html.fromHtml(getResources().getString(R.string.instruction_text2)));
        instructionText3 = (TextView) getActivity().findViewById(R.id.instruction_text_3);
        instructionText3.setText(Html.fromHtml(getResources().getString(R.string.instruction_text3)));

        instructionImage1 = (ImageView) getActivity().findViewById(R.id.instruction_img_1);
        instructionImage2 = (ImageView) getActivity().findViewById(R.id.instruction_img_2);
        instructionImage3 = (ImageView) getActivity().findViewById(R.id.instruction_img_3);

        fadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.fade_out);
        fadeIn.setDuration(FADE_IN_DURATION);
        fadeOut.setDuration(FADE_OUT_DURATION);
        fadeOut.setStartOffset(ANIMATION_DELAY);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (currentInstruction) {
                    case 1:
                        instructionText1.startAnimation(fadeOut);
                        instructionImage1.startAnimation(fadeOut);
                        break;
                    case 2:
                        instructionText2.startAnimation(fadeOut);
                        instructionImage2.startAnimation(fadeOut);
                        break;
                    case 3:
                        instructionText3.startAnimation(fadeOut);
                        instructionImage3.startAnimation(fadeOut);
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (currentInstruction) {
                    case 1:
                        step.setText(R.string.step_text2);
                        currentInstruction = 2;
                        instructionText2.startAnimation(fadeIn);
                        instructionImage2.startAnimation(fadeIn);
                        instructionText1.setVisibility(View.INVISIBLE);
                        instructionImage1.setVisibility(View.INVISIBLE);
                        instructionText2.setVisibility(View.VISIBLE);
                        instructionImage2.setVisibility(View.VISIBLE);
                        instructionText3.setVisibility(View.INVISIBLE);
                        instructionImage3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        step.setText(R.string.step_text3);
                        currentInstruction = 3;
                        instructionText3.startAnimation(fadeIn);
                        instructionImage3.startAnimation(fadeIn);
                        instructionText1.setVisibility(View.INVISIBLE);
                        instructionImage1.setVisibility(View.INVISIBLE);
                        instructionText2.setVisibility(View.INVISIBLE);
                        instructionImage2.setVisibility(View.INVISIBLE);
                        instructionText3.setVisibility(View.VISIBLE);
                        instructionImage3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        instructionText1.setVisibility(View.INVISIBLE);
                        instructionImage1.setVisibility(View.INVISIBLE);
                        instructionText2.setVisibility(View.INVISIBLE);
                        instructionImage2.setVisibility(View.INVISIBLE);
                        instructionText3.setVisibility(View.INVISIBLE);
                        instructionImage3.setVisibility(View.INVISIBLE);
                        ((Main) getActivity()).printQuestion();
                        redirectToIntro();
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        currentInstruction = 1;
        instructionText1.startAnimation(fadeIn);
        instructionImage1.startAnimation(fadeIn);
        instructionText1.setVisibility(View.VISIBLE);
        instructionImage1.setVisibility(View.VISIBLE);

        mHandler.postDelayed(SmoothIncrement, PROGRESS_BAR_UPDATE_DELAY);
    }

    Runnable SmoothIncrement = new Runnable() {
        @Override
        public void run() {
            if (progressBar != null) {
                progressBar.setProgress(progress++);
            }
            mHandler.postDelayed(this, PROGRESS_BAR_UPDATE_DELAY);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        instructionText1.clearAnimation();
        instructionImage1.clearAnimation();
        instructionText2.clearAnimation();
        instructionImage2.clearAnimation();
        instructionText3.clearAnimation();
        instructionImage3.clearAnimation();
    }

    private void redirectToIntro() {
        Fragment newFragment = new Intro();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}