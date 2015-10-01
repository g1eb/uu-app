package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Question extends Fragment implements NumberPicker.OnValueChangeListener {

    NumberPicker adverbSelector;
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
        adverbSelector = (NumberPicker) getActivity().findViewById(R.id.adverb_selector);
        adverbSelector.setMinValue(0);
        adverbSelector.setMaxValue(adverbs.length - 1);
        adverbSelector.setFocusable(true);
        adverbSelector.setFocusableInTouchMode(true);
        adverbSelector.setDisplayedValues(adverbs);
        adverbSelector.setOnValueChangedListener(this);

        questionInput = (EditText) getActivity().findViewById(R.id.question);
        questionInput.setSelected(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.adverbs, R.layout.question_item);
        adverbSelector.setAdapter(adapter);
        adverbSelector.setOnItemSelectedListener(this);

        btnPrint = (Button) getActivity().findViewById(R.id.btn_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !questionInput.getText().equals("") ) {
                    String question = selectedAdverb + " " + questionInput.getText() + "?";
                    ((Main)getActivity()).printLabel(question);
                }
                redirectToOutro();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedAdverb = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
