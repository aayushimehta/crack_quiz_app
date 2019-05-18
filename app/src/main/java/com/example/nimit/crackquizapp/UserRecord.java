package com.example.nimit.crackquizapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRecord {
    String fname;
    String lname;
    String email_id;
    String pswd;
    String accountType;
    int grade;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Context context;

    public static final String PREFERENCES_FILE = "quiz";

    public UserRecord(String uname, int grade){
        this.fname = uname;
        this.grade = grade;
    }

    public UserRecord(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setFname(String aFname)
    {
        fname = aFname;
        editor.putString("userFirstName",fname);
        editor.commit();
    }

    public String getFName()
    {
        fname = sharedPref.getString("userFirstName","");
        return fname;
    }

    public void setLname(String aLname)
    {
        lname = aLname;
    }

    public  String getLName()
    {
        return lname;
    }


    public void setEmail(String aEmail)
    {
        email_id = aEmail;
        editor.putString("userEmail",email_id);
        editor.commit();
    }

    public  String getEmail()
    {
        email_id = sharedPref.getString("userEmail","");
        return email_id;

    }

    public void setPswd(String aPswd)
    {
        pswd= aPswd;
    }

    public  String getPswd()
    {
        return pswd;
    }

    public  void  setAccountType(String acType)
    {
        accountType = acType;
        editor.putString("userType",accountType);
        editor.commit();
    }

    public  String  getAccountType()
    {
        accountType = sharedPref.getString("userType","");
        return  accountType;
    }

    public void removeSession(){
        editor.clear();
        editor.commit();
    }
}
