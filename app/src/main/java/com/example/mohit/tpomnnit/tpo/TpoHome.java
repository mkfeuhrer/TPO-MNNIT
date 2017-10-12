package com.example.mohit.tpomnnit.tpo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.Landing;
import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.contactUs;
import com.example.mohit.tpomnnit.login_signup.TpoLogin;
import com.example.mohit.tpomnnit.student.StudentProfile;
import com.example.mohit.tpomnnit.student.profile.UserData;
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
import java.util.ArrayList;
import java.util.List;

public class TpoHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText name,regnum,branch,course,regno;
    private String registrationnum,userId;
    private DatabaseReference mDatabase;
    private StorageReference storage,imageref;
    private ImageView imageview,verified;
    String nameuser;
    Spinner spinnerbranch,spinnercourse;
    String branchselected,courseselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(TpoHome.this,AddCompany.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference("tpouserdata");
        userId=mDatabase.push().getKey();
        name=(EditText)findViewById(R.id.name);
        regnum=(EditText)findViewById(R.id.regnum);
        branch=(EditText)findViewById(R.id.branch);
        course=(EditText)findViewById(R.id.course);
        verified=(ImageView)findViewById(R.id.verified);
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
                Toast.makeText(TpoHome.this,"Image not found",Toast.LENGTH_LONG).show();
            }
        });


        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user= dataSnapshot.getValue(UserData.class);
                for(DataSnapshot userDetails : dataSnapshot.getChildren()) {

                    if(registrationnum.equals(userDetails.child("regno").getValue().toString()))
                    {
                        name.setText(userDetails.child("name").getValue().toString());
                        View h1 = navigationView.getHeaderView(0);
                        TextView nav_user = h1.findViewById(R.id.name);
                        TextView nav_email = h1.findViewById(R.id.email);
                        nav_user.setText( "\t  "+userDetails.child("name").getValue().toString());
                        nav_email.setText("\t  "+userDetails.child("email").getValue().toString());
                        /*View h1 = navigationView.getHeaderView(0);
                        TextView nav_user = h1.findViewById(R.id.name);
                        TextView nav_email = h1.findViewById(R.id.email);
                        //Toast.makeText(StudentProfile.this,""+userDetails.child("name").getValue().toString(),Toast.LENGTH_LONG).show();
                        nav_user.setText( "\t  "+userDetails.child("name").getValue().toString());
                        nav_email.setText("\t  "+userDetails.child("email").getValue().toString());*/
                        course.setText(userDetails.child("course").getValue().toString());
                        branch.setText(userDetails.child("branch").getValue().toString());
                        regnum.setText(registrationnum);
                        /*int val=Integer.parseInt(userDetails.child("verified").getValue().toString());
                        if(val==1)
                        {
                            //Drawable d=R.drawable.tick;
                            verified.setImageResource(R.drawable.tick);
                        }*/
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
        mDatabase.addListenerForSingleValueEvent(vel);

    }

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(TpoHome.this,Landing.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tpo_home, menu);
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
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.verifyuser) {
            Intent i = new Intent(TpoHome.this,VerifyUser.class);
            i.putExtra("flag",0);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.manage_student) {
            final Dialog dialog = new Dialog(TpoHome.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_student_filter);
            dialog.setCanceledOnTouchOutside(true);
            dialog.onBackPressed();
            spinnerbranch=(Spinner)dialog.findViewById(R.id.spinnerbranch);
            spinnercourse=(Spinner)dialog.findViewById(R.id.spinnercourse);
            regno=(EditText) dialog.findViewById(R.id.regno);
            Button find=(Button)dialog.findViewById(R.id.find);
            branchspinner();
            coursespinner();
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent intent=new Intent(TpoHome.this,ManageStudents.class);
                    intent.putExtra("flag",1);
                    intent.putExtra("course",courseselected);
                    intent.putExtra("branch",branchselected);
                    intent.putExtra("regno",regno.getText().toString().trim());
                    startActivity(intent);
                }
            });
            dialog.show();

            //Intent i = new Intent(TpoHome.this,StudentFilter.class);

            //startActivity(i);

        } else if (id == R.id.update_company) {
            Intent intent=new Intent(TpoHome.this,UpdateCompany.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent i = new Intent(TpoHome.this,contactUs.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.logout){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logging Off")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null);
                            data.execSQL("drop table if exists tpo");
                            Intent i = new Intent(TpoHome.this, TpoLogin.class);
                            startActivity(i);
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void branchspinner()
    {

        // Spinner click listener
        List<String> branches = new ArrayList<String>();
        branches.add("ALL");
        branches.add("CSE");
        branches.add("IT");
        branches.add("ECE");
        branches.add("EE");
        branches.add("ME");
        branches.add("PIE");
        branches.add("CHE");
        branches.add("BIO");
        branches.add("CIVIL");
        branches.add("MCA");

        ArrayAdapter<String> dataAdapterbranch;
        dataAdapterbranch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,branches);

        // Drop down layout style - list view with radio button
        dataAdapterbranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerbranch.setAdapter(dataAdapterbranch);

        spinnerbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                branchselected = parent.getItemAtPosition(position).toString();
                if(branchselected.equals("ALL"))
                    branchselected="";
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void coursespinner()
    {

        // Spinner click listener
        List<String> courses = new ArrayList<String>();
        courses.add("ALL");
        courses.add("BTech");
        courses.add("MTech");
        courses.add("MCA");
        courses.add("PhD");
        courses.add("MBA");

        ArrayAdapter<String> dataAdapterbranch;
        dataAdapterbranch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,courses);

        // Drop down layout style - list view with radio button
        dataAdapterbranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnercourse.setAdapter(dataAdapterbranch);

        spinnercourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                courseselected = parent.getItemAtPosition(position).toString();
                if(courseselected.equals("ALL"))
                    courseselected="";
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
}
