package com.example.mohit.tpomnnit.student.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 2/28/2017.
 */

public class Tab5Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    EditText oldpass,newpass,confirmpass;
    private String registrationnum,key,userId,pass;
    private DatabaseReference mDatabase;
    Button save;
    ValueEventListener vel;
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5_fragment,container,false);
        oldpass = (EditText) view.findViewById(R.id.oldpass);
        newpass = (EditText) view.findViewById(R.id.newpass);
        confirmpass = (EditText) view.findViewById(R.id.confirmpass);
        MyProfile myProfile = (MyProfile) getActivity();
        registrationnum = myProfile.getRegno();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        userId = mDatabase.push().getKey();
        save = (Button) view.findViewById(R.id.save);

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("studentid").getValue().toString());
                    if (registrationnum.equals(userDetails.child("studentid").getValue().toString())) {
                        pass = userDetails.child("password").getValue().toString().trim();
                        key=userDetails.getKey();
                    }
//                            Log.d("valueName:", userDetails.child("name").getValue().toString());
//                            Log.d("valueEmail:", userDetails.child("email").getValue().toString());
//                            Log.d("valueuserid:", userDetails.child("studentid").getValue().toString());
//                            Log.d("password:", userDetails.child("password").getValue().toString());
                }
                mDatabase.removeEventListener(vel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.equals(oldpass.getText().toString().trim())) {
                    if (newpass.getText().toString().trim().equals(confirmpass.getText().toString().trim())) {
                        mDatabase.child(key).child("password").setValue(newpass.getText().toString().trim());
                        oldpass.setText("");
                        newpass.setText("");
                        confirmpass.setText("");
                        Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getActivity(), "Confirm Password doesnt match", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Incorrect Old Password", Toast.LENGTH_LONG).show();
                }
                // mDatabase.child(key).child("state").setValue(state.getText().toString().trim());
            }
        });
        return view;
    }
}