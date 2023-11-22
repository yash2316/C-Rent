package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserProfile extends AppCompatActivity {

    TextInputLayout FullName, Email, phoneNum, Password;
    TextView fullNameLabel, usernameLabel;

    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;

    Button update;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);


        reference = FirebaseDatabase.getInstance().getReference("users");

        FullName = findViewById(R.id.full_name_profile);
        Email = findViewById(R.id.email_profile);
        phoneNum = findViewById(R.id.phone_no_profile);
        Password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.full_name);
        usernameLabel = findViewById(R.id.username_field);

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        showAllUserData();

    }

    private void showAllUserData() {

        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText("@" + _USERNAME);
        FullName.getEditText().setText(_NAME);
        Email.getEditText().setText(_EMAIL);
        phoneNum.getEditText().setText(_PHONENO);
        Password.getEditText().setText(_PASSWORD);
    }


    public void update(View view) {

        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(UserProfile.this, "DATA IS UPDATED", Toast.LENGTH_SHORT).show();

        }

    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(Password.getEditText().getText().toString())) {

            reference.child(_USERNAME).child("password").setValue(Password.getEditText().getText().toString());

            _PASSWORD = Password.getEditText().getText().toString();

            return true;

        } else {

            return false;

        }

    }

    private boolean isNameChanged() {

        if (!_NAME.equals(FullName.getEditText().getText().toString())) {

            reference.child(_USERNAME).child("name").setValue(FullName.getEditText().getText().toString());

            _NAME = FullName.getEditText().getText().toString();

            return true;

        } else {

            return false;

        }

    }
}