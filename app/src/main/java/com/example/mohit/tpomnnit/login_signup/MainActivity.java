package com.example.mohit.tpomnnit.login_signup;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.example.mohit.tpomnnit.student.profile.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mohit.tpomnnit.R.id.confirmpass;
import static com.example.mohit.tpomnnit.R.id.username;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button register;
    private EditText year,password,regnum,name,confirmpassword,mobile;
    private DatabaseReference mDatabase,mDatabase2;
    private String userId,userId2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        userId = mDatabase.push().getKey();

        mDatabase2 = FirebaseDatabase.getInstance().getReference("userdata");
        userId2 = mDatabase2.push().getKey();

        register   = (Button)findViewById(R.id.register);
        regnum     = (EditText)findViewById(R.id.regnum);
        name       = (EditText)findViewById(username);
        year       = (EditText)findViewById(R.id.year);
        password   = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        mobile     = (EditText)findViewById(R.id.mobile);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s1 = regnum.getText().toString().trim();
                String s2 = password.getText().toString().trim();
                String nam = name.getText().toString().trim();
                String confirmpass = confirmpassword.getText().toString().trim();
                String mob  = mobile.getText().toString().trim();
                String years = year.getText().toString().trim();
                Log.e("regnum",s1+"adf");
                Log.e("year",years);
                if(confirmpass.length() == 0 || mob.length() == 0 || years.length() == 0 || s1.length() == 0 || s2.length() == 0 || nam.length() == 0)
                {
                    Toast.makeText(MainActivity.this,"Fill all entries",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //database in android

                    // Check for already existed userId
                    if (TextUtils.isEmpty(userId)) {
                    }
                    else
                        createUser(name.getText().toString().trim(), year.getText().toString().trim(), regnum.getText().toString().trim(), password.getText().toString().trim(), userId);

                    finish();
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                }
            }
        });
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String year,String regnum,String password,String userid) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userid)) {
//            userId = mDatabase.push().getKey();
        }
        Users user = new Users(name, year,regnum,password,userid);
        UserData userData1 = new UserData(regnum,name,"n/a","n/a",year,"n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a","n/a",10,0,1);
        ArrayList<String> companies = new ArrayList<String>();
        companies.add("Temp");
        userData1.setCompanies(companies);
        addUserChangeListener(user);
        addUserDataChangeListener(userData1);
    }
    private void addUserChangeListener(final Users user) {
        // User data change listener
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user1 = user;

                // Check for null
                if (user1 == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                final String userId = mDatabase.push().getKey();
                mDatabase.child(userId).setValue(user1);
                Log.e(TAG, "User data is changed!" + user1.name + ", " + user1.year);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
    private void addUserDataChangeListener(final UserData user) {
        // User data change listener
        mDatabase2.child(userId2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user1 = user;

                // Check for null
                if (user1 == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                final String userId1 = mDatabase2.push().getKey();
                mDatabase2.child(userId1).setValue(user1);
                Log.e(TAG, "Userdata data is changed!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

}
