package com.example.mohit.tpomnnit.tpo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.*;
import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.company.Companies;
import com.example.mohit.tpomnnit.student.company.CompaniesAdapter;
import com.example.mohit.tpomnnit.student.profile.MyProfile;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.*;
import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.alexvasilkov.foldablelayout.UnfoldableView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mohit.tpomnnit.R.id.companies;

public class VerifyUser extends AppCompatActivity {
    List<UserData> userList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String userId;
    String currsel="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mohit.tpomnnit.R.layout.activity_verify_user);
        mDatabase=FirebaseDatabase.getInstance().getReference("userdata");
        userId=mDatabase.push().getKey();
        userList=new ArrayList<>();
        prepareData();


    }
    void prepareData()
    {
        System.out.println("in prepare data");
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    //UserData userData=new UserData(userDetails.child("regnum").getValue().toString(),userDetails.child("name").getValue().toString(),userDetails.child("branch").getValue().toString(),userDetails.child("batch").getValue().toString(),userDetails.child("course").getValue().toString(),userDetails.child("dob").getValue().toString(),userDetails.child("email").getValue().toString(),userDetails.child("skypeid").getValue().toString(),userDetails.child("linkedinid").getValue().toString(),userDetails.child("gender").getValue().toString(),userDetails.child("category").getValue().toString(),userDetails.child("phychal").getValue().toString(),null,userDetails.child("guardian").getValue().toString(),userDetails.child("presentadd").getValue().toString(),userDetails.child("permanentadd").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),userDetails.child("mobileno").getValue().toString(),0,0,0);
                    UserData userData=new UserData();
                    userData.setName(userDetails.child("name").getValue().toString());
                    userData.setRegnum(userDetails.child("regnum").getValue().toString());
                    userData.setBatch(userDetails.child("batch").getValue().toString());
                    userData.setBranch(userDetails.child("branch").getValue().toString());
                    userData.setCountry(userDetails.child("country").getValue().toString());
                    userData.setCourse(userDetails.child("course").getValue().toString());
                    userData.setDob(userDetails.child("dob").getValue().toString());
                    userData.setEmail(userDetails.child("email").getValue().toString());
                    userData.setLinkedinid(userDetails.child("linkedinid").getValue().toString());
                    userData.setSkypeid(userDetails.child("skypeid").getValue().toString());
                    userData.setGender(userDetails.child("gender").getValue().toString());
                    userData.setCategory(userDetails.child("category").getValue().toString());
                    userData.setPhychal(userDetails.child("phychal").getValue().toString());
                    userData.setPresentadd(userDetails.child("presentadd").getValue().toString());
                    userData.setPermanentadd(userDetails.child("permanentadd").getValue().toString());
                    userData.setMobileno(userDetails.child("mobileno").getValue().toString());
                    userData.setGuardianmobile(userDetails.child("guardianmobile").getValue().toString());
                    userData.setProject(userDetails.child("project").getValue().toString());
                    userData.setInternship(userDetails.child("internship").getValue().toString());
                    //userData.getName()
                    //Companies companies=dataSnapshot.getValue(Companies.class);
                    userList.add(userData);
                    //System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
                }
                addUsers();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

    }
    public void addUsers()
    {
        ListView theListView = (ListView) findViewById(R.id.mainListView);
        /*userList.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });*/

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, userList);

        // add default btn handler for each request btn on each item if custom handler not found

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VerifyUser.this, MyProfile.class);
                intent.putExtra("reg",currsel);
                //Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS "+currsel, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                System.out.println("toggle");
                currsel=userList.get(pos).getRegnum().toString();
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
    }
}
