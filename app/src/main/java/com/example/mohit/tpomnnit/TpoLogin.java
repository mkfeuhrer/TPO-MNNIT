package com.example.mohit.tpomnnit;

import android.content.Intent;
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

public class TpoLogin extends AppCompatActivity {

    private Button signin;
    private EditText regnum,password;
    private TextView signup;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_login);
        regnum = (EditText) findViewById(R.id.regnum);
        password = (EditText)findViewById(R.id.loginpassword);
        signin = (Button)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);
        mDatabase = FirebaseDatabase.getInstance().getReference("tpouser");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener val=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot tpoDetails : dataSnapshot.getChildren())
                        {
                            if(regnum.getText().toString().trim().equals(tpoDetails.child("studentid").getValue().toString()))
                            {
                                String passwd=tpoDetails.child("password").getValue().toString();
                                if(passwd.equals(password.getText().toString().trim()))
                                {
                                    //Log.e("sa","password matched");
                                    Toast.makeText(TpoLogin.this,"Logged in...:-)",Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent i = new Intent(TpoLogin.this,TpoHome.class);
                                    i.putExtra("reg",regnum.getText().toString().trim());
                                    startActivity(i);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mDatabase.addValueEventListener(val);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(TpoLogin.this, TpoSignup.class);
                startActivity(i);
            }
        });

    }
}
