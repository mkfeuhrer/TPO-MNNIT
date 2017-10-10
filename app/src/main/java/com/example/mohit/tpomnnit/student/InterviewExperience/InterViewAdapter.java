package com.example.mohit.tpomnnit.student.InterviewExperience;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mohit.tpomnnit.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class InterViewAdapter extends ArrayAdapter<Interview> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public InterViewAdapter(Context context, List<Interview> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        Interview item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.interview_cell, parent, false);
            // binding view parts to view holder
            viewHolder.company = (TextView) cell.findViewById(R.id.company);
            viewHolder.company1 = (TextView) cell.findViewById(R.id.company1);
            viewHolder.name = (TextView) cell.findViewById(R.id.name);
            viewHolder.profile = (TextView) cell.findViewById(R.id.profile);
            viewHolder.profil1 = (TextView) cell.findViewById(R.id.profile1);
            viewHolder.date = (TextView) cell.findViewById(R.id.date);
            viewHolder.year = (TextView) cell.findViewById(R.id.year);
            viewHolder.experience = (TextView) cell.findViewById(R.id.experience);
            viewHolder.nameini = (TextView) cell.findViewById(R.id.nameini);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.company.setText(item.getCompany());
        viewHolder.company1.setText(item.getCompany());
        viewHolder.name.setText(item.getName());
        viewHolder.profile.setText(item.getProfile());
        viewHolder.profil1.setText(item.getProfile());
        viewHolder.date.setText(item.getDate());
        viewHolder.year.setText(item.getYear());
        viewHolder.experience.setText(item.getExperience());
        if(!item.getCompany().equals("")) {
            Character t = item.getCompany().charAt(0);
            String str = t.toString();
            viewHolder.nameini.setText(str);
        }

        // set custom btn handler for list item from that item
        /*if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {*/
        // (optionally) add "default" handler if no handler found in item
        //viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        //}


        return cell;
    }

    // simple methods for register cell state changes
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
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView company,name,profile,year,date,experience,profil1,company1,nameini;
    }
}
