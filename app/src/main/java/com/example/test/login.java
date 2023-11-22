package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class login extends AppCompatActivity {
    Button callSignUp,login_btn,paybutton;
    ImageView image;
    TextView logo, slogantext,paytext;
    TextInputLayout username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        callSignUp=findViewById(R.id.signup);
        image=findViewById(R.id.logo_image);
        logo=findViewById(R.id.logo_name);
        slogantext=findViewById(R.id.slogan_name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login_btn);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(login.this,SignUp.class);
                Pair<View, String> pair1 = new Pair<>(image, "logo_image");

                Pair<View, String> pair2 = new Pair<>(logo, "logo_text");
                Pair<View, String> pair3 = new Pair<>(slogantext, "logo_dec");
                Pair<View, String> pair4 = new Pair<>(username, "usrname_tran");
                Pair<View, String> pair5 = new Pair<>(password, "password_tran");
                Pair<View, String> pair6 = new Pair<>(callSignUp, "login_signup_tran");


                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(login.this, pair1, pair2,pair3,pair4,pair5,pair6);

                startActivity(intent, options.toBundle());
            }
        });
    }


    private Boolean validateUsername() {

        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {

            username.setError("Field cannot be empty");

            return false;

        }
        else {

            username.setError(null);

            username.setErrorEnabled(false);
            return true;

        }

    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }
//
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }}

    public void loginUser(View view){

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this,Menu.class);

                if (!validateUsername() | !validatePassword()){
                    return;
                }
                else{
                    isUser();
                }
                Toast.makeText(login.this,"LOADING....",Toast.LENGTH_SHORT).show();



            }
        });

    }

    private void isUser() {

        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredpassword= password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo((userEnteredUsername));

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredpassword)){

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String EmailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),Menu.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",EmailFromDB);
                        intent.putExtra("phoneNo",phoneNoFromDB);
                        intent.putExtra("password",passwordFromDB);

                        startActivity(intent);

                    }
                    else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }

                }

                else{
                    username.setError("No such user exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}