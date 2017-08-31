package com.example.mohit.tpomnnit;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    EditText regno,name,course,branch,dob,email,skype,linkedin,gender,
            category,pwd,residential,guardian,presentaddress,permanentaddress,
            marital,state,country,parentcontact,personalcontact;
    Button save;
    private String registrationnum,userId;
    private DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        regno = (EditText) view.findViewById(R.id.regno);
        name = (EditText) view.findViewById(R.id.name);
        course = (EditText) view.findViewById(R.id.course);
        branch = (EditText) view.findViewById(R.id.branch);
        dob = (EditText) view.findViewById(R.id.dob);
        email = (EditText) view.findViewById(R.id.email);
        skype = (EditText) view.findViewById(R.id.skypeid);
        linkedin = (EditText) view.findViewById(R.id.linkedinid);
        gender = (EditText) view.findViewById(R.id.gender);
        category = (EditText) view.findViewById(R.id.category);
        pwd = (EditText) view.findViewById(R.id.pwd);
        residential = (EditText) view.findViewById(R.id.residential);
        guardian = (EditText) view.findViewById(R.id.guardian);
        presentaddress = (EditText) view.findViewById(R.id.presentAddress);
        permanentaddress = (EditText) view.findViewById(R.id.permanentAddress);
        marital = (EditText) view.findViewById(R.id.marital);
        state = (EditText) view.findViewById(R.id.state);
        country = (EditText) view.findViewById(R.id.country);
        parentcontact = (EditText) view.findViewById(R.id.parentcontact);
        personalcontact = (EditText) view.findViewById(R.id.personalcontact);
        save=(Button)view.findViewById(R.id.save);
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId=mDatabase.push().getKey();
        MyProfile myProfile=(MyProfile)getActivity();
        registrationnum=myProfile.getRegno();
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user= dataSnapshot.getValue(UserData.class);
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if(registrationnum.equals(userDetails.child("regnum").getValue().toString()))
                    {
                        System.out.println("in in in ini ni nin in in ");
                        name.setText(userDetails.child("name").getValue().toString());
                        branch.setText(userDetails.child("branch").getValue().toString());
                        regno.setText(registrationnum);
                        course.setText(userDetails.child("course").getValue().toString());
                        dob.setText(userDetails.child("dob").getValue().toString());
                        email.setText(userDetails.child("email").getValue().toString());
                        skype.setText(userDetails.child("skypeid").getValue().toString());
                        linkedin.setText(userDetails.child("linkedinid").getValue().toString());
                        gender.setText(userDetails.child("gender").getValue().toString());
                        category.setText(userDetails.child("category").getValue().toString());
                        pwd.setText(userDetails.child("phychal").getValue().toString());name.setText(userDetails.child("name").getValue().toString());
                        residential.setText(userDetails.child("residentialstatus").getValue().toString());
                        guardian.setText(userDetails.child("guardian").getValue().toString());
                        permanentaddress.setText(userDetails.child("permanentadd").getValue().toString());
                        presentaddress.setText(userDetails.child("presentadd").getValue().toString());
                        state.setText(userDetails.child("state").getValue().toString());
                        country.setText(userDetails.child("country").getValue().toString());
                        parentcontact.setText(userDetails.child("guardianmobile").getValue().toString());
                        personalcontact.setText(userDetails.child("mobileno").getValue().toString());

                        //Access all data

                    }
//                            Log.d("valueName:", userDetails.child("name").getValue().toString());
//                            Log.d("valueEmail:", userDetails.child("email").getValue().toString());
//                            Log.d("valueuserid:", userDetails.child("studentid").getValue().toString());
//                            Log.d("password:", userDetails.child("password").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

  /*      btnTEST = (Button) view.findViewById(R.id.btnTEST);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1",Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }
} 