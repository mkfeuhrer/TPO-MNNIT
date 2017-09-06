package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.services.NotificationService;

public class Landing extends AppCompatActivity {

    private Button tpoadmin,student;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent serviceIntent =new Intent(Landing.this, NotificationService.class);
        startService(serviceIntent);

        imageView = (ImageView) findViewById(R.id.landingimage);

        imageView.setImageResource(R.drawable.mnnit);

        tpoadmin = (Button)findViewById(R.id.tpoadmin);
        student  = (Button)findViewById(R.id.student);

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
    }
}
