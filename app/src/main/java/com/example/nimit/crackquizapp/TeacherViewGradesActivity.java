package com.example.nimit.crackquizapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherViewGradesActivity extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);
    UserRecord userRecord;
    private RecyclerView recyclerView;
    private MyCustomAdapter myAdapter;
    Button btnSaveToFile;

    List<Integer> gradeList = new ArrayList<>();
    List<String> unameList = new ArrayList<>();
    List<UserRecord> userRecordList = new ArrayList<>();

    private static final String FILE_NAME = "quiz_grades.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_grades);

        userRecord = new UserRecord(this);

        btnSaveToFile =(Button)findViewById(R.id.buttonSaveToFIle);

        gradeList = db.getScoresList();
        unameList = db.getGradedStudents();

        for(int i = 0; i<gradeList.size(); i++)
        {
            userRecord = new UserRecord(unameList.get(i), gradeList.get(i));
            userRecordList.add(userRecord);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        myAdapter = new MyCustomAdapter(userRecordList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(myAdapter);

        btnSaveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

    }

    public void save(View v) {
        FileOutputStream fos = null;
        String textToSave = "";

        for(int i = 0; i<gradeList.size(); i++)
        {
            textToSave += userRecordList.get(i).fname + "\t"+ userRecordList.get(i).grade +"\n";
        }

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write("\n".getBytes());
            fos.write(textToSave.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
