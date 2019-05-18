package com.example.nimit.crackquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final int DATABASE_VERSION =1;
    private  static final String db_name = "quiz.db";

    //creating table for users
    private  static  final  String table_users = "users";
    private  static  final  String column_id = "id";
    private  static  final  String column_fname = "fname";
    private  static  final  String column_lname = "lname";
    private  static  final  String column_email = "email";
    private  static  final  String column_pswd = "pass";
    private  static  final  String column_acc_type = "accType";

    //creating table for questions
    private  static  final  String table_questions = "questions";

    //creating table for student grades
    private  static  final  String table_student_grades = "grades";


    SQLiteDatabase db;

    public final  String tableCreate = "CREATE TABLE "+ table_users +"(fname text not null," +
            "lname text not null, email text primary key, pass text not null, accType text not null);";
    public final String createQuestionTable = "CREATE TABLE "+ table_questions +"(_id integer primary key AUTOINCREMENT,question text not null," +
            "option1 text not null,option2 text not null,option3 text not null,correctanswer int not null);";
    public final String createGradeTable = "CREATE TABLE "+ table_student_grades +"(_id integer primary key AUTOINCREMENT," +
            "email text not null,grade int not null);";

    public  DatabaseHelper(Context context)
    {
        super(context, db_name, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;
         db.execSQL(tableCreate);
         db.execSQL(createQuestionTable);
         db.execSQL(createGradeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
      String query_users = "DROP TABLE IF EXISTS " + table_users;
      db.execSQL(query_users);

      String query_questions = "DROP TABLE IF EXISTS " + table_questions;
      db.execSQL(query_questions);

      String query_grades = "DROP TABLE IF EXISTS " + table_student_grades;
      db.execSQL(query_grades);

      this.onCreate(db);
    }

    public void insertRecord(UserRecord C)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_fname, C.getFName());
        values.put(column_lname,C.getLName());
        values.put(column_email,C.getEmail());
        values.put(column_pswd, C.getPswd());
        values.put(column_acc_type, C.getAccountType());
        db.insert(table_users,null,values);
    }

     public String[] searchPassword(String username) {
          db = this.getWritableDatabase();
          String query = "SELECT email , pass, accType , fname FROM " + table_users;
          Cursor cuser = db.rawQuery(query, null);
          String UName, Pass="", AccountType="", firstName="";
          UName = "";

          if (cuser.moveToFirst())
          {
              do {
                  UName = cuser.getString(0);
                  if (UName.equals(username)) {
                      Pass = cuser.getString(1);
                      AccountType=cuser.getString(2);
                      firstName = cuser.getString(3);
                      break;
                  }
              } while (cuser.moveToNext());
          }

          return new String[]{Pass, AccountType, firstName};
    }

    //AADING A NEW QUESTION
    public void addQuestions(QuestionRecord q){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("question",q.getQuestion());
        cv.put("option1",q.getOption1());
        cv.put("option2",q.getOption2());
        cv.put("option3",q.getOption3());
        cv.put("correctanswer",q.getCorrectanswer());

        db.insert(table_questions,null,cv);
    }

    //RETRIEVING ALL QUESTIONS
    public List<QuestionRecord> retrieveQuestions(){
        List<QuestionRecord> QuestionsList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table_questions , null);

        if(c.moveToFirst()){
            do{
                QuestionRecord q = new QuestionRecord();
                q.setQuestion(c.getString(1));
                q.setOption1(c.getString(2));
                q.setOption2(c.getString(3));
                q.setOption3(c.getString(4));
                q.setCorrectanswer(c.getInt(5));
                QuestionsList.add(q);
            }
            while(c.moveToNext());
        }
        c.close();
        return QuestionsList;
    }

    //ADDING STUDENT GRADES
    public void addStudentGrades(String email, int grade){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("grade",grade);

        db.insert(table_student_grades,null,cv);
    }

    //RETRIEVING STUDENT RECORDS
    public String[] retrieveStudentGradesForStudent(String userName){
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT s.fname, s.email, g.grade FROM "+ table_users +" as s " +
                "INNER JOIN "+ table_student_grades +" as g ON s.email = g.email;", null);
        String firstName = "", email= "";
        int studentGrade = 0;

        if(c.moveToFirst()){
            do{
                email = c.getString(1);
                if (email.equals(userName)) {
                    firstName = c.getString(0);
                    studentGrade = c.getInt(2);
                }
            }while(c.moveToNext());
        }
        c.close();
        return new String[]{firstName, email, studentGrade+""};
    }
 // getting the scorelist
    public List<Integer> getScoresList()
    {
        List<Integer> scoresList = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table_student_grades , null);

        if(c.moveToFirst()) {
            do {
                scoresList.add(c.getInt(2));
            }while (c.moveToNext());
        }
        c.close();
        return  scoresList;

    }

    public List<String> getGradedStudents()
    {
        List<String> gradedStudentsList = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table_student_grades , null);

        if(c.moveToFirst()) {
            do {
                gradedStudentsList.add(c.getString(1));
            }while (c.moveToNext());
        }
        c.close();
        return  gradedStudentsList;
    }

    public List<String> getRegisteredUsers()
    {
        List<String> registeredUsersList = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table_users, null);

        if(c.moveToFirst()) {
            do {
                registeredUsersList.add(c.getString(2));
            }while (c.moveToNext());
        }
        c.close();
        return  registeredUsersList;
    }

    public void deleteCurrentQuiz(){
        db = this.getWritableDatabase();

        String query1= "DELETE FROM "+table_questions;
        db.execSQL(query1);

        String query2= "DELETE FROM "+table_student_grades;
        db.execSQL(query2);

    }



}
