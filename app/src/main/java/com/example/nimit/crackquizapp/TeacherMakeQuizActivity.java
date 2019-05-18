package com.example.nimit.crackquizapp;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherMakeQuizActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_make_quiz);

        final Button btnAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        final TextInputLayout textInputLayoutQuestion = (TextInputLayout) findViewById(R.id.textInputQuestion);
        final EditText editTextAddQues = (EditText) findViewById(R.id.editTextAddQuestion);
        final EditText editTextOption1 = (EditText) findViewById(R.id.editTextOption1);
        final EditText editTextOption2 = (EditText) findViewById(R.id.editTextOption2);
        final EditText editTextOption3 = (EditText) findViewById(R.id.editTextOption3);
        final EditText editTextCorrect = (EditText) findViewById(R.id.editTextCorrectAns);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ques = editTextAddQues.getText().toString();
                String op1 = editTextOption1.getText().toString();
                String op2 = editTextOption2.getText().toString();
                String op3 = editTextOption3.getText().toString();
                String correctAnswerStr = editTextCorrect.getText().toString();
                //editTextOption1.setText(ques);
                int correctAnswer = 0;

                if(ques.equals("") || op1.equals("") || op2.equals("")|| op3.equals("") || correctAnswerStr.equals("")){
                    Toast.makeText(TeacherMakeQuizActivity.this, "Enter values on all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(op1.equals(op2) || op2.equals(op3)|| op3.equals(op1)){
                    Toast.makeText(TeacherMakeQuizActivity.this, "Please enter different options for the question", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        correctAnswer = Integer.parseInt(correctAnswerStr);
                    }catch(Exception ex){
                        Toast.makeText(TeacherMakeQuizActivity.this, "Please enter the correct option number between 1 and 3", Toast.LENGTH_SHORT).show();
                    }
                    QuestionRecord eachQues = new QuestionRecord();
                    eachQues.setQuestion(ques);
                    eachQues.setOption1(op1);
                    eachQues.setOption2(op2);
                    eachQues.setOption3(op3);
                    eachQues.setCorrectanswer(correctAnswer);

                    helper.addQuestions(eachQues);

                    Toast.makeText(TeacherMakeQuizActivity.this, "Added Question Successfully!", Toast.LENGTH_SHORT).show();

                    editTextAddQues.setText("");
                    editTextOption1.setText("");
                    editTextOption2.setText("");
                    editTextOption3.setText("");
                    editTextCorrect.setText("");
                }
            }
        });
    }

}
