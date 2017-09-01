package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Landing extends AppCompatActivity {

    private Button tpoadmin,student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        tpoadmin = (Button)findViewById(R.id.tpoadmin);
        student  = (Button)findViewById(R.id.student);

        tpoadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(Landing.this, CompanyStudent.class);
                startActivity(i);
                //Toast.makeText(Landing.this,"TPO Register",Toast.LENGTH_LONG).show();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(Landing.this, Login.class);
                startActivity(i);
            }
        });
    }
}
