package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mohit.tpomnnit.R.id.companyname;


public class AddCompany extends AppCompatActivity {

    private static final String TAG = AddCompany.class.getSimpleName();
    private DatabaseReference mDatabase;
    private String companyId;
    private Button addcompany;
    private EditText year,ctc,location,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        mDatabase = FirebaseDatabase.getInstance().getReference("companies");
        companyId = mDatabase.push().getKey();


        addcompany   = (Button)findViewById(R.id.addcompany);
        year     = (EditText)findViewById(R.id.year);
        name     = (EditText)findViewById(R.id.companyname);
        ctc      = (EditText)findViewById(R.id.ctc);
        location   = (EditText)findViewById(R.id.location);

        addcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for already existed userId
                if (TextUtils.isEmpty(companyId)) {
                }
                else
                    createCompany(name.getText().toString().trim(), year.getText().toString().trim(), ctc.getText().toString().trim(), location.getText().toString().trim(),companyId);

                finish();
                Intent i = new Intent(AddCompany.this,TpoHome.class);
                i.putExtra("company",name.getText().toString().trim());
                startActivity(i);
            }
        });

    }

    private void createCompany(String name, String year,String ctc,String location,String companyid) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(companyid)) {
//            userId = mDatabase.push().getKey();
        }
        ArrayList<String> branch = new ArrayList<String>();
        ArrayList<String> registeredstudents = new ArrayList<String>();
        ArrayList<String> selectedstudents = new ArrayList<String>();
        Companies company = new Companies(name,ctc,location,"",year,"",companyId,"","",branch,registeredstudents,selectedstudents);
        addCompanyChangeListener(company);
    }
    private void addCompanyChangeListener(final Companies company) {
        // User data change listener
        mDatabase.child(companyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Companies company1 = company;

                // Check for null
                if (company1 == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                final String companyId = mDatabase.push().getKey();
                mDatabase.child(companyId).setValue(company1);
                Log.e(TAG, "User data is changed!" + company1.name + ", " + company1.year);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

}
