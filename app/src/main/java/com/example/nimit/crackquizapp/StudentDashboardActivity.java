package com.example.nimit.crackquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    String listStudentMenu[] = {"View Quiz","View Grades"};
    private List<QuestionRecord> questionRecordList;
    Boolean takenQuiz = false;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);
        final UserRecord userRecord = new UserRecord(this);

        final DatabaseHelper db = new DatabaseHelper(this);
        final String email = userRecord.getEmail();

        questionRecordList = db.retrieveQuestions();
        final int totalQuestions = questionRecordList.size();

        Button btnLogout = (Button) findViewById(R.id.buttonStudentLogout);

        TextView textViewWelcome = (TextView) findViewById(R.id.textViewWelcome) ;
        textViewWelcome.setText("Welcome "+userRecord.getFName());

        ListView listViewStudentMenu = (ListView)findViewById(R.id.listViewStudent);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listStudentMenu);
        listViewStudentMenu.setAdapter(myAdapter);

        listViewStudentMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> gradedStudentList = db.getGradedStudents();
                if(gradedStudentList.size() > 0) {
                    for (int i = 0; i < gradedStudentList.size(); i++) {
                        String dbEmail = gradedStudentList.get(i);
                        if (dbEmail.equals(email)) {
                            takenQuiz = true;
                        }
                    }
                }
                switch(position){
                    case 0:
                        if(takenQuiz){
                            Toast.makeText(StudentDashboardActivity.this, "You have completed your Quiz!", Toast.LENGTH_SHORT).show();
                        }
                        else if(totalQuestions == 0){
                            Toast.makeText(StudentDashboardActivity.this, "You don't have any quiz right now!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(StudentDashboardActivity.this, StudentViewQuizActivity.class));
                        }
                        break;

                    case 1:
                        if(!takenQuiz){
                            Toast.makeText(StudentDashboardActivity.this, "You haven't taken the Quiz yet!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(StudentDashboardActivity.this, StudentViewGradeActivity.class));
                        }
                        break;
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRecord.removeSession();
                startActivity(new Intent(StudentDashboardActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
