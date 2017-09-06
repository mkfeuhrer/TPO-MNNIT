package com.example.mohit.tpomnnit;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    EditText project,internship;
    private String registrationnum,key,userId;
    private DatabaseReference mDatabase;
    Button save;
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        project = (EditText) view.findViewById(R.id.project);
        internship = (EditText) view.findViewById(R.id.intern);
        MyProfile myProfile = (MyProfile) getActivity();
        registrationnum = myProfile.getRegno();
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        save = (Button) view.findViewById(R.id.save);

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (registrationnum.equals(userDetails.child("regnum").getValue().toString())) {
                        System.out.println("in in in ini ni nin in in ");
                        project.setText(userDetails.child("project").getValue().toString());
                        internship.setText(userDetails.child("internship").getValue().toString());
                        key=userDetails.getKey();
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(key).child("project").setValue(project.getText().toString().trim());
                mDatabase.child(key).child("internship").setValue(internship.getText().toString().trim());
                // mDatabase.child(key).child("state").setValue(state.getText().toString().trim());
            }
        });
        return view;
    }
}