package com.nknahom.logoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Variables used for question one
    private boolean questionOneChecked;
    private boolean isQuestionOneCorrect;
    private View view_questionOne;

    // Variable used for question two
    private boolean isQuestionTwoCorrect;

    // Variables used for question three
    private boolean questionThreeChecked;
    private boolean isQuestionThreeCorrect;
    private View view_questionThree;
    // Variable qThree_answerOne & qThree_answerThree should be selected in order Question three to be correct answer
    private boolean qThree_answerOne;
    private boolean qThree_answerTwo;
    private boolean qThree_answerThree;

    // Variable used for question four
    private boolean questionFourChecked;
    private boolean isQuestionFourCorrect;
    private View view_questionFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method called when question one selected in the activity
    public void onQuestionOneClicked(View view) {
        // Is the button now checked?
        questionOneChecked = ((RadioButton) view).isChecked();

        view_questionOne = view;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.mozilla_firefox:
                if (questionOneChecked)
                    // The answer is Mozilla Firefox
                    isQuestionOneCorrect = true;
                break;
            case R.id.google_chrome:
            case R.id.internet_explorer:
                if (questionOneChecked)
                    // The answer is not correct
                    isQuestionOneCorrect = false;
                break;
        }

    }

    // Method called when question three selected in the activity
    public void onQuestionThreeClicked(View view) {
        // Is the view now checked?
        questionThreeChecked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.fiml_company:
                qThree_answerOne = questionThreeChecked;
                break;
            case R.id.telecom_company:
                qThree_answerTwo = questionThreeChecked;
                break;
            case R.id.warner_brothers:
                qThree_answerThree = questionThreeChecked;
                break;
        }
        view_questionThree = view;
    }


    // Method called when question four selected in the activity
    public void onQuestionFourClicked(View view) {
        // Is the button now checked?
        questionFourChecked = ((RadioButton) view).isChecked();

        view_questionFour = view;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.google:
                if (questionFourChecked)
                    // The answer is Google
                    isQuestionFourCorrect = true;
                break;
            case R.id.yahoo:
            case R.id.Askcom:
                if (questionFourChecked)
                    // The answer is not correct
                    isQuestionFourCorrect = false;
                break;
        }

    }


    // This method called me the answer button clicked
    public void questionSubmitted(View view) {

        TextView questionTwo = findViewById(R.id.answer_questionTwo);
        String answerTwo = questionTwo.getText().toString().toLowerCase();
        boolean questionTwoChecked = !answerTwo.isEmpty();

        if (questionOneChecked && questionTwoChecked && questionThreeChecked && questionFourChecked){

            if (answerTwo.trim().equals("facebook")){
                isQuestionTwoCorrect = true;
                colorAnswer(questionTwo, true);
            } else {
                isQuestionTwoCorrect = false;
                colorAnswer(questionTwo, false);
            }

            checkQuestionThree();
            toastAnswer(totalCorrectAnswered());
            colorAnswer(view_questionOne, isQuestionOneCorrect);
            colorAnswer(view_questionThree, isQuestionThreeCorrect);
            colorAnswer(view_questionFour, isQuestionFourCorrect);

            LinearLayout relativeLayout = findViewById(R.id.childLayout);
            disableAllSettings(relativeLayout);
        } else {
            String message = "Please answer all questions";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    // This method calculate total number of questions answered
    private int totalCorrectAnswered(){
        int answer = 0;
        if (isQuestionOneCorrect){
            answer += 1;
        }
        if (isQuestionTwoCorrect)
            answer += 1;
        if (isQuestionThreeCorrect)
            answer += 1;
        if (isQuestionFourCorrect)
            answer += 1;
        return answer;
    }

    // This toast display the total number of answered questions
    public void toastAnswer(int answer){
        String message = "You have answered " + answer + " out of 4 question";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // This method validate for question three as correct answers are "A film industry" & "Warner Brothers"
    private void checkQuestionThree(){
        isQuestionThreeCorrect = (qThree_answerOne && qThree_answerThree) && !(qThree_answerTwo);
    }

    // This method highlights the answers as Green for correct and Red for wrong answer
    private void colorAnswer(View view, boolean isCorrect) {

        // Check if you answer correctly, it will highlight by green else by red
        if(isCorrect){
            view.setBackgroundColor(0xAA00FF00);
        } else {
            view.setBackgroundColor(0xAAFF0000);
        }

    }

    // This method disable all the nested views in order to protect from entering new answer
    public void disableAllSettings(ViewGroup mGroup) {

        int itotal = mGroup.getChildCount();
        for (int i = 0; i < itotal; i++) {
            if (mGroup.getChildAt(i) instanceof ViewGroup) {
                disableAllSettings((ViewGroup) mGroup.getChildAt(i));
            } else {
                mGroup.getChildAt(i).setEnabled(false);
            }
        }
    }

    // This method called when Replay button called and restart the app
    public void reset(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
