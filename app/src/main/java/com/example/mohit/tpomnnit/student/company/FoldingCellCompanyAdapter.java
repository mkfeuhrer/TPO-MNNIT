package com.example.mohit.tpomnnit.student.company;

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
public class FoldingCellCompanyAdapter extends ArrayAdapter<Companies> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private StorageReference storage,imageref;

    public FoldingCellCompanyAdapter(Context context, List<Companies> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        Companies item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.company_cell, parent, false);
            viewHolder.name = (TextView) cell.findViewById(R.id.name);
            viewHolder.name1 = (TextView) cell.findViewById(R.id.name1);
            viewHolder.location = (TextView) cell.findViewById(R.id.location);
            viewHolder.nameini = (TextView) cell.findViewById(R.id.nameini);
            viewHolder.location1 = (TextView) cell.findViewById(R.id.location1);
            viewHolder.year = (TextView) cell.findViewById(R.id.year);
            viewHolder.ctc=(TextView)cell.findViewById(R.id.ctc);
            viewHolder.profile = (TextView) cell.findViewById(R.id.profile);
            viewHolder.profile1 = (TextView) cell.findViewById(R.id.profile1);
            viewHolder.ppo = (TextView) cell.findViewById(R.id.ppo);
            viewHolder.deadline = (TextView) cell.findViewById(R.id.deadline);
            viewHolder.deadline1 = (TextView) cell.findViewById(R.id.deadline1);
            viewHolder.link = (TextView) cell.findViewById(R.id.link);
            viewHolder.note = (TextView) cell.findViewById(R.id.note);
            viewHolder.updates = (TextView) cell.findViewById(R.id.updates);
            viewHolder.cpi = (TextView) cell.findViewById(R.id.cpi);
            viewHolder.class10 = (TextView) cell.findViewById(R.id.class10);
            viewHolder.class12 = (TextView) cell.findViewById(R.id.class12);
            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if(!item.getName().equals("")) {
            Character t = item.getName().charAt(0);
            String str = t.toString();
            viewHolder.nameini.setText(str);
        }
        viewHolder.name.setText(item.getName());
        viewHolder.name1.setText(item.getName());
        viewHolder.location.setText(item.getLocation());
        viewHolder.location1.setText(item.getLocation());
        viewHolder.year.setText(item.getYear());
        viewHolder.ctc.setText(item.getCtc());
        viewHolder.profile.setText(item.getProfile());
        viewHolder.profile1.setText(item.getProfile());
        viewHolder.ppo.setText(item.getPpo());
        viewHolder.deadline.setText(item.getDeadline());
        viewHolder.deadline1.setText(item.getDeadline());
        //viewHolder.link.setText(item.getLink());
        /*viewHolder.note.setText(item.getPermanentadd());
        viewHolder.updates.setText(item.getMobileno());
        viewHolder.class10.setText(item.getGuardianmobile());
        viewHolder.class12.setText(item.getProject());
        viewHolder.cpi.setText(item.);*/
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
       // ImageView dp;
        TextView name,name1,ctc,location,location1,profile,profile1,year,
               ppo,companyid,deadline,deadline1,link,nameini,note,updates,cpi,class10,class12;

    }
}
