package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {


    public QuizPagerAdapter( FragmentManager fragmentManager, Lifecycle lifeCycle ) {
        super( fragmentManager, lifeCycle );
    }

    @Override
    public Fragment createFragment( int questionNum ) {
        return QuizQuestionFragment.newInstance( questionNum );
    }

    @Override
    public int getItemCount() {
        return QuizQuestionFragment.getNumberOfQuestions();
    }
}