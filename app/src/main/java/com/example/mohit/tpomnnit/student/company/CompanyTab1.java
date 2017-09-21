package com.example.mohit.tpomnnit.student.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CompanyTab1 extends Fragment {
    RecyclerView recyclerView;
    List<Companies> companiesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    CompaniesAdapter companiesAdapter;
    String companyId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_companytab1, container, false);
        mDatabase=FirebaseDatabase.getInstance().getReference("companies");
        companyId=mDatabase.push().getKey();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        companiesList=new ArrayList<>();
       // Companies companies=new Companies("name","ctc","fasfd","fasfd","fasfd","fasfd","fasfd","fasfd","fasfd",null,null,null);
       // companiesList.add(companies);
        prepareData();
       /* companiesAdapter=new CompaniesAdapter(companiesList);
        CompanyStudent companyStudent=(CompanyStudent)getActivity();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(companyStudent.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(companiesAdapter);*/
        return view;
    }
    void prepareData()
    {
        System.out.println("in prepare data");
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    Companies companies=new Companies(userDetails.child("name").getValue().toString(),userDetails.child("ctc").getValue().toString(),userDetails.child("location").getValue().toString(),null,null,null,null,null,null,null,null,null);
                    //Companies companies=dataSnapshot.getValue(Companies.class);
                            companiesList.add(companies);
                    System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
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
        companiesAdapter=new CompaniesAdapter(companiesList);
        final CompanyStudent companyStudent=(CompanyStudent)getActivity();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(companyStudent.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(companyStudent.getApplicationContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(companyStudent.getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Companies companies=companiesList.get(position);
                Toast.makeText(companyStudent.getApplicationContext(),"position selected: "+position,Toast.LENGTH_LONG).show();
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
        recyclerView.setAdapter(companiesAdapter);

    }

}
