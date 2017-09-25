package com.example.mohit.tpomnnit.student.InterviewExperience;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.company.*;
import com.example.mohit.tpomnnit.student.company.RecyclerTouchListener;
import com.example.mohit.tpomnnit.tpo.AddCompany;
import com.example.mohit.tpomnnit.tpo.TpoHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class interviewexperience extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Interview> interviewList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    InterViewAdapter interviewAdapter;
    String interviewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewexperience);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(interviewexperience.this,AddInterviewExperience.class);
                startActivity(i);
            }
        });

        mDatabase     = FirebaseDatabase.getInstance().getReference("interviews");
        interviewId   = mDatabase.push().getKey();
        recyclerView  = (RecyclerView)findViewById(R.id.recyclerView);
        interviewList = new ArrayList<>();
        prepareData();

    }


    void prepareData()
    {
        System.out.println("in prepare data");
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    Interview interviews=new Interview(userDetails.child("username").getValue().toString(),userDetails.child("regno").getValue().toString(),userDetails.child("year").getValue().toString(),userDetails.child("company").getValue().toString(),userDetails.child("experience").getValue().toString(),userDetails.child("date").getValue().toString());
                    //Companies companies=dataSnapshot.getValue(Companies.class);
                    interviewList.add(interviews);
                    System.out.println("in"+ userDetails.child("username").getValue().toString()+" : "+ interviews.getExperience()+" : " + interviews.getDate());
                }
                addRecycler();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

    }
    void addRecycler()
    {
       /* System.out.println("out");
        for(int i=0;i<companiesList.size();i++)
        {
            Companies companies=companiesList.get(i);
            System.out.println("ex "+companies.getName()+" : "+companies.getLocation());
        }*/
        interviewAdapter=new InterViewAdapter(interviewList);
//        final CompanyStudent companyStudent=(CompanyStudent)getActivity();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(interviewexperience.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new com.example.mohit.tpomnnit.student.company.DividerItemDecoration(interviewexperience.this,LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new com.example.mohit.tpomnnit.student.company.RecyclerTouchListener(interviewexperience.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Interview interviews = interviewList.get(position);
                //Toast.makeText(interviewexperience.this,"position selected: "+position,Toast.LENGTH_LONG).show();
                        /*Intent intent=new Intent(starQuestion.this,starQuestionDisplay.class);
                        intent.putExtra("position",position);
                        startActivity(intent);*/
                //finish();
                //Toast.makeText(getApplicationContext(), question.getQUESTION() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(interviewAdapter);

    }
}
