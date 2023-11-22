package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn , regLoginbtn;
    FirebaseDatabase rootnode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName=findViewById(R.id.name);
        regUsername=findViewById(R.id.username);
        regEmail=findViewById(R.id.email);
        regPhoneNo=findViewById(R.id.phoneNo);
        regPassword=findViewById(R.id.password);
        regBtn=findViewById(R.id.goo);
        regLoginbtn=findViewById(R.id.log_in);

    }


    private Boolean validateName() {

        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {

            regName.setError("Field cannot be empty");

            return false;

        }

        else {

            regName.setError(null);

            regName.setErrorEnabled(false);

            return true;

        }

    }

    private Boolean validateUsername() {

        String val = regUsername.getEditText().getText().toString();

        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {

            regUsername.setError("Field cannot be empty");

            return false;

        } else if (val.length() >= 15) {

            regUsername.setError("Username too long");

            return false;

        } else if (!val.matches(noWhiteSpace)) {

            regUsername.setError("White Spaces are not allowed");

            return false;

        } else {

            regUsername.setError(null);

            regUsername.setErrorEnabled(false);

            return true;

        }

    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }}

    public void registerUser (View view){

        if(!validateName() |!validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername())
        {
            return;
        }

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootnode=FirebaseDatabase.getInstance();
                reference=rootnode.getReference("users");

                String name =regName.getEditText().getText().toString();
                String username =regUsername.getEditText().getText().toString();
                String email =regEmail.getEditText().getText().toString();
                String phoneNo =regPhoneNo.getEditText().getText().toString();
                String password =regPassword.getEditText().getText().toString();

                UserHelperClass helperClass=new UserHelperClass(name,username,email,phoneNo,password);

                reference.child(username).setValue(helperClass);
                Toast.makeText(SignUp.this,"Registered",Toast.LENGTH_SHORT).show();
            }
        });


    }
}