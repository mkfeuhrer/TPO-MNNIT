package com.example.mohit.tpomnnit.login_signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.services.NotificationService;
import com.example.mohit.tpomnnit.student.StudentProfile;
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
    private ImageView imageView;
    ValueEventListener vel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = (ImageView) findViewById(R.id.code);
        imageView.setImageResource(R.drawable.loginback);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        regnum = (EditText) findViewById(R.id.regnum);
        password = (EditText)findViewById(R.id.loginpassword);
        signin = (Button)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);
        SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null); //nobody other can access
        data.execSQL("create table if not exists student (regno varchar, password varchar);");
        String s = "select * from student";
        Cursor cursor = data.rawQuery(s, null); // whatever query i run i can store something in cursor it is a class
        if (cursor.getCount()==1) {
            cursor.moveToFirst();

            Intent i = new Intent(Login.this,StudentProfile.class);
            i.putExtra("reg",cursor.getString(cursor.getColumnIndex("regno")));
            startActivity(i);
            finish();

        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vel = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int f = 0;
                        for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                            if(regnum.getText().toString().trim().length() == 0)
                            {
                                Toast.makeText(Login.this,"Enter Username",Toast.LENGTH_LONG).show();
                                f = 1;
                                break;
                            }
                            if(regnum.getText().toString().trim().equals(userDetails.child("studentid").getValue().toString()))
                            {
                                f = 1;
                                String passw = userDetails.child("password").getValue().toString();
                                Log.e("Pass",passw);
                                if(password.getText().toString().trim().length() == 0)
                                {
                                    Toast.makeText(Login.this,"Enter Password",Toast.LENGTH_LONG).show();
                                    break;
                                }
                                if(passw.equals(password.getText().toString().trim()))
                                {

                                    SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null); //nobody other can access
                                    data.execSQL("create table if not exists student (regno varchar, password varchar);");
                                    data.execSQL("insert into student values ('" + regnum.getText().toString().trim() + "','" + password.getText().toString().trim() + "');");
                                    finish();
                                    Intent i = new Intent(Login.this,StudentProfile.class);
                                    i.putExtra("reg",regnum.getText().toString().trim());
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(Login.this,"Invalid Password",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                        if(f==0)
                        {
                            Toast.makeText(Login.this,"User Not Registered",Toast.LENGTH_LONG).show();
                        }
                        //mDatabase.removeEventListener(vel);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(vel);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
                finish();
                //finish();
            }
        });

    }
}