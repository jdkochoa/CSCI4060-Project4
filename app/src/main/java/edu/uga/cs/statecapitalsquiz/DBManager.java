package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;

    public DBManager(Context context) {
        quizDBHelper = QuizDBHelper.getDBInstance(context);
    }

    // Open the database
    public void open() {
        db = quizDBHelper.getWritableDatabase();
    }

    // Check if DB is open
    public boolean isDBOpen() {
        return db.isOpen();
    }

    // Close the DB
    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }

    public ArrayList<String[]> getAllQuizResults() {

        String sortOrder = QuizContract.QuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                QuizContract.QuizEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<String[]> quizResults = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] quizResult = new String[2];
            String date = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuizEntry.COLUMN_QUIZ_DATE));
            String result = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuizEntry.COLUMN_QUIZ_RESULT));
            quizResult[0] = date;
            quizResult[1] = result;
            quizResults.add(quizResult);
        }
        cursor.close();

        return quizResults;
    }

    public long insertQuizScore(String date, int quizScore) {

        ContentValues values = new ContentValues();
        values.put(QuizContract.QuizEntry.COLUMN_QUIZ_DATE, date);
        values.put(QuizContract.QuizEntry.COLUMN_QUIZ_RESULT, quizScore);

        // insert() returns the primary key of the inserted tuple
        return db.insert(QuizContract.QuizEntry.TABLE_NAME, null, values);
    }
    /*
    Insert a question into the database.
     */
    public void insertQuizData(QuizQuestion question) {

        ContentValues values = new ContentValues();
        values.put(QuizContract.QuestionEntry.COLUMN_STATE, question.getState());
        values.put(QuizContract.QuestionEntry.COLUMN_STATE_CITY, question.getStateCapital());
        values.put(QuizContract.QuestionEntry.COLUMN_SECOND_CITY, question.getSecondCity());
        values.put(QuizContract.QuestionEntry.COLUMN_THIRD_CITY, question.getThirdCity());

        db.insert(QuizContract.QuestionEntry.TABLE_NAME, null, values);
    }

    /*
    Get a quiz question from the database.
     */
    public QuizQuestion getQuizQuestion(int primaryKey) {

        String key = Integer.toString(primaryKey);

        // The columns to project from a selected tuple
        String[] projection = {
                QuizContract.QuestionEntry.COLUMN_STATE,
                QuizContract.QuestionEntry.COLUMN_STATE_CITY,
                QuizContract.QuestionEntry.COLUMN_SECOND_CITY,
                QuizContract.QuestionEntry.COLUMN_THIRD_CITY
        };

        // Select a tuple based on its primary key
        String select = QuizContract.QuestionEntry._ID + " = ?";
        String[] selectArgument = {key};

        Cursor cursor = db.query(
                QuizContract.QuestionEntry.TABLE_NAME,
                projection,
                select,
                selectArgument,
                null,
                null,
                null
        );

        QuizQuestion question = null;
        while (cursor.moveToNext()) {
            String state = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuestionEntry.COLUMN_STATE));
            String stateCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuestionEntry.COLUMN_STATE_CITY));
            String secondCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuestionEntry.COLUMN_SECOND_CITY));
            String thirdCity = cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.QuestionEntry.COLUMN_THIRD_CITY));

            question = new QuizQuestion(state, stateCity, secondCity, thirdCity);
        }
        cursor.close();

        return question;
    }

}
