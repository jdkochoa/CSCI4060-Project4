package edu.uga.cs.statecapitalsquiz;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * This class represents an adapter class to assist in the ViewPager2 implementation.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {

    public static final String TAG = "QuizPagerAdapter";

    static int currentQuizQuestion;
    static ArrayList<QuizQuestion> quizQuestions;
    static int quizScore;

    /**
     * Constructor which assists in managing fragments within the quiz in a ViewPager2 setup,
     * allowing users to swipe between quiz questions.
     *
     * @param fragmentManager Used to interact with the fragment associated with this adapter.
     * @param lifeCycle The Lifecycle associated with the adapter to handle the lifecycle
     * of each fragment within the ViewPager2.
     */
    public QuizPagerAdapter( FragmentManager fragmentManager, Lifecycle lifeCycle ) {
        super( fragmentManager, lifeCycle );
        Log.d(TAG, "QuizPagerAdapter.constructor");

        currentQuizQuestion = 0;
        quizQuestions = null;
        quizScore = 0;
    }

    /**
     * Creates a new instance of a quiz question.
     *
     * @param questionNum The current question number.
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment( int questionNum ) {
        Log.d(TAG, "QuizPagerAdapter.createFragment");

        int updateNum = questionNum + 1;
        Log.d("QuizPagerAdapter", "Score before question " + updateNum + ": " + quizScore);

        if (questionNum == 6) {
            Log.d(TAG, "QuizPagerAdapter.quizScore: " + quizScore);
            return QuizResults.newInstance(quizScore);
        }
        return QuizQuestionFragment.newInstance( questionNum );
    }

    /**
     * Retrieves the current number of questions.
     *
     * @return The current number of questions.
     */
    @Override
    public int getItemCount() {
        //Log.d(TAG, "QuizPagerAdapter.getItemCount");
        return QuizQuestionFragment.getNumberOfQuestions();
    }
}
