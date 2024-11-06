package edu.uga.cs.statecapitalsquiz;

import java.io.Serializable;

public class QuizQuestion implements Serializable {

    public boolean isAnsweredCorrectly = false;

    private int id;
    private String state;
    private String stateCapital;
    private String secondCity;
    private String thirdCity;

    public QuizQuestion() {
        id = -1;
        state = null;
        stateCapital = null;
        secondCity = null;
        thirdCity = null;
    }

    public QuizQuestion(String state, String stateCapital, String secondCity, String thirdCity) {
        this.state = state;
        this.stateCapital = stateCapital;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
    }

    public String getState() {
        return state;
    }

    public String getStateCapital() {
        return stateCapital;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public String getThirdCity() {
        return thirdCity;
    }
}
