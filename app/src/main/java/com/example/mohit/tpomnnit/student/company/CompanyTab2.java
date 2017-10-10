package com.example.mohit.tpomnnit.student.company;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;


public class CompanyTab2 extends Fragment {
    List<Companies> companiesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String companyId;
    String currsel="null";
    View view1;
    ValueEventListener vel;
    CompanyStudent companyStudent=(CompanyStudent)getActivity();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view1= inflater.inflate(R.layout.fragment_companytab2, container, false);
        mDatabase=FirebaseDatabase.getInstance().getReference("companies");
        companyId=mDatabase.push().getKey();
        companiesList=new ArrayList<>();
        prepareData();
        return view1;

    }
    void prepareData()
    {
        System.out.println("in prepare data");
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    //Companies companies=new Companies(userDetails.child("name").getValue().toString(),userDetails.child("ctc").getValue().toString(),userDetails.child("location").getValue().toString(),null,null,null,null,null,null,null,null,null);
                    Companies companies=new Companies();
                    companies.setName(userDetails.child("name").getValue().toString());
                    companies.setCtc(userDetails.child("ctc").getValue().toString());
                    companies.setLocation(userDetails.child("location").getValue().toString());
                    companies.setProfile(userDetails.child("profile").getValue().toString());
                    companies.setYear(userDetails.child("year").getValue().toString());
                    companies.setPpo(userDetails.child("ppo").getValue().toString());
                    companies.setCompanyid(userDetails.child("companyid").getValue().toString());
                    companies.setDeadline(userDetails.child("deadline").getValue().toString());
                    companies.setLink(userDetails.child("link").getValue().toString());
                    //ArrayList<String> branch=new ArrayList<String>();
                    System.out.println("branch "+userDetails.child("branch").getValue());
                    //companies.setBranch(userDetails.child("branch").getValue().toString());
                    companiesList.add(companies);
                    //System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
                }
                addCompany();
                mDatabase.removeEventListener(vel);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

    }
    void addCompany()
    {
        ListView theListView = (ListView) view1.findViewById(R.id.mainListView);
        final FoldingCellCompanyAdapter adapter= new FoldingCellCompanyAdapter(getContext(),companiesList);

       /* adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alertDialoge = new ViewDialog();
                alertDialoge.showDialog(getActivity(), "Register");
                //Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS "+currsel, Toast.LENGTH_SHORT).show();
            }
        });*/

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                System.out.println("toggle");
                currsel=companiesList.get(pos).getName().toString();
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
    }

}
