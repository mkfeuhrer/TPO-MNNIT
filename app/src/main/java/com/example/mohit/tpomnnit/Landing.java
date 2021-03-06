package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohit.tpomnnit.charts.PlacementStat;
import com.example.mohit.tpomnnit.login_signup.Login;
import com.example.mohit.tpomnnit.login_signup.TpoLogin;
import com.example.mohit.tpomnnit.login_signup.TpoSignup;
import com.example.mohit.tpomnnit.messenger.chat;
import com.example.mohit.tpomnnit.services.NotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Landing extends AppCompatActivity {

    private Button tpoadmin,student,placementstat;
    private ImageView imageView;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        TextView textView=(TextView)findViewById(R.id.textView);
        int flag= NotificationService.flag;
        System.out.println("flag : "+flag);
        if(flag!=2) {
            Intent serviceIntent = new Intent(Landing.this, NotificationService.class);
            startService(serviceIntent);
        }
        Typeface tf = Typeface.createFromAsset(this.getAssets(),"bst.ttf");
        textView.setTypeface(tf);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("app_title").setValue("TPO MNNIT");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("Title", "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                //getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Error", "Failed to read app title value.", error.toException());
            }
        });
        //NotificationService ob=new NotificationService();
        /*int flag=NotificationService.flag;
        System.out.println("flag : "+flag);
        if(flag!=2) {
            Intent serviceIntent = new Intent(Landing.this, NotificationService.class);
            startService(serviceIntent);
        }*/
        imageView = (ImageView) findViewById(R.id.landingimage);

        imageView.setImageResource(R.drawable.imagee);

        tpoadmin = (Button)findViewById(R.id.tpoadmin);
        student  = (Button)findViewById(R.id.student);
        placementstat = (Button)findViewById(R.id.placementstat);

        tpoadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Landing.this, TpoLogin.class);
                startActivity(i);
                //Toast.makeText(Landing.this,"TPO Register",Toast.LENGTH_LONG).show();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Landing.this, Login.class);
                startActivity(i);
            }
        });

        placementstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Landing.this,PlacementStat.class);
                startActivity(i);
            }
        });
    }
}
