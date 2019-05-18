package com.example.nimit.crackquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button regBtn;
    Button loginBtn;

    //Session session = new Session(this);
    DatabaseHelper loginHelper = new DatabaseHelper(this);
    UserRecord userSession ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regBtn = (Button) findViewById(R.id.btnRegister);
        loginBtn = (Button) findViewById(R.id.btnLogin) ;
        userSession = new UserRecord(this);
        final EditText lUsername =(EditText)findViewById(R.id.editTxtUserName);
        final  EditText lPass = (EditText) findViewById(R.id.editTxtPswd);


        regBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
              }
          });

        loginBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  String enteredUserName, enteredPass;

                  if (lUsername.getText().toString().equals("")  ||  lPass.getText().toString().equals("")) {

                      Toast.makeText(MainActivity.this, "Please fill out both the fields", Toast.LENGTH_SHORT).show();
                  }
                  else{
                      enteredUserName = lUsername.getText().toString();
                      enteredPass = lPass.getText().toString();

                      String returnedPass[] = loginHelper.searchPassword(enteredUserName);
                      userSession.setEmail(enteredUserName);
                      userSession.setAccountType(returnedPass[1]);
                      userSession.setFname(returnedPass[2]);

                      if (enteredPass.equals(returnedPass[0])) {
                          if(returnedPass[1].equals("Student")) {

                              Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(MainActivity.this, StudentDashboardActivity.class));
                          }else{
                              //userSession.setEmail(enteredUserName);
                              //userSession.setAccountType(returnedPass[1]);
                              Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(MainActivity.this, TeacherDashboardActivity.class));
                         }
                      } else {
                          Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                      }
                  }
              }
      });



    }
}
