package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class replaces the empty fragment container view which the app initially creates with the
 * splash screen to be shown when the app is first opened.
 */
public class MainActivity extends AppCompatActivity {

    DBManager databaseManager;
    QuizDBHelper quizDBHelper;

    public static final String TAG = "MainActivity";

    /**
     * Inflates the main activity and replaces the empty fragment container view with the splash
     * screen.
     *
     * @param savedInstanceState The saved state of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity.onCreate");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
         If the database does not exist, insert the data from the csv file into the database.
         Else, open the database.
        */
        File dbFile = getDatabasePath("Quiz.db");

        databaseManager = new DBManager(getApplicationContext());
        quizDBHelper = QuizDBHelper.getDBInstance(getApplicationContext());

        if (!dbFile.exists()) {

            List<QuizQuestion> questionsList = new ArrayList<>();
            try {
                InputStream inputStream = getAssets().open("state_capitals.csv");
                CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

                String[] row;
                while ((row = csvReader.readNext()) != null) {
                    questionsList.add(new QuizQuestion(row[0], row[1], row[2], row[3]));
                }
                new InsertCSVData().execute(questionsList.toArray(new QuizQuestion[0]));
            } catch (Exception e) {
                Log.e("Error", e.toString() );
            }
        }

        if (savedInstanceState == null) {
            SplashFragment splashFragment = new SplashFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, splashFragment)
                    .commit();
        }
    }

    private class InsertCSVData extends AsyncTask<QuizQuestion, QuizQuestion> {

        @Override
        protected QuizQuestion doInBackground(QuizQuestion... questions) {
            databaseManager = new DBManager(getApplicationContext());
            databaseManager.open();
            for (QuizQuestion question : questions) {
                databaseManager.insertQuizData(question);
            }
            return null;
        }

        @Override
        protected void onPostExecute(QuizQuestion question) {
            databaseManager.closeDB();
        }

    }
}