package com.example.mohit.tpomnnit.student.InterviewExperience;

/**
 * Created by mohit on 23/9/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.tpomnnit.R;

import java.util.List;

public class InterViewAdapter extends RecyclerView.Adapter<com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder> {
    private List<Interview> interviewList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,company,date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textName);
            company = (TextView) view.findViewById(R.id.textCompany);
            date = (TextView) view.findViewById(R.id.textDate);
        }
    }
    public InterViewAdapter(List<Interview> interviewList)
    {
        this.interviewList = interviewList;
    }
    @Override
    public com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.interview_row,parent,false);
        return new com.example.mohit.tpomnnit.student.InterviewExperience.InterViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Interview ques=interviewList.get(position);
        holder.name.setText(ques.getName());
        holder.company.setText(ques.getCompany());
        holder.date.setText(ques.getDate());
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }
}

