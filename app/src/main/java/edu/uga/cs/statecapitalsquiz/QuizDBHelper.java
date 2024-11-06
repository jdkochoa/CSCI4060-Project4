package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Quiz.db";
    private static QuizDBHelper dbInstance;

    // SQL query to create the question table
    private static final String CREATE_QUESTION_TABLE =
            "create table " + QuizContract.QuestionEntry.TABLE_NAME + "("
                    + QuizContract.QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + QuizContract.QuestionEntry.COLUMN_STATE + " TEXT,"
                    + QuizContract.QuestionEntry.COLUMN_STATE_CITY + " TEXT,"
                    + QuizContract.QuestionEntry.COLUMN_SECOND_CITY + " TEXT,"
                    + QuizContract.QuestionEntry.COLUMN_THIRD_CITY + " TEXT"
                    + ")"
            ;

    // SQL query to create the quiz table
    private static final String CREATE_QUIZ_TABLE =
            "create table " + QuizContract.QuizEntry.TABLE_NAME + "("
                    + QuizContract.QuizEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + QuizContract.QuizEntry.COLUMN_QUIZ_DATE + " TEXT,"
                    + QuizContract.QuizEntry.COLUMN_QUIZ_RESULT + "INTEGER"
                    + ")"
            ;

    // SQL query to create the relationship table. It creates a foreign key constraint between
    // the question and quiz table.
    public static final String CREATE_QUESTION_QUIZ_TABLE =
            "create table " + QuizContract.QuestionQuizEntry.TABLE_NAME + "("
                    + QuizContract.QuestionQuizEntry.COLUMN_QUESTION_ID + " INTEGER,"
                    + QuizContract.QuestionQuizEntry.COLUMN_QUIZ_ID + " INTEGER,"
                    + "FOREIGN KEY(" + QuizContract.QuestionQuizEntry.COLUMN_QUESTION_ID + ") REFERENCES "
                    + QuizContract.QuizEntry.TABLE_NAME +"(" + QuizContract.QuizEntry._ID + "),"
                    + "FOREIGN KEY(" + QuizContract.QuestionQuizEntry.COLUMN_QUIZ_ID + ") REFERENCES "
                    + QuizContract.QuestionEntry.TABLE_NAME +"(" + QuizContract.QuestionEntry._ID + ")"
                    + ")"
            ;

    private QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDBHelper getDBInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new QuizDBHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
        db.execSQL(CREATE_QUESTION_QUIZ_TABLE);

        Log.d(DEBUG_TAG, "Tables " + "created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + QuizContract.QuestionEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + QuizContract.QuestionQuizEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + QuizContract.QuizEntry.TABLE_NAME);
        onCreate(db);
    }
}
