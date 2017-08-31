package com.example.mohit.tpomnnit;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2/28/2017.
 */

public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab4Fragment";
    Uri filePath;
    ImageView image;
    int PICK_IMAGE_REQUEST=111;
    Button upload,choose;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageref=storage.getReference("userimage");
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4_fragment,container,false);
        upload=(Button)view.findViewById(R.id.upload);
        image=(ImageView)view.findViewById(R.id.image);
        choose=(Button) view.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath!=null)
                {
                    StorageReference childref=storageref.child("20154076.jpg");
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

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}