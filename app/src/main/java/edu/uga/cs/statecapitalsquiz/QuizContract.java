package edu.uga.cs.statecapitalsquiz;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {};

    /*
    This class defines the schema for the questions table
     */
    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_STATE_CITY = "stateCity";
        public static final String COLUMN_SECOND_CITY = "secondCity";
        public static final String COLUMN_THIRD_CITY = "thirdCity";
    }

    /*
    This class defines the schema for the quizzes table.
    */
    public static class QuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "quizzes";
        public static final String COLUMN_QUIZ_DATE = "quizDate";
        public static final String COLUMN_QUIZ_RESULT = "quizResult";
    }

    /*
    This class defines the schema of the Relationship table. It connects the
    questions table to the quizzes table.
     */
    public static class QuestionQuizEntry {
        public static final String TABLE_NAME = "questionQuiz";
        public static final String COLUMN_QUESTION_ID = "questionID";
        public static final String COLUMN_QUIZ_ID = "quizID";
    }

}
