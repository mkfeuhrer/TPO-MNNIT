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
import com.example.mohit.tpomnnit.student.StudentProfile;
import com.example.mohit.tpomnnit.tpo.TpoHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TpoLogin extends AppCompatActivity {

    private Button signin;
    private EditText regnum,password;
    private TextView signup;
    private DatabaseReference mDatabase;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_login);

        imageView = (ImageView) findViewById(R.id.code);
        imageView.setImageResource(R.drawable.loginback);
        regnum = (EditText) findViewById(R.id.regnum);
        password = (EditText)findViewById(R.id.loginpassword);
        signin = (Button)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);
        SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null); //nobody other can access
        data.execSQL("create table if not exists tpo (regno varchar, password varchar);");
        String s = "select * from tpo";
        Cursor cursor = data.rawQuery(s, null); // whatever query i run i can store something in cursor it is a class
        if (cursor.getCount()==1) {
            cursor.moveToFirst();

            Intent i = new Intent(TpoLogin.this,TpoHome.class);
            i.putExtra("reg",cursor.getString(cursor.getColumnIndex("regno")));
            startActivity(i);
            finish();

        }
        mDatabase = FirebaseDatabase.getInstance().getReference("tpouserdata");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regnum.getText().toString().trim().equals("") || password.getText().toString().trim().equals(""))
                {
                    Toast.makeText(TpoLogin.this,"Please enter registration no and password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    ValueEventListener val = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int f = 0;
                            for (DataSnapshot tpoDetails : dataSnapshot.getChildren()) {
                                if (regnum.getText().toString().trim().equals(tpoDetails.child("regno").getValue().toString())) {
                                    f = 1;
                                    String passwd = tpoDetails.child("password").getValue().toString();
                                    if (passwd.equals(password.getText().toString().trim())) {
                                        SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null); //nobody other can access
                                        data.execSQL("create table if not exists tpo (regno varchar, password varchar);");
                                        data.execSQL("insert into tpo values ('" + regnum.getText().toString().trim() + "','" + password.getText().toString().trim() + "');");
                                        Intent i = new Intent(TpoLogin.this, TpoHome.class);
                                        i.putExtra("reg", regnum.getText().toString().trim());
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(TpoLogin.this, "Invalid Password", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }
                            if (f == 0) {
                                Toast.makeText(TpoLogin.this, "User Not Registered", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    mDatabase.addValueEventListener(val);
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TpoLogin.this,TpoSignup.class);
                startActivity(i);
            }
        });

    }
}
