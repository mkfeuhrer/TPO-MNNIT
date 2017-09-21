package com.example.mohit.tpomnnit.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.student.company.CompanyStudent;
import com.example.mohit.tpomnnit.student.profile.MyProfile;
import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.example.mohit.tpomnnit.login_signup.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class StudentProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText name,regnum,branch,tpocredit,company;
    private String registrationnum,userId;
    private DatabaseReference mDatabase;
    private StorageReference storage,imageref;
    private ImageView imageview,verified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId=mDatabase.push().getKey();
        name=(EditText)findViewById(R.id.name);
        regnum=(EditText)findViewById(R.id.regnum);
        branch=(EditText)findViewById(R.id.branch);
        verified=(ImageView)findViewById(R.id.verified);
        tpocredit=(EditText)findViewById(R.id.tpocredit);
        company=(EditText)findViewById(R.id.company);
        imageview = (ImageView)findViewById(R.id.imageView3);
        registrationnum = getIntent().getStringExtra("reg");
//        Log.e("reg",registrationnum);

        storage = FirebaseStorage.getInstance().getReference("userimage/"+registrationnum+".jpg");

        imageref = storage;
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        imageref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Toast.makeText(getApplicationContext(),"File Download",Toast.LENGTH_LONG);
                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                imageview.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user= dataSnapshot.getValue(UserData.class);
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {

                    if(registrationnum.equals(userDetails.child("regnum").getValue().toString()))
                    {
                        name.setText(userDetails.child("name").getValue().toString());
                        branch.setText(userDetails.child("branch").getValue().toString());
                        regnum.setText(registrationnum);
                        tpocredit.setText(userDetails.child("tpocredit").getValue().toString());
                        company.setText(userDetails.child("company").getValue().toString());
                        int val=Integer.parseInt(userDetails.child("isverified").getValue().toString());
                        if(val==1)
                        {
                            //Drawable d=R.drawable.tick;
                            verified.setImageResource(R.drawable.tick);
                        }
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



    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.studentprofile) {
          //  finish();
           /* Intent i = new Intent(StudentProfile.this,StudentProfile.class);
            startActivity(i);*/
            // Handle the camera action
        } else if (id == R.id.myprofile) {
          //  finish();
            Intent i = new Intent(StudentProfile.this,MyProfile.class);
            i.putExtra("reg",regnum.getText().toString().trim());
            startActivity(i);

        } else if (id == R.id.companies) {
            Intent i = new Intent(StudentProfile.this, CompanyStudent.class);
            startActivity(i);

        } else if (id == R.id.interviewexperience) {

        } else if (id == R.id.contact) {

        } else if (id == R.id.rate) {

        } else if( id == R.id.logout) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Logged","false");
            editor.apply();
            Intent i = new Intent(StudentProfile.this, Login.class);

            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
