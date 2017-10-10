package com.example.mohit.tpomnnit.student.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 2/28/2017.
 */

public class    Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    public EditText school10,year10,board10,percentage10,school12,year12,board12,percentage12;
    public EditText spi1,spi2,spi3,spi4,spi5,spi6,spi7,spi8,cpi;
    public String project,internship,company;
    private Button btnTEST;
    private String registrationnum,userId,key;
    Button save;
    ValueEventListener vel;
    private DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        school10 = (EditText) view.findViewById(R.id.school10);
        board10 = (EditText) view.findViewById(R.id.board10);
        year10 = (EditText) view.findViewById(R.id.year10);
        percentage10 = (EditText) view.findViewById(R.id.percentage10);
        school12 = (EditText) view.findViewById(R.id.school12);
        board12 = (EditText) view.findViewById(R.id.board12);
        year12 = (EditText) view.findViewById(R.id.year12);
        percentage12 = (EditText) view.findViewById(R.id.percentage12);
        spi1 = (EditText) view.findViewById(R.id.spi1);
        spi2 = (EditText) view.findViewById(R.id.spi2);
        spi3 = (EditText) view.findViewById(R.id.spi3);
        spi4 = (EditText) view.findViewById(R.id.spi4);
        spi5 = (EditText) view.findViewById(R.id.spi5);
        spi6 = (EditText) view.findViewById(R.id.spi6);
        spi7 = (EditText) view.findViewById(R.id.spi7);
        spi8 = (EditText) view.findViewById(R.id.spi8);
        cpi = (EditText) view.findViewById(R.id.cpi);
        save=(Button)view.findViewById(R.id.save);
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        MyProfile myProfile = (MyProfile) getActivity();
        registrationnum = myProfile.getRegno();
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (registrationnum.equals(userDetails.child("regnum").getValue().toString())) {
                        System.out.println("in in in ini ni nin in in ");
                        school10.setText(userDetails.child("school10").getValue().toString());
                        board10.setText(userDetails.child("board10").getValue().toString());
                        year10.setText(userDetails.child("year10").getValue().toString());
                        percentage10.setText(userDetails.child("percentage10").getValue().toString());
                        school12.setText(userDetails.child("school12").getValue().toString());
                        board12.setText(userDetails.child("board12").getValue().toString());
                        year12.setText(userDetails.child("year12").getValue().toString());
                        percentage12.setText(userDetails.child("percentage12").getValue().toString());
                        spi1.setText(userDetails.child("spi1").getValue().toString());
                        spi2.setText(userDetails.child("spi2").getValue().toString());
                        spi3.setText(userDetails.child("spi3").getValue().toString());
                        spi4.setText(userDetails.child("spi4").getValue().toString());
                        spi5.setText(userDetails.child("spi5").getValue().toString());
                        spi6.setText(userDetails.child("spi6").getValue().toString());
                        spi7.setText(userDetails.child("spi7").getValue().toString());
                        spi8.setText(userDetails.child("spi8").getValue().toString());
                        key=userDetails.getKey();
                        //Access all data

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

  /*      btnTEST = (Button) view.findViewById(R.id.btnTEST);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1",Toast.LENGTH_SHORT).show();
            }
        });*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(key).child("school10").setValue(school10.getText().toString().trim());
                mDatabase.child(key).child("board10").setValue(board10.getText().toString().trim());
                mDatabase.child(key).child("year10").setValue(year10.getText().toString().trim());
                mDatabase.child(key).child("percentage10").setValue(percentage10.getText().toString().trim());
                mDatabase.child(key).child("school12").setValue(school12.getText().toString().trim());
                mDatabase.child(key).child("board12").setValue(board12.getText().toString().trim());
                mDatabase.child(key).child("year12").setValue(year12.getText().toString().trim());
                mDatabase.child(key).child("percentage12").setValue(percentage12.getText().toString().trim());
                mDatabase.child(key).child("spi1").setValue(spi1.getText().toString().trim());
                mDatabase.child(key).child("spi2").setValue(spi2.getText().toString().trim());
                mDatabase.child(key).child("spi3").setValue(spi3.getText().toString().trim());
                mDatabase.child(key).child("spi4").setValue(spi4.getText().toString().trim());
                mDatabase.child(key).child("spi5").setValue(spi5.getText().toString().trim());
                mDatabase.child(key).child("spi6").setValue(spi6.getText().toString().trim());
                mDatabase.child(key).child("spi7").setValue(spi7.getText().toString().trim());
                mDatabase.child(key).child("spi8").setValue(spi8.getText().toString().trim());
                // mDatabase.child(key).child("state").setValue(state.getText().toString().trim());
            }
        });
        return view;
    }
}