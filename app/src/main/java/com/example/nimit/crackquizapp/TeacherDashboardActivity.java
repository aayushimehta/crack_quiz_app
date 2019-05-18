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

import java.util.ArrayList;
import java.util.List;

public class TeacherDashboardActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private List<QuestionRecord> questionRecordList;
    String listTeacherMenu[] = {"Make a new Quiz","View Student Grades","View Statistics","Delete Current Quiz"};
    List<Integer> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dash);

        dbHelper = new DatabaseHelper(this);
        final UserRecord userRecord = new UserRecord(this);

        questionRecordList = dbHelper.retrieveQuestions();


        TextView textViewWelcomeTeacher = (TextView) findViewById(R.id.textViewWelcomeTeacher);
        textViewWelcomeTeacher.setText("Welcome "+userRecord.getFName());

        Button btnLogout = (Button) findViewById(R.id.buttonTeacherLogout);

        ListView listViewTeacherMenu = (ListView)findViewById(R.id.listViewTeacher);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listTeacherMenu);
        listViewTeacherMenu.setAdapter(myAdapter);

        gradeList = dbHelper.getScoresList();

        listViewTeacherMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        startActivity(new Intent(TeacherDashboardActivity.this, TeacherMakeQuizActivity.class));
                        break;
                    case 1:
                        gradeList = dbHelper.getScoresList();
                        if(gradeList.size()==0){
                            Toast.makeText(TeacherDashboardActivity.this, "Nobody has attempted the quiz yet!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(TeacherDashboardActivity.this, TeacherViewGradesActivity.class));
                        }

                        break;
                    case 2:
                        gradeList = dbHelper.getScoresList();
                         if(gradeList.size()==0){
                            Toast.makeText(TeacherDashboardActivity.this, "Nobody has attempted the quiz yet!", Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(TeacherDashboardActivity.this, TeacherViewStatisticsActivity.class));
                        }

                        break;
                    case 3:
                        int totalQuestions = questionRecordList.size();
                        if(totalQuestions == 0){
                            Toast.makeText(TeacherDashboardActivity.this, "You don't have any quiz right now!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            dbHelper.deleteCurrentQuiz();
                            Toast.makeText(TeacherDashboardActivity.this, "The current quiz is deleted!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRecord.removeSession();
                startActivity(new Intent(TeacherDashboardActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
