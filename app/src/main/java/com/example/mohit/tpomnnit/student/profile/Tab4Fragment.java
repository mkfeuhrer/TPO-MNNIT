package com.example.mohit.tpomnnit.student.profile;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.example.mohit.tpomnnit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

/**
 * Created by User on 2/28/2017.
 */

public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab4Fragment";
    private StorageReference imageref,str;
    TextView resumeDisplay;
    Uri filePath,resumePath;
    ImageView image;
    byte[] byteimage;
    private  String registrationnum;
    int progFlag=2;
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
        progFlag=2;
        uploadresume = (Button)view.findViewById(R.id.uploadresume);
        chooseresume = (Button)view.findViewById(R.id.chooseresume);
        resumeDisplay=(TextView)view.findViewById(R.id.resumeDisplay);
        //resumeDisplay.setFocusable(false);
        resumeDisplay.setClickable(false);
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
        /*final ProgressDialog progressDialog=new ProgressDialog(MyProfile.getContextOfApplication());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Data please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();*/
        str = FirebaseStorage.getInstance().getReference("userimage/"+registrationnum+".jpg");
        final StorageReference childref=storageref1.child(registrationnum+".pdf");
        childref.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                if(bytes.length!=0)
                {
                    resumeDisplay.setTextColor(Color.BLUE);
                    resumeDisplay.setClickable(true);
                    resumeDisplay.setPaintFlags(resumeDisplay.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    resumeDisplay.setText(childref.getName());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
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
            }
        });
        resumeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!resumeDisplay.getText().equals("File Name")) {
                    File localFile = null;
                    try {
                        localFile = File.createTempFile("newresumefile", "pdf");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final File finalLocalFile = localFile;
                    final StorageReference childref=storageref1.child(registrationnum+".pdf");
                    childref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                }

                else
                {
                    Toast.makeText(MyProfile.getContextOfApplication(),"File not found",Toast.LENGTH_LONG).show();
                }
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
                    UploadTask uploadTask=childref.putBytes(byteimage);
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
                int y=(int)(15000/size);
                if(y>100)
                    y=100;
                Bitmap bitmap1=bitmap;
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
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