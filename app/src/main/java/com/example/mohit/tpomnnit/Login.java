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
                        Users user= dataSnapshot.getValue(Users.class);
                        for(DataSnapshot userDetails : dataSnapshot.getChildren()) {

                            Log.e("Id",dataSnapshot.getKey().toString());
                            if(regnum.toString().trim().equals(userDetails.child("studentid").getValue().toString()))
                            {
                                String passw = userDetails.child("password").getValue().toString();
                                SQLiteDatabase data=openOrCreateDatabase("users",MODE_PRIVATE,null);
                                data.execSQL("create table if not exists hint (id varchar, regnum varchar);");
                                String s = "select * from hint where name='" + dataSnapshot.getKey().toString() + "' and regnum='" + regnum.toString().trim() + "'";


                                if(passw.equals(password.toString().trim()))
                                {
                                    finish();
                                    Intent i = new Intent(Login.this,StudentProfile.class);
                                    startActivity(i);
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


                        //

//                        if(s1.equals("")) {
//                        Toast.makeText(Login.this, "Please Enter username.", Toast.LENGTH_LONG).show();
//                        }
//                        else if(s2.equals("")) {
//                        Toast.makeText(Login.this, "Please Enter password.", Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                        Cursor cursor = data.rawQuery(s, null);
//                        if (cursor.getCount() > 0) {
//                        SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        SharedPreferences.Editor editor2 = settings2.edit();
//                        editor2.putString("username", s1).apply();
//                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString("Logged","true");
//                        editor.apply();
//                        Intent i=new Intent(Login.this,home.class);
//        startActivity(i);
//        finish();
//        //Toast.makeText(Login.this, "sjkhfdkjhafl", Toast.LENGTH_LONG).show();
//        } else {
//        Toast.makeText(Login.this, "Not a registered User , Kindly signup first", Toast.LENGTH_LONG).show();
//        }
//        }