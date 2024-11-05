package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * This class represents an adapter class to assist in the ViewPager2 implementation.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {


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
