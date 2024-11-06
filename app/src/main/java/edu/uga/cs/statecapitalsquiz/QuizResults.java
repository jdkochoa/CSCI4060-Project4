package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResults extends Fragment {

    TextView textView;
    int quizScore;
    Date date;


    public QuizResults() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuizResults.
     */
    public static QuizResults newInstance(int quizScore) {
        QuizResults fragment = new QuizResults();
        Bundle args = new Bundle();
        args.putInt( "quizScore", quizScore );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizScore = getArguments().getInt("quizScore");
            date = new Date();
        }

        String[] quizData = {date.toString(), Integer.toString(quizScore)};
        new InsertQuizScore().execute(quizData);
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

    private class InsertQuizScore extends AsyncTask<String, Long> {

        DBManager databaseManager;

        @Override
        protected Long doInBackground(String... quizResults) {
            databaseManager = new DBManager(getActivity());
            databaseManager.open();
            return databaseManager.insertQuizScore(quizResults[0], Integer.parseInt(quizResults[1]));
        }

        @Override
        protected void onPostExecute(Long primaryKey) {

        }
    }
}