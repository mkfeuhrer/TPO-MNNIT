package com.example.mohit.tpomnnit.student.profile;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

import com.example.mohit.tpomnnit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2/28/2017.
 */

public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab4Fragment";
    private StorageReference imageref,str;
    Uri filePath,resumePath;
    ImageView image;
    private  String registrationnum;
    int PICK_IMAGE_REQUEST=111;
    Button upload,choose,uploadresume,chooseresume;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageref=storage.getReference("userimage");
    StorageReference  islandRef = storageref.child("image.jpg");
    StorageReference storageref1=storage.getReference("userresume");


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4_fragment,container,false);
        upload=(Button)view.findViewById(R.id.upload);
        image=(ImageView)view.findViewById(R.id.image);
        choose=(Button) view.findViewById(R.id.choose);
        uploadresume = (Button)view.findViewById(R.id.uploadresume);
        chooseresume = (Button)view.findViewById(R.id.chooseresume);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
            }
        });
        MyProfile myProfile = (MyProfile) getActivity();
        registrationnum = myProfile.getRegno();

        str = FirebaseStorage.getInstance().getReference("userimage/"+registrationnum+".jpg");

        imageref = str;
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
                Toast.makeText(getActivity(),"File Download",Toast.LENGTH_LONG);
                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath!=null)
                {
                    String regnum ;
                    MyProfile obj = (MyProfile)getActivity();
                    regnum = obj.getRegno();
                    StorageReference childref=storageref.child(regnum+".jpg");
                    UploadTask uploadTask=childref.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Context applicationContext = MyProfile.getContextOfApplication();
                            Toast.makeText(applicationContext, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Context applicationContext = MyProfile.getContextOfApplication();
                            Toast.makeText(applicationContext, "Upload fail -> "+e, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Context applicationContext = MyProfile.getContextOfApplication();
                    Toast.makeText(applicationContext, "Select an image", Toast.LENGTH_LONG).show();
                }
            }
        });
        chooseresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Resume"),PICK_IMAGE_REQUEST);
            }
        });
        uploadresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath!=null)
                {
                    String regnum ;
                    MyProfile obj = (MyProfile)getActivity();
                    regnum = obj.getRegno();
                    StorageReference childref=storageref1.child(regnum+".pdf");
                    UploadTask uploadTask=childref.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Context applicationContext = MyProfile.getContextOfApplication();
                            Toast.makeText(applicationContext, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Context applicationContext = MyProfile.getContextOfApplication();
                            Toast.makeText(applicationContext, "Upload fail -> "+e, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Context applicationContext = MyProfile.getContextOfApplication();
                    Toast.makeText(applicationContext, "Select an resume", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //getting image from gallery
                //ContentResolver cr=getApplicationCon;
                Context applicationContext = MyProfile.getContextOfApplication();
                ContentResolver cr=applicationContext.getContentResolver();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr,filePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] imageInByte = stream.toByteArray();
                long size = imageInByte.length;
                size/=2000;
                int y=(int)(1000/size);
                Bitmap bitmap1=bitmap;
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, y,stream1);
                byte[] imageInByte1=stream1.toByteArray();
                size=imageInByte1.length;
                size/=2000;
                System.out.println("image size "+size);
                image.setImageBitmap(bitmap1);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}