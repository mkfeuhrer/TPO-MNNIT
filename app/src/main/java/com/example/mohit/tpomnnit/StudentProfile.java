package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.annotation.InterpolatorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {


    private TextView name,email,regnum;
    private String registrationnum;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        name    = (TextView)findViewById(R.id.name);
        email   = (TextView)findViewById(R.id.email);
        regnum  = (TextView)findViewById(R.id.regnum);
        registrationnum = getIntent().getStringExtra("reg");
        Log.e("reg",registrationnum);

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user= dataSnapshot.getValue(Users.class);
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {

                    if(registrationnum.equals(userDetails.child("studentid").getValue().toString()))
                    {
                        name.setText(userDetails.child("name").getValue().toString());
                        email.setText(userDetails.child("email").getValue().toString());
                        regnum.setText(registrationnum);
                        //Access all data

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
}
