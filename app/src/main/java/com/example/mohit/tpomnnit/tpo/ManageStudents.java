package com.example.mohit.tpomnnit.tpo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.MyProfile;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class ManageStudents extends AppCompatActivity {
    List<UserData> userList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String userId,reg,key;
    String regno,branch,course;
    static String currsel="null";
    static int adapterFlag=0;
    int flag;
    ValueEventListener vel,vel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        mDatabase=FirebaseDatabase.getInstance().getReference("userdata");
        userId=mDatabase.push().getKey();
        userList=new ArrayList<>();
        regno = getIntent().getStringExtra("regno");
        branch = getIntent().getStringExtra("branch");
        course = getIntent().getStringExtra("course");
        prepareData();

    }
    void prepareData()
    {
        System.out.println("in prepare data");
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
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
                    userData.setIsverified(Integer.parseInt(userDetails.child("isverified").getValue().toString()));
                    //userData.getName()
                    //Companies companies=dataSnapshot.getValue(Companies.class);

                    if ((course.equals("") || course.equals(userData.getCourse())) && (branch.equals("") || branch.equals(userData.getBranch())) && (regno.equals("") || regno.equals(userData.getRegnum())))
                        userList.add(userData);
                    //System.out.println("in"+ userDetails.child("name").getValue().toString()+" : "+companies.getCtc()+" : "+companies.getLocation());
                }
                addUsers();
                mDatabase.removeEventListener(vel);

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

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, userList);

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageStudents.this, MyProfile.class);
                intent.putExtra("reg",currsel);
                //Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS "+currsel, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        adapter.setDefaultVerifyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ManageStudentDialog alertDialoge = new ManageStudentDialog();
                alertDialoge.showDialog(VerifyUser.this, "Register");*/
                    final Dialog dialog = new Dialog(ManageStudents.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_manage_student);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.onBackPressed();
                    Button deducttpo=(Button)dialog.findViewById(R.id.deducttpo);
                    Button deleteuser=(Button)dialog.findViewById(R.id.deleteaccount);
                    Button addcompany=(Button)dialog.findViewById(R.id.addcompany);
                    Button blockaccount=(Button)dialog.findViewById(R.id.blockaccount);
                    deducttpo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    deleteuser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    addcompany.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    blockaccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(ManageStudents.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Block " + currsel)
                                    .setMessage("Are you sure you want to block user?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            blockUser(currsel);
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();

                        }
                    });
                    dialog.show();
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

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

    void deductcredit(int n)
    {

    }
    void deleteUser(String regno)
    {
        reg=regno;
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (reg.equals(userDetails.child("regnum").getValue().toString())) {
                        key=userDetails.getKey();
                    }
                }
                mDatabase.child(key).child("isverified").setValue(0);
                //System.out.println("verified");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);

    }
    void blockUser(final String regno)
    {
        reg=regno;
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        vel1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (reg.equals(userDetails.child("regnum").getValue().toString())) {
                        key=userDetails.getKey();
                    }
                }
                mDatabase.child(key).child("isverified").setValue(0);
                Toast.makeText(getApplicationContext(), regno+" Blocked", Toast.LENGTH_SHORT).show();
                mDatabase.removeEventListener(vel1);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel1);

    }
}
