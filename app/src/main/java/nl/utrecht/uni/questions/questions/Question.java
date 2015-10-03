package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Question extends Fragment implements NumberPicker.OnValueChangeListener, TextWatcher, TextView.OnEditorActionListener {

    static final int DELAY_IDLE = 60000; // milliseconds
    Handler mHandler;

    AdverbPicker adverbSelector;
    EditText questionInput;
    Button btnPrint;
    String[] adverbs;
    String selectedAdverb;

    public static Question newInstance() {
        Question fragment = new Question();

        return fragment;
    }

    public Question() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(delayedRedirect);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adverbs = getResources().getStringArray(R.array.adverbs);
        adverbSelector = (AdverbPicker) getActivity().findViewById(R.id.adverb_selector);
        adverbSelector.setMinValue(0);
        adverbSelector.setMaxValue(adverbs.length - 1);
        adverbSelector.setFocusable(true);
        adverbSelector.setFocusableInTouchMode(true);
        adverbSelector.setDisplayedValues(adverbs);
        adverbSelector.setOnValueChangedListener(this);

        questionInput = (EditText) getActivity().findViewById(R.id.question);
        questionInput.setFocusableInTouchMode(true);
        questionInput.setFocusable(true);
        questionInput.requestFocus();
        questionInput.addTextChangedListener(this);
        questionInput.setOnEditorActionListener(this);

        // Bring up the soft keyboard
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnPrint = (Button) getActivity().findViewById(R.id.btn_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQuestion();
            }
        });

        // Redirect to intro after a delay
        mHandler.postDelayed(delayedRedirect, DELAY_IDLE);
    }

    private void sendQuestion() {
        selectedAdverb = adverbs[adverbSelector.getValue()];
        String input = questionInput.getText().toString();
        System.out.println(input);
        if (selectedAdverb != null && input != null && !input.equals("")) {
            hideKeyboard();

            // Print the question
            String question = selectedAdverb + " " + input + "?";
            ((Main) getActivity()).printQuestion(question);

            redirectToOutro();
        }
    }

    Runnable delayedRedirect = new Runnable() {
        @Override
        public void run() {
            hideKeyboard();
            redirectToIntro();
            mHandler.postDelayed(this, DELAY_IDLE);
        }
    };

    private void hideKeyboard() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(questionInput.getWindowToken(), 0);
    };

    private void redirectToIntro() {
        Fragment newFragment = new Intro();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void redirectToOutro() {
        Fragment newFragment = new Outro();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        mHandler.removeCallbacks(delayedRedirect);
        mHandler.postDelayed(delayedRedirect, DELAY_IDLE);
        selectedAdverb = adverbs[newVal];
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mHandler.removeCallbacks(delayedRedirect);
        mHandler.postDelayed(delayedRedirect, DELAY_IDLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        mHandler.removeCallbacks(delayedRedirect);
        mHandler.postDelayed(delayedRedirect, DELAY_IDLE);
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            sendQuestion();
        }
        return false;
    }
}
