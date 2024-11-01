package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

/**
 * This class is the initiation point for the start of a quiz.
 *
 * When a user selects the "Start" button on the splash screen, the MainActivity will transition to
 * this QuizFragment which holds a ViewPager2. The ViewPager2 will then hold an instance of a
 * QuizQuestionFragment and replace with a new QuizQuestionFragment instance as the user swipes
 * through each question.
 */
public class QuizFragment extends Fragment {

    private ViewPager2 viewPager;
    private QuizPagerAdapter quizPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.quiz_pager, container, false );
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        viewPager = view.findViewById( R.id.quizPager);
        quizPagerAdapter = new QuizPagerAdapter( getChildFragmentManager(), getLifecycle() );
        viewPager.setAdapter( quizPagerAdapter );
    }
}
