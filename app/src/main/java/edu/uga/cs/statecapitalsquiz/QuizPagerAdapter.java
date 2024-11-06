package edu.uga.cs.statecapitalsquiz;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * This class represents an adapter class to assist in the ViewPager2 implementation.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {

    public static int quizScore = 0;

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
    }

    /**
     * Creates a new instance of a quiz question.
     *
     * @param questionNum The current question number.
     * @return
     */
    @Override
    public Fragment createFragment( int questionNum ) {
        Log.d("QuizPagerAdapter", "Score after question " + questionNum + ": " + quizScore);

        if (questionNum == 0) {
            quizScore = 0;
        }

        if (questionNum == 6) {
            return QuizResults.newInstance( quizScore );
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
        return QuizQuestionFragment.getNumberOfQuestions();
    }
}
