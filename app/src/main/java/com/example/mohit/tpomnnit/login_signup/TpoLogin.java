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

       /* SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String isLogged = preferences1.getString("LoggedTpo", "");
        Log.e("islogged",isLogged);
        if(!isLogged.equalsIgnoreCase(""))
        {
            if(isLogged.equals("true")){
                Intent i=new Intent(TpoLogin.this,TpoHome.class);
                SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String regn = settings2.getString("tpoadminregister","");
                Toast.makeText(getApplicationContext(),"Already Logged in!",Toast.LENGTH_LONG);
                Log.e("regis",regn);
                i.putExtra("reg",regn);
                startActivity(i);
                finish();
            }
        }
*/

        regnum = (EditText) findViewById(R.id.regnum);
        password = (EditText)findViewById(R.id.loginpassword);
        signin = (Button)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);
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
