package com.example.mohit.tpomnnit.login_signup;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.MyProfile;
import com.example.mohit.tpomnnit.student.profile.Users;
import com.example.mohit.tpomnnit.tpo.TpoUserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.mohit.tpomnnit.R.id.choose;
import static com.example.mohit.tpomnnit.R.id.email;
import static com.example.mohit.tpomnnit.R.id.username;

public class TpoSignup extends AppCompatActivity {
    private Button register,chooseImage;
    private EditText year,password,regnum,name,confirmpassword,mobile,email;
    private TextView registered;
    private DatabaseReference mDatabase;
    private StorageReference imageref,str;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageref=storage.getReference("userimage");
    StorageReference  islandRef = storageref.child("image.jpg");
    private String userId,branchselected,courseselected;
    int PICK_IMAGE_REQUEST=111;
    Spinner spinnerbranch,spinnercourse;
    Uri filePath;
    ProgressDialog progressDialog;
    ImageView image;
    byte[] byteimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_signup);
        mDatabase=FirebaseDatabase.getInstance().getReference("tpouserdata");
        userId     = mDatabase.push().getKey();
        register   = (Button)findViewById(R.id.register);
        regnum     = (EditText)findViewById(R.id.regnum);
        name       = (EditText)findViewById(R.id.name);
        year       = (EditText)findViewById(R.id.year);
        email=(EditText)findViewById(R.id.email);
        password   = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        mobile     = (EditText)findViewById(R.id.mobile);
        spinnerbranch=(Spinner)findViewById(R.id.spinnerbranch);
        spinnercourse=(Spinner)findViewById(R.id.spinnercourse);
        image=(ImageView)findViewById(R.id.image);
        chooseImage=(Button)findViewById(R.id.upload);
        branchspinner();
        coursespinner();
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String s1 = regnum.getText().toString();
                final String s2 = password.getText().toString().trim();
                final String nam = name.getText().toString().trim();
                final String confirmpass = confirmpassword.getText().toString().trim();
                final String mob  = mobile.getText().toString().trim();
                final String years = year.getText().toString().trim();
                final String email1=email.getText().toString().trim();

                if(confirmpass.length() == 0 || mob.length() == 0 || years.length() == 0 || s1.length() == 0 || s2.length() == 0 || nam.length() == 0 || email1.length() == 0 )
                {
                    Toast.makeText(TpoSignup.this,"Fill all entries",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(!s2.equals(confirmpass))
                    {
                        Toast.makeText(TpoSignup.this,"Password not matched",Toast.LENGTH_LONG).show();
                    }
                    else if(filePath==null)
                    {
                        Toast.makeText(TpoSignup.this,"Please select an image",Toast.LENGTH_LONG).show();;
                    }
                    else
                    {
                        progressDialog=new ProgressDialog(TpoSignup.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Registering user please wait");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        StorageReference childref=storageref.child(s1+".jpg");
                        UploadTask uploadTask=childref.putBytes(byteimage);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(TpoSignup.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                createUser(nam, s1, branchselected, courseselected, years, email1, mob, s2);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TpoSignup.this, "Upload fail -> "+e, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });

                    }
                }
            }
        });
    }
    private void createUser(String name, String regnum,String branch,String course,String year,String email,String mobile,String pass) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
       /* if (TextUtils.isEmpty(userid)) {
            userId = mDatabase.push().getKey();
        }*/
        TpoUserData user = new TpoUserData();
        user.setName(name);
        user.setRegno(regnum);
        user.setBranch(branch);
        user.setCourse(course);
        user.setYear(year);
        user.setEmail(email);
        user.setMobileno(mobile);
        user.setPassword(pass);
        addUserChangeListener(user);
    }
    private void addUserChangeListener(final TpoUserData user) {
        // User data change listener
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TpoUserData user1 = user;
                // Check for null
                if (user1 == null) {
                    return;
                }
                final String userId = mDatabase.push().getKey();
                mDatabase.child(userId).setValue(user1);
                progressDialog.dismiss();
                Intent i = new Intent(TpoSignup.this, TpoLogin.class);
                startActivity(i);
                finish();
              //  Log.e(TAG, "User data is changed!" + user1.name + ", " + user1.year);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                progressDialog.dismiss();
                //Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
    private void branchspinner()
    {

        // Spinner click listener
        List<String> branches = new ArrayList<String>();
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
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //getting image from gallery
                //ContentResolver cr=getApplicationCon;
                ContentResolver cr=this.getContentResolver();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr,filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] imageInByte = stream.toByteArray();
                long size = imageInByte.length;
                size/=2000;
                int y=(int)(15000/size);
                Bitmap bitmap1=bitmap;
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                if(y>100)
                    y=100;
                bitmap1.compress(Bitmap.CompressFormat.JPEG, y,stream1);
                byteimage=stream1.toByteArray();
                size=byteimage.length;
                size/=2000;
                System.out.println("image size "+size);
                image.setImageBitmap(bitmap1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
