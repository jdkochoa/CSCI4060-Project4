package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResults extends Fragment {

    TextView textView;
    int quizScore;

    public QuizResults() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuizResults.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizResults newInstance( int num ) {
        QuizResults fragment = new QuizResults();
        Bundle args = new Bundle();
        args.putInt( "quizScore", num );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizScore = getArguments().getInt("quizScore");
            // mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_results, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.tv);
        textView.setText( "You scored " + QuizPagerAdapter.quizScore + " out of 6 questions correct.");

    }
}