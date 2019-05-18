package com.example.nimit.crackquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Button subBtn = (Button) findViewById(R.id.btnSubmit);
        final  EditText fnameEd = (EditText) findViewById(R.id.editTxtFname);
        final  EditText lnameEd = (EditText) findViewById(R.id.editTxtLName);
        final  EditText emailEd = (EditText) findViewById(R.id.editTxtEmail);
        final  EditText pass1Ed = (EditText) findViewById(R.id.editTxtRPswd1);
        final  EditText pass2Ed = (EditText) findViewById(R.id.editTextRPswd2);
        final Spinner accoutTypeSpnr = (Spinner)findViewById(R.id.spnrAccountType);



        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = fnameEd.getText().toString();
                String lname = lnameEd.getText().toString();
                String email = emailEd.getText().toString();
                String psw1 = pass1Ed.getText().toString();
                String psw2 = pass2Ed.getText().toString();
                String accountTYpe = accoutTypeSpnr.getSelectedItem().toString();

                String emailValidate = emailEd.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(fname.equals("")||lname.equals("")||email.equals("")||psw1.equals("")||psw2.equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter values on all the fields", Toast.LENGTH_SHORT).show();
                }else  if (!psw1.equals(psw2)){
                        Toast.makeText(RegistrationActivity.this, "Passwords should match",
                                Toast.LENGTH_SHORT).show();
                }
                else if(!(emailValidate.matches(emailPattern))){
                    Toast.makeText(getApplicationContext(),"Please enter a valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else{
                      List<String> registeredUsersList = helper.getRegisteredUsers();
                      Boolean registered = false;

                        if(registeredUsersList.size() > 0) {
                            for (int i = 0; i < registeredUsersList.size(); i++) {
                                String dbEmail = registeredUsersList.get(i);
                                if(dbEmail.equals(email)) {
                                    registered = true;
                                }
                            }
                        }
                        if(registered){
                            Toast.makeText(RegistrationActivity.this, "Sorry! You are Already Registered. Please check the information entered or login again!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            UserRecord newUser = new UserRecord(RegistrationActivity.this);
                            newUser.setFname(fname);
                            newUser.setLname(lname);
                            newUser.setEmail(email);
                            newUser.setPswd(psw1);
                            newUser.setAccountType(accountTYpe);

                            helper.insertRecord(newUser);
                            Toast.makeText(RegistrationActivity.this, "Welcome to CrackQuiz "+fname, Toast.LENGTH_SHORT).show();
                            if(accountTYpe.equals("Student")){
                                startActivity(new Intent(RegistrationActivity.this, StudentDashboardActivity.class));
                            }
                            else{
                                startActivity(new Intent(RegistrationActivity.this, TeacherDashboardActivity.class));

                            }
                        }
                }
            }
        });

    }
}
