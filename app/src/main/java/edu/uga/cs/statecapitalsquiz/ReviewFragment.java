package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * This fragment class is designed to represent a list of past quiz results, specifically including
 * a past quiz's date, time, and score.
 */
public class ReviewFragment extends Fragment {

    private TextView textView;
    private DBManager databaseManager;
    ArrayList<String[]> quizResults;

    /**
     * Inflates the review_layout.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to. The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The inflated splash_layout.
     */

    // Required empty public constructor
    public ReviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.review_layout, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        new RetrievePastQuizzes().execute(1);
        textView = view.findViewById(R.id.past_quiz);
    }

    public void displayQuizResults() {

        StringBuilder stringBuilder = new StringBuilder();
        int count = 1;
        for (String[] result : quizResults) {
        stringBuilder.append(count).append(". Quiz Date: ").append(result[0]).append(" Quiz Result: ").append(result[1]);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(System.lineSeparator());
        count++;
        }

        textView.setText(stringBuilder.toString());
    }

    public class RetrievePastQuizzes extends AsyncTask<Integer, ArrayList<String[]>> {

        @Override
        protected ArrayList<String[]> doInBackground(Integer... num) {
            databaseManager = new DBManager(getActivity());
            databaseManager.open();
            return quizResults = databaseManager.getAllQuizResults();
        }

        @Override
        protected void onPostExecute(ArrayList<String[]> queriedQuizResults) {
            quizResults = queriedQuizResults;
            displayQuizResults();
        }
    }
}
