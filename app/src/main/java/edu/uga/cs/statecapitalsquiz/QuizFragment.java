package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    /**
     * Inflates the ViewPager2 container where quiz question fragments are held.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to. The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */

    // Required empty public constructor
    public QuizFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.quiz_pager, container, false );
    }

    /**
     * After inflating the container, create a new quiz pager adapter which will manage the
     * ViewPager2 and assist in swiping capabilities.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        ViewPager2 viewPager = view.findViewById(R.id.quizPager);
        QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(quizPagerAdapter);
    }
}
