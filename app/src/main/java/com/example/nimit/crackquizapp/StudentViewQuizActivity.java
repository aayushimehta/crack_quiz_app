package com.example.nimit.crackquizapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StudentViewQuizActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private String email;

    private static final long COUNTDOWN_TIMER = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<QuestionRecord> questionRecordList;
    private QuestionRecord currentQues;
    private int studentScore, quesCurrentCount, totalQuestions;
    private boolean studentAnswerFlag;

    private TextView textViewQuestion, textViewQuestionNo;
    private Button btnSubmit, btnEndQuiz;
    private RadioGroup radioGroupOptions;
    RadioButton radioButtonOpt1,radioButtonOpt2, radioButtonOpt3;
    private TextView textViewCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_quiz);

        //adding session
        final UserRecord userRecord = new UserRecord(this);
        email = userRecord.getEmail();

        btnSubmit = (Button) findViewById(R.id.buttonSubmitAnswer);
        btnEndQuiz = (Button) findViewById(R.id.buttonEndQuiz);
        textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewQuestionNo = (TextView) findViewById(R.id.textViewQuestionNo);
        radioGroupOptions = (RadioGroup) findViewById(R.id.radioGroupOptions);
        radioButtonOpt1 = (RadioButton) findViewById(R.id.radioButtonOption1);
        radioButtonOpt2 = (RadioButton) findViewById(R.id.radioButtonOption2);
        radioButtonOpt3 = (RadioButton) findViewById(R.id.radioButtonOption3);
        textViewCountDown = (TextView) findViewById(R.id.textViewTimer);

        questionRecordList = dbHelper.retrieveQuestions();
        totalQuestions = questionRecordList.size();

        Collections.shuffle(questionRecordList);
        showNextQuestion();//for first time loading questions

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (studentAnswerFlag) {
                        if (radioButtonOpt1.isChecked() || radioButtonOpt2.isChecked() || radioButtonOpt3.isChecked()) {
                            checkAnswer();
                            showNextQuestion();
                        } else {
                            Toast.makeText(StudentViewQuizActivity.this, "Please choose at least one option", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            btnEndQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.addStudentGrades(email,studentScore);
                    finish();
                }
            });


    }
    private void showNextQuestion(){
        radioGroupOptions.clearCheck();

        if(quesCurrentCount < totalQuestions){
            currentQues = questionRecordList.get(quesCurrentCount);
            textViewQuestion.setText(currentQues.getQuestion());
            radioButtonOpt1.setText(currentQues.getOption1());
            radioButtonOpt2.setText(currentQues.getOption2());
            radioButtonOpt3.setText(currentQues.getOption3());

            quesCurrentCount++;
            textViewQuestionNo.setText("Question "+quesCurrentCount+" of "+totalQuestions);
            studentAnswerFlag = true;

            timeLeftInMillis = COUNTDOWN_TIMER;
            startCountdown();
        }
        else{
            dbHelper.addStudentGrades(email,studentScore);
            finish();
        }
    }

    private void checkAnswer(){
        studentAnswerFlag = false;
        countDownTimer.cancel();

        RadioButton radioSelected = findViewById(radioGroupOptions.getCheckedRadioButtonId());
        int selectedAnswer = radioGroupOptions.indexOfChild(radioSelected) + 1;

        if (selectedAnswer == currentQues.getCorrectanswer()) {
            studentScore++;
        }
    }

    private void startCountdown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdown();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountdown();
                showNextQuestion();
            }
        }.start();
    }

    private void updateCountdown(){
        int minutes = (int)(timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;

        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormat);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}
