package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizQuestionFragment extends Fragment {

    private TextView quizQuestion;
    private RadioGroup choicesRG;
    private RadioButton choiceOne;
    private RadioButton choiceTwo;
    private RadioButton choiceThree;

    DBManager databaseManager;
    // This values contains the current quiz question number
    private int questionNumber;
    // This list will contain all 6 quiz questions
    private ArrayList<QuizQuestion> quizQuestions;
    private static final int numberOfQuestions = 6;

    public static QuizQuestionFragment newInstance(int questionNumber) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNumber", questionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNumber = getArguments().getInt("questionNumber");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_question_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        new QueryDatabase().execute(primaryKeys);

        quizQuestion = view.findViewById(R.id.questionTv);
        choicesRG = view.findViewById(R.id.answersRg);
        choiceOne = view.findViewById(R.id.choice1);
        choiceTwo = view.findViewById(R.id.choice2);
        choiceThree = view.findViewById(R.id.choice3);
    }

    public void updateUI() {
        QuizQuestion currentQuestion = quizQuestions.get(questionNumber);

        List<String> answersList = new ArrayList<>();
        answersList.add(currentQuestion.getStateCapital());
        answersList.add(currentQuestion.getSecondCity());
        answersList.add(currentQuestion.getThirdCity());

        Collections.shuffle(answersList);

        quizQuestion.setText("What is the city of " + currentQuestion.getState() + "?");
        choiceOne.setText(answersList.get(0));
        choiceTwo.setText(answersList.get(1));
        choiceThree.setText(answersList.get(2));
    }

    public static int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    private class QueryDatabase extends AsyncTask<Integer, ArrayList<QuizQuestion>> {

        @Override
        protected ArrayList<QuizQuestion> doInBackground(Integer... primaryKeys) {
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
            quizQuestions = queriedQuestions;
            updateUI();
            databaseManager.closeDB();
        }
    }
}