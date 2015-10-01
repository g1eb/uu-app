package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Question extends Fragment implements NumberPicker.OnValueChangeListener {

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

        // Bring up the soft keyboard
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnPrint = (Button) getActivity().findViewById(R.id.btn_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAdverb = adverbs[adverbSelector.getValue()];
                String input = questionInput.getText().toString();
                System.out.println(input);
                if ( selectedAdverb != null && input != null && !input.equals("")) {
                    // Hide soft keyboard
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(questionInput.getWindowToken(), 0);

                    // Print the question
                    String question = selectedAdverb + " " + input + "?";
                    ((Main) getActivity()).printQuestion(question);

                    redirectToOutro();
                }
            }
        });
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
        selectedAdverb = adverbs[newVal];
    }
}