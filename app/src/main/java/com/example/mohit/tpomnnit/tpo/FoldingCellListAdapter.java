package com.example.mohit.tpomnnit.tpo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ramotion.foldingcell.FoldingCell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<UserData> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private View.OnClickListener defaultVerifyClickListener;
    private StorageReference storage,imageref;
    int flg;

    public FoldingCellListAdapter(Context context, List<UserData> objects,int flag) {
        super(context, 0, objects);
        flg=flag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        UserData item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            viewHolder.dp=(ImageView)cell.findViewById(R.id.dp);
            viewHolder.name = (TextView) cell.findViewById(R.id.name);
            viewHolder.regno1 = (TextView) cell.findViewById(R.id.regno1);
            viewHolder.regno = (TextView) cell.findViewById(R.id.location);
            viewHolder.branch = (TextView) cell.findViewById(R.id.branch);
            viewHolder.course = (TextView) cell.findViewById(R.id.course);
            viewHolder.year = (TextView) cell.findViewById(R.id.year);
            viewHolder.email=(TextView)cell.findViewById(R.id.email);
            viewHolder.dob = (TextView) cell.findViewById(R.id.dob);
            viewHolder.linkedinid = (TextView) cell.findViewById(R.id.linkedinid);
            viewHolder.skypeid = (TextView) cell.findViewById(R.id.skypeid);
            viewHolder.gender = (TextView) cell.findViewById(R.id.gender);
            viewHolder.category = (TextView) cell.findViewById(R.id.category);
            viewHolder.pwd = (TextView) cell.findViewById(R.id.pwd);
            viewHolder.presentaddr = (TextView) cell.findViewById(R.id.presentaddress);
            viewHolder.permanentaddr = (TextView) cell.findViewById(R.id.permanentaddress);
            viewHolder.personalcontact = (TextView) cell.findViewById(R.id.personalcontact);
            viewHolder.parentcontact = (TextView) cell.findViewById(R.id.parentcontact);
            viewHolder.project = (TextView) cell.findViewById(R.id.project);
            viewHolder.internship = (TextView) cell.findViewById(R.id.internship);
            viewHolder.button = (TextView) cell.findViewById(R.id.content_request_btn);
            viewHolder.verify = (TextView) cell.findViewById(R.id.verify);
            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder

        /*if(viewHolder.name.getText().equals("")){


        }*/
       /* if(!item.getName().equals("")) {
            Character t = item.getName().charAt(0);
            String str = t.toString();
            viewHolder.nameini.setText(str);
        }*/
        viewHolder.name.setText(item.getName());
        viewHolder.regno.setText(item.getRegnum());
        viewHolder.regno1.setText(item.getRegnum());
        viewHolder.year.setText(item.getBatch());
        viewHolder.branch.setText(item.getBranch());
        viewHolder.course.setText(item.getCourse());
        viewHolder.email.setText(item.getEmail());
        viewHolder.dob.setText(item.getDob());
        viewHolder.linkedinid.setText(item.getLinkedinid());
        viewHolder.skypeid.setText(item.getSkypeid());
        viewHolder.gender.setText(item.getGender());
        viewHolder.category.setText(item.getCategory());
        viewHolder.pwd.setText(item.getPhychal());
        viewHolder.presentaddr.setText(item.getPresentadd());
        viewHolder.permanentaddr.setText(item.getPermanentadd());
        viewHolder.personalcontact.setText(item.getMobileno());
        viewHolder.parentcontact.setText(item.getGuardianmobile());
        //viewHolder.project.setText(item.getProject());
        //viewHolder.internship.setText(item.getInternship());
        if(flg==1)
            viewHolder.verify.setText("Manage User");
       /* if(viewHolder.button.isFocused())
        {
            System.out.println("Clicked at "+item.getRegnum());
        }*/
        //viewHolder.pwd.setText(item.getCourse());

        if(!item.getRegnum().equals(""))
        {
            storage = FirebaseStorage.getInstance().getReference("userimage/"+item.getRegnum()+".jpg");
            imageref = storage;
            File localFile = null;
            try {
                localFile = File.createTempFile("images","jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            final File finalLocalFile = localFile;
            imageref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    //Toast.makeText(getApplicationContext(),"File Download",Toast.LENGTH_LONG);
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    viewHolder.dp.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        // set custom btn handler for list item from that item
        /*if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {*/
        // (optionally) add "default" handler if no handler found in item
        viewHolder.button.setOnClickListener(defaultRequestBtnClickListener);
        viewHolder.verify.setOnClickListener(defaultVerifyClickListener);
        //}
        return cell;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultVerifyClickListener(){
        return defaultVerifyClickListener;
    }
    public void setDefaultVerifyClickListener(View.OnClickListener defaultVerifyClickListener){
        this.defaultVerifyClickListener=defaultVerifyClickListener;
    }
    public View.OnClickListener getDefaultRequestBtnClickListener() {
        System.out.println("Clicked at 1 ");
        return defaultRequestBtnClickListener;
    }
    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        System.out.println("Clicked at 2");
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView dp;
        TextView name,regno,regno1,branch,course,year,nameini,
                dob,email,linkedinid,skypeid,gender,category,pwd,presentaddr,permanentaddr,
                personalcontact,parentcontact,project,internship,button,verify;

    }
}
