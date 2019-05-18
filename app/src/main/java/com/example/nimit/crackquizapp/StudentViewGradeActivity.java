package com.example.nimit.crackquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentViewGradeActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_grade);

        UserRecord userRecord = new UserRecord(this);
        String email = userRecord.getEmail();
        String[] returnedGrades = db.retrieveStudentGradesForStudent(email);

        TextView textViewStudentGrade = (TextView) findViewById(R.id.textViewStudentGrade);
        textViewStudentGrade.setText(returnedGrades[2]+"");


    }
}
