package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private Button signin;
    private EditText regnum,password;
    private TextView signup;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        regnum = (EditText) findViewById(R.id.regnum);
        password = (EditText)findViewById(R.id.loginpassword);
        signin = (Button)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValueEventListener vel = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                            if(regnum.getText().toString().trim().equals(userDetails.child("studentid").getValue().toString()))
                            {
                                String passw = userDetails.child("password").getValue().toString();
                                Log.e("Pass",passw);
                                if(passw.equals(password.getText().toString().trim()))
                                {
                                    finish();
//                                    Intent i = new Intent(Login.this,StudentProfile.class);
//                                    i.putExtra("reg",regnum.getText().toString().trim());
//                                    startActivity(i);
                                }
                            }
//                            Log.d("valueName:", userDetails.child("name").getValue().toString());
//                            Log.d("valueEmail:", userDetails.child("email").getValue().toString());
//                            Log.d("valueuserid:", userDetails.child("studentid").getValue().toString());
//                            Log.d("password:", userDetails.child("password").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mDatabase.addValueEventListener(vel);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}