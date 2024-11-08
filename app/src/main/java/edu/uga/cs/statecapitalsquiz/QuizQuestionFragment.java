package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizQuestionFragment extends Fragment {

    public static final String TAG = "QuizQuestionFragment";
    private TextView quizQuestion;
    private RadioGroup choicesRG;
    private RadioButton choiceOne;
    private RadioButton choiceTwo;
    private RadioButton choiceThree;

    int currentScore;

    DBManager databaseManager;
    // This contains the current quiz question number
    private int questionNumber;
    // This list will contain all 6 quiz questions
    private ArrayList<QuizQuestion> quizQuestions;
    // This array represents the pages in the quiz
    private static final String[] pages = {
            "questionOne",
            "questionTwo",
            "questionThree",
            "questionFour",
            "questionFive",
            "questionSix",
            "resultsPage"
    };

    // Required empty public constructor
    public QuizQuestionFragment() {}

    public static QuizQuestionFragment newInstance(int questionNumber) {
        Log.d(TAG, "QuizQuestionFragment.newInstance");

        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNumber", questionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Overrides onCreate() method to retrieve the saved question key.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "QuizQuestionFragment.onCreate");

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNumber = getArguments().getInt("questionNumber");
        }
        if (savedInstanceState != null) {
            currentScore = savedInstanceState.getInt("currentScore"); // Retrieve the saved score
            Log.d(TAG, "CURRENT SCORE: " + currentScore);

            quizQuestions = (ArrayList<QuizQuestion>) savedInstanceState.getSerializable("recreateQuiz");
        }
        if (quizQuestions == null && QuizPagerAdapter.quizQuestions != null) {
            quizQuestions = QuizPagerAdapter.quizQuestions;
        }
        if (quizQuestions == null) {

        }
    }

    /**
     * Inflates the quiz question.
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "QuizQuestionFragment.onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_question_layout, container, false);
    }

    /**
     * Initializes widgets and views with their respective ids.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "QuizQuestionFragment.onViewCreated");

        if (savedInstanceState != null) {
            // Only reset quizScore if we're restoring from a previous state
            QuizPagerAdapter.quizScore = savedInstanceState.getInt("currentScore", -100);
        }

        quizQuestion = view.findViewById(R.id.questionTv);
        choicesRG = view.findViewById(R.id.answersRg);
        choiceOne = view.findViewById(R.id.choice1);
        choiceTwo = view.findViewById(R.id.choice2);
        choiceThree = view.findViewById(R.id.choice3);

        // Only create a a new set of quiz question when taking a new quiz
        if (quizQuestions == null) {
            Log.d(TAG, "QuizQuestionFragment.onViewCreated: creating new questions.");
            Integer[] primaryKeys = new Integer[6];
            Set<Integer> set = new HashSet<>();

            // Generate random primary keys
            Random random = new Random();
            for (int i = 0; i < primaryKeys.length; i++) {
                int randomPrimaryKey = random.nextInt(50) + 1;
                while (set.contains(randomPrimaryKey)) {
                    randomPrimaryKey = random.nextInt(50) + 1;
                }
                set.add(randomPrimaryKey);
                primaryKeys[i] = randomPrimaryKey;
            }

            // Query the database for the quiz questions
            new QueryDatabase().execute(primaryKeys);
        } else {
            Log.d(TAG, "QuizQuestionFragment.onViewCreated: questions exist, no need to create");
            updateUI();
        }

    }

    public void updateUI() {
        Log.d(TAG, "QuizQuestionFragment.updateUI");

        QuizQuestion currentQuestion = quizQuestions.get(questionNumber);

        List<String> answersList = new ArrayList<>();
        answersList.add(currentQuestion.getStateCapital());
        answersList.add(currentQuestion.getSecondCity());
        answersList.add(currentQuestion.getThirdCity());

        Collections.shuffle(answersList);


        quizQuestion.setText(questionNumber + 1 + ". What is the capital of " + currentQuestion.getState() + "?");
        choiceOne.setText(answersList.get(0));
        choiceTwo.setText(answersList.get(1));
        choiceThree.setText(answersList.get(2));

        choicesRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                String selectedAnswer = checkedRadioButton.getText().toString();

                if (selectedAnswer.equals(currentQuestion.getStateCapital())) {
                    if (!currentQuestion.isAnsweredCorrectly) {
                        QuizPagerAdapter.quizScore++;
                        currentQuestion.isAnsweredCorrectly = true; // Mark the question as correctly answered
                    }
                } else {
                    if (currentQuestion.isAnsweredCorrectly) {
                        QuizPagerAdapter.quizScore--;
                        currentQuestion.isAnsweredCorrectly = false; // Mark the question as incorrectly answered
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "QuizQuestionFragment.onPause");
        QuizPagerAdapter.currentQuizQuestion = questionNumber;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "QuizQuestionFragment.onResume");
        questionNumber = QuizPagerAdapter.currentQuizQuestion;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "QuizQuestionFragment.onSaveInstanceState");

        QuizPagerAdapter.quizQuestions = quizQuestions;
        outState.putInt("questionNumber", questionNumber);
        currentScore = QuizPagerAdapter.quizScore;
        outState.putInt("currentScore", currentScore);

        outState.putSerializable("recreateQuiz", quizQuestions);
    }

    public static int getNumberOfQuestions() {
        //Log.d(TAG, "QuizQuestionFragment.getNumberOfQuestions");
        return pages.length;
    }

    private class QueryDatabase extends AsyncTask<Integer, ArrayList<QuizQuestion>> {

        @Override
        protected ArrayList<QuizQuestion> doInBackground(Integer... primaryKeys) {
            Log.d(TAG, "QuizQuestionFragment.doInBackground");
            ArrayList<QuizQuestion> questions = new ArrayList<>();
            databaseManager = new DBManager(getActivity());
            databaseManager.open();
            for (Integer key : primaryKeys) {
                QuizQuestion question = databaseManager.getQuizQuestion(key);
                questions.add(question);
            }
            return questions;
        }

        @Override
        protected void onPostExecute(ArrayList<QuizQuestion> queriedQuestions) {
            Log.d(TAG, "QuizQuestionFragment.onPostExecute");
            quizQuestions = queriedQuestions;
            QuizPagerAdapter.quizQuestions = quizQuestions;
            updateUI();
            //databaseManager.closeDB();
        }
    }
}