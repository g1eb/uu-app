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
import android.widget.ImageView;
import android.widget.TextView;

public class Outro extends Fragment {

    static final int INSTRUCTION_INTERVAL = 5000; // milliseconds
    Handler mHandler;
    ImageView instructionImage;
    TextView instruction;
    String[] instructions;
    private int[] instructionImages = new int[]{
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3
    };

    int count;

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
        count = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outro, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(changeInstructions);
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

        instructions = getResources().getStringArray(R.array.instructions);
        instruction = (TextView) getActivity().findViewById(R.id.instruction_text);
        instruction.setText(Html.fromHtml(instructions[count]));

        instructionImage = (ImageView) getActivity().findViewById(R.id.instruction_image);
        instructionImage.setImageResource(instructionImages[count]);

        mHandler.postDelayed(changeInstructions, INSTRUCTION_INTERVAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    Runnable changeInstructions = new Runnable() {
        @Override
        public void run() {
            if (count == 2) {
                redirectToIntro();
            } else {
                count++;
                instruction.setText(Html.fromHtml(instructions[count]));
                instructionImage.setImageResource(instructionImages[count]);
                mHandler.postDelayed(this, INSTRUCTION_INTERVAL);
            }
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
