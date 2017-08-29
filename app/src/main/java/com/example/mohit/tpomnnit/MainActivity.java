package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.name;
import static com.example.mohit.tpomnnit.R.id.username;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button register;
    private EditText email,password,regnum,name;
    private TextView registered;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase,mDatabase2;
    private String userId,userId2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("app_title").setValue("TPO MNNIT");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Title", "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Error", "Failed to read app title value.", error.toException());
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        userId = mDatabase.push().getKey();

        mDatabase2 = FirebaseDatabase.getInstance().getReference("userdata");
        userId2 = mDatabase2.push().getKey();

        register   = (Button)findViewById(R.id.register);
        regnum     = (EditText)findViewById(R.id.regnum);
        name       = (EditText)findViewById(username);
        email      = (EditText)findViewById(R.id.email);
        password   = (EditText)findViewById(R.id.password);
        registered = (TextView)findViewById(R.id.registered);

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                }
                else
                createUser(name.getText().toString().trim(), email.getText().toString().trim(), regnum.getText().toString().trim(), password.getText().toString().trim(),userId);

                finish();
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email,String regnum,String password,String userid) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userid)) {
//            userId = mDatabase.push().getKey();
        }
        Users user = new Users(name, email,regnum,password,userid);
        UserData userData1 = new UserData(regnum,name,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",1);
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
                Log.e(TAG, "User data is changed!" + user1.name + ", " + user1.email);
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
