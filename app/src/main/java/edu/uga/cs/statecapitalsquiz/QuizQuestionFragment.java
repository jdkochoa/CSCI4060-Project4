package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class QuizQuestionFragment extends Fragment {

    TextView questionTv;
    RadioGroup answersRg;
    RadioButton choice1;
    RadioButton choice2;
    RadioButton choice3;

    private String question;

    // needed to create this array for viewPager2 adapter class
    // in other words, this data type can be adjusted and populated as needed
    private static String[] questionList = {
            "question 1",
            "question 2",
            "question 3",
            "question 4",
            "question 5",
            "question 6"};

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            question = getArguments().getString( "question" );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_question_layout, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        questionTv = view.findViewById(R.id.questionTv);
        answersRg = view.findViewById(R.id.answersRg);
        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);

    }

    // NOTE: may not need questionNum; included for now just in case for later state saving

    public static QuizQuestionFragment newInstance( int questionNum ) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        // args.putString( "question", question );
        args.putInt( "questionNum", questionNum );
        fragment.setArguments( args );
        return fragment;
    }

    public static int getNumberOfQuestions() {
        return questionList.length;
    }
}
