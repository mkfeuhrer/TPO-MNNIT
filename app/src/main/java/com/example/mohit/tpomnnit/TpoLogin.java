package com.example.mohit.tpomnnit;

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

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String isLogged = preferences1.getString("Logged", "");
        if(!isLogged.equalsIgnoreCase(""))

        {
            if(isLogged.equals("true")){
                Intent i=new Intent(TpoLogin.this,TpoHome.class);
                SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String regn = settings2.getString("tpoadminregister","");
                Log.e("regis",regn);
                i.putExtra("reg",regn);
                startActivity(i);
                finish();
            }
        }


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
                                    SQLiteDatabase data=openOrCreateDatabase("tpo",MODE_PRIVATE,null); //nobody other can access
                                    //it is stored in our phone only
                                    data.execSQL("create table if not exists tpologin(name varchar, password varchar);");
                                    //
                                    String s1 = regnum.getText().toString().trim();
                                    String s2 = password.getText().toString().trim();
                                    String s = "select * from tpologin where name='" + s1 + "' and password='" + s2 + "'";

                                    Cursor cursor = data.rawQuery(s, null);
                                    if (cursor.getCount() > 0) {
                                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("tpoadminregister", s1).apply();
                                        editor.putString("Logged","true");
                                        editor.apply();
                                        //Toast.makeText(Login.this, "sjkhfdkjhafl", Toast.LENGTH_LONG).show();
                                    }
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
                Intent i = new Intent(TpoLogin.this,TpoSignup.class);
                startActivity(i);
                finish();
            }
        });

    }
}
