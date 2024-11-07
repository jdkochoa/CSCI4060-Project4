package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

/**
 * This class represents a splash screen to be displayed at the start of the app. Users will have
 * the option to click three buttons including "Start", "Review Past Quizzes", or "Help".
 */
public class SplashFragment extends Fragment {

    private Button startB;
    private Button reviewB;
    private Button helpB;

    private TextView titleTv;
    private TextView directionsTv;

    // Required empty public constructor
    public SplashFragment() {

    }

    /**
     * Inflates the splash_layout.
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_layout, container, false);
    }

    /**
     * Initializes the TextViews and Buttons, and assigns event handlers to each of the
     * three Buttons, respectively.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);

        startB = getView().findViewById(R.id.startButton);
        reviewB = getView().findViewById(R.id.reviewButton);
        helpB = getView().findViewById(R.id.helpButton);

        titleTv = getView().findViewById(R.id.titleTv);
        directionsTv = getView().findViewById(R.id.directionsTv);

        startB.setOnClickListener( new StartButtonClickListener() );

        reviewB.setOnClickListener( new ReviewButtonClickListener() );

        helpB.setOnClickListener( new HelpButtonClickListener() );

    }

    /**
     * Event handler which begins a quiz when a user clicks the "Start" button.
     */
    private class StartButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new QuizFragment() )
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * Event handler which opens the list of past quizzes when a user clicks the "Review Past
     * Quizzes" button.
     */
    private class ReviewButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ReviewFragment() )
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * Event handler which presents more information to the user when the user clicks the "Help"
     * button.
     */
    private class HelpButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new HelpFragment() )
                    .addToBackStack(null)
                    .commit();
        }
    }


}