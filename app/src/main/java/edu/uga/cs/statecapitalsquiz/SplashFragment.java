package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    private Button startB;
    private Button reviewB;
    private Button helpB;

    private TextView titleTv;
    private TextView directionsTv;

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_layout, container, false);
    }

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

    private class StartButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new QuizFragment() )
                    .commit();
        }
    }

    private class ReviewButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ReviewFragment() )
                    .commit();
        }
    }

    private class HelpButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new HelpFragment() )
                    .commit();
        }
    }


}
