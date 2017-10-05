package com.example.mohit.tpomnnit.student.InterviewExperience;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

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

        mDatabase     = FirebaseDatabase.getInstance().getReference("interviews");
        interviewId   = mDatabase.push().getKey();
       // recyclerView  = (RecyclerView)findViewById(R.id.recyclerView);
        interviewList = new ArrayList<>();
        prepareData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(interviewexperience.this,AddInterviewExperience.class);
                startActivity(i);
            }
        });

    }


    void prepareData()
    {
        System.out.println("in prepare data");
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    Interview interviews=new Interview(
                            userDetails.child("username").getValue().toString(),
                            userDetails.child("regno").getValue().toString(),
                            userDetails.child("year").getValue().toString(),
                            userDetails.child("company").getValue().toString(),
                            userDetails.child("experience").getValue().toString(),
                            userDetails.child("date").getValue().toString());
                    //Companies companies=dataSnapshot.getValue(Companies.class);
                    interviewList.add(interviews);
                    System.out.println("in"+ userDetails.child("username").getValue().toString()+" : "+ interviews.getExperience()+" : " + interviews.getDate());
                }
                addInterview();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

    }
    void addInterview()
    {
        ListView theListView = (ListView) findViewById(R.id.mainListView);
        final InterViewAdapter adapter = new InterViewAdapter(this, interviewList);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

    }
}
